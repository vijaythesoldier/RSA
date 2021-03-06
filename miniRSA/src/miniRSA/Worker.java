package miniRSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class to handle a request from client
 * 
 * @author Yue
 *
 */
public class Worker implements Runnable {
	
	private Socket socket;
	private PriKey privateKey;
	
	Worker(Socket s, PriKey pri) {
		this.socket = s;
		this.privateKey = pri;
	}

	@Override
	public void run() {
		
		// output my public key
		PrintWriter out = null;
		try {
            out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			System.err.println("I/O Exception.");
		}
		String pub = ChatServer.keyPair.pub.e.toString() + " " + ChatServer.keyPair.pub.c.toString();
		out.println(pub);
		
		// read from client
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("[SYSTEM] Error getting input stream from socket.");
		}
		
		try {
			String cyperText = in.readLine();
			String [] cyperLetters = cyperText.split(" ");
			System.out.println("[COMMUNICATION] Received message from " + socket.getRemoteSocketAddress().toString());
			System.out.println("[COMMUNICATION] Cyper text: " + cyperText);
			System.out.print("[COMMUNICATION] Plain text: ");

			for (int i = 0; i < cyperLetters.length; ++i) {
				try {
					if (cyperLetters[i].length() > 0)
						System.out.print((char) RSA.endecrypt(new BigInteger(cyperLetters[i]), 
								  privateKey.d, privateKey.c).intValue());
				} catch (Exception e) {
					
				}
			}
			System.out.println();
			System.out.println();

			socket.close();
			
		} catch (Exception e) {
			System.out.println("[SYSTEM] I/O Error with " + socket.getRemoteSocketAddress().toString());
		}
			

	}

}
