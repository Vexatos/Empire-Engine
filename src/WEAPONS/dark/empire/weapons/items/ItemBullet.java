package dark.empire.weapons.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.common.Configuration;
import dark.core.prefab.items.ItemBasic;
import dark.empire.weapons.EmpireWeapons;
import dark.empire.weapons.items.ItemProjectileWeapon.Guns;

/** Class for bullets, clips, and ammo in general
 *
 * @author DarkGuardsman */
public class ItemBullet extends ItemBasic
{
    public static int spacing = 100;

    public ItemBullet()
    {
        super(EmpireWeapons.getNextItemId(), "EWItemBullet", EmpireWeapons.CONFIGURATION);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(this, 1, BulletData.SMALL_BULLET.ordinal() * spacing + 1));
        par3List.add(new ItemStack(this, 1, BulletData.MEDIUM_BULLET.ordinal() * spacing + 1));
        par3List.add(new ItemStack(this, 1, BulletData.SHOTGUN_SHELL.ordinal() * spacing + 1));

    }

    @Override
    public Icon getIconFromDamage(int i)
    {
        int type = i / spacing;
        int sub = i % spacing;
        if (i < BulletData.values().length && sub < BulletData.values()[type].icons.length)
        {
            return BulletData.values()[type].icons[sub];
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        BulletData.SMALL_BULLET.icons[1] = par1IconRegister.registerIcon(EmpireWeapons.instance.PREFIX + "Bullet_small");
        BulletData.MEDIUM_BULLET.icons[1] = par1IconRegister.registerIcon(EmpireWeapons.instance.PREFIX + "Bullet_medium");
        BulletData.SHOTGUN_SHELL.icons[1] = par1IconRegister.registerIcon(EmpireWeapons.instance.PREFIX + "Bullet_shotgun");
    }

    public static enum BulletData
    {
        MUSKET_BALL(),
        SMALL_BULLET(),
        MEDIUM_BULLET(),
        HEAVY_BULLET(),
        SHOTGUN_SHELL();
        public Icon[] icons = new Icon[spacing];
    }

    public static enum BulletTypes
    {
        SHELL(),
        NORMAL(),
        HE(),
        FIRE(),
        POISON();
    }

}
