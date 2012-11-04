import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;


public class SaveData implements Serializable {
	// Store all relevant SCRAME data.
	private static final long serialVersionUID = 1L;
	
	// Attributes
	private int currentSemester;
	private HashSet<School> schools;
	private HashMap<Integer, Semester> semesters;
	
	// Constructor
	public SaveData() {
		this.schools = new HashSet<School>();
		this.semesters = new HashMap<Integer, Semester>();
	}
	
	// Getters and setters
	public Semester getCurrentSemester() { return semesters.get(currentSemester); }
	public HashSet<School> getSchools() { return schools; }
	public HashMap<Integer, Semester> getSemesters() { return semesters; }
	
	public void setCurrentSemester(int sem) { this.currentSemester = sem; }
	public void setSchools(HashSet<School> schools) { this.schools = schools; }
	public void setSemesters(HashMap<Integer, Semester> semesters) { this.semesters = semesters; }
	
	// Specific methods
	public void addSchool(School school) throws DuplicateKeyException {
		if (schools.contains(school)) throw new DuplicateKeyException(String.format("School %s already exist.",school.getName()));
		else schools.add(school);
	}
	public void rmSchool(School school) throws KeyErrorException {
		if (schools.contains(school)) schools.remove(school);
		else throw new KeyErrorException(String.format("School %s does not exist.", school.getName()));
	}
	public void addSemester(Semester semester) throws DuplicateKeyException {
		if (semesters.containsKey(semester.getID())) throw new DuplicateKeyException(String.format("Semester %s already exist.",semester.getName()));
		else semesters.put(semester.getID(), semester);
	}
	public void rmSemester(Semester semester) throws KeyErrorException {
		if (semesters.containsKey(semester.getID())) schools.remove(semester);
		else throw new KeyErrorException(String.format("School %s does not exist.", semester.getName()));
	}
	
}
