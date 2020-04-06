package top.trou.forgedev.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class UniversalIronTool extends ItemPickaxe {
    public UniversalIronTool() {
        super(ToolMaterial.IRON);
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setRegistryName("universal_irontool");
        this.setTranslationKey("forgedev.universalIronTool");
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return this.efficiency;
    }
}
