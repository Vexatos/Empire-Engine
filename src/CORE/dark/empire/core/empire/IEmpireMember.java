package dark.empire.core.empire;

import universalelectricity.core.vector.Vector3;
import net.minecraft.world.World;

import com.builtbroken.common.Pair;

/** Nodes that are part of the empire like fortresses, villages, forwards bases, houses, building,
 * ect
 *
 * @author DarkGuardsman */
public interface IEmpireMember
{
    public Empire getEmpire();

    public void setEmpire();

    public Pair<World, Vector3> getLocation();
}
