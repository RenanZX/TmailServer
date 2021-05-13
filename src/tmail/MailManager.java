/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author RenanPC
 */
public class MailManager {
    private Connection conn = null;
    
    MailManager(){
        try{
            String url = "jdbc:sqlite:emails.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connected to emails db!");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    
    void CloseConection(){
        try{
            if(conn != null){
                conn.close();
            }
            System.out.println("Closing connection");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void ExecuteCmd(String cmd){
        System.out.println("Comando a executar:"+cmd);
        try{
            Statement st = conn.createStatement();
            st.execute(cmd);
            st.close();
        }catch(SQLException e){
            System.out.println("Exception!"+e.getMessage());
        }
    }
    
    public void PostEmail(String origem, String email, String message){
        ExecuteCmd("insert into messages(message, origem_email, email) values('"+message+"','"+origem+"','"+email+"')");
    }
    
    public String PrintEmails(String email){
        String sqlv = "select * from messages where email = '"+email+"'";
        try{
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sqlv);
            String result = "";
            while(res.next()){
              result += "\n====================================\n";
              result += "Id: "+res.getInt("id")+"\n";
              result += "Email:"+res.getString("origem_email")+"\n";
              result += "Message:"+res.getString("message") + "\n";
            }
            result += "====================================\n";
            st.close();
            return result;
        }catch(SQLException e){
            return e.getMessage();
        }
    }
    
    public void ExcluirMensagem(String id){
        ExecuteCmd("delete from messages where id = '"+id+"'");
    }
    
    public void CadastrarEmail(String email){
        ExecuteCmd("insert into mailsreg(email) values ('"+email+"')");
    }
    
    public void ExcluirEmail(String email){
        ExecuteCmd("delete from mailsreg where email = '"+email+"'");
        ExecuteCmd("delete from messages where email = '"+email+"'");
    }
    
    
}
