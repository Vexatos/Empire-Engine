package dark.empire.weapons;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import dark.core.common.RecipeLoader;

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

    public void loadRecipes()
    {
        super.loadRecipes();
    }
}
