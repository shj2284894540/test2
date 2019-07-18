package chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {
	static boolean stop=true;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			DataInputStream dis=null;
			DataOutputStream dos=null;
			Socket s=null;
			String NAME="";
			String str=null;
			Scanner sc=new Scanner(System.in);
			try {
				s=new Socket("10.25.44.155",8888);
				dis=new DataInputStream(s.getInputStream());
				dos=new DataOutputStream(s.getOutputStream());
				System.out.println(dis.readUTF());
		//		while(true){
					NAME=sc.nextLine();
					dos.writeUTF(NAME);
					dos.flush();
					System.out.println(dis.readUTF());
					
	//			}
				System.out.println(dis.readUTF());
				while(true) {
					str=dis.readUTF();
					if(!str.equals(null)) {
						System.out.println(dis.readUTF());
					}
					String content=sc.nextLine();
					dos.writeUTF(content);
					dos.flush();
					if(content.equals("exit")) {
						break;
					}
					String contents=dis.readUTF();
					System.out.println(contents);
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				if(e.getMessage().equals("Connection reset")) {
					System.out.println("Á¬½Ó¶Ï¿ª£¡");
				}else{
				e.printStackTrace();
				}
			}finally {
				try {
					dis.close();
					dos.close();
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	}
	
	public static void stop() {
		stop=false;
	}
	
}
