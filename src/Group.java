import java.util.HashMap;

public class Group implements PrimaryKeyManager {
	// Attributes
	private static int pk = 1;
	private int id;
	private String name;
	private int capacity;
	private CourseType type;
	private Professor professor;
	private HashMap<Integer, Student> students;
	
	// Constructor
	public Group(CourseType type, int capacity) {
		this.id = pk;
		autoIncrement(this.id);
		this.type = type;
		this.capacity = capacity;
		this.students = new HashMap<Integer, Student>();
	}
	
	// Getters and Setters
	public int getID() { return id; }
	public String getName() { return name; }
	public int getCapacity() { return capacity; }
	public CourseType getType() { return type; }
	public Professor getProfessor() { return professor; }
	public HashMap<Integer, Student> getStudents() { return students; }
	
	public void setID(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setType(CourseType type) { this.type = type; }
	public void setCapacity(int capacity) { this.capacity = capacity; }
	public void setProfessor(Professor prof) { this.professor = prof; }
	
	// Specific Methods
	public int getVacancy() { return (getCapacity() - getStudents().size()); }
	public Student getStudent(int sid) throws KeyErrorException {
		if (students.containsKey(sid)) return students.get(sid);
		else throw new KeyErrorException();
	}
	public void addStudent(Student obj) throws DuplicateKeyException, MaxCapacityException {
		if (getCapacity() - getStudents().size() <= 0) throw new MaxCapacityException(String.format("%s not enrolled as Group %d is full.", obj.getName(), getID()));
		if (students.containsKey(obj.getID())) 
			throw new DuplicateKeyException(String.format("%s has already enrolled in Group %d", obj.getName(), getID()));
	}
	public void rmStudent(Student obj) throws KeyErrorException {
		if (students.containsKey(obj.getID())) students.remove(obj.getID());
		else throw new KeyErrorException(String.format("%s is not enrolled in Group %d", obj.getName(), getID()));
	}
	
	@Override
	public void autoIncrement(int id) {
		pk = ++id;
	}
}
