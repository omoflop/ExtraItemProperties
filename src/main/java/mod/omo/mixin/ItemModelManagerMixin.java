package mod.omo.mixin;

import mod.omo.client.EIPClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelManager.class)
public class ItemModelManagerMixin {
    @Inject(method = "updateForNonLivingEntity", at = @At("HEAD"))
    private void omo$captureNonLivingEntity(ItemRenderState renderState, ItemStack stack, ModelTransformationMode transformationMode, Entity entity, CallbackInfo ci) {
        EIPClient.nonLivingEntity = entity;
    }
}
