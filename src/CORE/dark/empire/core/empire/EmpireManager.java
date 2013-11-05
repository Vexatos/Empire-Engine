package dark.empire.core.empire;

import java.util.HashMap;

public class EmpireManager
{
    /** Current Active empires */
    private static HashMap<String, Empire> empires = new HashMap<String, Empire>();
    /** Empire prefabs that were registered to create new empires from or help load saved ones */
    private static HashMap<String, Empire> empireClasses = new HashMap<String, Empire>();

    public static void registerEmpirePrefab(String id, Empire empire)
    {
        if (!empireClasses.containsKey(id))
        {
            empireClasses.put(id, empire.clone());
        }
    }
}
