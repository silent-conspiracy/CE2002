import java.util.Date;
import java.util.Scanner;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class Person implements Serializable, SortByName, Comparable<Person> {
	private static final long serialVersionUID = 1L;
	// Attributes
	private String name;
	private Gender gender;
	private String ic;
	private String contact;
	private String email;
	private String address;
	private Date dob;
	
	// Constructors
	public Person(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
		this.ic = null;
		this.contact = null;
		this.email = null;
		this.address = null;
		this.dob = null;
	}
	
	// Getters and Setters
	public abstract int getID();
	public String getName() { return name; }
	public String getGender() { return gender.getDescription(); }
	public String getIC() { return this.ic; }
	public String getContact() { return this.contact; }
	public String getEmail() { return this.email; }
	public String getAddress() { return this.address; }
	public Date getDob() { return this.dob; }
	
	public abstract void setID(int id);
	public void setGender(Gender sex) { this.gender = sex; }
	public void setName(String name) { this.name = name; }
	public void setIC(String ic) { this.ic = ic; }
	public void setContact(String contact) { this.contact = contact; }
	public void setEmail(String email) { this.email = email; }
	public void setAddress(String address) { this.address = address; }
	public void setDob(Date dob) { this.dob = dob; }
	
	// Specific methods
	public void readDOB(String string) throws ParseException {
		SimpleDateFormat datetime = new SimpleDateFormat("dd/MM/yyyy");
		datetime.setLenient(false);
		if (string.matches("[0-3]\\d/[01]\\d/\\d{4}")) {
			Date date = datetime.parse(string);
			setDob(date);
		}
		else throw new ParseException("Format error", 0);
	}
	public String printDOB() {
		SimpleDateFormat datetime = new SimpleDateFormat("dd/MM/yyyy");
		if (getDob() == null) return null;
		return datetime.format(getDob());
	}
	
	@Override
	public int compareTo(Person obj) {
		return (this.getID() - (obj.getID()));
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Person)) return false;
		Person person = (Person) obj;
		if (person.getClass().equals(this.getClass())) {
			if (person.getID() == this.getID()) return true;
			if (person.getName().equals(this.getName()) &&
				person.getGender() == this.getGender() ) return true;
		}
		return false;
	}
	
	public String printPersonParticulars(String tabs) {
		String msg = new String();
		msg += String.format("%sName: %s\n", tabs, name);
		msg += String.format("%sGender: %s\n", tabs, gender.getDescription());
		msg += String.format("%sIC: %s\n", tabs, ic);
		msg += String.format("%sContact: %s\n", tabs, contact);
		msg += String.format("%sPersonal email: %s\n", tabs, email);
		msg += String.format("%sAddress: %s\n", tabs, address);
		msg += String.format("%sDate of Birth: %s\n", tabs, printDOB());
		return msg;
	}
	
	public static void main(Person person, Scanner scan) {
		// Declarations
		int choice = 0;
		boolean done = false;
		String stringInput = null;
		
		while (!done) {
			System.out.printf("Person: %d, %s\n", person.getID(), person.getName());
			System.out.println(person.printPersonParticulars("\t"));
			System.out.println("\t1. Edit Name.");
			System.out.println("\t2. Edit Gender.");
			System.out.println("\t3. Edit IC.");
			System.out.println("\t4. Edit Contact.");
			System.out.println("\t5. Edit Personal eMail.");
			System.out.println("\t6. Edit Address.");
			System.out.println("\t7. Edit Date of Birth.");
			System.out.println("\t0. Go back to previous menu.");
			System.out.print("Choice: ");
			choice = scan.nextInt(); scan.nextLine();
			
			switch (choice) {
				case 0:
					done = true;
					break;
				case 1:
					System.out.print("Please input new name: ");
					stringInput = scan.nextLine();
					person.setName(stringInput);
					break;
				case 2:
					System.out.print("Please choose gender (M/F): ");
					choice = scan.nextLine().charAt(0);
					switch (choice) {
						case 'M':
						case 'm':
							person.setGender(Gender.MALE);
							break;
						case 'F':
						case 'f':
							person.setGender(Gender.FEMALE);
							break;
						default:
							System.out.println("Error: Invalid selection.");
							break;
					}
					break;
				case 3:
					System.out.print("Please input IC: ");
					stringInput = scan.nextLine();
					person.setIC(stringInput);
					break;
				case 4:
					System.out.print("Please input Contact: ");
					stringInput = scan.nextLine();
					person.setContact(stringInput);
					break;
				case 5:
					System.out.print("Please input Personal eMail: ");
					stringInput = scan.nextLine();
					person.setEmail(stringInput);
					break;
				case 6:
					System.out.print("Please input Address: ");
					stringInput = scan.nextLine();
					person.setAddress(stringInput);
					break;
				case 7:
					System.out.print("Please input Date of Birth (DD/MM/YYYY): ");
					stringInput = scan.nextLine();
					try {
						person.readDOB(stringInput);
					} catch (ParseException e) {
						System.out.println("Error: Date format or value incompatible.");
					}
					break;
				default:
					System.out.println("Error: Invalid choice.");
					break;
			}
		}
	}
}
