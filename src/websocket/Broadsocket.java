package websocket;

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

import org.codehaus.jettison.json.JSONObject;



@ServerEndpoint("/broadcasting")
public class Broadsocket {

	private static Set<SessionData> clients = Collections
			.synchronizedSet(new HashSet<SessionData>());
	
	private static HashMap<String, String> IDTable = new HashMap<>();



	public static void inputID(String ID, String sessionID) {
		System.out.println("접속된 ID : " + ID);
		System.out.println("접속된세션 ID : " + sessionID);
		IDTable.put(sessionID, ID);
	}
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		
		System.out.println(message);
		synchronized (clients) {
			// Iterate over the connected sessions
			// and broadcast the received message

			String ID = getid(session);
			List<SessionData> Remove = new ArrayList<>();
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
			for (SessionData client : clients) {
				clients.remove(Remove);
			}
		}
	}
	private String getid(Session session) {
		//return IDTable.get(session.getQueryString().replace("id=",  ""));
		return "";
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
		clients.add(new SessionData(session, ID));
		System.out.println("연결된 클라이언트 수 : " + clients.size());
		
	}

	@OnClose
	public void onClose(Session session) {
		// Remove session from the connected sessions set
		System.out.println("접속해제 : " + session.getId());
		System.out.println("연결된 클라이언트 수 : " + clients.size());
	}
}