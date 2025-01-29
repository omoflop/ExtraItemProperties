package mod.omo.mixin;

import mod.omo.client.property.BiomeProperty;
import mod.omo.client.property.InstrumentProperty;
import net.minecraft.client.render.item.property.select.SelectProperties;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SelectProperties.class)
public class SelectPropertiesMixin {
    @Shadow @Final private static Codecs.IdMapper<Identifier, SelectProperty.Type<?, ?>> ID_MAPPER;

    @Inject(method = "bootstrap", at = @At("TAIL"))
    private static void omo$addProperties(CallbackInfo ci) {
        ID_MAPPER.put(Identifier.of("extra:instrument"), InstrumentProperty.TYPE);
        ID_MAPPER.put(Identifier.of("extra:biome"), BiomeProperty.TYPE);

    }
}
