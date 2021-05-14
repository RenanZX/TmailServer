/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmail;

import tmail.State;
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
    private static final int DESCADASTRAR_EMAIL = 4;
    private static final int EXCLUIR_EMAIL = 6;
    
    
    private int id = 0;
    
    public TmailServer(int id) {
        this.id = id;
        new ServiceReplica(id,this,this);
    }

    @Override
    public byte[] appExecuteOrdered(byte[] bytes, MessageContext mc) { 
         //c++;
         
         //System.out.println("Recebeu requisição "+c+": "+request);
         
         
         //return ("Resposta servidor: ").getBytes();
         
        
        //return ("id "+id).getBytes();
        throw new UnsupportedOperationException("Requisição Inválida!");
        
    }

    @Override
    public byte[] appExecuteUnordered(byte[] bytes, MessageContext mc) {
        //String request = new String(bytes);
        //System.out.println("ID count:"+id);
        State request = new State(bytes);
        
        MailManager m = new MailManager(id);
        request.PrintState();
        System.out.println("Requisição recebida: "+request.operation);
        if(request.operation == -1){
           System.out.println("Requisição Inválida!");
           throw new UnsupportedOperationException("Requisição Inválida!"); //To change body of generated methods, choose Tools | Templates.
        }
            
        String origem = "";
        String email = "";
        String message = "";
        if(request.inputValue.contains(";")){
          String[] res = request.inputValue.split(";");
          origem = res[0];
          email = res[1];
          message = res[2];
        }else{
          email = request.inputValue;
        }
        switch(request.operation){
            case CADASTRAR:
              System.out.println("Email a cadastrar:"+email);
              m.CadastrarEmail(email);
              m.CloseConection();
              return ("Email Cadastrado com sucesso!").getBytes();
            case ENVIAR_EMAIL:
              System.out.println("Email destinatario:"+email);
              System.out.println("Mensagem a ser entrege:"+message);
              m.PostEmail(origem, email, message);
              m.CloseConection();
              System.out.println("Retorna valor de sucesso!\n");
              return ("Email Postado Com sucesso").getBytes();
            case EXCLUIR_EMAIL:
              System.out.println("id do email a ser excluido: "+request.inputValue);
              m.ExcluirMensagem(request.inputValue);
              return ("Email Excluído\n").getBytes();
            case DESCADASTRAR_EMAIL:
              System.out.println("email a ser descadastrado: "+request.inputValue);
              m.ExcluirEmail(request.inputValue);
              return ("Email Descadastrado com sucesso\n").getBytes();
            case CAIXA_ENTRADA:
              System.out.println("Email recebido: "+request.inputValue);
              String ret = m.PrintEmails(request.inputValue);
              m.CloseConection();
              return (ret).getBytes();
            }
        
        
        return ("Falha na operação!\n").getBytes();
        
    }
    
    @Override
    public byte[] getSnapshot() {
        return Integer.toString(id).getBytes();
    }

    @Override
    public void installSnapshot(byte[] bytes) {
        id = Integer.parseInt(new String(bytes));
    }

   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       new TmailServer(Integer.parseInt(args[0]));
       
    }
    
}
