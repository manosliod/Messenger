package com.messages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.messages.DBconf.*;

import static com.messages.Main.*;

//A class for the Admins
public class AdminLogin {
    private String username;
    private String password;
    public UserDAO us = new UserDAO();
    public UserDAO up = new UserDAO();
    public SentDAO send = new SentDAO();
    public InboxDAO inbox = new InboxDAO();
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    //Admin's menu
    public void admin_auth_menu() {
        System.out.println("\n1) Show User's Data n' Roles \n2) Show Inbox\n3) Show Sent Messages\n4) Send Message\n5) Logout");
        switch(in.nextInt()){
            case 1:
                users_info();       //User's Info 
                break;
            case 2:
                InboxDAO.show_ms(); //For more info CTRL + Click
                break;
            case 3:
                SentDAO.show_sent();    //For more info CTRL + Click
                break;
            case 4:
                SentDAO.send_ms();      //For more info CTRL + Click
                break;
            case 5:
                display_menu();         //Display home menu
                break;
        }
    }
    //Delete a user
    public void delete_user(){
        int id = 0;
        Scanner del = new Scanner(System.in);
        System.out.println("\nSelect user to delete by ID: ");
        id = del.nextInt();
        try{
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            Statement st = con.createStatement();
            String delete_user = "DELETE FROM `users`.`user` WHERE (`id` = '" + id + "');";
            String delete = "DELETE FROM `users`.`role` WHERE (`user_id` = '" + id + "');";
            st.executeUpdate(delete_user);
            st.executeUpdate(delete);
            
        }catch(SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        admin_auth_menu();
    }
    
    //User's info
    public void users_info(){
        try{
            //DB connecion
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            Statement st = con.createStatement();
            //Query to collect all users wiith their roles
            String show_ms = "SELECT user.id, user.fname, user.lname, user.tel, user.address, user.email, user.username, user.password, role.role"
                    + " FROM users.role, users.user"
                    + " WHERE user.id = user_id;";
            ResultSet rs = st.executeQuery(show_ms);
            System.out.format("\n%-25s%-25s%-25s%-25s%-25s%-25s%-25s%-25s%-25s\n\n", "ID", "First Name", "Last Name", "Telephone", "Address", "e-Mail", "Username", "Password", "Role");
            while(rs.next()){
                System.out.format("%-25s%-25s%-25s%-25s%-25s%-25s%-25s%-25s%-25s\n", rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("tel"), rs.getString("address"), rs.getString("email"), rs.getString("username"), rs.getString("password"), rs.getString("role") );
            }
        }catch(SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         System.out.println("\n1) Update User's Data \n2) Delete User\n3) Back");
        switch(in.nextInt()){
            case 1:
                up.update_user();       //Update User's info (CTRL + Click)
                break;
            case 2:
                delete_user();          //DELETE a user (CTRL + Click)
                break;
            case 3:
                admin_auth_menu();      //Admin's menu (CTRL + Click)
                break;
        }
    }
}
