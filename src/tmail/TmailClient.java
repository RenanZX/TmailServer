/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmail;

import tmail.State;
import bftsmart.tom.ServiceProxy;
import java.util.Scanner;
/**
 *
 * @author eduardo
 */
public class TmailClient {
    
    private static final int CADASTRAR = 1;
    private static final int ENVIAR_EMAIL = 2;
    private static final int CAIXA_ENTRADA = 3;
    private static final int DESCADASTRAR_EMAIL = 4;
    private static final int SAIR = 5;
    
    private static final int EXCLUIR_EMAIL = 6;
   
    
    
    public static void main(String[] args){
        ServiceProxy proxy = new ServiceProxy(1001);
        Scanner input = new Scanner(System.in);
        State st = new State();
        int op = 0;
        
       // byte[] request = args[0].getBytes();
        
        while(op != SAIR){
            System.out.println("1. Cadastrar Email");
            System.out.println("2. Enviar Email");
            System.out.println("3. Caixa de Entrada");
            System.out.println("4. Descadastrar Email");
            System.out.println("5. Sair");
            op = input.nextInt();
            input.nextLine();
            String entrada = "";
        
            switch(op){
                case CADASTRAR:
                    System.out.println("Digite o email que deseja cadastrar:");
                    entrada = input.nextLine();
                    break;
                case ENVIAR_EMAIL:
                    System.out.println("Digite o seu email:");
                    entrada = input.nextLine();
                    input.nextLine();
                    entrada += ";";
                    System.out.println("Digite o email do destinatario:");
                    entrada += input.nextLine();
                    input.nextLine();
                    entrada += ";";
                    System.out.println("Digite a mensagem:");
                    entrada += input.nextLine();
                    break;
                case CAIXA_ENTRADA:
                    System.out.println("Digite o email que deseja consultar:");
                    entrada = input.nextLine();
                    break;
                case DESCADASTRAR_EMAIL:
                    System.out.println("Digite o email que deseja descadastrar:");
                    entrada = input.nextLine();
                    break;
                case SAIR:
                    proxy.close();
                    return;
            }
            
            if(entrada.contains("#")){
               System.out.println("Erro entrada inválida!");
               proxy.close();
               return;
            }
            
            st.inputValue = entrada;
            st.operation = op;
            byte[] request;
            request = st.getBytes();
            try{
                byte[] reply = proxy.invokeUnordered(request);
                String replyString = new String(reply);
                System.out.println("Resposta do Servidor:"+replyString);
                if(op == CAIXA_ENTRADA){
                  System.out.println("Deseja excluir um email?(S/N) \n");
                  entrada = input.nextLine();
                  input.nextLine();
                  if(entrada.equals("S")){
                     System.out.println("Digite o id que deseja excluir:");
                     entrada = input.nextLine();
                     st.inputValue = entrada;
                     st.operation = EXCLUIR_EMAIL;
                     request = st.getBytes();
                     reply = proxy.invokeUnordered(request);
                     replyString = new String(reply);
                     System.out.println("Resposta do Servidor:"+replyString);
                  }
                }
            }catch(Exception e){
                System.out.println("Falha no servidor:" + e.getMessage());
              if(e.getMessage().contains("Impossible to connect to servers!")){
                System.out.println("Servers Offline");
                proxy.close();
                return;
            }
           }
       }
    }
    
}
