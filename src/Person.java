import java.util.Date;
import java.io.Serializable;
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
	
	@Override
	public int compareTo(Person obj) {
		return (this.getID() - (obj.getID()));
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Person)) return false;
		Person person = (Person) obj;
		if (person.getClass() == this.getClass()) {
			if (person.getID() == this.getID()) return true;
			if (person.getName() == this.getName() &&
				person.getGender() == this.getGender() ) return true;
		}
		return false;
	}
	
	// TODO remove main method after implementing read method for Dates
	public static void main(String[] args) {
		SimpleDateFormat datetime = new SimpleDateFormat("dd-MM-yyyy");
		try{
		Date date = datetime.parse("30-10-2012");
		System.out.println(datetime.format(date));
		} catch(Exception e) {
			
		}
	}
}
