package com.messages;

import java.util.Scanner;

public class Main {
    //Global objs
    static UserDAO us = new UserDAO();
    static UserLogin login = new UserLogin();
    static AdminLogin admin = new AdminLogin();
    static Scanner in = new Scanner(System.in);
    //Method for the menu display
    public static void display_menu() {
        int i = 0;
        do{
            System.out.println("\n1) Login \n2) Register\n3) Exit");
            i = in.nextInt();
            switch(i){
                case 1:
                    login.login_menu();
                    break;

                case 2:
                    us.user_reg();
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        }while( !(i>0 && i<5));
    }
    public static void main(String[] args) {
        display_menu();     //Creates the display menu which is right above.
    }
}
