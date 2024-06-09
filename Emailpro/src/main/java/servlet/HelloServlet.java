package servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/hello/servlet")
public class HelloServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init() : 최초 한번만 실행되는 메소드입니다");
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse reponse)
				throws ServletException, IOException {
		
		System.out.println("service() : 실제 작업이 수행되는 메소드이며, 사용자가 요청할 때마다 실행됩니다");
	}
	
	@Override
	public void destroy() {
		System.out.println("destroy() : 서버재시작 등에 실행되는 메소드입니다");
	}
}



