import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
		if (capacity < getStudents().size()) throw new MaxCapacityException("Capacity must not be less than current size");
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
		HashSet<Student> temp = null;
		if (lecture != null) {
			temp = getStudentsInLecture();
			for (Student student : temp) students.add(student);
		}
		if (tutorials != null) {
			for(int i : tutorials.keySet()) {
				temp = getStudentsInTutorial(tutorials.get(i));
				for (Student student : temp) students.add(student);
			}
		}
		if (labs != null) {
			for(int i : labs.keySet()) {
				temp = getStudentsInLab(labs.get(i));
				for (Student student : temp) students.add(student);
			}
		}
		return students;
	}
	public HashSet<Student> getStudentsInLecture() {
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
		return students;
	}
	public HashSet<Student> getStudentsInTutorial(Group tutorial) {
		HashSet<Student> students = new HashSet<Student>();
		for (int j : tutorial.getStudents().keySet()) {
			try {
				students.add(tutorial.getStudent(j));
			} catch (KeyErrorException e) {
				// Pass
			}
		}
		return students;
	}
	public HashSet<Student> getStudentsInLab(Group lab) {
		HashSet<Student> students = new HashSet<Student>();
		for (int j : lab.getStudents().keySet()) {
			try {
				students.add(lab.getStudent(j));
			} catch (KeyErrorException e) {
				// Pass
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
	public void printStudents() {
		System.out.println(printStudents(""));
	}
	public String printStudents(String tabs) {
		String msg = new String();
		if (getLecture() != null) {
			msg += printStudentsInLecture(tabs+"\t");
		}
		if (getTutorials() != null) {
			for (Group tutorial : getTutorials().values()) {
				msg += printStudentsInTutorial(tabs+"\t", tutorial);
			}
		}
		if (getLabs() != null) {
			for (Group lab : getLabs().values()) {
				msg += printStudentsInLab(tabs+"\t", lab);
			}
		}
		return msg;
	}
	public String printStudentsInLecture(String tabs) {
		String msg = new String();
		ArrayList<Student> students = null;
		students = new ArrayList<Student>(getStudentsInLecture());
		int counter = 0;
		Collections.sort(students, new SortByNameComparator());
		msg += String.format("%sLecture ID, Name: %d, %d\n", tabs, getLecture().getID(), getLecture().getName());
		msg += String.format("%sLecture Vacancy: %d / %d\n", tabs, students.size(), getLecture().getCapacity());
		if (students.size() > 0) msg += String.format("%sStudent List:\n", tabs, students.size(), getLecture().getCapacity());
		for (Student student : students) {
			msg += String.format("%s\t%d. %s, ID: %d", tabs, ++counter, student.getName(), student.getID());
		}
		return msg+'\n';
	}
	public String printStudentsInTutorial(String tabs, Group tutorial) {
		String msg = new String();
		ArrayList<Student> students = null;
		students = new ArrayList<Student>(getStudentsInTutorial(tutorial));
		int counter = 0;
		Collections.sort(students, new SortByNameComparator());
		msg += String.format("%sTutorial ID, Name: %d, %d\n", tabs, tutorial.getID(), tutorial.getName());
		msg += String.format("%sTutorial Vacancy: %d / %d\n", tabs, students.size(), tutorial.getCapacity());
		if (students.size() > 0) msg += String.format("%sStudent List:\n", tabs, students.size(), tutorial.getCapacity());
		for (Student student : students) {
			msg += String.format("%s\t%d. %s, ID: %d", tabs, ++counter, student.getName(), student.getID());
		}
		return msg+'\n';
	}
	public String printStudentsInLab(String tabs, Group lab) {
		String msg = new String();
		ArrayList<Student> students = null;
		students = new ArrayList<Student>(getStudentsInLab(lab));
		int counter = 0;
		Collections.sort(students, new SortByNameComparator());
		msg += String.format("%sTutorial ID, Name: %d, %d\n", tabs, lab.getID(), lab.getName());
		msg += String.format("%sTutorial Vacancy: %d / %d\n", tabs, students.size(), lab.getCapacity());
		if (students.size() > 0) msg += String.format("%sStudent List:\n", tabs, students.size(), lab.getCapacity());
		for (Student student : students) {
			msg += String.format("%s\t%d. %s, ID: %d", tabs, ++counter, student.getName(), student.getID());
		}
		return msg+'\n';
	}
}