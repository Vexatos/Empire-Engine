package dark.empire.village.village;

import java.util.HashMap;

import dark.empire.core.empire.Empire;

public class VillageManager
{
    private HashMap<String, String> villageToSaveName = new HashMap();

    public static Village createNewVillage(String name, Empire empire, Object creator)
    {
        Village village = new Village();

        return village;
    }

    public void loadVillage(String name)
    {

    }

    public void saveVillage(Village village)
    {

    }

    public void loadVillagesFromWorld()
    {
        //TODO not per say load up the villages as only the chunks loaded get there villages loaded
        //However, we need to preload all the villages and check which IDs and names are used.
    }
}
