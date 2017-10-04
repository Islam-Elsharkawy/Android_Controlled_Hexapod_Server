/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hexapod;

import java.io.IOException;
import java.nio.ByteBuffer;
import jdk.dio.DeviceManager;
import jdk.dio.i2cbus.I2CDevice;
import jdk.dio.i2cbus.I2CDeviceConfig;
import static jdk.dio.i2cbus.I2CDeviceConfig.ADDR_SIZE_7;

/**
 *
 * @author Administrator
 */
public class PWM_Driver {
    
    I2CDevice device = null;
    ByteBuffer byteToRead;
    ByteBuffer byteToWrite;
    
    public PWM_Driver(int Address) {
        I2CDeviceConfig config = new I2CDeviceConfig.Builder()
           .setControllerNumber(1)
           .setAddress(Address, ADDR_SIZE_7)
           .build();
                
        byteToRead = ByteBuffer.allocateDirect(1);
        byteToWrite = ByteBuffer.allocateDirect(1);
        
        try {
            device = DeviceManager.open(config);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public byte readData(int register) {
        int readResult = -1;
        
        try{
            byteToRead.clear();
            readResult = device.read(register, 1, byteToRead);
            if (readResult != -1) {
                byteToRead.rewind();
                return byteToRead.get();
            }    
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void writeData(int register, byte data) {
        try{
            byteToWrite.clear();
            byteToWrite.put(data);
            byteToWrite.rewind();

            device.write(register, 1, byteToWrite);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setFrequency(int frequency) {
        int data = 25000000/(4096*frequency);
        writeData(0x00, (byte)0x10);
        writeData(0xFE, (byte)data);
        writeData(0x01, (byte)0x0C);
        writeData(0x00, (byte)0x00);
    }
    
    public void powerOff() {
        writeData(0x00, (byte)0x10);
    }
    
    public void setPWM (int channel, double pwm) {
        double delay = 0.1;
        int LED_ON = (int)(4096.0*delay - 1);
        int LED_OFF = LED_ON + (int)(pwm*4096);
        int LED_ON_H = LED_ON/256;
        int LED_ON_L = LED_ON%256;
        int LED_OFF_H = LED_OFF/256;
        int LED_OFF_L = LED_OFF%256;
        switch (channel) {
            case 0:
                writeData(0x06, (byte)LED_ON_L);
                writeData(0x07, (byte)LED_ON_H);
                writeData(0x08, (byte)LED_OFF_L);
                writeData(0x09, (byte)LED_OFF_H);
                break;
            case 1:
                writeData(0x0A, (byte)LED_ON_L);
                writeData(0x0B, (byte)LED_ON_H);
                writeData(0x0C, (byte)LED_OFF_L);
                writeData(0x0D, (byte)LED_OFF_H);
                break;
            case 2:
                writeData(0x0E, (byte)LED_ON_L);
                writeData(0x0F, (byte)LED_ON_H);
                writeData(0x10, (byte)LED_OFF_L);
                writeData(0x11, (byte)LED_OFF_H);
                break;
            case 3:
                writeData(0x12, (byte)LED_ON_L);
                writeData(0x13, (byte)LED_ON_H);
                writeData(0x14, (byte)LED_OFF_L);
                writeData(0x15, (byte)LED_OFF_H);
                break;
            case 4:
                writeData(0x16, (byte)LED_ON_L);
                writeData(0x17, (byte)LED_ON_H);
                writeData(0x18, (byte)LED_OFF_L);
                writeData(0x19, (byte)LED_OFF_H);
                break;
            case 5:
                writeData(0x1A, (byte)LED_ON_L);
                writeData(0x1B, (byte)LED_ON_H);
                writeData(0x1C, (byte)LED_OFF_L);
                writeData(0x1D, (byte)LED_OFF_H);
                break;
            case 6:
                writeData(0x1E, (byte)LED_ON_L);
                writeData(0x1F, (byte)LED_ON_H);
                writeData(0x20, (byte)LED_OFF_L);
                writeData(0x21, (byte)LED_OFF_H);
                break;
            case 7:
                writeData(0x22, (byte)LED_ON_L);
                writeData(0x23, (byte)LED_ON_H);
                writeData(0x24, (byte)LED_OFF_L);
                writeData(0x25, (byte)LED_OFF_H);
                break;
            case 8:
                writeData(0x26, (byte)LED_ON_L);
                writeData(0x27, (byte)LED_ON_H);
                writeData(0x28, (byte)LED_OFF_L);
                writeData(0x29, (byte)LED_OFF_H);
                break;
            case 9:
                writeData(0x2A, (byte)LED_ON_L);
                writeData(0x2B, (byte)LED_ON_H);
                writeData(0x2C, (byte)LED_OFF_L);
                writeData(0x2D, (byte)LED_OFF_H);
                break;
            case 10:
                writeData(0x2E, (byte)LED_ON_L);
                writeData(0x2F, (byte)LED_ON_H);
                writeData(0x30, (byte)LED_OFF_L);
                writeData(0x31, (byte)LED_OFF_H);
                break;
            case 11:
                writeData(0x32, (byte)LED_ON_L);
                writeData(0x33, (byte)LED_ON_H);
                writeData(0x34, (byte)LED_OFF_L);
                writeData(0x35, (byte)LED_OFF_H);
                break;
            case 12:
                writeData(0x36, (byte)LED_ON_L);
                writeData(0x37, (byte)LED_ON_H);
                writeData(0x38, (byte)LED_OFF_L);
                writeData(0x39, (byte)LED_OFF_H);
                break;
            case 13:
                writeData(0x3A, (byte)LED_ON_L);
                writeData(0x3B, (byte)LED_ON_H);
                writeData(0x3C, (byte)LED_OFF_L);
                writeData(0x3D, (byte)LED_OFF_H);
                break;
            case 14:
                writeData(0x3E, (byte)LED_ON_L);
                writeData(0x3F, (byte)LED_ON_H);
                writeData(0x40, (byte)LED_OFF_L);
                writeData(0x41, (byte)LED_OFF_H);
                break;
            case 15:
                writeData(0x42, (byte)LED_ON_L);
                writeData(0x43, (byte)LED_ON_H);
                writeData(0x44, (byte)LED_OFF_L);
                writeData(0x45, (byte)LED_OFF_H);
                break;
        }
    }

    public void setAngle (int channel, int angle) {
        double pwm = (angle*.0005)+.075;
        setPWM(channel, pwm);
    }  
    
    public void close () {
        try {
            System.out.println("yup");
            device.unlock();
            device.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
}
