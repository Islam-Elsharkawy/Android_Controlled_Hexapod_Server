/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hexapod;

import java.io.InputStream;
import java.io.DataInputStream;
import java.net.Socket;

/**
 *
 * @author Administrator
 */
public class Hexa {
    
    private InputStream is = null;
    private DataInputStream dis = null;
    
    PWM_Driver pwmDriver = new PWM_Driver(0x41); 
    String request = null;
    boolean client_resume = true;
    boolean resume = true;
    
    public Hexa(Socket client) {
        
        pwmDriver.setFrequency(50);
        move("stop");
        new Thread(new Runnable(){
            public void run(){
                try {
                    is = client.getInputStream();
                    dis = new DataInputStream(is);
                    client_resume = true;
                    while(client_resume) {
                        request = dis.readUTF();
                        System.out.println(request);
                        move(request);
                    }
                }catch(Exception e) {e.printStackTrace();}
            }
        }).start();
    }
    
    public void setLeg (int legNumber, int angle1, int angle2) {
        switch (legNumber) {
            case 0:
                pwmDriver.setAngle(0, angle1);
                pwmDriver.setAngle(1, angle2);
                break;
            case 1:
                pwmDriver.setAngle(2, angle1);
                pwmDriver.setAngle(3, angle2);
                break;
            case 2:
                pwmDriver.setAngle(4, angle1);
                pwmDriver.setAngle(5, angle2);
                break;
            case 3:
                pwmDriver.setAngle(10, angle1);
                pwmDriver.setAngle(11, angle2);
                break;
            case 4:
                pwmDriver.setAngle(12, angle1);
                pwmDriver.setAngle(13, angle2);
                break;
            case 5:
                pwmDriver.setAngle(14, angle1);
                pwmDriver.setAngle(15, angle2);
                break;
        }                
    }
    
    public void moveLeg0_Still(){ setLeg(0, -45, 0);}
    public void moveLeg1_Still(){ setLeg(1, 0, 0);}
    public void moveLeg2_Still(){ setLeg(2, 45, 0);}
    public void moveLeg3_Still(){ setLeg(3, -45, 0);}
    public void moveLeg4_Still(){ setLeg(4, 0, 0);}
    public void moveLeg5_Still(){ setLeg(5, 45, 0);}
    
    public void moveLeg0_FU(){ setLeg(0, -25, 30);}
    public void moveLeg1_FU(){ setLeg(1, 20, 30);}
    public void moveLeg2_FU(){ setLeg(2, 65, 30);}
    public void moveLeg3_FU(){ setLeg(3, -65, -30);}
    public void moveLeg4_FU(){ setLeg(4, -20, -30);}
    public void moveLeg5_FU(){ setLeg(5, 25, -30);}
    
    public void moveLeg0_BU(){ setLeg(0, -65, 30);}
    public void moveLeg1_BU(){ setLeg(1, -20, 30);}
    public void moveLeg2_BU(){ setLeg(2, 25, 30);}
    public void moveLeg3_BU(){ setLeg(3, -25, -30);}
    public void moveLeg4_BU(){ setLeg(4, 20, -30);}
    public void moveLeg5_BU(){ setLeg(5, 65, -30);}
    
    public void moveLeg0_FD(){ setLeg(0, -25, 0);}
    public void moveLeg1_FD(){ setLeg(1, 20, 0);}
    public void moveLeg2_FD(){ setLeg(2, 65, 0);}
    public void moveLeg3_FD(){ setLeg(3, -65, 0);}
    public void moveLeg4_FD(){ setLeg(4, -20, 0);}
    public void moveLeg5_FD(){ setLeg(5, 25, 0);}
    
    public void moveLeg0_BD(){ setLeg(0, -65, 0);}
    public void moveLeg1_BD(){ setLeg(1, -20, 0);}
    public void moveLeg2_BD(){ setLeg(2, 25, 0);}
    public void moveLeg3_BD(){ setLeg(3, -25, 0);}
    public void moveLeg4_BD(){ setLeg(4, 20, 0);}
    public void moveLeg5_BD(){ setLeg(5, 65, 0);}
    
    public void moveStill() {
        moveLeg0_Still();
        moveLeg1_Still();
        moveLeg2_Still();
        moveLeg3_Still();
        moveLeg4_Still();
        moveLeg5_Still();
        pause(300);
    }
    public void moveForward() {
        new Thread(new Runnable(){
            public void run(){
                resume = true;
                moveLeg1_FU();
                moveLeg3_FU();
                moveLeg5_FU();
                pause(300);
                while(resume) {
                    moveLeg1_FD();
                    moveLeg3_FD();
                    moveLeg5_FD();        
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg0_FU();
                    moveLeg2_FU();
                    moveLeg4_FU();
                    moveLeg1_Still();
                    moveLeg3_Still();
                    moveLeg5_Still();
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg0_FD();
                    moveLeg2_FD();
                    moveLeg4_FD();        
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg1_FU();
                    moveLeg3_FU();
                    moveLeg5_FU();
                    moveLeg0_Still();
                    moveLeg2_Still();
                    moveLeg4_Still();
                    pause(300);
                }
            }
        }).start();
    }
    
        public void moveBackward() {
        new Thread(new Runnable(){
            public void run(){
                resume = true;
                moveLeg0_BU();
                moveLeg2_BU();
                moveLeg4_BU();
                pause(300);
                while(resume) {
                    moveLeg0_BD();
                    moveLeg2_BD();
                    moveLeg4_BD();        
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg1_BU();
                    moveLeg3_BU();
                    moveLeg5_BU();
                    moveLeg0_Still();
                    moveLeg2_Still();
                    moveLeg4_Still();
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg1_BD();
                    moveLeg3_BD();
                    moveLeg5_BD();        
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg0_BU();
                    moveLeg2_BU();
                    moveLeg4_BU();
                    moveLeg1_Still();
                    moveLeg3_Still();
                    moveLeg5_Still();
                    pause(300);
                }
            }
        }).start();
    }

    public void moveRight() {
        new Thread(new Runnable(){
            public void run(){
                resume = true;
                moveLeg0_FU();
                moveLeg2_FU();
                moveLeg4_BU();
                pause(300);
                while(resume) {
                    moveLeg0_FD();
                    moveLeg2_FD();
                    moveLeg4_BD();        
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg1_FU();
                    moveLeg3_BU();
                    moveLeg5_BU();
                    moveLeg0_Still();
                    moveLeg2_Still();
                    moveLeg4_Still();
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg1_FD();
                    moveLeg3_BD();
                    moveLeg5_BD();        
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg0_FU();
                    moveLeg2_FU();
                    moveLeg4_BU();
                    moveLeg1_Still();
                    moveLeg3_Still();
                    moveLeg5_Still();
                    pause(300);
                }
            }
        }).start();
    }        
        
    public void moveLeft() {
        new Thread(new Runnable(){
            public void run(){
                resume = true;
                moveLeg1_BU();
                moveLeg3_FU();
                moveLeg5_FU();
                pause(300);
                while(resume) {
                    moveLeg1_BD();
                    moveLeg3_FD();
                    moveLeg5_FD();        
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg0_BU();
                    moveLeg2_BU();
                    moveLeg4_FU();
                    moveLeg1_Still();
                    moveLeg3_Still();
                    moveLeg5_Still();
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg0_BD();
                    moveLeg2_BD();
                    moveLeg4_FD();        
                    pause(300);
                    if(resume == false) {break;}
                    moveLeg1_BU();
                    moveLeg3_FU();
                    moveLeg5_FU();
                    moveLeg0_Still();
                    moveLeg2_Still();
                    moveLeg4_Still();
                    pause(300);
                }
            }
        }).start();
    }
    
    public void move (String d1) {
        resume = false;
        pause(400);
        moveStill();
        
        switch (d1){
            case "forward":
                moveForward();
                break;
            case "backward":
                moveBackward();
                break;
            case "right":
                moveRight();
                break;
            case "left":
                moveLeft();
                break;
            case "stop":
                moveStill();
                break;
            case "close":
                client_resume = false;
                try{dis.close();}catch(Exception e){e.printStackTrace();}
                pwmDriver.powerOff();
                pwmDriver.close();
        }
                
    }
    
    void pause (int time)
    {
        try {
            Thread.sleep(time);
        }catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
}
