import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SCRAME {
	public static final String SAVE_FILE = "SCRAME.sav";
	public static SaveData data;
	public static void saveData(SaveData data) throws IOException {
		FileOutputStream fileWriter = new FileOutputStream(SAVE_FILE);
		BufferedOutputStream bufferedWriter = new BufferedOutputStream(fileWriter);
		ObjectOutputStream objectWriter = new ObjectOutputStream(bufferedWriter);
		objectWriter.writeObject(data);
		objectWriter.writeObject(Professor.pk);
		objectWriter.writeObject(Student.pk);
		objectWriter.writeObject(Course.pk);
		objectWriter.writeObject(Group.pk);
		objectWriter.close();
	}
	public static void saveData(SaveData data, String temp) throws IOException {
		FileOutputStream fileWriter = new FileOutputStream(temp);
		BufferedOutputStream bufferedWriter = new BufferedOutputStream(fileWriter);
		ObjectOutputStream objectWriter = new ObjectOutputStream(bufferedWriter);
		objectWriter.writeObject(data);
		objectWriter.writeObject(Professor.pk);
		objectWriter.writeObject(Student.pk);
		objectWriter.writeObject(Course.pk);
		objectWriter.writeObject(Group.pk);
		objectWriter.close();
	}
	public static SaveData loadData() throws ClassNotFoundException, IOException {
		FileInputStream fileReader = new FileInputStream(SAVE_FILE);
		BufferedInputStream bufferedReader = new BufferedInputStream(fileReader);
		ObjectInputStream objectReader = new ObjectInputStream(bufferedReader);
		SaveData data = (SaveData) objectReader.readObject();
		Professor.pk = (Integer) objectReader.readObject();
		Student.pk = (Integer) objectReader.readObject();
		Course.pk = (Integer) objectReader.readObject();
		Group.pk = (Integer) objectReader.readObject();
		objectReader.close();
		return data;
	}
	public static void main (String[] args){
		// Variable declaration
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		int error_count = 0;
		boolean done = false;
		School school = null;
		ArrayList<School> schoolList = null;

		System.out.println("Loading data from 'SCRAME.sav'... Please wait...");
		// Load data
		try {
			data = loadData();
			System.out.println("Data load successful.");
		} catch (IOException e) {
			System.out.println("Error: "+SAVE_FILE+" not found.");
		} catch (Exception f) {
			System.out.println("Error: "+SAVE_FILE+" corrupted!");
		} finally {
			if (data == null) {
				// Initialization
				System.out.println("Initializing default data...");
				data = new SaveData();
				school = new School("School of Computer Engineering");
				try {
					data.addSchool(school);
				} catch (DuplicateKeyException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		System.out.println();
		System.out.println("Welcome to Course Registration portal! (powered by KTCube)");
		while (!done) {
			try {
				System.out.println("Please select a school: ");
				schoolList = new ArrayList<School>(data.getSchools());
				for (School sch : schoolList) {
					System.out.printf("\t%d. %s\n", (schoolList.indexOf(sch))+1, sch.getName());
				}
				System.out.println("\t0. Save and Quit.");
				//System.out.println("\t-1. Create new School.");
				System.out.print("Choice: ");
				choice = scan.nextInt(); scan.nextLine();
				
				switch(choice) {
					case 0:
						try {
							saveData(data);
						} catch (IOException e) {
							System.out.println("Error: Unable to save data.");
						}
						System.out.print("Proceed to quit? (Y/N): ");
						if (scan.nextLine().toUpperCase().charAt(0) != 'Y') break;
						System.out.println("Thank you! Good Bye.");
						scan.close();
						done = true;
						break;
					/*
					case -1:
						// Uncomment to test scalability of program
						// This case adds a new school.
						System.out.println("Please input school name: ");
						String stringInput = scan.nextLine();
						try {
							data.addSchool(school = new School(stringInput));
						} catch (DuplicateKeyException e) {
							System.out.println(e.getMessage());
						}
						break;
					*/
					default:
						try {
							school = schoolList.get(choice-1);
							School.main(school, data, scan);
						} catch (IndexOutOfBoundsException e) {
							System.out.println("Error: Invalid choice.");
						}
						break;
				}
			} catch (InputMismatchException e) {
				scan.nextLine(); // Eat up bad inputs.
				System.out.println("Error: Input Mismatch. Please provide compatible inputs.");
				System.out.println("NOTICE: Please save your work or progress to avoid loss of data.");
			} catch (Exception f) {
				System.out.println("Error: Unexpected error occured.");
				System.out.println("NOTICE: Attempting to save data in 'SCRAME_TEMP.sav'.");
				try {
					saveData(data, "SCRAME_TEMP.sav");
					System.out.println("NOTICE: Data saved in 'SCRAME_TEMP.sav'.");
				} catch (IOException e) {
					System.out.println("Error: Unable to save data.");
				}
				System.out.println("Please copy the following lines and send it to your system provider.");
				System.out.println(new String(new char [60]).replace('\0','-'));
				System.out.println(f.getMessage());
				f.printStackTrace(System.out);
				System.out.println(new String(new char [60]).replace('\0','-'));
				System.out.println("NOTICE: Please save your work or progress to avoid loss of data.");
				if (error_count++ > 10) return;
			}
		}		
	}
}