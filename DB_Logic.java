package testpack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB_Logic extends DB_Variables {

//creating connection variable
	private Connection con;
	
//creating statement variable
	private Statement st;
	
//default constructor
	public DB_Logic() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);     //Establishing a connection to data source and setting 
																		//the user ID and password 
			st = con.createStatement();
			
			} catch (Exception e) {
			e.printStackTrace();
		}
	}


//method to insert data
	public String insertData(Student student) {
		
		String result;
		
	//creating sql statement
		String sql = "insert into studentinfo ( first_name, last_name, phone) "
				+ "values ('"+student.getFirstName()+"','"+student.getLastName()+"','"+student.getPhone()+"')";
		try {
			st.executeUpdate(sql);                              //executing sql statement
		} catch (SQLException e) {
			result = "The insertion was not successful!!";          
			e.printStackTrace();
		}
		result = "The insertion was successful!!";
		return result;
	}
	
//method to update data
	public String updateData(Student student) {
		
	//creating sql statement
		String result;
		String sql = "update studentinfo set first_name = '"+student.getFirstName()+"', last_name = '"+student.getLastName()+"',"
				+ "phone ='"+student.getPhone()+"' where id_student = "+student.getId()
				;
		try {
			st.executeUpdate(sql);   //executing sql statement 
			result = "Update was succesfull";
		} catch (SQLException e) {
			result = "Updating data failed!!";
			e.printStackTrace();
		}
		
		return result;	}

//method to delete data using id
	public String deleteData(int id) {
		
	//creating sql statement
		String result;
		String sql = "delete from studentinfo where id_student = "+id;
		try {
			st.executeUpdate(sql);                           //executing sql statement
			result = "Succesfully deleted";
		} catch (SQLException e) {
			result = "Deletion was not succesful";
			e.printStackTrace();
		}
		return result;
	}
	
//method to show all student details
	public ArrayList<Student> getAllStudents(){
		
		//creating array list to hold all the studemt objects
		ArrayList<Student> all = new ArrayList<Student>();
		
		//creating sql statement
		String sql = "select id_student, first_name, last_name, phone from studentinfo";
		try {
			ResultSet rs = st.executeQuery(sql);           //resultset to hold the results of the executed sql statement
			while(rs.next()) {
			  Student student = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getLong(4));
			  all.add(student);
			}
		} catch (SQLException e) {
			System.out.println("Not succesful");
			e.printStackTrace();
		}
		return all;
				}
	
//method to search by student first name
	public ArrayList<Student> searchStudentsByName(String firstName, String lastName){
		
		//creating array list
		ArrayList<Student> all = new ArrayList<Student>();
		
		//creating sql statement
		String sql = "select id_student, first_name, last_name, phone from studentinfo where first_name = '"+firstName+"'"
				+ "and last_name = '"+lastName+"'";
		try {
			ResultSet rs = st.executeQuery(sql);    //resultset to hold the results of the executed sql statement
			while(rs.next()) {
			  Student student = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getLong(4));
			  all.add(student);
			}
		} catch (SQLException e) {
			System.out.println("Not succesful");
			e.printStackTrace();
		}
		return all;
					}
//method to search student information using phone numbers
	public ArrayList<Student> searchByPhone(long phone) {
		
		//creating array list 
		ArrayList<Student> all = new ArrayList<Student>();
		
		//creating sql statement
		String sql = "select id_student, first_name, last_name, phone from studentinfo where phone ="+phone;
		try {
			ResultSet rs = st.executeQuery(sql);      //resultset to hold the results of the executed sql statement
			while(rs.next()) {
				Student student = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getLong(4));
				all.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return all;
	}
	
//method to check if an id exists in the table
	public boolean idCheck(int id) {
		
		//declaring variables
		ResultSet result;
		boolean idExist = true;
		
		//creating sql statement
		String sql = "select id_student from studentinfo where id_student = "+id;
		try {
			result = st.executeQuery(sql);
			if(!result.next()) {
				idExist = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return idExist;
	}

//method to select by id
	public Student studentWithId(int id) {
		
		//creating student object
		Student student = null;
		
		//creating sql statement
		String sql = "select id_student, first_name, last_name, phone from studentinfo where id_student = "+id;
		
		try {
			st.execute(sql);
			ResultSet rs = st.executeQuery(sql); 
			if(rs.next()) {
			 student = new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getLong(4));}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
		
			
		}
	
	}
