package com.jjc.imgup.common.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTest {
	public static boolean isLoclePortUsing(int port){  
        boolean flag = true;  
        try {  
            flag = isPortUsing("10.158.44.201", port);  
        } catch (Exception e) {  
        }  
        return flag;  
    }  
	
	public static boolean isPortUsing(String host,int port) throws UnknownHostException{  
        boolean flag = false;  
        InetAddress theAddress = InetAddress.getByName(host);  
        try {  
            Socket socket = new Socket(theAddress,port);  
            flag = true;  
        } catch (IOException e) {  
           e.printStackTrace();   
        }  
        return flag;  
    }
	
	public static void main(String[] args) {
		for (int i = 63636; i < 63640; i++) {
			try {
				InetAddress localHost = InetAddress.getLocalHost();
				Socket socket = new Socket(localHost, i);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
