package dark.empire.api.village;

import dark.api.save.IVirtualObject;
import dark.empire.api.IEmpireNode;

/** Just used with the interfaces so the actual classes for this don't have to be included
 * 
 * @author DarkGuardsman */
public interface IVillage extends IEmpireNode, IVirtualObject
{
    public void init();

    public void registerVillageObject(IVillageObject object);

    public void unregisterVillageObject(IVillageObject object);
}
