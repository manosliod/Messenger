package com.messages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.messages.Main.*;
import static com.messages.UserDAO.user_menu;
import static com.messages.DBconf.*;

//A class for the inbox messages
public class InboxDAO {
    private String inbox_ms;

    public String getInbox_ms() {
        return inbox_ms;
    }

    public void setInbox_ms(String inbox_ms) {
        this.inbox_ms = inbox_ms;
    }
    //Display inbox messages
    public static void show_ms(){
        String role = null;
        try{
            //Connection with teh DB
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            Statement st = con.createStatement();
            //Query for the display of inbox messages
            String show_ms = "SELECT * FROM users.inbox, users.user "
                    + "WHERE user_id=" + us.getId() + " AND user.id=from_user_id;";
            ResultSet rs = st.executeQuery(show_ms);
            while(rs.next()){
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("\nFrom: " + rs.getString("email"));
                System.out.println(rs.getString("inbox_ms") + "\n");
            }
            String find_role = "SELECT * FROM users.role WHERE user_id=" + us.getId() + ";";
            ResultSet rs2 = st.executeQuery(find_role);
            
            while(rs2.next()){
                role = rs2.getString("role");
            }
        }catch(SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\nSelect:\n1) Back\n2) Delete\n3) Logout\n");
        switch(in.nextInt()){
            case 1:
                //checkin the role if it's 'admin' to go to the right menu
                if("admin".equals(role)){
                    admin.admin_auth_menu();
                }else{
                    user_menu();        
                }
                break;
            case 2:
                delete_inbox_ms();      //Method Call for delete
            case 3:
                display_menu();     //DIsplay Home menu
                break;
        }
    }
    //Delete a message
    public static void delete_inbox_ms(){
        int id = 0;
        String role = null;
        try{
            System.out.println("\nSelect to delete by ID: ");
            Scanner del = new Scanner(System.in);
            id = del.nextInt();
            //Connection with teh DB
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            Statement st = con.createStatement();
            //Query for delete
            String query = "UPDATE `users`.`inbox` SET `user_id` = null WHERE (`id` = '" + id + "');";
            st.executeUpdate(query);
            
        }catch(SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        show_ms();
    }
}
