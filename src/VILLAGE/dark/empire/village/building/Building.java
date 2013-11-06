package dark.empire.village.building;

import java.util.List;

import universalelectricity.core.vector.Vector3;
import net.minecraft.world.World;
import dark.empire.api.village.IVillage;
import dark.empire.api.village.IVillageObject;
import dark.empire.village.village.Village;

public class Building implements IVillageObject
{
    private Village village;
    protected World worldObj;
    protected Vector3 center;
    /** Max area in all directions from creation point that will be searched */
    protected int maxSize = 10;

    public Building()
    {

    }

    public Building(World world, Vector3 center)
    {
        this.worldObj = world;
        this.center = center;
    }

    /** Call when the building is create from a save, or by the village its in */
    public void onCreated()
    {
        //TODO scan area for content that is used by this building
    }

    /** List of things the building needs to be created. Normally is just blocks */
    public List<Object> requirements()
    {
        return null;
    }

    @Override
    public IVillage getVillage()
    {
        return village;
    }

    @Override
    public void setVillage(IVillage village)
    {
        if (village instanceof Village)
        {
            this.village = (Village) village;
        }
    }

}
