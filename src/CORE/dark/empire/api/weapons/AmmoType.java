package dark.empire.api.weapons;

/** Basic ammo types used by all weapons in the empire engine
 * 
 * @author DarkGuardsman */
public enum AmmoType
{
    /** Bolt from a crossbow */
    BOLT("bolt"),
    /** Arrow from a bow */
    ARROW("arrow"),
    /** Musket shot */
    BALL("ball"),
    /** Handgun and small rifle rounds */
    SMALL("small"),
    /** Assult rifle and rifle rounds */
    MEDIUM("medium"),
    /** Anti-Tank rifle rounds, Some Machine Guns, and Heavy Machine Guns */
    HEAVY("heavy"),
    /** 12 Gauge shotgun rounds */
    SHOTGUN("shotgun"),
    /** Rockets fire from launchers */
    ROCKET("rocket"),
    /** Standard battery used by several mods */
    BATTERY("battery"),
    /** Combat version of the battery */
    CELL("cell");
    public final String name;

    private AmmoType(String name)
    {
        this.name = name;
    }
}
