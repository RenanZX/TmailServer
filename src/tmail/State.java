/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmail;

import java.util.Random;

/**
 *
 * @author RenanPC
 */
public class State {
    public int leader;
    public int operation;
    public String inputValue;
    
    State(int lead){
        this.leader = lead;
    }
    
    State(byte[] bytes){
      String values = new String(bytes);
        String[] arrv = values.split("#");
        try{
            leader = Integer.parseInt(arrv[0]);
        }catch(NumberFormatException e){
            leader = 0;
        }
        try{
            operation = Integer.parseInt(arrv[1]);
        }catch(NumberFormatException e){
            operation = 0;
        }
        inputValue = arrv[2];
    }
    
    void UpdateState(){
      Random gerador = new Random();
      leader = gerador.nextInt(3);
      if(leader > 2){
        leader = 0;
      }
    }
    
    void PrintState(){
      System.out.println("Actual Leader: "+leader);
      System.out.println("operation: "+operation);
      System.out.println("Input Value: "+inputValue);
    }
    
    byte[] getBytes(){
        return (leader + "#" + operation + "#" + inputValue).getBytes();
    }
    
    void setBytes(byte[] bytes){
        String values = new String(bytes);
        String[] arrv = values.split("#");
        try{
            leader = Integer.parseInt(arrv[0]);
        }catch(NumberFormatException e){
            leader = 0;
        }
        try{
            operation = Integer.parseInt(arrv[1]);
        }catch(NumberFormatException e){
            operation = 0;
        }
        inputValue = arrv[2];
    }
}
