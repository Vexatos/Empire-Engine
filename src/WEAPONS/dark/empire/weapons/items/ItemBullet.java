package dark.empire.weapons.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark.core.prefab.ModPrefab;
import dark.core.prefab.items.ItemBasic;
import dark.empire.api.weapons.IBullet;
import dark.empire.weapons.EmpireWeapons;

/** Class for bullets, clips, and ammo in general
 * 
 * @author DarkGuardsman */
public class ItemBullet extends ItemBasic implements IBullet
{
    public static int spacing = 100;

    public ItemBullet()
    {
        super(ModPrefab.getNextItemId(), "EWItemBullet", EmpireWeapons.CONFIGURATION);
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
    public final String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return this.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }

    @Override
    public Icon getIconFromDamage(int i)
    {
        int type = i / spacing;
        int sub = i % spacing;
        if (type < BulletData.values().length && sub < BulletData.values()[type].icons.length)
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
        BulletData.SMALL_BULLET.icons[0] = par1IconRegister.registerIcon(EmpireWeapons.instance.PREFIX + "shell");
        BulletData.MEDIUM_BULLET.icons[1] = par1IconRegister.registerIcon(EmpireWeapons.instance.PREFIX + "Bullet_medium");
        BulletData.MEDIUM_BULLET.icons[0] = par1IconRegister.registerIcon(EmpireWeapons.instance.PREFIX + "shell");
        BulletData.SHOTGUN_SHELL.icons[1] = par1IconRegister.registerIcon(EmpireWeapons.instance.PREFIX + "Bullet_shotgun");
        BulletData.SHOTGUN_SHELL.icons[0] = par1IconRegister.registerIcon(EmpireWeapons.instance.PREFIX + "shotgun_shell");
    }

    @Override
    public Bullet getBullet(ItemStack stack)
    {
        if (stack != null)
        {
            int type = stack.getItemDamage() / spacing;
            int sub = stack.getItemDamage() % spacing;
            if (type < BulletData.values().length && sub < BulletTypes.values().length && sub != 0)
            {
                return BulletData.values()[type].bullets[sub];
            }
        }
        return null;
    }

    @Override
    public ItemStack getShell(ItemStack stack)
    {
        if (stack != null)
        {
            int type = stack.getItemDamage() / spacing;
            int sub = stack.getItemDamage() % spacing;
            if (type < BulletData.values().length && type != 0 && sub != 0)
            {
                return new ItemStack(this, 1, type * spacing);
            }
        }
        return null;
    }

    @Override
    public AmmoType getType(ItemStack stack)
    {
        if (stack != null)
        {
            int type = stack.getItemDamage() / spacing;
            if (type < BulletData.values().length)
            {
                return BulletData.values()[type].type;
            }
        }
        return null;
    }

    public static enum BulletData
    {
        MUSKET_BALL("ball", AmmoType.BALL),
        SMALL_BULLET("small", AmmoType.SMALL),
        MEDIUM_BULLET("medium", AmmoType.MEDIUM),
        HEAVY_BULLET("heavy", AmmoType.HEAVY),
        SHOTGUN_SHELL("shotgun", AmmoType.SHOTGUN, 8);
        public String name;
        public AmmoType type;
        public Icon[] icons = new Icon[spacing];
        public Bullet[] bullets;
        public int r = 1;

        private BulletData(String name, AmmoType type)
        {
            this(name, type, 1);
        }

        private BulletData(String name, AmmoType type, int rr)
        {
            this.name = name;
            this.type = type;
            this.r = rr;
            this.bullets = new Bullet[BulletTypes.values().length];
            for (int i = 0; i < bullets.length; i++)
            {
                this.bullets[i] = new Bullet(this.name + BulletTypes.values()[i], rr);
            }
        }
    }

    public static enum BulletTypes
    {
        SHELL("SHELL"),
        NORMAL(""),
        HE("HE"),
        FIRE("FIRE"),
        POISON("POISON");
        public String name;

        private BulletTypes(String name)
        {
            this.name = name;
        }
    }

}
