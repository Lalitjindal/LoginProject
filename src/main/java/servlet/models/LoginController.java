package servlet.models;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		System.out.println("Servlet Information : " + context.getServerInfo());
		System.out.println("Servlet ContentType : " + request.getContentType());
		System.out.println("Version : " + context.getMajorVersion());

		ApplicationContext springContext = new ClassPathXmlApplicationContext(
				"spring.xml");
		models.UserDetails user = (models.UserDetails) springContext
				.getBean("userDetails");

		((ClassPathXmlApplicationContext) springContext).close();
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username.equals(user.getUsername())
				&& password.equals(user.getPassword())) {
			HttpSession session=request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("password", password);
			

		} 
		response.sendRedirect("UserDetails");
	}

}
