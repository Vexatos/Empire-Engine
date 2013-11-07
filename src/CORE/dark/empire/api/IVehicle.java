package dark.empire.api;

import java.util.List;

import net.minecraft.entity.Entity;

/** Used to implement some basic methods on a vehicle entity. Entities are not restricted to Players
 * or even living creates. They can be mine carts, boats, or even items. Though it is recomended
 * that the drive be an entity or the vehicle itself
 * 
 * @author DarkGuardsman */
public interface IVehicle
{
    public Entity getDriver();

    public List<Entity> getPassengers();
}
