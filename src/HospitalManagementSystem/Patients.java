package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Patients {
    private Connection connection;
    private Scanner scanner;


    //constructor
    public Patients(Connection connection , Scanner scanner){
        this.connection = connection;  //connection: Represents the database connection object.
        this.scanner = scanner;  // for input
    }

    public void addPatients(){
        // to take input form the user
         System.out.print("Enter the name of the Patient:");
         String name = scanner.next();
         System.out.print("Enter the age of the Patient:");
         int age = scanner.nextInt();
         System.out.print("Enter the Patients gender");
         String gender = scanner.next();

         try{
             String query = "INSERT INTO patients ( name , age , gender )  VALUES (? , ? , ?)";
             //PreparedStatement: Prepares the SQL query and binds values to the placeholders:
             PreparedStatement preparedStatement = connection.prepareStatement(query);

             //setString(1, name) binds the name to the first placeholder.
             //setInt(2, age) binds the age to the second placeholder.
             //setString(3, gender) binds the gender to the third placeholder.

             preparedStatement.setString(1 , name );
             preparedStatement.setInt(2,age);
             preparedStatement.setString(3,gender);

             int affectedRows = preparedStatement.executeUpdate();  //  executeUpdate the no. of roes affected
                   // if no of rows affected is more then 0 that means that the data is inserted
             if(affectedRows >0){
                 System.out.println("Patients added succesfully");
             }else{
                 System.out.println("Failed to add Patients");
             }

             //Catches any database-related errors and prints the stack trace
    }catch(SQLException e){
           e.printStackTrace();
         }
    }

    //Displays all patients from the database in a table format.
    public void viewPatients(){
       String query = "select * from patients";
      try{
          // to select all rows from the patients table
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          //ResultSet: Stores the result of the query (a table of patient records).
          ResultSet resultSet = preparedStatement.executeQuery();   // executeQuery used for executing SELECT statements.
          // ResultSet object that contains the data retrieved from the database .

          System.out.println("Patients: ");
          System.out.println("+--------------+-------------------------+--------+------------+");
          System.out.println("|  Patient ID  | Name                    | Age    | Gender     |");
          System.out.println("+--------------+-------------------------+--------+------------+");
          while(resultSet.next()){

              //Iterates through each row in the ResultSet:
              //Fetches column values (id, name, age, gender) using methods like getInt() and getString().
              //Prints the values in a formatted table.

              int id = resultSet.getInt("id");
              String name = resultSet.getString("name");
              int age = resultSet.getInt("age");
              String gender = resultSet.getString("gender");
              System.out.printf("| %-12s | %23s | %7s | %10s|\n" , id , name , age , gender);
              System.out.println("---------------+-------------------------+--------+-------------+");
          }
      }catch(SQLException e){
          e.printStackTrace();
      }
    }

    public boolean getPatientByID(int id){
       String query = "SELECT * From patients WHERE id =?";
       try {
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          preparedStatement.setInt(1 , id);
          ResultSet resultSet = preparedStatement.executeQuery();  // store the result in set Result
           if(resultSet.next()) {
               return true;
           }else{
               return false;
           }
       }catch(SQLException e){
           e.printStackTrace();
       }
       return false;
       }
    }

