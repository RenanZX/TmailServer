/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmail;

import tmail.State;
import bftsmart.tom.ServiceProxy;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author eduardo
 */
public class TmailClient {
    
    private static final int CADASTRAR = 1;
    private static final int ENVIAR_EMAIL = 2;
    private static final int CAIXA_ENTRADA = 3;
    private static final int LIXO = 4;
    private static final int SAIR = 5;
   
    
    public static void main(String[] args){
        ServiceProxy proxy = new ServiceProxy(1001);
        Scanner input = new Scanner(System.in);
        State st = new State(0);
        int op = 0;
        
       // byte[] request = args[0].getBytes();
        
        //O cliente envia uma mensagem dizendo um comando
        //1. Enviar Email.
        //2. Caixa de Entrada
        //3. Lixo
        
        /*A replica recebe o comando e processa
        (ainda to na dúvida se a gente faz para cada réplica processar uma operação ou 
        qualquer réplica processar qualquer operação, e caso falhe passe o comando para
        a próxima).
        */
        while(op != SAIR){
            System.out.println("1. Cadastrar Email");
            System.out.println("2. Enviar Email");
            System.out.println("3. Caixa de Entrada");
            System.out.println("4. Lixo");
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
                case LIXO:
                    System.out.println("Digite o email que deseja consultar:");
                    entrada = input.nextLine();
                    break;
                case SAIR:
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
                st.UpdateState();
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
