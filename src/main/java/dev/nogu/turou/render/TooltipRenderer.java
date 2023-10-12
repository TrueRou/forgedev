package dev.nogu.turou.render;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.nogu.turou.ForgeDev;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.joml.Matrix4f;

import java.util.List;

public class TooltipRenderer {
    private static GuiGraphics guiGraphicsContext;
    private static final int COLOR_FROM = 0x60500000;
    private static final int COLOR_TO = -1602211792;

    public static void removeColors(RenderTooltipEvent.Color event) {
        // make the border and background transparent here so that we can redraw it later.
        event.setBorderStart(0);
        event.setBorderEnd(0);
        event.setBackgroundStart(0);
        event.setBackgroundEnd(0);
    }

    public static void renderFinals(ItemStack itemStack, PoseStack pose, int x, int y, Font font, int width, int height, List<ClientTooltipComponent> components) {
        if (itemStack.getItem() instanceof SwordItem) {
            renderFancyBorder(x, y, width, height);
            renderSwordCharacter(x, y);
            return;
        }
        if (itemStack.getItem() instanceof ArmorItem) renderArmorItem(itemStack, x - 54, y + 16);
        renderLinearBorder(x, y, width, height);
    }

    public static void setGuiGraphicsContext(GuiGraphics guiGraphics) {
        guiGraphicsContext = guiGraphics;
    }

    private static ResourceLocation getPath(String part) {
        return new ResourceLocation(ForgeDev.MODID, "textures/tooltip/" + part + ".png");
    }

    private static ResourceLocation getBorderPath(String borderPart) {
        return getPath("border_" + borderPart);
    }

    private static void innerBlit(ResourceLocation pAtlasLocation, int pX, int pY, int pWidth, int pHeight) {
        // duration maybe negative, we don't render negative width
        if (pWidth > 0) guiGraphicsContext.blit(pAtlasLocation, pX, pY, 0, 0, pWidth, pHeight, pWidth, pHeight);
    }

    private static void renderArmorItem(ItemStack itemStack, int x, int y) {
        Minecraft minecraft = Minecraft.getInstance();
        BakedModel bakedmodel = minecraft.getItemRenderer().getModel(itemStack, minecraft.level, null, 0);
        guiGraphicsContext.pose().pushPose();
        guiGraphicsContext.pose().translate((float)(x + 8), (float)(y + 8), 399f);
        guiGraphicsContext.pose().mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
        guiGraphicsContext.pose().scale(64.0f, 64.0f, 1.0f);
        if (!bakedmodel.usesBlockLight()) {
            Lighting.setupForFlatItems();
        }

        minecraft.getItemRenderer().render(itemStack, ItemDisplayContext.GUI, false, guiGraphicsContext.pose(), guiGraphicsContext.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
        guiGraphicsContext.flush();
        if (!bakedmodel.usesBlockLight()) {
            Lighting.setupFor3DItems();
        }
        guiGraphicsContext.pose().popPose();
    }

    private static void renderLinearBorder(int x, int y, int width, int height) {
        guiGraphicsContext.fillGradient(x - 3, y - 3 + 1, x - 3 + 1, y + height + 3 - 1, 400, COLOR_FROM, COLOR_TO);
        guiGraphicsContext.fillGradient(x + width + 2, y - 3 + 1, x + width + 3, y + height + 3 - 1, 400, COLOR_FROM, COLOR_TO);
        guiGraphicsContext.fillGradient(x - 3, y - 3, x + width + 3, y - 3 + 1, 400, COLOR_FROM, COLOR_TO);
        guiGraphicsContext.fillGradient(x - 3, y + height + 2, x + width + 3, y + height + 3, 400, COLOR_FROM, COLOR_TO);
    }

    private static void renderSwordCharacter(int x, int y) {
        guiGraphicsContext.pose().pushPose();
        guiGraphicsContext.pose().translate(0, 0, 400);
        RenderSystem.enableBlend();
        innerBlit(getPath("sword_character"), x - 66, y - 8, 48, 76);
        RenderSystem.disableBlend();
        guiGraphicsContext.pose().popPose();
    }

    private static void renderFancyBorder(int x, int y, int width, int height) {
        guiGraphicsContext.pose().pushPose();
        guiGraphicsContext.pose().translate(0, 0, 400); // beneath the text and upon other items
        RenderSystem.enableBlend();

        // render for border
        var widthOffset = 2;
        width += widthOffset;
        var horizonStart = x - 16;
        var horizonDuration = width - 55;
        var horizonEnd = x - 30 + width;
        var verticalStart = y - 14;
        var verticalDuration = height - 38;
        var verticalEnd = y - 20 + height;
        var horizonCenter = (int) (horizonStart + 0.5 * width);
        innerBlit(getBorderPath("left_top"), horizonStart, verticalStart, 41, 37);
        innerBlit(getBorderPath("top"), horizonStart + 41, verticalStart + 7, horizonDuration, 4);
        innerBlit(getBorderPath("right_top"), horizonEnd, verticalStart, 40, 37);
        innerBlit(getBorderPath("left_bottom"), horizonStart, verticalEnd, 46, 32);
        innerBlit(getBorderPath("left"), horizonStart + 9, verticalStart + 37, 1, verticalDuration);
        innerBlit(getBorderPath("right"), horizonEnd + 30, verticalStart + 37, 1, verticalDuration);
        innerBlit(getBorderPath("right_bottom"), horizonEnd - 6, verticalEnd, 46, 32);
        innerBlit(getBorderPath("bottom"), horizonStart + 46, verticalEnd + 25, width - 66, 2);
        innerBlit(getBorderPath("stars"), horizonCenter + 7, verticalStart + 8, 12, 3);

        // render for background
        guiGraphicsContext.pose().translate(0, 0, -1); // beneath the text and upon other items (under the border)
        innerBlit(getPath("fancy_bg"), x - 6, y - 3, width + 8, height + 6);

        RenderSystem.disableBlend();
        guiGraphicsContext.pose().popPose();
    }
}
