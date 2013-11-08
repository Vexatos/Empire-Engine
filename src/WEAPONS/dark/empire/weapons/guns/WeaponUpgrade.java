package dark.empire.weapons.guns;

import java.util.logging.Level;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.FMLLog;

public class WeaponUpgrade
{
    protected String id = "gun:1";
    protected String name = "upgrade";
    protected float bonus = .01f;
    protected WeaponUpgradeType type = WeaponUpgradeType.OTHER;

    public WeaponUpgrade(String name, float bonus)
    {
        this.name = name;
        this.bonus = bonus;
    }

    public WeaponUpgrade(String name, WeaponUpgradeType type, float bonus)
    {
        this(name, bonus);
        this.type = type;
    }

    public String getName()
    {
        if (name == null)
        {
            return "unkown";
        }
        return name;
    }

    public float getBonus()
    {
        return this.bonus;
    }

    public WeaponUpgradeType getType()
    {
        return this.type;
    }

    public NBTTagCompound save(NBTTagCompound nbt)
    {
        nbt.setString("id", ProjectileWeaponManager.getUpgradeID(this));
        return nbt;
    }

    public void load(NBTTagCompound nbt)
    {

    }

    /** Creates a new entity and loads its data from the specified NBT. */
    public static WeaponUpgrade createAndLoad(NBTTagCompound par0NBTTagCompound)
    {
        WeaponUpgrade upgrade = null;

        try
        {
            WeaponUpgrade weapon = ProjectileWeaponManager.getUpgrade(par0NBTTagCompound.getString("id"));

            if (weapon != null)
            {
                upgrade = (WeaponUpgrade) weapon.clone();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        if (upgrade != null)
        {
            try
            {
                upgrade.load(par0NBTTagCompound);
            }
            catch (Exception e)
            {
                FMLLog.log(Level.SEVERE, e, "A Weapon Upgrade %s(%s) has thrown an exception during loading, its state cannot be restored. Report this to the mod author", par0NBTTagCompound.getString("id"), upgrade.getClass().getName());
                upgrade = null;
            }
        }
        else
        {
            MinecraftServer.getServer().getLogAgent().logWarning("Skipping Weapon Upgrade with id " + par0NBTTagCompound.getString("id"));
        }

        return upgrade;
    }

    public static enum WeaponUpgradeType
    {
        SIGHT(),
        GRIP(),
        BARREL(),
        BARREL_END(),
        RECOIL(),
        TRIGGER(),
        OTHER();
    }
}
