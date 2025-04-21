package snownee.clearview.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public class MixinScreenEffectRenderer {

  @Inject(at = @At("HEAD"), method = "renderFireOverlay", cancellable = true)
  private static void renderFire(MinecraftClient minecraft, MatrixStack poseStack, CallbackInfo ci) {
    ClientPlayerEntity player = MinecraftClient.getInstance().player;
    if (player == null)
      return;
    if (player.isCreative())
      ci.cancel();
    if (player.isSubmergedIn(FluidTags.LAVA)
        && (player.isFireImmune() || player.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)))
      poseStack.translate(0, -0.25, 0);
  }
}