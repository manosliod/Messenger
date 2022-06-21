package com.messages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.messages.Main.display_menu;
import static com.messages.Main.us;
import static com.messages.UserDAO.user_menu;
import static com.messages.DBconf.*;

//A class for userLogin
public class UserLogin {
    private String username;
    private String password;

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
    //Simple user's menu
    public void login_menu(){
        Scanner username = new Scanner(System.in);
        Scanner password = new Scanner(System.in);
        System.out.println("\nUsername: ");
        String user = username.next();
        System.out.println("Password: ");
        String pass = password.next();
        String role = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //DB connection
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            Statement st = con.createStatement();
            String sql = "SELECT * FROM users.user "
                    + "WHERE username='" + user + "' AND password='" + pass + "';";
            ResultSet rs = st.executeQuery(sql);
            
            if(rs.next()){
                System.out.println("\nWelcome " + rs.getString("fname"));
                us.setUsername(user);
                us.setPassword(pass);
                us.setId(rs.getInt("id"));
            }else{
                System.out.println("\nThis user does not exist!\nTry to login again by checking your typing or else go to register.");
                display_menu();
            }
            String find_role = "SELECT * FROM users.role WHERE user_id=" + us.getId() + ";";
            ResultSet rs2 = st.executeQuery(find_role);
            
            while(rs2.next()){
                role = rs2.getString("role");
            }
            //check the role of the user to display the proper menu
            if("admin".equals(role)){
                AdminLogin ad = new AdminLogin();
                ad.admin_auth_menu();
            }else{
                user_menu();
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
