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
    
    public MailManager(){
        try{
            String url = "jdbc:sqlite:emails.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connected to emails db!");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    protected void finalize() throws Throwable{
      CloseConection();
    }
    
    void CloseConection(){
        try{
            if(conn != null){
                conn.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    private void ExecuteCmd(String cmd){
        try{
            Statement st = conn.createStatement();
            st.execute(cmd);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void PostEmail(String email, String message){
        ExecuteCmd("insert into messages(message, origem_mail) values("+message+","+email+")");
    }
    
    public String PrintEmails(String email){
        String sqlv = "select message,origem_mail from messages where email = "+email;
        try{
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sqlv);
            String result = "";
            while(res.next()){
              result += "====================================\n";
              result += "Email:"+res.getString("origem_mail")+"\n";
              result += "Message:"+res.getString("message") + "\n";
            }
            return result;
        }catch(SQLException e){
            return e.getMessage();
        }
    }
    
    public void ExcluirMensagem(String message, String email){
        ExecuteCmd("delete from messages where message = "+message+" and email = "+email);
    }
    
    public void CadastrarEmail(String email){
        ExecuteCmd("insert into mailsreg(email) values ("+email+")");
    }
    
    public void ExcluirEmail(String email){
        ExecuteCmd("delete from mailsreg where email = "+email);
    }
    
    
}
