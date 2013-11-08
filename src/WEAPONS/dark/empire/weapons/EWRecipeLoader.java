package dark.empire.weapons;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import dark.core.common.RecipeLoader;
import dark.empire.weapons.guns.ItemClip;
import dark.empire.weapons.guns.ItemProjectileWeapon;
import dark.empire.weapons.guns.ItemWeaponUpgrade;

public class EWRecipeLoader extends RecipeLoader
{
    private static EWRecipeLoader instance;
    public static Item itemGun;
    public static Item itemBullet;
    public static Item gunUpgrades;
    public static Item itemClip;
    public static Block weaponWorkBench;

    public static EWRecipeLoader instance()
    {
        if (instance == null)
        {
            instance = new EWRecipeLoader();
        }
        return instance;
    }

    @Override
    public void loadRecipes()
    {
        super.loadRecipes();
        if (itemGun instanceof ItemProjectileWeapon)
        {
            GameRegistry.addRecipe(new ItemStack(itemGun, 1, ItemProjectileWeapon.Guns.PumpShotGun.ordinal()), new Object[] { "DDF", "III", "SSS", 'D', Item.diamond, 'F', Item.flintAndSteel, 'I', Item.ingotIron, 'S', Item.stick });

        }
        if (itemBullet != null)
        {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemBullet, 64, 1), new Object[] { "SLS", 'L', "ingotLead", 'S', Block.sand }));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemBullet, 64, 100), new Object[] { " L ", "L L", 'L', "ingotTin" }));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemBullet, 64, 200), new Object[] { " L ", "L L", 'L', "ingotCopper" }));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemBullet, 64, 300), new Object[] { " L ", "L L", 'L', "ingotBronze" }));

            GameRegistry.addRecipe(new ItemStack(itemBullet, 64, 400), new Object[] { "L L", "L L", "LPL", 'P', Item.paper, 'L', Item.ingotIron });

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemBullet, 7, 101), new Object[] { "BIB", "BGB", "BBB", 'B', "bullet.small.shell", 'I', "bullet.ball.", 'G', Item.gunpowder }));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemBullet, 7, 201), new Object[] { "BIB", "BGB", "BBB", 'B', "bullet.medium.shell", 'I', "bullet.ball.", 'G', Item.gunpowder }));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemBullet, 7, 301), new Object[] { "BIB", "BGB", "BBB", 'B', "bullet.heavy.shell", 'I', "bullet.ball.", 'G', Item.gunpowder }));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemBullet, 7, 401), new Object[] { "BIB", "BGB", "BBB", 'B', "bullet.shotgun.shell", 'I', "bullet.ball.", 'G', Item.gunpowder }));

        }
        if (itemGun instanceof ItemClip)
        {

        }
        if (itemGun instanceof ItemWeaponUpgrade)
        {

        }
    }
}
