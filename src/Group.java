import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class Group implements PrimaryKeyManager, Serializable, SortByName, Comparable<Group> {
	private static final long serialVersionUID = 1L;
	// Attributes
	public static int pk = 1;
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
		this.name = "Group "+this.id;
		this.type = type;
		this.capacity = capacity;
		this.professor = null;
		this.students = new HashMap<Integer, Student>();
	}
	public Group(CourseType type, int capacity, Professor prof) {
		this.id = pk;
		autoIncrement(this.id);
		this.name = "Group "+this.id;
		this.type = type;
		this.capacity = capacity;
		this.professor = prof;
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
	public void setCapacity(int capacity) throws MaxCapacityException { 
		if (capacity < getStudents().size()) throw new MaxCapacityException("Capacity must not be less than current student size");
		this.capacity = capacity; 
	}
	public void setProfessor(Professor prof) { this.professor = prof; }
	public void setStudents(HashMap<Integer, Student> students) { this.students = students; }
	
	// Specific Methods
	public int getVacancy() { return (getCapacity() - getStudents().size()); }
	public Student getStudent(int sid) throws KeyErrorException {
		if (students.containsKey(sid)) return students.get(sid);
		else throw new KeyErrorException();
	}
	public void addStudent(Student obj) throws DuplicateKeyException, MaxCapacityException {
		if (getVacancy() <= 0) throw new MaxCapacityException(String.format("%s not enrolled as Group %d is full.", obj.getName(), getID()));
		if (students.containsKey(obj.getID())) 
			throw new DuplicateKeyException(String.format("%s has already enrolled in Group %d", obj.getName(), getID()));
		else students.put(obj.getID(), obj);
	}
	public void rmStudent(Student obj) throws KeyErrorException {
		if (students.containsKey(obj.getID())) students.remove(obj.getID());
		else throw new KeyErrorException(String.format("%s is not enrolled in Group %d", obj.getName(), getID()));
	}
	public String print(String tabs) {
		String msg = new String();
		msg += String.format("%sGroup ID: %d\n", tabs, getID());
		msg += String.format("%sGroup Name: %s\n", tabs, getName());
		msg += String.format("%sGroup Type: %s\n", tabs, getType());
		msg += String.format("%sGroup Capacity: %d\n", tabs, getCapacity());
		msg += String.format("%sRegistered Students: %d\n", tabs, getStudents().size());
		msg += String.format("%sGroup Vacancy: %d\n", tabs, getVacancy());
		msg += String.format("%sGroup Professor: %d, %s\n", tabs, getProfessor().getID(), getProfessor().getName());
		return msg;
	}
	public String printStudents(String tabs, Comparator<SortByName> comparator) {
		String msg = new String();
		msg += String.format("%sStudents list of Group: %d, %s\n", tabs, getID(), getName());
		msg += String.format("%s%-5s | %-10s | %-60s\n", tabs, "NO", "STUDENT ID", "NAME");
		msg += String.format("%s%s\n", tabs, new String(new char[81]).replace('\0','-'));
		ArrayList<Student> studentList = new ArrayList<Student>(getStudents().values());
		if (comparator != null) Collections.sort(studentList, comparator);
		else Collections.sort(studentList);
		for (Student std : studentList) {
			msg += String.format("%s%-5d | %-10d | %-60s\n", tabs, studentList.indexOf(std)+1, std.getID(), std.getName());
		}
		return msg;
	}
	@Override
	public void autoIncrement(int id) {
		if (id < pk) return;
		pk = ++id;
	}
	@Override
	public void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		autoIncrement(this.id);
	}
	@Override
	public int compareTo(Group obj) {
		return (this.getID()-obj.getID());
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Group)) return false;
		Group temp = (Group) obj;
		if (this.id == temp.getID()) return true;
		return false;
	}
	
	public static void main(Group group, School school, Scanner scan) {
		int choice = 0;
		boolean done = false;
		String stringInput = null;
		
		while (!done) {
			System.out.printf("Group ID: %d, Type: %s\n", group.getID(), group.getType());
			System.out.println(group.print("\t"));
			System.out.println("\t1. Print students list by ID.");
			System.out.println("\t2. Print students list by Name.");
			System.out.println("\t3. Edit name.");
			System.out.println("\t4. Edit Capacity.");
			System.out.println("\t5. Edit Professor.");
			System.out.println("\t0. Go back to previous menu.");
			System.out.print("Choice: ");
			choice = scan.nextInt(); scan.nextLine();
			switch (choice) {
				case 0:
					done = true;
					break;
				case 1:
					System.out.println(group.printStudents("\t", (SortByNameComparator)null ));
					break;
				case 2:
					System.out.println(group.printStudents("\t", new SortByNameComparator()));
					break;
				case 3:
					System.out.print("Please input new group name: ");
					stringInput = scan.nextLine();
					group.setName(stringInput);
					break;
				case 4:
					System.out.print("Please input new capacity: ");
					choice = scan.nextInt(); scan.nextLine();
					try {
						group.setCapacity(choice);
					} catch (MaxCapacityException e) {
						System.out.println(e.getMessage());
					}
					break;
				case 5:
					System.out.print("Please input new Professor ID: ");
					choice = scan.nextInt(); scan.nextLine();
					try {
						group.setProfessor(school.getProfessor(choice));
					} catch (KeyErrorException e) {
						System.out.println(e.getMessage());
					}
					break;
				default:
					System.out.println("Error: Invalid choice.");
					break;
			}
		}
	}
}
