
import com.tony.core.constant.ITPort;
import com.tony.core.http.JettyServer;

public class LogicMain extends JettyServer {

	
	public static void main(String[] args) {

		LogicMain m = new LogicMain();
		m.setPort(ITPort.LOGIC);
		m.addWebappPath("tony-logic/src/main/webapp");
		m.setContextPath("/");
		m.start();
	}
	
}
