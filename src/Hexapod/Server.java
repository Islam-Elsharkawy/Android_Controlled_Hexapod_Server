/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hexapod;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import jdk.dio.DeviceManager;
import jdk.dio.i2cbus.I2CDevice;
import jdk.dio.i2cbus.I2CDeviceConfig;
import java.nio.ByteBuffer;
import static jdk.dio.i2cbus.I2CDeviceConfig.*;

/**
 *
 * @author Administrator
 */
public class Server {
    
    public static final int port = 8043;
    private Socket client = null; 
    private boolean serverThreadRunning = true;
    private boolean looping = true;
    private ServerSocket server = null;
            
    public Server() {
        
        try {server = new ServerSocket(port);}
        catch(Exception e) {e.printStackTrace();}
        
        new Thread(new Runnable() {
            public void run(){
                while(serverThreadRunning) {
                    try {
                        System.out.println("hello");
                        client = server.accept();
                        System.out.println("hello");
                        new Hexa(client);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    

    /**
     * @param args the byteToWrite line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Server test = new Server();
    }
}
