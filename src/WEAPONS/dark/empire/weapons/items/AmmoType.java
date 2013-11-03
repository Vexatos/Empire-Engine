package dark.empire.weapons.items;

public enum AmmoType
{
    BOLT("bolt"),
    ARROW("arrow"),
    BALL("ball"),
    SMALL("small"),
    MEDIUM("medium"),
    HEAVY("heavy"),
    SHOTGUN("shotgun"),
    ROCKET("rocket"),
    BATTERY("battery"),
    CELL("cell");
    public final String name;

    private AmmoType(String name)
    {
        this.name = name;
    }
}
