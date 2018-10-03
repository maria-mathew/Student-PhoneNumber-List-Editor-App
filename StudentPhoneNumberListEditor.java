package testpack;

import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class StudentPhoneNumberListEditor extends Application{



//Labels
	Label lbStudentId = new Label("Student ID: ");
	Label lbFirstName = new Label("First Name: ");
	Label lbLastName = new Label("Last Name: ");
	Label lbPhone = new Label("Phone Number: ");
	Label notify = new Label();
	
//Text Fields
	TextField tfStudentId = new TextField();
	TextField tfFirstName = new TextField();
	TextField tfLastName = new TextField();
	TextField tfPhone = new TextField();
	TextArea tfDisplay = new TextArea();
	
//radio buttons
	RadioButton display = new RadioButton("Display");
	RadioButton update = new RadioButton("Update");
	RadioButton insert = new RadioButton("Insert");
	RadioButton delete = new RadioButton("Delete");
	RadioButton searchByName = new RadioButton("Search By Name");
	RadioButton searchByPhone = new RadioButton("Search By Phone");
	
//Botton
	Button done = new Button("Done");
	Button clear = new Button("Clear");
	
//Border Pane
	BorderPane pane = new BorderPane();
	
//Grid panes
	GridPane top = new GridPane();
	GridPane center = new GridPane();
	GridPane bottom = new GridPane();
	GridPane left = new GridPane();

	
@Override
	public void start(Stage primaryStage) throws Exception {
	
//Creating DB_Logic Object
	DB_Logic db  = new DB_Logic();
		
//bottom custom pane
		pane.setBottom(new CustomPane(bottom));
		bottom.setAlignment(Pos.CENTER);
		bottom.setPadding(new Insets(5, 5, 5, 5));
		bottom.setHgap(10);
		bottom.setVgap(10);
		bottom.add(lbStudentId, 0, 1);
		bottom.add(tfStudentId, 1, 1);
		bottom.add(lbFirstName, 2, 1);
		bottom.add(tfFirstName, 3, 1);
		bottom.add(lbLastName, 0, 2);
		bottom.add(tfLastName, 1, 2);
		bottom.add(lbPhone, 2, 2);
		bottom.add(tfPhone, 3, 2);
		 
		 
//left custom pane
		 pane.setLeft(new CustomPane(left));
		 left.setAlignment(Pos.CENTER);
		 left.setPadding(new Insets(10, 10, 10, 10));
		 left.setHgap(10);
		 left.setVgap(10);
		 left.add(display, 0, 0);
		 left.add(insert, 0, 1);
		 left.add(update, 0, 2);
		 left.add(delete, 0, 3);
		 left.add(searchByName, 0, 4);
		 left.add(searchByPhone, 0, 5);
		 left.add(done, 0, 6);
		 left.add(clear, 0, 7);


//bottom custom pane
		 pane.setCenter(new CustomPane(center));
		 center.setPadding(new Insets(10, 10, 10, 10));
		 center.setVgap(5);
		 center.add(tfDisplay, 0, 0);
		 center.add(notify, 0, 1);

//initial state
		 initialState();
	
//set on action for radio buttons
		 
		 //set on action for insert radio button
				insert.setOnAction(e->{
					if(insert.isSelected()) {
						clearFields();
						initialState();
					tfFirstName.setDisable(false);
					tfLastName.setDisable(false);
					tfPhone.setDisable(false);}
				});
				
		//set on action for update radio button
				update.setOnAction(e->{
					if(update.isSelected()) {
						clearFields();
						initialState();
						tfStudentId.setDisable(false);
					}
				});
		
		//set on action for delete radio button
				delete.setOnAction(e->{
					if(delete.isSelected()) {
						clearFields();
						initialState();
						tfStudentId.setDisable(false);
					}
				});
				
		//set on action for display radio button	
				display.setOnAction(e->{
					if(display.isSelected()) {
						clearFields();
						initialState();
					}
				});
				
		//set on action for searchByName radio button
				searchByName.setOnAction(e->{
					if(searchByName.isSelected()) {
						clearFields();
						initialState();
						tfFirstName.setDisable(false);
						tfLastName.setDisable(false);
					}
				});
				
		//set on action for searchByPhone
				searchByPhone.setOnAction(e->{
					if(searchByPhone.isSelected()) {
						clearFields();
						initialState();
						tfPhone.setDisable(false);
					}
				});
				
//set on action for buttons
				
			//set on action for clear button
				clear.setOnAction(e->{
					clear();
				});
				
				
			//set on action for done button
				done.setOnAction(e->{
			
				if(!display.isSelected() && !insert.isSelected() && !delete.isSelected() && !update.isSelected() &&
						!searchByName.isSelected() && !searchByPhone.isSelected()) {
					notify.setText("Select an action!");
				}
			
//displaying all  contents
			String list = "";
			if(display.isSelected()) {
				ArrayList<Student> all = db.getAllStudents();        //arraylist to hold all student objects from the table
				if(all.size()!=0) {
					for(Student student:all) {
						 list += showStudent(student);
				}
					tfDisplay.setText(list);}
				else {
					notify.setText("The Table is Empty!");
				} 
			}
			
//insert contents into the table
			if(insert.isSelected()) {
				notify.setText("");
				long phone = 0;
				
				tfFirstName.setDisable(false);
				tfLastName.setDisable(false);
				tfPhone.setDisable(false);
				
			//first name
				if(tfFirstName.getText().trim().isEmpty()) {
					notify.setText("Please Enter First Name!");
					return;
						}
				
				String firstName = tfFirstName.getText();
				if(!firstName.matches("[a-zA-Z]+")) {
					notify.setText("Enter a valid first name!");;
					return;
					}
				
			//last name
				if(tfLastName.getText().trim().isEmpty()) {
					notify.setText("Please Enter Last Name!");
					return;
				}
			
				String lastName = tfLastName.getText();
				if(!lastName.matches("[a-zA-Z]+")) {
					notify.setText("Enter a valid last name!");
					return;
				}
				
			//phone
				if(tfPhone.getText().trim().isEmpty()) {
					notify.setText("Please Enter Phone!");
					return;
				}
			
				try {
					 phone = Long.parseLong(tfPhone.getText());
				}
				catch(Exception e1) {
					notify.setText("Enter a valid phone number");
					return;
				}
				
			//creating student object
				Student student =  new Student(0, firstName, lastName, phone) ;
				clearFields();
				if(student!=null) {
				notify.setText(db.insertData(student));}}
				
				
//update contents
				if(update.isSelected()) {
					notify.setText(" ");
					
				//declaring id to update the contents
					int id;
					
					notify.setText("");
					if(tfStudentId.getText().trim().isEmpty()) {
						notify.setText("Please Enter the Id to be updated!");
						return;
					}
					
				//getting the value of id
					try {
					 id = Integer.parseInt(tfStudentId.getText());}
					catch(Exception e2) {
						notify.setText("Enter an integer value");
						return;
					}
					
				//checking if the id exist
					if(!db.idCheck(id)) {
						notify.setText("The id does not exists!");
						return;
					}
				//showing the details of the student with the given id
					Student temp = db.studentWithId(id); 
					tfDisplay.setText(showStudent(temp));
					
				//enabling text fields to allow user to enter details
					tfStudentId.setDisable(true);
					tfFirstName.setDisable(false);
					tfLastName.setDisable(false);
					tfPhone.setDisable(false);
					
				//Declaring phone
					long phone;
					
				//first name
					if(tfFirstName.getText().trim().isEmpty()) {
						notify.setText("Please Enter First Name!");
						return;
							
						}
					
					String firstName = tfFirstName.getText();
					if(!firstName.matches("[a-zA-Z]+")) {
						notify.setText("Enter a valid first name!");;
						return;
					}
					
				//last name
					if(tfLastName.getText().trim().isEmpty()) {
						notify.setText("Please Enter Last Name!");
						return;
					}
				
					String lastName = tfLastName.getText();
					if(!lastName.matches("[a-zA-Z]+")) {
						notify.setText("Enter a valid last name!");
						return;
					}
					
				//phone
					if(tfPhone.getText().trim().isEmpty()) {
						notify.setText("Please Enter Phone!");
						return;
					}
				
					try {
						 phone = Long.parseLong(tfPhone.getText());
					}
					catch(Exception e1) {
						notify.setText("Enter a valid phone number");
						return;
					}
					
				//creating student object
					Student student =  new Student(id, firstName, lastName, phone) ;
					clearFields();
					 notify.setText(db.updateData(student));
					 
				}
//delete contents
				if(delete.isSelected()) {
					notify.setText("");
					//declaring id 
					int id;
					
					notify.setText("");
					if(tfStudentId.getText().trim().isEmpty()) {
						notify.setText("Please Enter the Id to be deleted!");
						return;
					}
					
				//getting the value of id
					try {
					 id = Integer.parseInt(tfStudentId.getText());}
					catch(Exception e2) {
						notify.setText("Enter an integer value");
						return;
					}
					
				//checking if the id exist
					if(!db.idCheck(id)) {
						notify.setText("The id does not exists!");
						return;
					}
					
				//deleting
					notify.setText(db.deleteData(id));
				}
				
//search by name
				if(searchByName.isSelected()) {
					notify.setText(" ");
					
				//first name
					if(tfFirstName.getText().trim().isEmpty()) {
						notify.setText("Please Enter First Name!");
						return;
							
						}
					
					String firstName = tfFirstName.getText();
					if(!firstName.matches("[a-zA-Z]+")) {
						notify.setText("Enter a valid first name!");;
						return;
					}
					
				//last name
					if(tfLastName.getText().trim().isEmpty()) {
						notify.setText("Please Enter Last Name!");
						return;
					}
				
					String lastName = tfLastName.getText();
					if(!lastName.matches("[a-zA-Z]+")) {
						notify.setText("Enter a valid last name!");
						return;
					}
					
				//returning results
					
					ArrayList <Student> studentWithName = db.searchStudentsByName(firstName, lastName);
					if(studentWithName.size()!=0) {
						for(Student temp1: studentWithName) {
							tfDisplay.setText(showStudent(temp1));                //showing result
							}
						
					}
					else if(studentWithName.size()==0) {
						notify.setText("Student with the name '"+firstName+"','"+lastName+"' does not exist in the table ");   //student with name entered does not exist
					return;
					}
				}
				
//search by phone
			if(searchByPhone.isSelected()) {
				notify.setText(" ");
				
			//declaring phone
				long phone;
				
			//getting phone
				if(tfPhone.getText().trim().isEmpty()) {
					notify.setText("Enter a phone number");
				}
				try {
				 phone = Long.parseLong(tfPhone.getText());
				}
				catch(Exception e3) {
					notify.setText("Enter a valid phone number!");
					return;
				}
				
				ArrayList <Student> studentWithPhone = db.searchByPhone(phone);
				if(studentWithPhone.size()!=0) {
					for (Student temp: studentWithPhone) {
						tfDisplay.setText(showStudent(temp));
					}}

				else if(studentWithPhone.size()==0) {
					notify.setText("No student with the phone numeber "+phone+" exists in the table!");
					return;
				}
			}
			
			});
		
		
		
		Scene scene = new Scene(pane, 700, 330);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setTitle("Student Phone Number List Editor");
		primaryStage.show();

		
//properties
		 display.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 update.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 delete.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 insert.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 searchByName.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 searchByPhone.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 lbFirstName.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 lbLastName.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 lbPhone.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 lbStudentId.setFont(Font.font("Times New Roman",FontWeight.BOLD, 14));
		 notify.setFont(Font.font("Times New Roman",FontWeight.BOLD, 15));
		 tfDisplay.setStyle("-fx-border-color: darkgrey; -fx-font-weight: bold");
		 done.setPrefWidth(100);
		 clear.setPrefWidth(100);
		 
		 display.setTextFill(Color.MIDNIGHTBLUE);
		 insert.setTextFill(Color.MIDNIGHTBLUE);
		 delete.setTextFill(Color.MIDNIGHTBLUE);
		 insert.setTextFill(Color.MIDNIGHTBLUE);
		 update.setTextFill(Color.MIDNIGHTBLUE);
		 searchByName.setTextFill(Color.MIDNIGHTBLUE);
		 searchByPhone.setTextFill(Color.MIDNIGHTBLUE);
		 lbStudentId.setTextFill(Color.MIDNIGHTBLUE);
		 lbFirstName.setTextFill(Color.MIDNIGHTBLUE);
		 lbLastName.setTextFill(Color.MIDNIGHTBLUE);
		 lbPhone.setTextFill(Color.MIDNIGHTBLUE);
		 notify.setTextFill(Color.DARKRED);
		 

		  done.setStyle(" -fx-font-family: magneto; -fx-pref-width: 200px; -fx-font-size: 1.20em;"
		  		+ "-fx-text-fill: maroon;  -fx-background-color: darkgrey;");

		  clear.setStyle(" -fx-font-family: magneto; -fx-pref-width: 200px; -fx-font-size: 1.20em;"
		  		+ "-fx-text-fill: maroon;  -fx-background-color: darkgrey;");

//background
	BackgroundImage paneBG= new BackgroundImage(new Image("image/123.png",700,700,false,true),
	        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
	          BackgroundSize.DEFAULT);
	pane.setBackground(new Background(paneBG));
 
//Toggle group
		ToggleGroup section = new ToggleGroup();
		display.setToggleGroup(section);
		update.setToggleGroup(section);
		delete.setToggleGroup(section);
		insert.setToggleGroup(section);
		searchByName.setToggleGroup(section);
		searchByPhone.setToggleGroup(section);
		
	}

	
//method to set app to initial state
	public void initialState() {
		 tfFirstName.setDisable(true);
		 tfStudentId.setDisable(true);
		 tfLastName.setDisable(true);
		 tfPhone.setDisable(true);
		 tfDisplay.setEditable(false);
	}

//method to clear all fields and set to initial state
	public void clear() {
			notify.setText("");
			tfDisplay.clear();
			 tfStudentId.clear();
			 tfFirstName.clear();
			 tfLastName.clear();
			 tfPhone.clear();
			 tfStudentId.setDisable(true);
			 tfFirstName.setDisable(true);
			 tfLastName.setDisable(true);
			 tfPhone.setDisable(true);
			 insert.setSelected(false);
			 update.setSelected(false);
			 delete.setSelected(false);
			 display.setSelected(false);
			 searchByName.setSelected(false);
			 searchByPhone.setSelected(false);
	
		}
//method to clear text fields
		public void clearFields() {
			tfStudentId.clear();
			 tfFirstName.clear();
			 tfLastName.clear();
			 tfPhone.clear();
			 notify.setText(" ");
		}
	
//main method
		public static void main (String [] args) {
			launch(args);
			
	}
	
//method to show student info
		public static  String showStudent(Student student) {
			Student student1 = new Student(student.getId(),student.getFirstName(), student.getLastName(), student.getPhone());
			String list = String.format("%15d%20s%20s%20d\n------------------------------------------------------------------------------\n",
					student.getId(),student.getFirstName(), student.getLastName(), student.getPhone());
			return list;
		}
		
//class custom pane
			class CustomPane extends StackPane {
			  public CustomPane(GridPane pane) {
			    getChildren().add(pane);
			    pane.setStyle("-fx-border-color: #606060; -fx-border-width: 1px");
	
	
			  }
			}
		}
