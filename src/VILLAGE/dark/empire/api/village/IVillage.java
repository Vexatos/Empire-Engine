package dark.empire.api.village;

import dark.api.IVirtualObject;
import dark.empire.core.empire.IEmpireObject;

/** Just used with the interfaces so the actual classes for this don't have to be included
 *
 * @author DarkGuardsman */
public interface IVillage extends IEmpireObject, IVirtualObject
{
    public void init();
}
