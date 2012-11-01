
public interface PrimaryKeyManager {
	// PrimaryKeyManager increments the ID of the objects,
	// making sure there is no duplicate IDs.
	// Interface for incrementing pk values.
	public abstract void autoIncrement(int id);
}
