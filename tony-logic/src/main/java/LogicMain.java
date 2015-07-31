
import com.tony.core.constant.EnvConf;
import com.tony.core.http.JettyServer;

public class LogicMain extends JettyServer {

	
	public static void main(String[] args) {

		LogicMain m = new LogicMain();
		m.setPort(Integer.parseInt(EnvConf.getConfig("LOGIC.PORT")));
		m.addWebappPath("tony-logic/src/main/webapp");
		m.setContextPath("/");
		m.start();
	}
	
}
