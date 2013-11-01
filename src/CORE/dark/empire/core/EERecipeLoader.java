package dark.empire.core;

import dark.core.common.RecipeLoader;

public class EERecipeLoader extends RecipeLoader
{
    private static EERecipeLoader instance;

    public static EERecipeLoader instance()
    {
        if (instance == null)
        {
            instance = new EERecipeLoader();
        }
        return instance;
    }

    public void loadRecipes()
    {
        super.loadRecipes();
    }
}