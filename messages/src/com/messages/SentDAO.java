package com.messages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.messages.InboxDAO.show_ms;
import static com.messages.Main.admin;
import static com.messages.Main.display_menu;
import static com.messages.Main.in;
import static com.messages.Main.us;
import static com.messages.UserDAO.user_menu;
import static com.messages.DBconf.*;

//A class for the send messages
public class SentDAO {
    private String sent_ms;
    public static Scanner to = new Scanner(System.in);
    public static Scanner msn = new Scanner(System.in);

    public void setSent_ms(String sent_ms) {
        this.sent_ms = sent_ms;
    }

    public String getSent_ms() {
        return sent_ms;
    }
    //Display sebd messages
    public static void show_sent(){
        String role = null;
        try{
            //Connection with teh DB
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            Statement st = con.createStatement();
            //Query for the display of the send messages
            String show_ms = "SELECT * FROM users.sent, users.user "
                    + "WHERE user_id=" + us.getId() + " AND user.id=sent_to_id;";
            ResultSet rs = st.executeQuery(show_ms);
            while(rs.next()){
                System.out.println("\nTo: " + rs.getString("email"));
                System.out.println(rs.getString("sent_ms") + "\n");
            }
            String find_role = "SELECT * FROM users.role WHERE user_id=" + us.getId() + ";";
            ResultSet rs2 = st.executeQuery(find_role);
            
            while(rs2.next()){
                role = rs2.getString("role");
            }
        }catch(SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\nSelect:\n1) Back\n2) Logout\n");
        switch(in.nextInt()){
            case 1:
                //checkin the role if it's 'admin' to go to the right menu
                if("admin".equals(role)){
                    admin.admin_auth_menu();
                }else{
                    user_menu();
                }
            case 2:
                delete_sent_ms();       //method call
                break;
            case 3:
                display_menu();     //Display home menu
                break;
        }
    }
    
    //Send a message
    public static void send_ms(){
        int id = 0;
        String role = null;
        try{
            System.out.println("\nSend to: ");
            String email = to.next();
            System.out.println("\nType your message:");
            String message = msn.next();
            //Connection with teh DB
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            Statement st = con.createStatement();
            //Queries for right delivery of the message
            String query = "SELECT id FROM users.user WHERE email = '" + email + "';";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                id = rs.getInt("id");
            }
            String send_ms = "INSERT INTO `users`.`sent` (`sent_ms`, `user_id`, `sent_to_id`) VALUES ('" + message + "','" + us.getId() + "','" + id + "');";
            st.executeUpdate(send_ms);
            String inbox_ms = "INSERT INTO `users`.`inbox` (`inbox_ms`, `user_id`, `from_user_id`) VALUES ('" + message + "', '" + id + "', '" + us.getId() + "');";
            st.executeUpdate(inbox_ms);
            String find_role = "SELECT * FROM users.role WHERE user_id=" + us.getId() + ";";
            ResultSet rs2 = st.executeQuery(find_role);
            
            while(rs2.next()){
                role = rs2.getString("role");
            }
        }catch(SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        //checkin the role if it's 'admin' to go to the right menu
        if("admin".equals(role)){
            admin.admin_auth_menu();
        }else{
            user_menu();
        }
    }
    //Delete a send message
    public static void delete_sent_ms(){
        int id = 0;
        String role = null;
        try{
            System.out.println("\nSelect to delete by ID: ");
            Scanner del = new Scanner(System.in);
            id = del.nextInt();
            //Connection with teh DB
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            Statement st = con.createStatement();
            //Query which deleting send messages
            String query = "UPDATE `users`.`sent` SET `user_id` = null WHERE (`id` = '" + id + "');";
            st.executeUpdate(query);
            
        }catch(SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        show_ms();  //Display send messages
    }
}
