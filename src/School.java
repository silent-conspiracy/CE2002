import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class School implements Serializable {
	private static final long serialVersionUID = 1L;
	// Attributes
	private String name;
	private HashMap<Integer, Student> students;
	private HashMap<Integer, Professor> professors;
	private HashMap<Integer, Course> courses;
	
	// Constructors
	public School(String name) {
		this.name = name;
		this.students = new HashMap<Integer, Student>();
		this.professors = new HashMap<Integer, Professor>();
		this.courses = new HashMap<Integer, Course>();
	}
	
	// Getters and Setters
	public String getName() { return name; }
	public HashMap<Integer, Student> getStudents() { return students; }
	public HashMap<Integer, Professor> getProfessors() { return professors; }
	public HashMap<Integer, Course> getCourses() { return courses; }
	
	public void setName(String name) { this.name = name; }
	public void setStudents(HashMap<Integer, Student> students) { this.students = students; }
	public void setProfessors(HashMap<Integer, Professor> profs) { this.professors = profs; }
	public void setCourses(HashMap<Integer, Course> courses) { this.courses = courses; }
	
	// Specific Methods
	public void addStudent(Student student) throws DuplicateKeyException {
		if (students.containsValue(student)) throw new DuplicateKeyException();
		else students.put(student.getID(), student);
	}
	public void addProfessor(Professor prof) throws DuplicateKeyException {
		if (professors.containsValue(prof)) throw new DuplicateKeyException();
		else professors.put(prof.getID(), prof);
	}
	public void addCourse(Course course) throws DuplicateKeyException {
		if (courses.containsValue(course)) throw new DuplicateKeyException();
		else courses.put(course.getID(), course);
	}
	public void rmStudent(Student student) throws KeyErrorException {
		if (students.containsKey(student.getID())) students.remove(student.getID());
		else throw new KeyErrorException();
	}
	public void rmProfessor(Professor prof) throws KeyErrorException {
		if (professors.containsKey(prof.getID())) professors.remove(prof.getID());
		else throw new KeyErrorException();
	}
	public void rmCourse(Course course) throws KeyErrorException {
		if (courses.containsKey(course.getID())) courses.remove(course.getID());
		else throw new KeyErrorException();
	}
	
	public static void main(String[] args) {
		School school = new School("School of Computer Engineering");
		Professor prof = new Professor("Mark", Gender.MALE, school);
		Student stud = new Student("Wei", Gender.MALE, school);
		HashMap<String, Double> weights = new HashMap<String, Double>();
		weights.put("Project", 100.0);
		Course course = new Course("Bed", 16, CourseType.LAB, prof, 1, weights);
		course.getGroups().addLab(1);
		HashMap<CourseType, Integer> groupid = (new HashMap<CourseType, Integer>());
		groupid.put(CourseType.LAB, 1);
		try {
			CourseGroup groups = course.getGroups();
			groups.addStudent(stud, course.getType(), groupid);
			System.out.println(groups.getStudents());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		school.professors.put(prof.getID(), prof);
		school.students.put(stud.getID(), stud);
		school.courses.put(course.getID(), course);
		
		try {
			FileOutputStream fos = new FileOutputStream("school.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(school);
			oos.close();
			
			FileOutputStream fos2 = new FileOutputStream("professors.dat");
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
			
			oos2.writeObject(school.getProfessors());
			oos2.close();
			
			FileOutputStream fos3 = new FileOutputStream("students.dat");
			ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
			
			oos3.writeObject(school.getStudents());
			oos3.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			FileInputStream fis = new FileInputStream("school.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			School sch = (School) ois.readObject();
			System.out.println("School: " + sch.getName());
			System.out.println("Professor: " + sch.professors.get(1).getName());
			System.out.println("Student: " + sch.students.get(1).getName());
			System.out.println("Student Object: " + sch.students.get(1));
			System.out.println("Course: " + sch.courses.get(1).getName());
			System.out.println(sch.courses.get(1).getGroups().getStudents());
			sch.courses.get(1).getGroups().getStudents();
			ois.close();
			
			FileInputStream fis2 = new FileInputStream("students.dat");
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			
			HashMap stds = (HashMap) ois2.readObject();
			System.out.println("Student Object: " + stds.get(1));
			
			ois2.close();
			FileInputStream fis3 = new FileInputStream("professors.dat");
			ObjectInputStream ois3 = new ObjectInputStream(fis3);
			
			HashMap profs = (HashMap) ois3.readObject();
			System.out.println("Prof Object: " + profs.get(1));
			
			ois3.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Done reading.");
	}
}