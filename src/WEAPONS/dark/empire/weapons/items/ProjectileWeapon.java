package dark.empire.weapons.items;

import java.util.Random;
import java.util.logging.Level;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Icon;
import cpw.mods.fml.common.FMLLog;

public class ProjectileWeapon
{
    protected String name = "gun";
    protected ProjectileWeaponTypes type;
    protected float maxDamage = 1, minDamage = 1;
    protected float critChance = .05f, critMuliplier = 1.1f;//10% bonus damage
    protected float range = 300, fallOffPerMeter = .1f;
    protected int shotsPerClick = 1;
    /** Time to reload the weapon per clip or shell if no clip */
    protected int reloadTicks = 20;
    /** Time before the next round */
    protected int fireDelayTicks = 10;
    protected WeaponUpgrade[] upgrades;
    public Icon icon;

    public ProjectileWeapon(String name, ProjectileWeaponTypes type, float damage, int reloadTime, int fireDelay, float fallOffPerMeter)
    {
        this.name = name;
        this.maxDamage = damage;
        this.type = type;
        this.reloadTicks = reloadTime;
        this.fireDelayTicks = fireDelay;
        this.fallOffPerMeter = fallOffPerMeter;
        this.upgrades = new WeaponUpgrade[WeaponUpgrade.WeaponUpgradeType.values().length];
    }

    public ProjectileWeapon setShotsPerClick(int shots)
    {
        this.shotsPerClick = shots;
        return this;
    }
    public ProjectileWeapon setMaxRange(int range)
    {
        this.range = range;
        return this;
    }

    /** Called when the gun is fired by the entity with the given bullet
     *
     * @param entity - entity firing use this for aim location
     * @param bullet - bullet fired
     * @return true to stop the built in firing code and replace it with your own custom code */
    public boolean onFired(Entity entity, Bullet bullet)
    {
        return false;
    }

    /** Creates a new entity and loads its data from the specified NBT. */
    public static ProjectileWeapon createAndLoadWeapon(NBTTagCompound par0NBTTagCompound)
    {
        ProjectileWeapon gun = null;

        try
        {
            ProjectileWeapon weapon = ProjectileWeaponManager.getWeapon(par0NBTTagCompound.getString("id"));

            if (weapon != null)
            {
                gun = (ProjectileWeapon) weapon.clone();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        if (gun != null)
        {
            try
            {
                gun.load(par0NBTTagCompound);
            }
            catch (Exception e)
            {
                FMLLog.log(Level.SEVERE, e, "A Weapon %s(%s) has thrown an exception during loading, its state cannot be restored. Report this to the mod author", par0NBTTagCompound.getString("id"), gun.getClass().getName());
                gun = null;
            }
        }
        else
        {
            MinecraftServer.getServer().getLogAgent().logWarning("Skipping Weapon with id " + par0NBTTagCompound.getString("id"));
        }

        return gun;
    }

    public NBTTagCompound save(NBTTagCompound nbt)
    {
        nbt.setString("id", ProjectileWeaponManager.getWeaponID(this));
        if (this.upgrades != null)
        {
            for (int i = 0; i < this.upgrades.length; i++)
            {
                if (this.upgrades[i] != null)
                {
                    nbt.setCompoundTag("upgrade" + i, upgrades[i].save(new NBTTagCompound()));
                }
            }
        }
        return nbt;
    }

    public void load(NBTTagCompound tag)
    {
        if (this.upgrades != null)
        {
            for (int i = 0; i < this.upgrades.length; i++)
            {
                this.upgrades[i] = WeaponUpgrade.createAndLoad(tag.getCompoundTag("upgrade" + i));
            }
        }
    }

    public String getName()
    {
        return this.name;
    }

    public ProjectileWeaponTypes getType()
    {
        return this.type;
    }

    public float getDamage(Random random)
    {
        float damage = this.minDamage + (random.nextFloat() * this.maxDamage);
        return (random.nextFloat() <= this.critChance ? damage * this.critMuliplier : damage);
    }

    public WeaponUpgrade[] getUpgrades()
    {
        return this.upgrades;
    }
}
