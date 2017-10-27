package websocket.chatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.xml.bind.DatatypeConverterInterface;

import model.DAOInterface;
import model.QuizData;
import websocket.SessionData;



@ServerEndpoint("/sockets/Paint")
public class PaintScreen {

	private static Set<SessionData> clients = Collections
			.synchronizedSet(new HashSet<SessionData>());
	
	private static HashMap<String, String> IDTable = new HashMap<>();
	private static String NowID = "";
	public static void SetNow(String Nowid) {
		NowID = Nowid;
		System.out.println("마스터 ID = " + NowID);
	}
	public static void Init() {
		IDTable.clear();
		clients.clear();
	}
	public static void inputID(String ID, String sessionID) {
		System.out.println("접속된 ID : " + ID);
		System.out.println("접속된세션 ID : " + sessionID);
		IDTable.put(sessionID, ID);
	}
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		
		System.out.println(message);
		String ID = getid(session);
		if(!ID.equals(NowID))
			return;
		synchronized (clients) {
			// Iterate over the connected sessions
			// and broadcast the received message

			for (SessionData client : clients) {
				if (!client.getSession().equals(session)) {
					try {
						client.getSession().getBasicRemote().sendText(message);
					}
					catch (Exception e) {
						Remove.add(client);
					}
				}
			}
			RemoveCommit();
		}
	}
	private String getid(Session session) {
		return IDTable.get(session.getQueryString().replace("id=",  ""));
		//return "";
	}
	private void print(Session session, String msg) {
		System.out.println("Session ID : " + session.getId());
		System.out.println("Session Query : " + session.getQueryString());
	}
	@OnOpen
	public void onOpen(Session session) throws IOException {
		// Add session to the connected sessions set
		String ID = getid(session);
		if(ID.equals(""))
		{
			print(session, "로그인 정보가 없음.");
			//session.close();
			//return;
		}
		else {
			print(session, "로그인 정보 : " + ID);
		}
		if(NowID.equals(ID)) {
			session.getBasicRemote().sendText("{\"pageX\":57,\"pageY\":398.125,\"color\":\"rgb(0, 0, 0)\",\"state\":\"master\"}");
		}
		clients.add(new SessionData(session, ID));
		System.out.println("연결된 클라이언트 수 : " + clients.size());
		
	}

	//로그아웃, 세션지우기
	private static List<SessionData> Remove = new ArrayList<>();
	private void RemoveList(SessionData session) {
		Remove.add(session);
	}
	private String RemoveList(Session session) {
		for (SessionData client : clients) {
			if (client.getSession().equals(session)) {
				Remove.add(client);
				return client.getID();
			}
		}
		return "";
	}
	private void RemoveCommit() {

		for (SessionData client : Remove) {
			clients.remove(client);
		}
		Remove.clear();
	}
	
	@OnClose
	public void onClose(Session session) {
		// Remove session from the connected sessions set
		RemoveList(session);
		System.out.println("접속해제 : " + session.getId());
		System.out.println("연결된 클라이언트 수 : " + clients.size());
	}
	public static String GetNow() {
		return NowID;
	}
}