package dark.empire.drone.client.animation;

import java.util.HashMap;
import java.util.Map.Entry;

import universalelectricity.core.vector.Vector3;

public class AnimatedRenderer
{
    int stage = 0;
    final int maxStage;
    HashMap<String, Part> bones = new HashMap<String, Part>();
    HashMap<String, Vector3[]> rotation = new HashMap<String, Vector3[]>();
    HashMap<String, Vector3[]> translations = new HashMap<String, Vector3[]>();

    public AnimatedRenderer(int stages, Part... parts)
    {
        this.maxStage = stages;
        for (Part part : parts)
        {
            bones.put(part.name, part);
            rotation.put(part.name, new Vector3[stages]);
            translations.put(part.name, new Vector3[stages]);

        }
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }

    public void nextStage()
    {
        this.setStage(this.getStage() + 1);
        for (Entry<String, Part> entry : bones.entrySet())
        {
            if (entry.getValue() != null)
            {
                Vector3[] r = rotation.get(entry.getKey());
                if (r != null && this.getStage() < r.length && r[this.getStage()] != null)
                {
                    entry.getValue().rotateBy(r[this.getStage()].floatX(), r[this.getStage()].floatY(), r[this.getStage()].floatZ());
                }
                r = translations.get(entry.getKey());
                if (r != null && this.getStage() < r.length && r[this.getStage()] != null)
                {
                    entry.getValue().translateBy(r[this.getStage()].floatX(), r[this.getStage()].floatY(), r[this.getStage()].floatZ());
                }

            }
        }
    }

    public int getStage()
    {
        return stage;
    }

    public int getMaxStage()
    {
        return maxStage;
    }
}
