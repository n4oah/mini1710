package websocket.chatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import model.DAOInterface;
import model.QuizData;
import websocket.SessionData;

@ServerEndpoint("/sockets/chatting")
public class GameSystem {

	private static Set<SessionData> clients = Collections.synchronizedSet(new HashSet<SessionData>());

	private static HashMap<String, String> IDTable = new HashMap<>();
	private static Thread Timer ;
	private static QuizData nowpro;
	private static Set<SessionData> GameList = Collections.synchronizedSet(new HashSet<SessionData>());
	private static boolean GameState;
	int countmax = 3;
	int count = 3;

	private static List<QuizData> Qu;
	static {
		try {
			Qu = DAOInterface.Select("select * from Sketch_quiz", null, QuizData.class);
			System.out.println(Qu.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void inputID(String ID, String sessionID) {
		System.out.println("접속된 ID : " + ID);
		System.out.println("접속된세션 ID : " + sessionID);
		IDTable.put(sessionID, ID);
	}
	
	private void run() {
		if(Timer != null)
		{
			Timer.stop();
		}
		Timer = new Thread() {
			@Override
			public void run() {
				for(int i = 5 ; i > 0; i--) {
					
					System.out.println(i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SendMessage("게임시작 " + i + "초 전");
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				GameState = true;
				GameStart();
			}
			
		};
		Timer.start();
	}
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException, JSONException {

		System.out.println(message);
		if (message.startsWith("!")) {
			if(message.startsWith("game", 1)) {
				if(GameState) {
					SendMessage("진행중인 게임이 있습니다. ");
					return;
				}
				boolean state = false;
				SessionData user = null;
				String ID = "";
				for(SessionData data : clients) {
					if(data.getSession().equals(session)) {
						user = data;
						ID = data.getID();
						break;
					}
				}
				for(SessionData data : GameList) {
					if(data.equals(user))
					{
						state = true;
						break;
					}
				}
				if(state) {
					DirectMessage("게임 진행자 명단에 등록되어있습니다. ", session);
					

					return;
				}
				countmax = 3;
				SendMessage(ID + "님이 게임에 참가합니다.");
				
				JSONObject obj = new JSONObject();
				
				obj.put("name", "join");
				obj.put("type", "game");
				GameList.add(user);
				run();
				
				session.getBasicRemote().sendText(obj.toString());
				
			}
			else if(message.startsWith("r", 1)) {
				if(!GameState) {
					DirectMessage("게임중이 아닙니다..", session);
					return;
				}
				String r = message.replace("!r ", "").replace("!r", "");
				System.out.println(r);
				if(getid(session).equals(PaintScreen.GetNow())) {
					DirectMessage("당사자는 참여가 불가능합니다.", session);
					return;
				}
				if(count == 0) {
					DirectMessage("더이상 참여하실 수 없습니다.", session);
					return;
				}
				if(nowpro.QUIZ.equals(r))
				{
					GameState= false;
					String id = getid(session);
					SendMessage(id + "님이 정답을 맞추셨습니다.");
					GameEnd();
				}
				else {
					//DirectMessage("틀렸습니다. 기회는 " + --count + "번 남았습니다.", session);
					DirectMessage("틀렸습니다", session);
				}
			}
		} else {
			SendMessage(message, session);
		}
	}

	// 유저 세션처리
	private void Login(Session session) {

		synchronized (clients) {
			// Iterate over the connected sessions
			// and broadcast the received message

			String ID = getid(session);
			for (SessionData client : clients) {
				if (!client.getSession().equals(session)) {
					try {
						JSONObject obj = new JSONObject();

						obj.put("name", ID);
						obj.put("type", "login");

						client.getSession().getBasicRemote().sendText(obj.toString());
					} catch (Exception e) {
						RemoveList(client);
					}
				}
			}
			RemoveCommit();
		}
	}

	private void LogOut(Session session) {
		synchronized (clients) {
			// Iterate over the connected sessions
			// and broadcast the received message

			String ID = getid(session);
			for (SessionData client : clients) {
				if (!client.getSession().equals(session)) {
					try {
						JSONObject obj = new JSONObject();

						obj.put("name", ID);
						obj.put("type", "logout");

						client.getSession().getBasicRemote().sendText(obj.toString());
					} catch (Exception e) {
						RemoveList(client);
					}
				}
			}
			RemoveCommit();
		}
	}

	private void Kick(Session session) {
		synchronized (clients) {
			// Iterate over the connected sessions
			// and broadcast the received message

			String ID = getid(session);
			for (SessionData client : clients) {
				if (!client.getSession().equals(session)) {
					try {
						JSONObject obj = new JSONObject();

						obj.put("name", ID);
						obj.put("type", "kick");

						client.getSession().getBasicRemote().sendText(obj.toString());
					} catch (Exception e) {
						RemoveList(client);
					}
				}
			}
			RemoveCommit();
		}
	}

	// 접속처리
	@OnOpen
	public void onOpen(Session session) throws IOException, JSONException {
		// Add session to the connected sessions set
		String ID = getid(session);

		if (ID == null) {
			print(session, "로그인 정보가 없음.");
			session.close();
			return;
		} else {
			print(session, "로그인 정보 : " + ID);
		}
		clients.add(new SessionData(session, ID));
		Login(session);
		SendUsers(session);
		System.out.println("연결된 클라이언트 수 : " + clients.size());

	}

	@OnClose
	public void onClose(Session session) throws IOException {
		// Remove session from the connected sessions set
		String id = RemoveList(session);
		if (id != null) {
			LogOut(session);
			System.out.println("접속해제 : " + session.getId());
			System.out.println("연결된 클라이언트 수 : " + clients.size());
		}
	}

	// 유저목록 전송
	public void SendUsers(Session session) throws JSONException, IOException {
		JSONObject main = new JSONObject();
		JSONArray arr = new JSONArray();
		for (SessionData sessionData : clients) {
			JSONObject obj = new JSONObject();
			obj.put("name", sessionData.getID());
			arr.put(obj);
		}
		main.put("type", "userinfos");
		main.put("list", arr);
		session.getBasicRemote().sendText(main.toString());
	}

	// 에코 해당 세션을 제외한 모든 유저에게 전송
	private void SendMessage(String message, Session session) {
		synchronized (clients) {
			String ID = getid(session);
			for (SessionData client : clients) {
				if (!client.getSession().equals(session)) {
					try {
						JSONObject obj = new JSONObject();

						obj.put("name", ID);
						obj.put("type", "msg");
						obj.put("msg", message);

						client.getSession().getBasicRemote().sendText(obj.toString());
					} catch (Exception e) {
						RemoveList(client);
					}
				}
			}
			RemoveCommit();
		}
	}
	private void DirectMessage(String message, Session session) {
		synchronized (clients) {
			String ID = getid(session);
			for (SessionData client : clients) {
				if (client.getSession().equals(session)) {
					try {
						JSONObject obj = new JSONObject();

						obj.put("name", "system");
						obj.put("type", "msg");
						obj.put("msg", message);

						client.getSession().getBasicRemote().sendText(obj.toString());
					} catch (Exception e) {
						RemoveList(client);
					}
				}
			}
			RemoveCommit();
		}
	}
	//모든 유저에게 때려박음
	private void SendMessage(String message) {
		synchronized (clients) {
			for (SessionData client : clients) {
				try {
					JSONObject obj = new JSONObject();

					obj.put("type", "all");
					obj.put("msg", message);

					client.getSession().getBasicRemote().sendText(obj.toString());
				} catch (Exception e) {
					RemoveList(client);

				}
			}
			RemoveCommit();
		}
	}
	private QuizData pro() {
		Random r = new Random();
		int a = r.nextInt();
		if(a < 0)
			a *= -1;
		 nowpro = Qu.get(a%Qu.size());
		return nowpro;
	}
	private void GameStart() {
		synchronized (clients) {
			PaintScreen.Init();
			Random r = new Random();
			int a = r.nextInt();
			if(a < 0)
				a *= -1;
			a = a % GameList.size();
			int i = 0 ;
			for (SessionData client : GameList) {
				PaintScreen.inputID(client.getID(), client.getSession().getQueryString().replace("id=", ""));
				if(i == a) {
					PaintScreen.SetNow(client.getID());
					SendMessage(client.getID() + " 님이 그림 그리는사람이 되었습니다.");
					SendMessage(client.getID() + " 님이 그리시는것에 따라 답이 떠오르신다면 !r 후 답을 입력해주세요! 기회는 세번뿐입니다.");
					DirectMessage("정답은 : " + pro() + "입니다", client.getSession());
				}
				try {
					JSONObject obj = new JSONObject();
					obj.put("type", "gamestart");
					client.getSession().getBasicRemote().sendText(obj.toString());
				} catch (Exception e) {
					RemoveList(client);
				}
				i++;
			}
			
		}
	}
	private void GameEnd() {
		synchronized (clients) {
			PaintScreen.Init();
			for (SessionData client : GameList) {
				try {
					JSONObject obj = new JSONObject();
					obj.put("type", "gameend");
					client.getSession().getBasicRemote().sendText(obj.toString());
				} catch (Exception e) {
					RemoveList(client);
				}
			}
			GameList.clear();
			GameState = false;
		}
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
		if(GameList.size() <= 1 && GameState)
		{
			GameEnd();
		}
	}
	//세션의 ID검색
	private String getid(Session session) {
		if (session == null) {
			// System.out.println("?");
			return "";
		} else
			return IDTable.get(session.getQueryString().replace("id=", ""));
		// return "";
	}
	//테스트용 출력
	private void print(Session session, String msg) {
		System.out.println("Session ID : " + session.getId());
		System.out.println("Session Query : " + session.getQueryString());
	}

}