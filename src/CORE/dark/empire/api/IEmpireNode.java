package dark.empire.api;

import net.minecraft.world.World;
import universalelectricity.core.vector.Vector3;

import com.builtbroken.common.Pair;

/** Nodes that are part of the empire like fortresses, villages, forwards bases, houses, building,
 * ect
 * 
 * @author DarkGuardsman */
public interface IEmpireNode extends IEmpireObject
{
    public Pair<World, Vector3> getLocation();
}
