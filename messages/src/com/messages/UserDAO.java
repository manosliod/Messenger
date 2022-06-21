package com.messages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import static com.messages.SentDAO.send_ms;
import static com.messages.SentDAO.show_sent;
import static com.messages.DBconf.*;

//A class for the user's menu, update and register
public class UserDAO {
    private int id;
    private String fname;
    private String lname;
    private String tel;
    private String address;
    private String email;
    private String username;
    private String password;
    InboxDAO inbox = new InboxDAO();
    SentDAO send = new SentDAO();
//    AdminLogin ad = new AdminLogin();
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getId() {
        return id;
    }
    
    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getTel() {
        return tel;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    //User's menu
    public static void user_menu(){
        System.out.println("\n1) Show Inbox\n2) Show Sent Messages\n3) Send Message\n4) Logout");
        switch(in.nextInt()){
                case 1:
                    show_ms();
                    break;
                case 2:
                    show_sent();
                    break;
                case 3:
                    send_ms();
                    break;
                case 4:
                    display_menu();
                    break;
            }
    }
    //Udate user's data using scanner and the user object 'up' which is located at AdminLogin
    public void update_user(){
        try {
            Scanner update = new Scanner(System.in);
            UserDAO up = new UserDAO();
            String fname = null, 
                    lname = null,
                    tel = null,
                    address = null,
                    email = null,
                    username = null,
                    password = null,
                    role = null;
            int id;
            //Connection with teh DB
            Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
            Statement st = con.createStatement();
            System.out.println("Select the id of the User you want to update: ");
            id = update.nextInt();
            
            System.out.println("First Name: ");
            fname = update.next();
            up.setFname(fname);
            
            System.out.println("Last Name: ");
            lname = update.next();
            up.setLname(lname);
            
            System.out.println("Telephone: ");
            tel = update.next();
            up.setTel(tel);
            
            System.out.println("Address: ");
            address = update.next();
            up.setAddress(address);
            
            System.out.println("e-Mail: ");
            email = update.next();
            up.setEmail(email);
            
            System.out.println("Username: ");
            username = update.next();
            up.setUsername(username);
            
            System.out.println("Password: ");
            password = update.next();
            up.setPassword(password);
            
            System.out.println("Role: ");
            role = update.next();
            
            String query = "UPDATE users.user SET fname = ?, lname = ?, tel = ?, address = ?, email = ?, username = ?, password = ? WHERE id = ?";
            String query_role = "UPDATE users.role SET role = ? WHERE user_id = ? ";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString  (1, up.getFname());
            ps.setString  (2, up.getLname());
            ps.setString  (3, up.getTel());
            ps.setString  (4, up.getAddress());
            ps.setString  (5, up.getEmail());
            ps.setString  (6, up.getUsername());
            ps.setString  (7, up.getPassword());
            ps.setInt     (8, id);
            ps.executeUpdate();
            ps.close();
            ps = con.prepareStatement(query_role);
            ps.setString(1, role);
            ps.setInt(2, up.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        admin.admin_auth_menu();        //Go to Admin's menu if the 'role' is 'admin' on our DB
    }
    
    //User's registration using prepared statement
    public void user_reg(){
        try{
            int id = 0;
            UserDAO reg = new UserDAO();
            Scanner register = new Scanner(System.in);
            String fname = null, 
                        lname = null,
                        tel = null,
                        address = null,
                        email = null,
                        username = null,
                        password = null,
                        role = null;
                //Connection with teh DB
                Connection con = DriverManager.getConnection(db_url, db_user, db_pass);
                Statement st = con.createStatement();

                System.out.println("First Name: ");
                reg.setFname(register.next());
                
                System.out.println("Last Name: ");
                reg.setLname(register.next());
                
                System.out.println("Telephone: ");
                reg.setTel(register.next());
                
                System.out.println("Address: ");
                reg.setAddress(register.next());
                
                System.out.println("e-Mail: ");
                reg.setEmail(register.next());
                
                System.out.println("Username: ");
                reg.setUsername(register.next());
                
                System.out.println("Password: ");
                reg.setPassword(register.next());
                
                String query = "INSERT INTO users.user (fname, lname, tel, address, email, username, password)"
                            + " VALUES (?, ?, ?, ?, ?, ?, ?);";
                String query_id = "SELECT id FROM users.user;";
                String query_role = "INSERT INTO `users`.`role` (`role`, `user_id`) VALUES (?, ?);";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, reg.getFname());
                ps.setString(2, reg.getLname());
                ps.setString(3, reg.getTel());
                ps.setString(4, reg.getAddress());
                ps.setString(5, reg.getEmail());
                ps.setString(6, reg.getUsername());
                ps.setString(7, reg.getPassword());
                ps.executeUpdate();
                ps.close();
                ResultSet rs = st.executeQuery(query_id);
                while(rs.next()){
                    id = rs.getInt("id");
                }
                PreparedStatement ps2 = con.prepareStatement(query_role);
                ps2.setString(1, "user");
                ps2.setInt   (2, id);
                ps2.executeUpdate();
                ps2.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        System.out.println("Successfully Registered!\n");
        display_menu();     //Go to Home menu
    }
    public void delete_user(){
        
    }
}
