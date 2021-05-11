/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eco;

import bftsmart.tom.ServiceProxy;

/**
 *
 * @author eduardo
 */
public class TmailClient {
    
    public static void main(String[] args){
        ServiceProxy proxy = new ServiceProxy(1001);
        
        byte[] request = args[0].getBytes();
        
        //O cliente envia uma mensagem dizendo um comando
        //1. Enviar Email.
        //2. Caixa de Entrada
        //3. Lixo
        
        /*A replica recebe o comando e processa
        (ainda to na dúvida se a gente faz para cada réplica processar uma operação ou 
        qualquer réplica processar qualquer operação, e caso falhe passe o comando para
        a próxima).
        */
        byte[] reply = proxy.invokeOrdered(request);
        
        String replyString = new String(reply);
        
        System.out.println("Valor recebido com sucesso!");
        
        System.out.println("Resposta recebida: "+replyString);
        
        
    }
    
}
