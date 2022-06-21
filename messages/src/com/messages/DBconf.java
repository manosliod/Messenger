package com.messages;

public class DBconf {
    static final String db_host = "localhost";
    static final String db_port = "3306";
    static final String db_user = "root";
    static final String db_pass = "root";
    static final String db_url = "jdbc:mysql:"+db_host+":"+db_port+"/users?zeroDateTimeBehavior=convertToNull&serverTimezone=Europe/Athens&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false";
}
