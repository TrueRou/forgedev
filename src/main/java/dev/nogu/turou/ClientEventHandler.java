package dev.nogu.turou;

import dev.nogu.turou.render.TooltipRenderer;
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
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void preInitTooltip(RenderTooltipEvent.Pre event) {
        TooltipRenderer.setGuiGraphicsContext(event.getGraphics());
    }
}
