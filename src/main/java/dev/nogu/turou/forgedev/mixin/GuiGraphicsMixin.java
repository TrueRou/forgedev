package dev.nogu.turou.forgedev.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.nogu.turou.forgedev.render.TooltipRenderer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {
    @Final
    @Shadow()
    private PoseStack pose;

    @Inject(method = "renderTooltipInternal",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void renderTooltipInternalMixin(Font font, List<ClientTooltipComponent> components, int x, int y, ClientTooltipPositioner positioner, CallbackInfo ci, RenderTooltipEvent.Pre preEvent, int width, int height, int i2, int j2, Vector2ic postPos) {
        TooltipRenderer.tryRenderBorder(preEvent.getItemStack(), pose, postPos.x(), postPos.y(), font, width, height, components);
    }
}
