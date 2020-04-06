package top.trou.forgedev;

import net.minecraft.block.material.Material;

public class ToolUtils {
    private static Material[] materialsPickaxe = new Material[]{ Material.ROCK, Material.IRON, Material.ICE, Material.GLASS, Material.PISTON, Material.ANVIL };
    private static Material[] materialsShovel = new Material[]{ Material.GRASS, Material.GROUND, Material.SAND, Material.SNOW, Material.CRAFTED_SNOW, Material.CLAY };
    private static Material[] materialsAxe = new Material[]{ Material.CORAL, Material.LEAVES, Material.PLANTS, Material.WOOD };

    public static String getRightTool(Material material) {
        for (Material mat : materialsAxe) {
            if (material == mat) return "axe";
        }
        for (Material mat : materialsPickaxe) {
            if (material == mat) return "pickaxe";
        }
        for (Material mat : materialsShovel) {
            if (material == mat) return "shovel";
        }
        return "none";
    }
}
