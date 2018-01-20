/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hexapod;

import java.io.DataOutputStream;
import java.net.Socket;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

/**
 *
 * @author Administrator
 */
public class Video_Stream {
    
    private VideoCapture camera = null;
    private final int imageWidth = 320;
    private final int imageHeight = 240;
    private Mat image = new Mat();
    private MatOfByte imageMatBuff = new MatOfByte();
    private int imageArrBuff_lenght;
    private byte[] imageArrBuff = null;
    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    
    private DataOutputStream dos = null;

    
    public Video_Stream(Socket client) {
        camera = new VideoCapture(0);
        camera.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, imageWidth);
        camera.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, imageHeight);
        try {dos = new DataOutputStream(client.getOutputStream());} catch(Exception e) {e.printStackTrace();}
        
        new Thread(new Runnable(){
            public void run(){
                if (camera.isOpened()) {
                    while(true){
                        camera.read(image);
                    }
                }
            }
        }).start();
        
        try {Thread.sleep(500);}catch(Exception e) {e.printStackTrace();}
        Imgcodecs.imencode(".bmp", image, imageMatBuff);
        imageArrBuff_lenght = (int)(imageMatBuff.total() * imageMatBuff.elemSize());
        imageArrBuff = new byte[imageArrBuff_lenght];

    }
    
}
