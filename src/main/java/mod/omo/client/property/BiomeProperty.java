package mod.omo.client.property;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import mod.omo.client.EIPClient;
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
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record BiomeProperty() implements SelectProperty<String> {
    public static final Type<BiomeProperty, String> TYPE;


    @Nullable
    public String getValue(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i, ModelTransformationMode modelTransformationMode) {
        if (clientWorld == null) return null;
        BlockPos pos;
        if (livingEntity == null) {
            if (EIPClient.nonLivingEntity == null) return null;
            pos = EIPClient.nonLivingEntity.getBlockPos();
        } else {
            pos = livingEntity.getBlockPos();
        }

        var key = clientWorld.getBiome(pos).getKey();
        return key.map(biomeRegistryKey -> biomeRegistryKey.getValue().toString()).orElse(null);
    }

    public Type<BiomeProperty, String> getType() {
        return TYPE;
    }

    static {
        TYPE = Type.create(MapCodec.unit(new BiomeProperty()), Codec.STRING);
    }
}