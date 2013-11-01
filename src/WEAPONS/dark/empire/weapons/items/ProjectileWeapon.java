package dark.empire.weapons.items;

import java.util.Random;
import java.util.logging.Level;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLLog;

public class ProjectileWeapon
{
    protected String name = "gun";
    protected ProjectileWeaponTypes type;
    protected float maxDamage = 1, minDamage = 1;
    protected float critChance = .05f, critMuliplier = 1.1f;//10% bonus damage
    protected WeaponUpgrade[] upgrades;
    protected float velocity = 600;

    public ProjectileWeapon(String name, ProjectileWeaponTypes type, float damage)
    {
        this.name = name;
        this.maxDamage = damage;
        this.type = type;
        this.upgrades = new WeaponUpgrade[WeaponUpgrade.WeaponUpgradeType.values().length];
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
