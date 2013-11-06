package dark.empire.api;

import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

import com.builtbroken.common.Pair;

import dark.empire.core.empire.Empire;

public interface IEmpireObject
{
    public Empire getEmpire();

    public void setEmpire(Empire empire);
}
