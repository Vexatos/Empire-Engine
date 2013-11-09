package dark.empire.api.weapons;

import java.util.Random;

import net.minecraft.entity.Entity;

/** Interface used to create bullet classes that represent the information about a bullet. This
 * should not be used to actually create entity bullets as that is managed by the weapon and not the
 * ammo. As stats for the bullet are added to the stats of the weapon. The weapn has major control
 * over the handling but the bullet control most of the damage so keep that in mind.
 * 
 * @author DarkGuardsman */
public interface IBullet
{
    /** Called when the bullet hits an entity */
    public void onImpact(Entity entity);

    /** Called by the bullet's entity, or attack handler to get the damage */
    public float getDamage(Random rand);

    /** Min damage this adds to the weapon's stats. */
    public float getMinDamage();

    /** Max damage this adds to the weapon's stats. */
    public float getMaxDamage();

    /** Change in fall off this bullet causes to the weapon */
    public float getFallOffBonus();

    /** Some bullets like shotgun shells actually created more than one projectile. This returns the
     * number of bullets created when the bullet is fired from the weapon. */
    public int getShotsFired();
}
