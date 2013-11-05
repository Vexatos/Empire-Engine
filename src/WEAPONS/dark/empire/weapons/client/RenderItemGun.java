package dark.empire.weapons.client;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import universalelectricity.core.item.IItemElectric;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark.empire.weapons.EmpireWeapons;
import dark.empire.weapons.client.models.ModelDBShotGun;
import dark.empire.weapons.client.models.ModelHandGun;
import dark.empire.weapons.client.models.ModelLaserRifle;
import dark.empire.weapons.client.models.ModelShotGun;
import dark.empire.weapons.items.ItemProjectileWeapon;

@SideOnly(Side.CLIENT)
public class RenderItemGun implements IItemRenderer
{

    private static final ModelHandGun HANDGUN = new ModelHandGun();
    private static final ModelShotGun SHOTGUN = new ModelShotGun();
    private static final ModelDBShotGun DBSHOTGUN = new ModelDBShotGun();
    private static final ModelLaserRifle LASERRIFLE = new ModelLaserRifle();
    private static final ResourceLocation TEXTURE_HANDGUN = new ResourceLocation(EmpireWeapons.instance.DOMAIN, EmpireWeapons.MODEL_DIRECTORY + "guns/HandGun.png");
    private static final ResourceLocation TEXTURE_SHOTGUN = new ResourceLocation(EmpireWeapons.instance.DOMAIN, EmpireWeapons.MODEL_DIRECTORY + "guns/PumpShotgun.png");
    private static final ResourceLocation TEXTURE_LASER = new ResourceLocation(EmpireWeapons.instance.DOMAIN, EmpireWeapons.MODEL_DIRECTORY + "guns/LaserRifle.png");
    private static final ResourceLocation TEXTURE_LASER1 = new ResourceLocation(EmpireWeapons.instance.DOMAIN, EmpireWeapons.MODEL_DIRECTORY + "guns/LaserRifle1.png");
    private static final ResourceLocation TEXTURE_LASER2 = new ResourceLocation(EmpireWeapons.instance.DOMAIN, EmpireWeapons.MODEL_DIRECTORY + "guns/LaserRifle2.png");
    private static final ResourceLocation TEXTURE_LASER3 = new ResourceLocation(EmpireWeapons.instance.DOMAIN, EmpireWeapons.MODEL_DIRECTORY + "guns/LaserRifle3.png");
    private static final ResourceLocation TEXTURE_NONE = new ResourceLocation(EmpireWeapons.instance.DOMAIN, EmpireWeapons.MODEL_DIRECTORY + "guns/grey.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        switch (ItemProjectileWeapon.Guns.get(item.getItemDamage()))
        {
            case HandGun:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_HANDGUN);
                break;
            case PumpShotGun:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_SHOTGUN);
                break;
            case LaserRifle:
                if (item.getItem() instanceof IItemElectric)
                {
                    switch ((int) (3 * (((IItemElectric) item.getItem()).getElectricityStored(item) / ((IItemElectric) item.getItem()).getMaxElectricityStored(item))))
                    {
                        case 0:
                            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_LASER3);
                            break;
                        case 1:
                            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_LASER2);
                            break;
                        case 2:
                            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_LASER1);
                            break;
                        case 3:
                            FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_LASER);
                            break;
                    }
                }
                else
                {
                    FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_LASER);
                }
                break;
            case DBShotGun:
            default:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(TEXTURE_NONE);
                break;
        }

        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
        {
            float scale = 2f;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, 1.7f, 0);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glRotatef(40, 0, 1, 0);
            GL11.glRotatef(-5, 1, 0, 0);
        }
        else if (type == ItemRenderType.EQUIPPED)
        {
            float scale = 2f;
            GL11.glScalef(scale, scale, scale);
            GL11.glRotatef(-105, 0, 0, 1);
            GL11.glRotatef(-70, 0, 1, 0);
            GL11.glTranslatef(0.3f, -0.9f, 0.42f);
        }
        switch (ItemProjectileWeapon.Guns.get(item.getItemDamage()))
        {
            case HandGun:
                HANDGUN.render(0.0625F);
                break;
            case PumpShotGun:
                if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
                {
                    GL11.glTranslatef(0f, 0f, .5f);
                }
                SHOTGUN.render(0.0625F);
                break;
            case DBShotGun:
                if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
                {
                    GL11.glTranslatef(0f, 0f, .5f);
                }
                DBSHOTGUN.render(0.0625F);
                break;
            case LaserRifle:
                if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
                {
                    GL11.glTranslatef(0f, 0f, .5f);
                }
                LASERRIFLE.render(0.0625F);
                break;
            default:
        }

        GL11.glPopMatrix();
    }
}
