package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MethodServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET /method 호출...");
        
        String method = request.getMethod();
        String uri = request.getRequestURI();
        @SuppressWarnings("unused")
		StringBuffer url = request.getRequestURL();
        String id = request.getParameter("id");
        
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>메소드 마스터</title>");
        out.println("</head>");
        out.println("<body>");
        out.println(" =========================================<br>");
        out.println(" &nbsp;&nbsp;&nbsp;&nbsp;< 메소드 마스터 입니다 ><br>");
        out.println(" =========================================<br>");
        out.println("요청 파라미터(id) : " + id + "<br>");
        out.println("요청 메서드 : " + method + "<br>");
        out.println("요청 URI : " + uri + "<br>");
        out.println(" ========================================= ");
        out.println("</body>");
        out.println("</html>");
        
        out.flush();
        out.close();
        
        /*
        System.out.println("요청 메소드 : " + method);
        System.out.println("요청 URI : " + uri);
        System.out.println("요청 URL : " + url.toString());
        System.out.println("요청 마라미더(id) : " + id);
        */
        
        
        
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// encoding 설정
		request.setCharacterEncoding("utf-8");
		
		System.out.println("POST /method 호출...");
		
        String method = request.getMethod();
        String uri = request.getRequestURI();
        @SuppressWarnings("unused")
		StringBuffer url = request.getRequestURL();
        String id = request.getParameter("id");
        
        /*
        System.out.println("요청 메소드 : " + method);
        System.out.println("요청 URI : " + uri);
        System.out.println("요청 URL : " + url.toString());
        System.out.println("요청 마라미더(id) : " + id);
        */
        
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>메소드마스터</title>");
        out.println("</head>");
        out.println("<body>");
        out.println(" =========================================<br>");
        out.println(" &nbsp;&nbsp;&nbsp;&nbsp;< 소드마스터 입니다 ><br>");
        out.println(" =========================================<br>");
        out.println("요청 바라미러(id) : " + id + "<br>");
        out.println("요청 메서도 : " + method + "<br>");
        out.println("요청 URI : " + uri + "<br>");
        out.println(" ========================================= ");
        out.println("</body>");
        out.println("</html>");      
	}

	
	/*
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/메머드 호출.");
	}	
	*/
		
}
