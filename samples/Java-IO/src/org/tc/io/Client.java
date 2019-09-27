package org.tc.io;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class Client {

	public static void main(String args[]) {
		new Thread(() -> {
			 try {
				 Socket socket = new Socket("127.0.0.1", 8080);
				 while (true) {
					 OutputStream mOutputStream = socket.getOutputStream();
					 mOutputStream.write((new Date() + " to server").getBytes());
					 mOutputStream.flush();
					 Thread.sleep(2000);
				 }
				 
			 } catch(Exception e) {
				 e.printStackTrace();
				 System.out.println("exception:" + e.getMessage());
			 }
		}).start();
		
	}
}
