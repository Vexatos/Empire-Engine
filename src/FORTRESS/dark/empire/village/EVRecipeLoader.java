package dark.empire.village;

import dark.core.common.RecipeLoader;
import dark.empire.siege.ESRecipeLoader;

public class EVRecipeLoader extends RecipeLoader
{
    private static EVRecipeLoader instance;

    public static EVRecipeLoader instance()
    {
        if (instance == null)
        {
            instance = new EVRecipeLoader();
        }
        return instance;
    }

    @Override
    public void loadRecipes()
    {
        super.loadRecipes();

    }
}
