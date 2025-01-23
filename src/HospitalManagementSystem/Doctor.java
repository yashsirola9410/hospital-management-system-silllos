package HospitalManagementSystem;

import java.sql.*;

public class Doctor {
    private Connection connection;

    //constructor
    public Doctor(Connection connection){
        this.connection = connection;  //connection: Represents the database connection object.
    }

    //Displays all patients from the database in a table format.
    public void viewDoctors(){
        String query = "select * from doctors";
        try{
            // to select all rows from the patients table
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            //ResultSet: Stores the result of the query (a table of patient records).
            ResultSet resultSet = preparedStatement.executeQuery();   // executeQuery used for executing SELECT statements.
            // ResultSet object that contains the data retrieved from the database .

            System.out.println("Doctors: ");
            System.out.println("+--------------+-------------------------+---------------------+");
            System.out.println("|  Doctors ID  | Name                    | Specialization      |");
            System.out.println("+--------------+-------------------------+---------------------+");
            while(resultSet.next()){
                //Iterates through each row in the ResultSet:
                //Fetches column values (id, name, age, gender) using methods like getInt() and getString().
                //Prints the values in a formatted table.
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-12s | %23s | %19s |\n" , id , name , specialization);
                System.out.println("---------------+-------------------------+---------------------+");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorByID(int id){
        String query = "SELECT * From doctors WHERE id =?";
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

