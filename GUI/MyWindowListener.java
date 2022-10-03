/*	MyWindowListener 
 *  클라이언트의 윈도우 클로징 이벤트 발생시
 *  서버에게 종료를 알리는 패킷을 전송 후 Sokect.close() 함수를 호출
 *  서버 역시 클라이언트의 종료 패킷을 받은 뒤 Socket.close() 함수를 호출함으로써
 *  양쪽 모두 정상적으로 소켓 연결 종료   
 */
package GUI;

import Network.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MyWindowListener extends WindowAdapter {
	private Socket socket;
	private ObjectOutputStream writer;

	public MyWindowListener(Socket s, ObjectOutputStream oos) {
		socket = s;
		writer = oos;
	}

	public void windowClosing(WindowEvent e) {
		try {
			writer.writeObject(new Protocol(0,0));
			System.out.println("소켓 종료");
			socket.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.exit(0);
	}
}
