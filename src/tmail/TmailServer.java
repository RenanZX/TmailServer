/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmail;

import bftsmart.tom.MessageContext;
import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;
import java.util.Scanner;
import tmail.MailManager;

/**
 *
 * @author Renan
 */

public class TmailServer extends DefaultSingleRecoverable{
    private static final int CADASTRAR = 1;
    private static final int ENVIAR_EMAIL = 2;
    private static final int CAIXA_ENTRADA = 3;
    private static final int LIXO = 4;
    
    private MailManager m;
    private boolean processed = false;
    
    public TmailServer(int id) {
        
        new ServiceReplica(id,this,this);
    }

    @Override
    public byte[] appExecuteOrdered(byte[] bytes, MessageContext mc) {
         String request = new String(bytes);
         
         //c++;
         
         //System.out.println("Recebeu requisição "+c+": "+request);
         
         
         return ("Resposta servidor: "+request).getBytes();
    }

    @Override
    public byte[] appExecuteUnordered(byte[] bytes, MessageContext mc) {
        String request = new String(bytes);
        Scanner input = new Scanner(System.in);
        m = new MailManager();
        String[] req = request.split("#");
        
        int operation;
        if(processed == true){
            return ("Operação Processada!").getBytes();
        }
        processed = true;
        try{
            String value = req[0];
            operation = Integer.parseInt(value);
        }catch(NumberFormatException e){
            operation = -1;
        }
        System.out.println("Requisição recebida: "+operation);
        if(operation == -1){
          System.out.println("Requisição Inválida!");
          throw new UnsupportedOperationException("Requisição Inválida!"); //To change body of generated methods, choose Tools | Templates.
        }
        String email;
        String message;
        switch(operation){
            case CADASTRAR:
                System.out.println("Email a cadastrar:"+req[1]);
                m.CadastrarEmail(req[1]);
                return ("Email Cadastrado com sucesso!\n").getBytes();
            case ENVIAR_EMAIL:
                email = req[1].split(";")[0];
                message = req[1].split(";")[1];
                System.out.println("Email destinatario:"+email);
                System.out.println("Mensagem a ser entrege:"+message);
                m.PostEmail(email, message);
                return ("Email Postado Com sucesso\n").getBytes();
            case CAIXA_ENTRADA:
                email = req[1];
                System.out.println("Email recebido: "+email);
                return (m.PrintEmails(email)).getBytes();
            case LIXO:
                //email = req[1];
                //m.PrintTrash(email);
                break;
        }
        
        return ("Falha na operação!\n").getBytes();
    }
    
    @Override
    public byte[] getSnapshot() {
        return Integer.toString(0).getBytes();
    }

    @Override
    public void installSnapshot(byte[] bytes) {
        //c = Integer.parseInt(new String(bytes));
        
    }

   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       new TmailServer(Integer.parseInt(args[0]));
       
    }
    
}
