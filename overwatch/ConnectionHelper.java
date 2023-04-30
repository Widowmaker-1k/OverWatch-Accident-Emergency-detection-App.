package com.example.overwatch;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection connection;
            String ip,port,un,pass,db;
            @SuppressLint("NewApi")
    public Connection conclas(){


             ip = " 127.0.0.1";
             port = "1433";
             db= "Login_register";
             un = "localhost";
             pass = "";


                StrictMode.ThreadPolicy tpolicy=new StrictMode.ThreadPolicy.Builder().permitAll().build();

                        StrictMode.setThreadPolicy(tpolicy);
                Connection con = null;
                String ConnetionURL=null;
                try {
Class.forName("net.sourceforge.jtds.jbc.Driver");
ConnetionURL = "jbc:jtds:sqlserver://"+ip+" : "+port+";"+"databaseName"+db+";user"+un+"password "+pass+";";
con= DriverManager.getConnection(ConnetionURL);




            }
                catch (Exception ex)
                {
                    Log.e("Error : ",ex.getMessage());

                }

                return con;
            }

}
