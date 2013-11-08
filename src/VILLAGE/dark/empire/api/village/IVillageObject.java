package dark.empire.api.village;

/** Implement this to have the object be included in the village is was created in. Make sure to
 * actually have your object register itself with the village. If you implement this you can get all
 * the empire data from the village rather than include it yourself. Use {@link #IVirtualObject} if
 * you want the village to save your object when it saves to the map
 * 
 * @author DarkGuardsman */
public interface IVillageObject
{
    /** Get the village instance */
    public IVillage getVillage();

    /** Sets the village object */
    public void setVillage(IVillage village);
}
