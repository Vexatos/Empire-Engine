package dark.empire.drone;

import dark.core.prefab.RecipeLoader;

public class EDRecipeLoader extends RecipeLoader
{
    private static EDRecipeLoader instance;

    public static EDRecipeLoader instance()
    {
        if (instance == null)
        {
            instance = new EDRecipeLoader();
        }
        return instance;
    }

    @Override
    public void loadRecipes()
    {
        super.loadRecipes();

    }
}
