package websocket;

import javax.websocket.Session;

public class SessionData {
	private Session session;
	private String ID;
	public SessionData(Session session, String iD) {
		super();
		this.session = session;
		ID = iD;
	}
	public Session getSession() {
		return session;
	}
	public String getID() {
		return ID;
	}
}
