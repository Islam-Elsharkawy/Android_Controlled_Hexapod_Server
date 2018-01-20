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
    enum movement {
        right_forward_up,
        right_forward_down,
        right_backward_up,
        right_backward_down,
        left_forward_up,
        left_forward_down,
        left_backward_up,
        left_backward_down,
        still
    };
    movement move_status = null;      
    String new_move = null;
    boolean client_resume = true;
    
    public Hexa(Socket client) {
        
        pwmDriver.setFrequency(50);
        moveStill();
        new Thread(new Runnable(){
            public void run(){
                try {
                    is = client.getInputStream();
                    dis = new DataInputStream(is);
                    client_resume = true;
                    while(client_resume) {
                        new_move = dis.readUTF();
                        System.out.println(new_move);
                        move(new_move);
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
    
    public void moveRight_FU(){ moveLeg1_FU(); moveLeg3_FU(); moveLeg5_FU();}
    public void moveRight_FD(){ moveLeg1_FD(); moveLeg3_FD(); moveLeg5_FD();}
    public void moveRight_BU(){ moveLeg1_BU(); moveLeg3_BU(); moveLeg5_BU();}
    public void moveRight_BD(){ moveLeg1_BD(); moveLeg3_BD(); moveLeg5_BD();}
    public void moveRight_Still(){moveLeg1_Still(); moveLeg3_Still(); moveLeg5_Still();}
    public void moveLeft_FU(){ moveLeg0_FU(); moveLeg2_FU(); moveLeg4_FU();}
    public void moveLeft_FD(){ moveLeg0_FD(); moveLeg2_FD(); moveLeg4_FD();}
    public void moveLeft_BU(){ moveLeg0_BU(); moveLeg2_BU(); moveLeg4_BU();}
    public void moveLeft_BD(){ moveLeg0_BD(); moveLeg2_BD(); moveLeg4_BD();}
    public void moveLeft_Still(){moveLeg0_Still(); moveLeg2_Still(); moveLeg4_Still();}

    public void moveForward() {
        switch(move_status) {
            case still:
                moveRight_FU();
                move_status = movement.right_forward_up;
                break;
            case right_forward_up:
                moveRight_FD();
                move_status = movement.right_forward_down;
                break;
            case right_forward_down:
                moveLeft_FU();
                moveRight_Still();
                move_status = movement.left_forward_up;
                break;
            case left_forward_up:
                moveLeft_FD();
                move_status = movement.left_forward_down;
                break;
            case left_forward_down:
                moveRight_FU();
                moveLeft_Still();
                move_status = movement.right_forward_up;
                break;
            default:
                moveStill();
        }
    }
    
    public void moveBackward() {
        switch(move_status) {
            case still:
                moveLeft_BU();
                move_status = movement.left_backward_up;
                break;
            case left_backward_up:
                moveLeft_BD();
                move_status = movement.left_backward_down;
                break;
            case left_backward_down:
                moveRight_BU();
                moveLeft_Still();
                move_status = movement.right_backward_up;
                break;
            case right_backward_up:
                moveRight_BD();
                move_status = movement.right_backward_down;
                break;
            case right_backward_down:
                moveLeft_BU();
                moveRight_Still();
                move_status = movement.left_backward_up;
                break;
            default:
                moveStill();
        }
    }

    public void moveLeft() {
        switch(move_status) {
            case still:
                moveRight_FU();
                move_status = movement.right_forward_up;
                break;
            case right_forward_up:
                moveRight_FD();
                move_status = movement.right_forward_down;
                break;
            case right_forward_down:
                moveLeft_BU();
                moveRight_Still();
                move_status = movement.left_backward_up;
                break;
            case left_backward_up:
                moveLeft_BD();
                move_status = movement.left_backward_down;
                break;
            case left_backward_down:
                moveRight_FU();
                moveLeft_Still();
                move_status = movement.right_forward_up;
                break;
            default:
                moveStill();
        }
    }
        
    public void moveRight() {
        switch(move_status) {
            case still:
                moveLeft_FU();
                move_status = movement.left_forward_up;
                break;
            case left_forward_up:
                moveLeft_FD();
                move_status = movement.left_forward_down;
                break;
            case left_forward_down:
                moveRight_BU();
                moveLeft_Still();
                move_status = movement.right_backward_up;
                break;
            case right_backward_up:
                moveRight_BD();
                move_status = movement.right_backward_down;
                break;
            case right_backward_down:
                moveLeft_FU();
                moveRight_Still();
                move_status = movement.left_forward_up;
                break;
            default:
                moveStill();
        }
    }
    
    public void moveStill() {
        switch(move_status) {
            case right_forward_up:
                moveRight_Still();
                move_status = movement.still;
                break;
            case right_forward_down:
                moveRight_FU();
                move_status = movement.right_forward_up;
                break;
            case right_backward_up:
                moveRight_Still();
                move_status = movement.still;                
                break;
            case right_backward_down:
                moveRight_BU();
                move_status = movement.right_backward_up;
                break;
            case left_forward_up:
                moveLeft_Still();
                move_status = movement.still;
                break;
            case left_forward_down:
                moveLeft_FU();
                move_status = movement.left_forward_up;                
                break;
            case left_backward_up:
                moveLeft_Still();                
                move_status = movement.still;
                break;
            case left_backward_down:
                moveLeft_BU();
                move_status = movement.left_backward_up;
                break;
            default:
                moveRight_Still();
                moveLeft_Still();
                move_status = movement.still;
        }

    }
    
    public void move (String d1) {
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
            case "still":
                moveStill();
                break;
            case "close":
                client_resume = false;
                try{dis.close();}catch(Exception e){e.printStackTrace();}
                pwmDriver.powerOff();
                pwmDriver.close();
        }
        pause(300);        
    }
    
    void pause (int time) {
        try{Thread.sleep(time);} catch(Exception e) {e.printStackTrace();}        
    }
}
