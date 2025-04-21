package snownee.clearview.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BackgroundRenderer.class)
public class MixinFogRenderer {

  @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSpectator()Z", ordinal = 0))
  private static boolean applyFog(Entity entity) {
    if (entity instanceof PlayerEntity player) {
      if (player.isCreative() || player.isFireImmune() || player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
        return true;
      }
    }
    return entity.isSpectator();
  }
}
