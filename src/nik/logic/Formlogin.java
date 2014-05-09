package nik.logic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nik.parsers.DOMPars;
import org.w3c.dom.Document;


/**
 * Servlet implementation class Formlogin
 */
@WebServlet("/Formlogin")
public class Formlogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String USER_KEY = "ServletLogin.user";
	public static String FIELD_USER = "username";
	public static String FIELD_PASSWORD = "password";
	public static String FIELD_EMAIL = "email";
	public static boolean FLAG = true;
	//DOMPars domPars = new DOMPars();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Formlogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	    response.setHeader("Expires", "Tues, 01 Jan 1980 00:00:00 GMT");
	    String uri = request.getRequestURI();
	    HttpSession session = request.getSession();
	    String user = (String) session.getAttribute(USER_KEY);
	    out.println();
	    DOMPars domPars = new DOMPars();
	    domPars = listRole(out,domPars);
	    if (user == null) {
	      out.println("<html>");
	      out.println("<head><body>");
		  out.println("<title>Login</title>");
	      login(out, uri);
	      //userList(out,domPars);
		  out.println("</body></html>");
	      return;
	    }else if (user != null ){
	      out.println("<html>");
	      out.println("<head>");
	      out.println("<title>Welcome, create POM!</title>");
	      out.println("</head>");
	      out.println("<body>");
	      out.println("<center><h2>" + user + " welcome to our site!</h2>");
		  out.println("<br><form method=POST action=\"" + uri + "\">");
		  out.println("<input type=submit value=\"QUIT\">");
	      out.println("</center><br><br>");
	      userList(out,domPars);
	      out.println("</body>");
	      out.println("</html>");
	      out.flush();
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
	    String user = (String) session.getAttribute(USER_KEY);
	    if (user == null ) {
	      String email = request.getParameter(FIELD_EMAIL);
	      String password = request.getParameter(FIELD_PASSWORD);
	      DOMPars domPars = new DOMPars();
	      domPars = listRole(out,domPars);
	      if (!validUser(email, password, domPars)) {
	        out.println("<html>");
	        out.println("<title>Invalid User</title>");
	        out.println("<body><center><h2>Invalid User!</h2><br></center>");
	        out.println("</body></html>");
	        out.flush();
	        return;
	      }
	      session.setAttribute(USER_KEY, email);
	      FLAG = true;
	      
	    }else if(user != null && FLAG == true){
	      session.invalidate();
	      FLAG = false;
	    }
	    response.sendRedirect(request.getRequestURI());//для 2 старниц
	}
	
	protected void login(PrintWriter out, String uri) throws java.io.IOException {
		out.println("<center><h2>Welcome! Please login</h2>");
		out.println("<br><form method=POST action=\"" + uri + "\">");
		out.println("<table>");
		out.println("<tr><td>EMAIL:</td>");
		out.println("<td><input type=text name=" + FIELD_EMAIL + " size=30></td></tr>");
		out.println("<tr><td>Password:</td>");
		out.println("<td><input type=password name=" + FIELD_PASSWORD + " size=10></td></tr>");
		out.println("</table><br>");
		out.println("<input type=submit value=\"Login\">");
		out.println("</form></center>");

	}
	
	protected void userList(PrintWriter out, DOMPars domPars){
		out.println("<ul>");
		for(int i = 0; i < domPars.getUsers().size(); i++){
			out.println("<li>" + domPars.getUsers().get(i) + "</li>");
		}
		out.println("</ul>");
	}
	
	protected DOMPars listRole(PrintWriter out, DOMPars domPars){
		try {
			String path = getServletContext().getRealPath("WEB-INF/resources/Users.xml");
		    File fXmlFile = new File(path);
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(fXmlFile);
		    doc.getDocumentElement().normalize();
		    domPars.pars(doc);	
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		return domPars;
	}

	protected boolean validUser(String email, String password, DOMPars domPars) {
		boolean valid = false;
		for(int i = 0; i < domPars.getUsers().size(); i++){
			if ((email != null) && (email.length() > 0) && domPars.getUsers().get(i).getEmail().equals(email)
					&& domPars.getUsers().get(i).getPassword().equals(password)) {
				valid = true;
				i = domPars.getUsers().size();
			}
		}
		return valid;
	}

}
