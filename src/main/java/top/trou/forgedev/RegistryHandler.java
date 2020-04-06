package top.trou.forgedev;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import top.trou.forgedev.item.UniversalIronTool;

@Mod.EventBusSubscriber(modid = "forgedev")
public class RegistryHandler {
    private static UniversalIronTool universalIronTool = new UniversalIronTool();

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(universalIronTool);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegistry(ModelRegistryEvent event) {
        final ModelResourceLocation stick = new ModelResourceLocation("stick");
        final ModelResourceLocation pickaxe = new ModelResourceLocation("iron_pickaxe");
        final ModelResourceLocation axe = new ModelResourceLocation("iron_axe");
        final ModelResourceLocation shovel = new ModelResourceLocation("iron_shovel");
        ModelLoader.registerItemVariants(universalIronTool, pickaxe, axe, shovel, stick);
        ModelLoader.setCustomMeshDefinition(universalIronTool, itemStack -> {
            if (itemStack.hasTagCompound()){
                NBTTagCompound nbtTag = itemStack.getTagCompound();
                switch (nbtTag.getString("toolType")) {
                    case "pickaxe":return pickaxe;
                    case "axe":return axe;
                    case "shovel":return shovel;
                    default:return stick;
                }
            }
            return stick;
        });
    }
}
