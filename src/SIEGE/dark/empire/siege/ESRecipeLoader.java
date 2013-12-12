package dark.empire.siege;

import dark.core.prefab.RecipeLoader;

public class ESRecipeLoader extends RecipeLoader
{
    private static ESRecipeLoader instance;

    public static ESRecipeLoader instance()
    {
        if (instance == null)
        {
            instance = new ESRecipeLoader();
        }
        return instance;
    }

    @Override
    public void loadRecipes()
    {
        super.loadRecipes();

    }
}
