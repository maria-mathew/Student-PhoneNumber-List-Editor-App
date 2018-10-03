package testpack;

public class Student {

//declaring variables for student
	private int id;
	private String firstName;
	private String lastName;
	private long phone;

//default constructor
	public Student () {
		
	}
	
//constructor with parameters to initialize student object
	public Student(int id, String firstName, String lastName, long phone) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

//getter for id
	public int getId() {
		return id;
	}

//setter for id
	public void setId(int id) {
		this.id = id;
	}
	
//getter for firstName
	public String getFirstName() {
		return firstName;
	}

//setter for firstName
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

//getter for lastName
	public String getLastName() {
		return lastName;
	}

//setter for lastName
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

//getter for phone
	public long getPhone() {
		return phone;
	}

//setter for phone
	public void setPhone(long phone) {
		this.phone = phone;
	}
	
	
	
}
