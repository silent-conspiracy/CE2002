import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class CourseGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	// Attributes
	private int capacity;
	private Group lecture;
	private HashMap<Integer, Group> tutorials;
	private HashMap<Integer, Group> labs;
	
	// Constructor
	public CourseGroup(CourseType type, int capacity) {
		this.capacity = capacity;
		// Constructing lecture, tutorial and lab groups according to type.
		// Null values allowed.
		if (type.getLectures().contains(type))
			setLecture();
		if (type.getTutorials().contains(type))
			setTutorials();
		if (type.getLabs().contains(type))
			setLabs();
	}
	
	// Getters and Setters
	public int getCapacity() { return capacity; }
	public Group getLecture() { return lecture; }
	public HashMap<Integer, Group> getTutorials() { return tutorials; }
	public HashMap<Integer, Group> getLabs() { return labs; }
	
	public void setCapacity(int capacity) throws MaxCapacityException {
		if (capacity < getStudents().size()) throw new MaxCapacityException("Capacity must not be less that current size");
		else this.capacity = capacity;
	}
	public void setLecture(Group lecture) { this.lecture = lecture; }
	public void setTutorials(HashMap<Integer, Group> tutorials) { this.tutorials = tutorials; }
	public void setLabs(HashMap<Integer, Group> labs) { this.labs = labs; }
	
	// Specific methods
	public void setLecture() { lecture = new Group(CourseType.LECTURE, capacity); }
	public void setTutorials() { tutorials = new HashMap<Integer, Group>(); }
	public void setLabs() { labs = new HashMap<Integer, Group>(); }
	
	public Group getTutorial(int gid) throws KeyErrorException { 
		if (tutorials.containsKey(gid)) return (Group)tutorials.get(gid);
		else throw new KeyErrorException();
	}
	public Group getLab(int gid) throws KeyErrorException {
		if (labs.containsKey(gid)) return (Group)labs.get(gid);
		else throw new KeyErrorException();
	}
	public void addTutorial(int capacity) {
		if (tutorials == null) return;
		Group tutorial = new Group(CourseType.TUTORIAL, capacity);
		tutorials.put(tutorial.getID(), tutorial);
	}
	public void addLab(int capacity) {
		if (labs == null) return;
		Group lab = new Group(CourseType.LAB, capacity);
		labs.put(lab.getID(), lab);
	}
	public void rmTutorial(int id) throws KeyErrorException, MaxCapacityException {
		if (tutorials.containsKey(id)) {
			if (tutorials.get(id).getStudents().size() == 0) tutorials.remove(id);
			else throw new MaxCapacityException("Please ensure tutorial group is empty before closing.");
		} else throw new KeyErrorException(String.format("Tutorial ID %d not found",id)); 
	}
	public void rmLab(int id) throws KeyErrorException, MaxCapacityException {
		if (labs.containsKey(id)) {
			if (labs.get(id).getStudents().size() == 0) labs.remove(id);
			else throw new MaxCapacityException("Please ensure tutorial group is empty before closing.");
		} else throw new KeyErrorException(String.format("Tutorial ID %d not found",id)); 
	}
	public int getVacancy() { return (getCapacity() - getStudents().size()); }
	public HashSet<Student> getStudents() {
		HashSet<Student> students = new HashSet<Student>();
		if (lecture != null) {
			for (int i : lecture.getStudents().keySet()) {
				try {
					students.add(lecture.getStudent(i));
				} catch (KeyErrorException e) {
					// Pass
				}
			}
		}
		if (tutorials != null) {
			for (int i : tutorials.keySet()) {
				for (int j : tutorials.get(i).getStudents().keySet()) {
					try {
						students.add(tutorials.get(i).getStudent(j));
					} catch (KeyErrorException e) {
						// Pass
					}
				}
			}
		}
		if (labs != null) {
			for (int i : labs.keySet()) {
				for (int j : labs.get(i).getStudents().keySet()) {
					try {
						students.add(labs.get(i).getStudent(j));
					} catch (KeyErrorException e) {
						// Pass
					}
				}
			}
		}
		return students;
	}
	public void addStudent(Student obj, CourseType type, HashMap<CourseType, Integer> gid) throws KeyErrorException, DuplicateKeyException, NullPointerException, MaxCapacityException {
		// Adds student according to the course types
		// Depending on course types, gid must have group ids for tutorials and labs
		// Method overloading not used as to reduce copy and pasting, and number of methods.
		if (getVacancy() <= 0) throw new MaxCapacityException(String.format("No more vacancy."));
		try{
			if (getStudents().contains(obj)) throw new DuplicateKeyException(String.format("Student %s is already enrolled.", obj.getName()));
			if (type.getLectures().contains(type)) {
				if (lecture == null) setLecture();
				lecture.addStudent(obj);
			}
			if (type.getTutorials().contains(type)) {
				if (gid.get(CourseType.TUTORIAL) == null) throw new NullPointerException("Error: Tutorial Group ID must be specified.");
				int id = gid.get(CourseType.TUTORIAL);
				if (tutorials.containsKey(id)) {
					if (tutorials == null) setTutorials();
					tutorials.get(gid.get(CourseType.TUTORIAL)).addStudent(obj);
				}
				else throw new KeyErrorException(String.format("Tutorial ID %d not found.", id));
			}
			if (type.getLabs().contains(type)) {
				if (gid.get(CourseType.LAB) == null) throw new NullPointerException("Error: Lab Group ID must be specified.");
				int id = gid.get(CourseType.LAB);
				if (labs.containsKey(id)) {
					if (labs == null) setLabs();
					labs.get(gid.get(CourseType.LAB)).addStudent(obj);
				}
				else throw new KeyErrorException(String.format("Lab ID %d not found.", id));
			}
		} catch (MaxCapacityException e) {
			System.out.println("\n"+e.getMessage());
			rmStudent(obj, type);
			System.out.println("Please register the student again in another group.");
		}
	}
	public void rmStudent(Student obj, CourseType type) throws KeyErrorException {
		// Iterates all groups and remove the student completely.
		if (!getStudents().contains(obj)) throw new KeyErrorException(String.format("Student %d, %s is not registered.", obj.getID(), obj.getName()));
		if (type.getLectures().contains(type)) lecture.rmStudent(obj);
		if (type.getTutorials().contains(type)) {
			for(Integer id : tutorials.keySet()) {
				try {
					tutorials.get(id).rmStudent(obj);
				} catch (KeyErrorException e) {
					// Pass
				}
			}
		}
		if (type.getLabs().contains(type)) {
			for(Integer id : labs.keySet()) {
				try {
					labs.get(id).rmStudent(obj);
				} catch (KeyErrorException e) {
					// Pass
				}
			}
		}
	}
}
