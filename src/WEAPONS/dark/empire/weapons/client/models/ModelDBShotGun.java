// Date: 11/2/2013 1:24:05 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package dark.empire.weapons.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelDBShotGun extends ModelBase
{
    //fields
    ModelRenderer Grip;
    ModelRenderer slide;
    ModelRenderer body2;
    ModelRenderer Grip2;
    ModelRenderer muzz;
    ModelRenderer guard;
    ModelRenderer leftBarrel;
    ModelRenderer rightBarrel;
    ModelRenderer leftBarrel2;
    ModelRenderer rightBarrel2;
    ModelRenderer body3;
    ModelRenderer body4;
    ModelRenderer body5;
    ModelRenderer body6;
    ModelRenderer body7;

    public ModelDBShotGun()
    {
        textureWidth = 64;
        textureHeight = 32;

        Grip = new ModelRenderer(this, 10, 24);
        Grip.addBox(0F, 0F, 0F, 2, 5, 2);
        Grip.setRotationPoint(0F, 19F, 0F);
        Grip.setTextureSize(64, 32);
        Grip.mirror = true;
        setRotation(Grip, 1.134464F, 0F, 0F);
        slide = new ModelRenderer(this, 25, 0);
        slide.addBox(-1.1F, 0F, 0F, 2, 2, 4);
        slide.setRotationPoint(0F, 16F, -2F);
        slide.setTextureSize(64, 32);
        slide.mirror = true;
        setRotation(slide, 0F, 0F, 0F);
        body2 = new ModelRenderer(this, 0, 0);
        body2.addBox(0F, 0F, 0F, 2, 1, 5);
        body2.setRotationPoint(0F, 18F, -3F);
        body2.setTextureSize(64, 32);
        body2.mirror = true;
        setRotation(body2, 0F, 0F, 0F);
        Grip2 = new ModelRenderer(this, 0, 0);
        Grip2.addBox(0F, 0F, 0F, 1, 5, 1);
        Grip2.setRotationPoint(0.5F, 19F, 0F);
        Grip2.setTextureSize(64, 32);
        Grip2.mirror = true;
        setRotation(Grip2, 1.047198F, 0F, 0F);
        muzz = new ModelRenderer(this, 0, 0);
        muzz.addBox(0.5F, 0.5F, 0F, 1, 1, 4);
        muzz.setRotationPoint(0F, 16F, -2F);
        muzz.setTextureSize(64, 32);
        muzz.mirror = true;
        setRotation(muzz, 0F, 0F, 0F);
        guard = new ModelRenderer(this, 0, 0);
        guard.addBox(0F, 0F, 0F, 1, 2, 1);
        guard.setRotationPoint(0.5F, 19F, -2F);
        guard.setTextureSize(64, 32);
        guard.mirror = true;
        setRotation(guard, 0F, 0F, 0F);
        leftBarrel = new ModelRenderer(this, 25, 0);
        leftBarrel.addBox(1.1F, 0F, 0F, 2, 2, 4);
        leftBarrel.setRotationPoint(0F, 16F, -2F);
        leftBarrel.setTextureSize(64, 32);
        leftBarrel.mirror = true;
        setRotation(leftBarrel, 0F, 0F, 0F);
        rightBarrel = new ModelRenderer(this, 25, 0);
        rightBarrel.addBox(-1.1F, 0F, 0F, 2, 2, 4);
        rightBarrel.setRotationPoint(0F, 16F, -2F);
        rightBarrel.setTextureSize(64, 32);
        rightBarrel.mirror = true;
        setRotation(rightBarrel, 0F, 0F, 0F);
        leftBarrel2 = new ModelRenderer(this, 25, 0);
        leftBarrel2.addBox(1.1F, 0F, 0F, 2, 2, 16);
        leftBarrel2.setRotationPoint(0F, 16F, -18.01F);
        leftBarrel2.setTextureSize(64, 32);
        leftBarrel2.mirror = true;
        setRotation(leftBarrel2, 0F, 0F, 0F);
        rightBarrel2 = new ModelRenderer(this, 25, 0);
        rightBarrel2.addBox(-1.1F, 0F, 0F, 2, 2, 16);
        rightBarrel2.setRotationPoint(0F, 16F, -18.01F);
        rightBarrel2.setTextureSize(64, 32);
        rightBarrel2.mirror = true;
        setRotation(rightBarrel2, 0F, 0F, 0F);
        body3 = new ModelRenderer(this, 0, 0);
        body3.addBox(-0.5F, 0F, 0F, 1, 2, 15);
        body3.setRotationPoint(1F, 17F, -17F);
        body3.setTextureSize(64, 32);
        body3.mirror = true;
        setRotation(body3, 0F, 0F, 0F);
        body4 = new ModelRenderer(this, 0, 0);
        body4.addBox(0F, 0F, 0F, 2, 3, 3);
        body4.setRotationPoint(0F, 17F, 2F);
        body4.setTextureSize(64, 32);
        body4.mirror = true;
        setRotation(body4, 0F, 0F, 0F);
        body5 = new ModelRenderer(this, 0, 0);
        body5.addBox(1.5F, 0F, 0F, 3, 4, 5);
        body5.setRotationPoint(-2F, 16.5F, 5F);
        body5.setTextureSize(64, 32);
        body5.mirror = true;
        setRotation(body5, -0.0698132F, 0F, 0F);
        body6 = new ModelRenderer(this, 0, 0);
        body6.addBox(-1.5F, 0F, 0F, 3, 2, 1);
        body6.setRotationPoint(1F, 16.1F, 2F);
        body6.setTextureSize(64, 32);
        body6.mirror = true;
        setRotation(body6, 0F, 0F, 0F);
        body7 = new ModelRenderer(this, 0, 0);
        body7.addBox(1.5F, 0F, 0F, 3, 4, 5);
        body7.setRotationPoint(-2F, 16.5F, 5F);
        body7.setTextureSize(64, 32);
        body7.mirror = true;
        setRotation(body7, 0.122173F, 0F, 0F);
    }

    public void render(float f5)
    {
        Grip.render(f5);
        slide.render(f5);
        body2.render(f5);
        Grip2.render(f5);
        muzz.render(f5);
        guard.render(f5);
        leftBarrel.render(f5);
        rightBarrel.render(f5);
        leftBarrel2.render(f5);
        rightBarrel2.render(f5);
        body3.render(f5);
        body4.render(f5);
        body5.render(f5);
        body6.render(f5);
        body7.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
