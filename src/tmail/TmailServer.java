/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmail;

import bftsmart.tom.MessageContext;
import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;
import tmail.MailManager;

/**
 *
 * @author eduardo
 */
public class TmailServer extends DefaultSingleRecoverable{
    private MailManager m;
    private int c = 0;
    
    public TmailServer(int id) {
        
        new ServiceReplica(id,this,this);
    }

    @Override
    public byte[] appExecuteOrdered(byte[] bytes, MessageContext mc) {
         String request = new String(bytes);
         
         c++;
         
         System.out.println("Recebeu requisição "+c+": "+request);
         
         
         return ("Resposta "+c+" servidor: "+request).getBytes();
    }

    @Override
    public byte[] appExecuteUnordered(byte[] bytes, MessageContext mc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public byte[] getSnapshot() {
        return Integer.toString(c).getBytes();
    }

    @Override
    public void installSnapshot(byte[] bytes) {
        c = Integer.parseInt(new String(bytes));
        
    }

   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       new TmailServer(Integer.parseInt(args[0]));
       
    }
    
}
