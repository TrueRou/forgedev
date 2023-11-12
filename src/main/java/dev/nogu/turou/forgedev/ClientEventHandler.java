package dev.nogu.turou.forgedev;

import dev.nogu.turou.forgedev.render.TooltipRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ForgeDev.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void colorTooltip(RenderTooltipEvent.Color event) {
        TooltipRenderer.removeColors(event);
        TooltipRenderer.tryRenderCharacter(event.getItemStack(), event.getX(), event.getY());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void preInitTooltip(RenderTooltipEvent.Pre event) {
        TooltipRenderer.setGuiGraphicsContext(event.getGraphics());
    }
}
