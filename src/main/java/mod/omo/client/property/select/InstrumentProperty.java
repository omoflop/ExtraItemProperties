package mod.omo.client.property.select;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Instrument;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record InstrumentProperty() implements SelectProperty<String> {
    public static final SelectProperty.Type<InstrumentProperty, String> TYPE;


    @Nullable
    public String getValue(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i, ModelTransformationMode modelTransformationMode) {
        RegistryEntry<Instrument> component = itemStack.get(DataComponentTypes.INSTRUMENT);
        if (component == null) return null;
        return component.getKey().get().getValue().toString();
    }

    public SelectProperty.Type<InstrumentProperty, String> getType() {
        return TYPE;
    }

    static {
        TYPE = Type.create(MapCodec.unit(new InstrumentProperty()), Codec.STRING);
    }
}