package dark.empire.weapons.items;

public enum ProjectileWeaponTypes
{
    CROSSBOW("crossbow", "Crossbow"),
    BOW("bow", "Bow"),
    HANDGUN("handgun", "Hand-Gun"),
    CARBINE("carbine", "Carbin"),
    ASSULT_RIFLE("assultrifle", "Assult Rifle"),
    RIFLE("rifle", "Rifle"),
    SUB_MACHINE_GUN("submachiengun", "Sub Machine Gun"),
    MACHINE_GUN("machinegun", "Machine Gun"),
    LIGHT_MACHINE_GUN("lightmachinegun", "Light Machine Gun"),
    HEAVY_MACHINE_GUN("heavymachinegun", "Heavy Machine Gun"),
    SHOTGUN("shotgun", "Shotgun"),
    LAUNCHER("launcher", "Launcher");
    public final String name;
    public final String displayName;

    private ProjectileWeaponTypes(String name, String display)
    {
        this.name = name;
        this.displayName = name;
    }
}
