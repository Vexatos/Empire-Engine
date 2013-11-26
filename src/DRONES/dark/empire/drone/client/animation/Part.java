package dark.empire.drone.client.animation;

import net.minecraft.client.model.ModelRenderer;
import universalelectricity.core.vector.Vector3;

public class Part
{
    final Vector3 defaultPos, defaultRotation;
    String name;
    Part child, parent;
    ModelRenderer peace;
    /** Can the pos be changed */
    boolean lockPos = false;
    /** Locks the rotation to that of its parent */
    boolean[] lockedRotation = new boolean[3];

    public Part(String name, ModelRenderer peace, Part child, boolean lockPos)
    {
        this.name = name;
        this.child = child;
        this.peace = peace;
        this.lockPos = lockPos;
        child.parent = this;
        defaultPos = new Vector3(peace.rotationPointX, peace.rotationPointY, peace.rotationPointZ);
        defaultRotation = new Vector3(peace.rotateAngleX, peace.rotateAngleY, peace.rotateAngleZ);
    }

    public ModelRenderer rotateBy(float rx, float ry, float rz)
    {
        return update(this.peace.rotateAngleX + rx, this.peace.rotateAngleY + ry, this.peace.rotateAngleZ + rz);
    }

    public ModelRenderer translateBy(float rx, float ry, float rz)
    {
        if (!this.lockPos)
        {
            if (this.child != null)
            {
                this.child.translateBy(rx, ry, rz);
            }
            this.peace.rotationPointX += rx;
            this.peace.rotationPointY += ry;
            this.peace.rotationPointZ += rz;
        }
        return this.peace;
    }

    public ModelRenderer update(float rx, float ry, float rz)
    {
        //Update rotation
        if (lockedRotation[0])
        {
            if (parent != null)
            {
                this.peace.rotateAngleX = parent.peace.rotateAngleX;
            }
            else
            {
                this.peace.rotateAngleX = (float) this.defaultRotation.x;
            }
        }
        else
        {
            this.peace.rotateAngleX = rx;
        }
        if (lockedRotation[1])
        {
            if (parent != null)
            {
                this.peace.rotateAngleY = parent.peace.rotateAngleY;
            }
            else
            {
                this.peace.rotateAngleY = (float) this.defaultRotation.y;
            }
        }
        else
        {
            this.peace.rotateAngleX = ry;
        }
        if (lockedRotation[2])
        {
            if (parent != null)
            {
                this.peace.rotateAngleZ = parent.peace.rotateAngleZ;
            }
            else
            {
                this.peace.rotateAngleZ = (float) this.defaultRotation.z;
            }
        }
        else
        {
            this.peace.rotateAngleX = rz;
        }
        //Tell the child part to update its position from the new rotation
        if (this.child != null)
        {
            if (this.child.parent != this)
            {
                this.child.parent = this;
            }
            this.child.updatePosition(0, 0, 0);
        }

        return this.peace;
    }

    public void updatePosition(float x, float y, float z)
    {
        if (this.lockPos)
        {
            this.peace.setRotationPoint((float) defaultPos.x, (float) defaultPos.y, (float) defaultPos.z);
        }
        else if (this.parent != null)
        {
            //Ignore coords as this part is locked to its parent
            // Quaternion quat = new Quaternion();
            //quat.FromEuler(x, y, z);
            Vector3 vec = new Vector3(parent.peace.rotationPointX, parent.peace.rotationPointY, parent.peace.rotationPointZ);
            //quat.multi(vec);
            this.peace.setRotationPoint((float) vec.x, (float) vec.y, (float) vec.z);
        }
        else
        {
            this.peace.setRotationPoint(x, y, z);
        }
    }

    public ModelRenderer reset()
    {
        this.peace.setRotationPoint((float) defaultPos.x, (float) defaultPos.y, (float) defaultPos.z);
        this.peace.rotateAngleX = (float) defaultRotation.x;
        this.peace.rotateAngleY = (float) defaultRotation.y;
        this.peace.rotateAngleZ = (float) defaultRotation.z;
        return this.peace;
    }
}
