package chatroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {
	public static final String EXIT="exit";
	public static final int PORT=8888;
	//static Map<String,Socket>NameSocketMap= new HashMap<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ServerSocket ss=new ServerSocket(PORT)){
			System.out.println("�����ҷ�������һ���������ڼ���"+PORT+"�˿�");
			while(true) {
				Socket socket=ss.accept();
				System.out.println("�����û����ӵ��������ˣ���ϢΪ:"+socket);
				new Thread(new ChatServerRunnable(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
