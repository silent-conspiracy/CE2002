import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;


public interface PrimaryKeyManager extends Serializable {
	// PrimaryKeyManager increments the ID of the objects,
	// making sure there is no duplicate IDs.
	// Interface for incrementing pk values.
	// One public static variable and one static method needed.
	
	// public static int pk = 1;
	// public static void autoIncrement(int id);
	public abstract void autoIncrement(int id);
	// Needed for reading ID and increment the value with the ID.
	public abstract void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException;
	
}
