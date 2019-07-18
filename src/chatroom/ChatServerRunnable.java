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
			dos.writeUTF("请输入你的昵称：");
			dos.flush();
			while(true) {
				NAME=dis.readUTF();
				if(!NameSocketMap.containsKey(NAME)) {
					NameSocketMap.put(NAME, socket);
					ChatClient.stop();
					dos.writeUTF("登录成功！");
					dos.flush();
					
					break;
				}else {
					dos.writeUTF("该昵称已存在，请重新输入！");
					dos.flush();
				}
			}
			dos.writeUTF("欢迎使用聊天系统\n输入【list users】可以查看当前登录用户列表\n输入"
					+ "【to all 消息内容】可以群发消息\n输入【to 某个用户 消息内容】可以给指定用户发送消息\n"
					+ "输入【exit】可以退出聊天");
			dos.flush();
			while(true) {
				String content=dis.readUTF();
				if(content.equals("exit")) {
					break;
				}else if(content.equals("list users")) {
					dos.writeUTF(String.valueOf(NameSocketMap.keySet()));
					dos.flush();
				}else if(!content.startsWith("to ")) {
					dos.writeUTF("输入格式错误！");
					dos.flush();
				}else {
					String []strs=content.split(" ");
					if(strs[1].equals("all")) {
						for(Socket sk:NameSocketMap.values()) {
							dos1=new DataOutputStream(sk.getOutputStream());
							dos1.writeUTF(NAME+"消息："+strs[2]);
							dos1.flush();
						}
					}else if(!NameSocketMap.containsKey(strs[1])) {
						dos.writeUTF(strs[1]+"不在线！");
					}else {
						for(String name:NameSocketMap.keySet()) {
							if(strs[1].equals(name)) {
								dos2=new DataOutputStream(NameSocketMap.get(name).getOutputStream());
								dos2.writeUTF(NAME+"消息："+strs[2]);
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
