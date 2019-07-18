package chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServerRunnable implements Runnable {
	private Socket socket;
	DataInputStream dis=null;
	DataOutputStream dos=null;
	DataOutputStream dos1=null;
	DataOutputStream dos2=null;
	String NAME="";
	int n=0;
	public static Map<String,Socket>NameSocketMap= new HashMap<>();
	public ChatServerRunnable(Socket s) {
		this.socket=s;
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
			dis=new DataInputStream(socket.getInputStream());
			dos=new DataOutputStream(socket.getOutputStream());
			dos.writeUTF("����������ǳƣ�");
			dos.flush();
			while(true) {
				NAME=dis.readUTF();
				if(!NameSocketMap.containsKey(NAME)) {
					NameSocketMap.put(NAME, socket);
					ChatClient.stop();
					dos.writeUTF("��¼�ɹ���");
					dos.flush();
					
					break;
				}else {
					dos.writeUTF("���ǳ��Ѵ��ڣ����������룡");
					dos.flush();
				}
			}
			dos.writeUTF("��ӭʹ������ϵͳ\n���롾list users�����Բ鿴��ǰ��¼�û��б�\n����"
					+ "��to all ��Ϣ���ݡ�����Ⱥ����Ϣ\n���롾to ĳ���û� ��Ϣ���ݡ����Ը�ָ���û�������Ϣ\n"
					+ "���롾exit�������˳�����");
			dos.flush();
			while(true) {
				String content=dis.readUTF();
				if(content.equals("exit")) {
					break;
				}else if(content.equals("list users")) {
					dos.writeUTF(String.valueOf(NameSocketMap.keySet()));
					dos.flush();
				}else if(!content.startsWith("to ")) {
					dos.writeUTF("�����ʽ����");
					dos.flush();
				}else {
					String []strs=content.split(" ");
					if(strs[1].equals("all")) {
						for(Socket sk:NameSocketMap.values()) {
							dos1=new DataOutputStream(sk.getOutputStream());
							dos1.writeUTF(NAME+"��Ϣ��"+strs[2]);
							dos1.flush();
						}
					}else if(!NameSocketMap.containsKey(strs[1])) {
						dos.writeUTF(strs[1]+"�����ߣ�");
					}else {
						for(String name:NameSocketMap.keySet()) {
							if(strs[1].equals(name)) {
								dos2=new DataOutputStream(NameSocketMap.get(name).getOutputStream());
								dos2.writeUTF(NAME+"��Ϣ��"+strs[2]);
								dos2.flush();
							}	
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	public void contentReceive(String content) {
		if(content.equals("exit")) {
			break;
		}else if(content.equals("list users")) {
			dos.writeUTF(String.valueOf(NameSocketMap.keySet()));
		}else {
			String []strs=dis.readUTF().split(" ");
			if(strs[1].equals("all")) {
				
			}
		}
	}
	*/
}
