package top.trou.forgedev;

import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import top.trou.forgedev.item.UniversalIronTool;

@Mod.EventBusSubscriber(modid = "forgedev")
public class EventHandler {
    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent event) {
                if (event.getSide().isClient()) return;
                if (event.getHand().equals(EnumHand.OFF_HAND)) return;
                if (event.getItemStack().getItem() instanceof UniversalIronTool) {
                    Material curMaterial = event.getWorld().getBlockState(event.getPos()).getMaterial();
                    NBTTagCompound nbtTag = event.getItemStack().hasTagCompound() ? event.getItemStack().getTagCompound() : new NBTTagCompound();
                    nbtTag.setString("toolType", ToolUtils.getRightTool(curMaterial));
                    event.getItemStack().setTagCompound(nbtTag);
        }
    }
}
