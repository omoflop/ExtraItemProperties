package mod.omo.client.property.bool;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.Nullable;

public record HasComponentProperty(ComponentType<?> componentType, boolean ignoreDefault) implements BooleanProperty {
    public static final MapCodec<net.minecraft.client.render.item.property.bool.HasComponentProperty> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(Registries.DATA_COMPONENT_TYPE.getCodec().fieldOf("component").forGetter(net.minecraft.client.render.item.property.bool.HasComponentProperty::componentType), Codec.BOOL.optionalFieldOf("ignore_default", false).forGetter(net.minecraft.client.render.item.property.bool.HasComponentProperty::ignoreDefault)).apply(instance, net.minecraft.client.render.item.property.bool.HasComponentProperty::new);
    });

    public HasComponentProperty(ComponentType<?> componentType, boolean ignoreDefault) {
        this.componentType = componentType;
        this.ignoreDefault = ignoreDefault;
    }

    public boolean getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed, ModelTransformationMode transformationMode) {
        return this.ignoreDefault ? stack.hasChangedComponent(this.componentType) : stack.contains(this.componentType);
    }


    public MapCodec<net.minecraft.client.render.item.property.bool.HasComponentProperty> getCodec() {
        return CODEC;
    }

    public ComponentType<?> componentType() {
        return this.componentType;
    }

    public boolean ignoreDefault() {
        return this.ignoreDefault;
    }
}
