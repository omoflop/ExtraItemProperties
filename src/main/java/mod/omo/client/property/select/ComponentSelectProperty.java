package mod.omo.client.property.select;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.model.SelectItemModel;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public record ComponentSelectProperty<T>(ComponentType<T> componentType) implements SelectProperty<T> {
    private static final SelectProperty.Type<? extends ComponentSelectProperty<?>, ?> TYPE = createType();

    public ComponentSelectProperty(ComponentType<T> componentType) {
        this.componentType = componentType;
    }

    private static <T> SelectProperty.Type<ComponentSelectProperty<T>, T> createType() {
        Codec<? extends ComponentType<?>> codec = Registries.DATA_COMPONENT_TYPE.getCodec().validate((componentType) -> {
            return componentType.shouldSkipSerialization() ? DataResult.error(() -> {
                return "Component can't be serialized";
            }) : DataResult.success(componentType);
        });
        Codec<ComponentType<T>> codec2 = (Codec<ComponentType<T>>) codec;
        MapCodec<SelectItemModel.UnbakedSwitch<ComponentSelectProperty<T>, T>> mapCodec = codec2.dispatchMap("component", ComponentSelectProperty::type, (ComponentType<T> componentType) -> {
            return createCaseListCodec(componentType.getCodecOrThrow()).xmap((cases) -> {
                return new SelectItemModel.UnbakedSwitch<>(new ComponentSelectProperty<>(componentType), cases);
            }, SelectItemModel.UnbakedSwitch::cases);
        });
        return new SelectProperty.Type<>(mapCodec);
    }

    public static <T> SelectProperty.Type<ComponentSelectProperty<T>, T> getTypeInstance() {
        return (Type<ComponentSelectProperty<T>, T>) TYPE;
    }

    private static <T> ComponentType<T> type(SelectItemModel.UnbakedSwitch<ComponentSelectProperty<T>, T> unbakedSwitch) {
        return unbakedSwitch.property().componentType;
    }

    @Nullable
    public T getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ModelTransformationMode modelTransformationMode) {
        return stack.get(this.componentType);
    }

    public SelectProperty.Type<ComponentSelectProperty<T>, T> getType() {
        return getTypeInstance();
    }

    public Codec<T> valueCodec() {
        return this.componentType.getCodecOrThrow();
    }

    public ComponentType<T> componentType() {
        return this.componentType;
    }


    private static <T>MapCodec<List<SelectItemModel.SwitchCase<T>>> createCaseListCodec(Codec<T> conditionCodec) {
        return SelectItemModel.SwitchCase.createCodec(conditionCodec).listOf().validate(ComponentSelectProperty::validateCases).fieldOf("cases");
    }

    private static <T> DataResult<List<SelectItemModel.SwitchCase<T>>> validateCases(List<SelectItemModel.SwitchCase<T>> cases) {
        if (cases.isEmpty()) {
            return DataResult.error(() -> {
                return "Empty case list";
            });
        } else {
            Multiset<T> multiset = HashMultiset.create();
            Iterator var2 = cases.iterator();

            while(var2.hasNext()) {
                SelectItemModel.SwitchCase<T> switchCase = (SelectItemModel.SwitchCase)var2.next();
                multiset.addAll(switchCase.values());
            }

            return multiset.size() != multiset.entrySet().size() ? DataResult.error(() -> {
                Stream var10000 = multiset.entrySet().stream().filter((entry) -> {
                    return entry.getCount() > 1;
                }).map((entry) -> {
                    return entry.getElement().toString();
                });
                return "Duplicate case conditions: " + (String)var10000.collect(Collectors.joining(", "));
            }) : DataResult.success(cases);
        }
    }
}
