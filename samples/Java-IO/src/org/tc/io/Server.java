package org.tc.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String args[]) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8080);
		
		new Thread(() -> {
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					new Thread(() -> {
						try {
							InputStream inputStream = socket.getInputStream();
							byte[] data = new byte[1024];
							while (true) {
								int len;
								while ((len = inputStream.read(data)) != -1) {
									System.out.println("received data from client: " + new String(data, 0, len));
								}
							}
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}).start();
		
		
	}
	
}
