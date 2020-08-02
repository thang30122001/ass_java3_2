/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class KetNoi {
    public Connection con;
    public void KetNoi() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con=DriverManager.getConnection("jdbc:sqlserver://localhost\\AdminPC\\sqlexpress:1433;databaseName=Assignment_java3","sa","thang3012");
    }
    
}
