import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class CourseGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	// Attributes
	private int capacity;
	private Course course;
	private Group lecture;
	private HashMap<Integer, Group> tutorials;
	private HashMap<Integer, Group> labs;
	
	// Constructor
	public CourseGroup(Course course, int capacity) {
		this.capacity = capacity;
		this.course = course;
		// Constructing lecture, tutorial and lab groups according to type.
		// Null values allowed.
		CourseType type = course.getType();
		if (type.getLectures().contains(type))
			setLecture();
		if (type.getTutorials().contains(type))
			setTutorials();
		if (type.getLabs().contains(type))
			setLabs();
	}
	
	// Getters and Setters
	public int getCapacity() { return capacity; }
	public Course getCourse() { return course; }
	public Group getLecture() { return lecture; }
	public HashMap<Integer, Group> getTutorials() { return tutorials; }
	public HashMap<Integer, Group> getLabs() { return labs; }
	
	public void setCapacity(int capacity) throws MaxCapacityException {
		if (capacity < getStudents().size()) throw new MaxCapacityException("Capacity must not be less than current size");
		else this.capacity = capacity;
	}
	public void setCourse(Course course) { this.course = course; }
	public void setLecture(Group lecture) { this.lecture = lecture; }
	public void setTutorials(HashMap<Integer, Group> tutorials) { this.tutorials = tutorials; }
	public void setLabs(HashMap<Integer, Group> labs) { this.labs = labs; }
	
	// Specific methods
	public void setLecture() { lecture = new Group(CourseType.LECTURE, capacity, getCourse().getCoordinator()); }
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
		Group tutorial = new Group(CourseType.TUTORIAL, capacity, getCourse().getCoordinator());
		tutorials.put(tutorial.getID(), tutorial);
	}
	public void addLab(int capacity) {
		if (labs == null) return;
		Group lab = new Group(CourseType.LAB, capacity, getCourse().getCoordinator());
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
	public HashMap<CourseType, Integer> getGroups(Student obj) {
		HashMap<CourseType, Integer> groups = new HashMap<CourseType,Integer>();
		if (getLecture() != null) {
			if (getStudentsInLecture().contains(obj)) 
				groups.put(CourseType.LECTURE, getLecture().getID());
		}
		if(getTutorials() != null) {
			for (Group tutorial : getTutorials().values())
				if (getStudentsInTutorial(tutorial).contains(obj)) 
					groups.put(CourseType.TUTORIAL, tutorial.getID());
		}
		if(getLabs() != null) {
			for (Group lab : getLabs().values())
				if (getStudentsInLab(lab).contains(obj)) 
					groups.put(CourseType.LAB, lab.getID());
		}
		return groups;
	}
	public void resetGroups(CourseType type) {
		HashSet<Student> students = getStudents();
		Group group = null;
		HashMap<Integer, Group> groups = null;
		if (type.getLectures().contains(type)) {
			if (getLecture() == null) {
				setLecture();
				for (Student std : students) {
					try {
						getLecture().addStudent(std);
					} catch (DuplicateKeyException e) {
						// Pass
					} catch (MaxCapacityException f) {
						// Pass
					}
				}
			}
		} else setLecture(group);
		if (type.getTutorials().contains(type)) {
			if (getTutorials() == null) {
				setTutorials();
				addTutorial(getCapacity());
				Group tutorial = new ArrayList<Group>(getTutorials().values()).get(0);
				for (Student std : students) {
					try {
						tutorial.addStudent(std);
					} catch (DuplicateKeyException e) {
						// Pass
					} catch (MaxCapacityException f) {
						// Pass
					}
				}
			}
		} else setTutorials(groups);
		if (type.getLabs().contains(type)) {
			if (getLabs() == null) {
				setLabs();
				addLab(getCapacity());
				Group lab = new ArrayList<Group>(getLabs().values()).get(0);
				for (Student std : students) {
					try {
						lab.addStudent(std);
					} catch (DuplicateKeyException e) {
						// Pass
					} catch (MaxCapacityException f) {
						// Pass
					}
				}
			}
		} else setLabs(groups);
	}
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
	public void addStudent(Student obj, HashMap<CourseType, Integer> gid) throws KeyErrorException, DuplicateKeyException, NullPointerException, MaxCapacityException {
		// Adds student according to the course types
		// Depending on course types, gid must have group ids for tutorials and labs
		// Method overloading not used as to reduce copy and pasting, and number of methods.
		CourseType type = getCourse().getType();
		if (getVacancy() <= 0) throw new MaxCapacityException("No more vacancy");
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
			rmStudent(obj);
			System.out.println("Please register the student again in another group.");
		}
	}
	public void rmStudent(Student obj) throws KeyErrorException {
		// Iterates all groups and remove the student completely.
		CourseType type = getCourse().getType();
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
	public String print(String tabs) {
		String msg = new String();
		if (getLecture() != null) {
			msg += String.format("%sLecture Group ID: %d\n", tabs, getLecture().getID());
			msg += String.format("%s\tNo. of Students: %d\n", tabs, getLecture().getStudents().size());
		}
		if (getTutorials() != null) {
			msg += String.format("%sTutorial Groups: %d group(s)\n", tabs, getTutorials().size());
			if (getTutorials().size() < 1) {
				msg += String.format("%s\tError: Please create tutorial groups in Course Manager -> Edit Course -> Edit CourseGroup.\n", tabs);
			} else msg += printTutorials(tabs+"\t");
		}
		if (getLabs() != null) {
			msg += String.format("%sLab Groups: %d group(s)\n", tabs, getLabs().size());
			if (getLabs().size() < 1) {
				msg += String.format("%s\tError: Please create lab groups in Course Manager -> Edit Course -> Edit CourseGroup.\n", tabs);
			} else msg += printLabs(tabs+"\t");
		}
		msg += String.format("%sTotal no. of students: %d \n", tabs, getStudents().size());
		return msg;
	}
	public String printTutorials(String tabs) {
		String msg = new String();
		msg += String.format("%s%-5s | %-15s | %-40s\n", tabs, "ID", "No. of Students", "GROUP NAME");
		msg += String.format("%s%s\n", tabs, new String(new char[83]).replace('\0', '-'));
		for (Group tutorial : getTutorials().values()) {
			msg += String.format("%s%-5d | %7d/%-7d | %-40s\n", 
					tabs, tutorial.getID(), tutorial.getStudents().size(), tutorial.getCapacity(), tutorial.getName());
		}
		return msg;
	}
	public String printLabs(String tabs) {
		String msg = new String();
		msg += String.format("%s%-5s | %-15s | %-40s\n", tabs, "ID", "No. of Students", "GROUP NAME");
		msg += String.format("%s%s\n", tabs, new String(new char[83]).replace('\0', '-'));
		for (Group lab : getLabs().values()) {
			msg += String.format("%s%-5d | %7d/%-7d | %-40s\n", 
					tabs, lab.getID(), lab.getStudents().size(), lab.getCapacity(), lab.getName());
		}
		return msg;
	}
	
	public String printStudents(String tabs, Comparator<SortByName> comparator) {
		String msg = new String();
		if (getLecture() != null)
			msg += printStudentsInLecture(tabs+"\t", comparator);
		if (getTutorials() != null)
			msg += printStudentsInTutorials(tabs+"\t", comparator);
		if (getLabs() != null)
			msg += printStudentsInLabs(tabs+"\t", comparator);
		return msg;
	}
	public String printStudentsInLecture(String tabs, Comparator<SortByName> comparator) {
		String msg = new String();
		msg += String.format("%sLecture ID, Name: %d, %s\n", tabs, getLecture().getID(), getLecture().getName());
		msg += String.format("%sLecture Occupancy: %d / %d\n", tabs, getLecture().getStudents().size(), getLecture().getCapacity());
		msg += getLecture().printStudents(tabs+"\t", comparator);
		return msg;
	}
	public String printStudentsInTutorials(String tabs, Comparator<SortByName> comparator) {
		String msg = new String();
		for (Group tutorial : getTutorials().values()) {
			msg += printStudentsInTutorial(tabs, tutorial, comparator);
		}
		return msg;
	}
	public String printStudentsInTutorial(String tabs, Group tutorial, Comparator<SortByName> comparator) {
		String msg = new String();
		msg += String.format("%sTutorial ID, Name: %d, %s\n", tabs, tutorial.getID(), tutorial.getName());
		msg += String.format("%sTutorial Occupancy: %d / %d\n", tabs, tutorial.getStudents().size(), tutorial.getCapacity());
		msg += tutorial.printStudents(tabs+"\t", comparator);
		return msg;
	}
	public String printStudentsInLabs(String tabs, Comparator<SortByName> comparator) {
		String msg = new String();
		for (Group lab : getLabs().values()) {
			msg += printStudentsInLab(tabs, lab, comparator);
		}
		return msg;
	}
	public String printStudentsInLab(String tabs, Group lab, Comparator<SortByName> comparator) {
		String msg = new String();
		msg += String.format("%sLab ID, Name: %d, %s\n", tabs, lab.getID(), lab.getName());
		msg += String.format("%sLab Occupancy: %d / %d\n", tabs, lab.getStudents().size(), lab.getCapacity());
		msg += lab.printStudents(tabs+"\t", comparator);
		return msg;
	}
	
	public static void main(CourseGroup courseGroup, School school, Scanner scan) {
		int choice = 0;
		int option = 0;
		boolean done = false;
		Course course = courseGroup.getCourse();
		
		System.out.println("Welcome to Course Group Manager.");
		while(!done) {
			System.out.printf("Course: %d, %s\n", course.getID(), course.getName());
			System.out.println(courseGroup.print("\t"));
			System.out.println("\t1. Manage Lecture Group.");
			System.out.println("\t2. Manage Tutorial Groups.");
			System.out.println("\t3. Manage Lab Groups.");
			System.out.println("\t4. Print student lists by ID.");
			System.out.println("\t5. Print student lists by Name.");
			System.out.println("\t0. Go back to previous menu.");
			System.out.print("Choice: ");
			choice = scan.nextInt(); scan.nextLine();
			
			switch (choice) {
				case 0:
					done = true;
					break;
				case 1:
					if (courseGroup.getLecture() != null) Group.main(courseGroup.getLecture(), school, scan);
					else System.out.println("Error: Lecture group does not exists.");
					break;
				case 2:
				case 3:
					while (!done) {
						if (choice == 2) System.out.println("Tutorial Groups Manager");
						else System.out.println("Lab Groups Manager");
						System.out.println("\t1. Print all groups by ID.");
						System.out.println("\t2. Print all groups by Name.");
						System.out.println("\t3. Print all students by ID.");
						System.out.println("\t4. Print all students by Name.");
						System.out.println("\t5. Add a new group.");
						System.out.println("\t6. Edit a group.");
						System.out.println("\t7. Delete a group.");
						System.out.println("\t0. Go back to previous menu.");
						System.out.print("Choice: ");
						option = scan.nextInt(); scan.nextLine();
						
						switch(option) {
							case 0:
								done = true;
								break;
							case 1:
							case 2:
								ArrayList<Group> groups;
								if (choice == 2) groups = new ArrayList<Group>(courseGroup.getTutorials().values());
								else groups = new ArrayList<Group>(courseGroup.getLabs().values());
								if (option == 1) Collections.sort(groups);
								else Collections.sort(groups, new SortByNameComparator());
								System.out.println("Groups List:");
								System.out.printf("%-5s | %-10s | %-60s\n", "NO", "GROUP ID", "GROUP NAME");
								for (Group grp : groups) {
									System.out.printf("%-5d | %-10d | %-60s\n", groups.indexOf(grp)+1, grp.getID(), grp.getName());
								}
								break;
							case 3:
							case 4:
								if (choice == 2) {
									if (option == 3) System.out.println(courseGroup.printStudentsInTutorials("\t", null));
								}
								else System.out.println(courseGroup.printStudents("\t", new SortByNameComparator()));
								break;
							case 5:
								System.out.print("Please input group capacity: ");
								option = scan.nextInt(); scan.nextLine();
								if (choice == 2) courseGroup.addTutorial(option);
								else courseGroup.addLab(option);
								break;
							case 6:
							case 7:
								System.out.print("Please input group ID: ");
								try {
									if (option == 6) {
										if (choice == 2) Group.main(courseGroup.getTutorial(scan.nextInt()), school, scan);
										else Group.main(courseGroup.getLab(scan.nextInt()), school, scan);
									}
									else {
										if (choice == 2) courseGroup.rmTutorial(scan.nextInt());
										else courseGroup.rmLab(scan.nextInt());
									}
								} catch (KeyErrorException e) {
									System.out.println(e.getMessage());
								} catch (MaxCapacityException f) {
									System.out.println(f.getMessage());
								}
								break;
							default:
								System.out.println("Error: Invalid choice.");
								break;
						}
					}
					done = false;
					break;
				case 4:
				case 5:
					if (choice == 4) System.out.println(courseGroup.printStudents("\t", null));
					else System.out.println(courseGroup.printStudents("\t", new SortByNameComparator()));
					break;
				default:
					System.out.println("Error: Invalid choice.");
					break;
			}
		}
	}
}