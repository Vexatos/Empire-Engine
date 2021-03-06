// Date: 11/2/2013 12:06:20 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package dark.empire.weapons.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelHandGun extends ModelBase
{
    //fields
    ModelRenderer Grip;
    ModelRenderer slide;
    ModelRenderer body;
    ModelRenderer body2;
    ModelRenderer Grip2;
    ModelRenderer muzz;
    ModelRenderer guard;

    public ModelHandGun()
    {
        textureWidth = 64;
        textureHeight = 32;

        Grip = new ModelRenderer(this, 10, 24);
        Grip.addBox(0F, 0F, 0F, 2, 5, 2);
        Grip.setRotationPoint(0F, 19F, 0F);
        Grip.setTextureSize(64, 32);
        Grip.mirror = true;
        setRotation(Grip, 0.418879F, 0F, 0F);
        slide = new ModelRenderer(this, 25, 0);
        slide.addBox(0F, 0F, 0F, 2, 2, 10);
        slide.setRotationPoint(0F, 16F, -7F);
        slide.setTextureSize(64, 32);
        slide.mirror = true;
        setRotation(slide, 0F, 0F, 0F);
        body = new ModelRenderer(this, 0, 0);
        body.addBox(0F, 0F, 0F, 1, 1, 9);
        body.setRotationPoint(0.5F, 18F, -6F);
        body.setTextureSize(64, 32);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
        body2 = new ModelRenderer(this, 0, 0);
        body2.addBox(0F, 0F, 0F, 2, 1, 4);
        body2.setRotationPoint(0F, 18F, -1F);
        body2.setTextureSize(64, 32);
        body2.mirror = true;
        setRotation(body2, 0F, 0F, 0F);
        Grip2 = new ModelRenderer(this, 0, 0);
        Grip2.addBox(0F, 0F, 0F, 1, 5, 1);
        Grip2.setRotationPoint(0.5F, 19F, 0F);
        Grip2.setTextureSize(64, 32);
        Grip2.mirror = true;
        setRotation(Grip2, 0.3490659F, 0F, 0F);
        muzz = new ModelRenderer(this, 0, 0);
        muzz.addBox(0.5F, 0.5F, 0.5F, 1, 1, 1);
        muzz.setRotationPoint(0F, 16F, -7.8F);
        muzz.setTextureSize(64, 32);
        muzz.mirror = true;
        setRotation(muzz, 0F, 0F, 0F);
        guard = new ModelRenderer(this, 0, 0);
        guard.addBox(0F, 0F, 0F, 1, 2, 1);
        guard.setRotationPoint(0.5F, 19F, -2F);
        guard.setTextureSize(64, 32);
        guard.mirror = true;
        setRotation(guard, 0F, 0F, 0F);
    }

    public void render(float f5)
    {
        Grip.render(f5);
        slide.render(f5);
        body.render(f5);
        body2.render(f5);
        Grip2.render(f5);
        muzz.render(f5);
        guard.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
