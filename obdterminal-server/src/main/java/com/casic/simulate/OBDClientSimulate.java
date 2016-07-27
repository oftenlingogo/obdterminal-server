package com.casic.simulate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.casic.utils.Hex;

public class OBDClientSimulate {
	private Socket con;

	public OBDClientSimulate() throws InterruptedException, IOException {
		clientInit();
	}

	public void clientInit() throws InterruptedException, IOException {
		try {
			this.con = new Socket("localhost", 34568);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.con.setSoTimeout(10000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		OutputStream out = this.con.getOutputStream();
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader("D:\\sample.txt"));
		StringBuilder sb = new StringBuilder();
		String line= null;
		while((line=reader.readLine())!=null)
			sb.append(line);
		
		try {
			byte[] data = Hex.decodeHex(sb.toString().toCharArray());
			
			out.write(data);
			System.out.println("+++++++++++send success~~~~~");
		} catch (IOException ie) {
			System.out.println("----------send failed!!!!!!");
			ie.toString();
		}
		this.con.close();
		out.close();
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		new OBDClientSimulate();
	}
}