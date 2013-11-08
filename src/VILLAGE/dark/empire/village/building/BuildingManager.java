package dark.empire.village.building;

import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

/** Handles the creation of buildings only. Everything else is handled by the village that the
 * building is created in
 * 
 * @author Darkguardsman */
public class BuildingManager
{
    public static void createBuilding(World world, Vector3 location, Object creator)
    {
        //TODO check if location is valid
        //TODO check if building min requirements are meet
        //TODO make sure there is no over lap with another building
    }
}
