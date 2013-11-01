package dark.empire.weapons;

import dark.core.common.RecipeLoader;

public class EWRecipeLoader extends RecipeLoader
{
    private static EWRecipeLoader instance;

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
