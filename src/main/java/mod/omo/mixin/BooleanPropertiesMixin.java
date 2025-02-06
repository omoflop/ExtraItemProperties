package mod.omo.mixin;

import com.mojang.serialization.MapCodec;
import mod.omo.client.property.bool.HasComponentProperty;
import mod.omo.client.property.select.BiomeProperty;
import mod.omo.client.property.select.ComponentSelectProperty;
import mod.omo.client.property.select.InstrumentProperty;
import net.minecraft.client.render.item.property.bool.BooleanProperties;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BooleanProperties.class)
public class BooleanPropertiesMixin {

    @Shadow @Final private static Codecs.IdMapper<Identifier, MapCodec<? extends BooleanProperty>> ID_MAPPER;

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void omo$addProperties(CallbackInfo ci) {
        ID_MAPPER.put(Identifier.ofVanilla("has_component"), HasComponentProperty.CODEC);
    }
}
