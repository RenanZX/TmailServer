/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmail;

/**
 *
 * @author RenanPC
 */
public class State {
    public int operation;
    public String inputValue;
    
    State(){
        operation = -1;
        inputValue = "$";
    }
    
    State(byte[] bytes){
      String values = new String(bytes);
        String[] arrv = values.split("#");
        try{
            operation = Integer.parseInt(arrv[0]);
        }catch(NumberFormatException e){
            operation = 0;
        }
        inputValue = arrv[1];
    }
    
    void PrintState(){
      System.out.println("operation: "+operation);
      System.out.println("Input Value: "+inputValue);
    }
    
    byte[] getBytes(){
        return (operation + "#" + inputValue).getBytes();
    }
    
    void setBytes(byte[] bytes){
        String values = new String(bytes);
        System.out.println("valores:"+values);
        String[] arrv = values.split("#");
        
        try{
            operation = Integer.parseInt(arrv[0]);
        }catch(NumberFormatException e){
            operation = 0;
        }
        inputValue = arrv[1];
    }
}
