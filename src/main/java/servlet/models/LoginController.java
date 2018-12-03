package servlet.models;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

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
		// ServletContext context = getServletContext();
		// System.out.println("Servlet Information : " +
		// context.getServerInfo());
		// System.out.println("Servlet ContentType : " +
		// request.getContentType());
		// System.out.println("Version : " + context.getMajorVersion());
		//
		// ApplicationContext springContext = new
		// ClassPathXmlApplicationContext(
		// "spring.xml");
		// models.UserDetails user = (models.UserDetails) springContext
		// .getBean("userDetails");
		//
		// ((ClassPathXmlApplicationContext) springContext).close();
		// String username = request.getParameter("username");
		// String password = request.getParameter("password");
		//
		// if (username.equals(user.getUsername())
		// && password.equals(user.getPassword())) {
		// HttpSession session = request.getSession();
		// session.setAttribute("username", username);
		// session.setAttribute("password", password);
		//
		// }

		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if (checkUserAuthentication(username, password)) {
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				session.setAttribute("password", password);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		response.sendRedirect("UserDetails");

	}

	private boolean checkUserAuthentication(String username, String password)
			throws Exception {

		MongoClientURI uri = new MongoClientURI(
				"mongodb://localhost:27017/test");
		try (MongoClient mongoClient = new MongoClient(uri)) {

			DB myDB = mongoClient.getDB("user_database");
			DBCollection UserDetailsCollection = myDB
					.getCollection("user_details");
			BasicDBObject queryofUsersDetails = new BasicDBObject();
			queryofUsersDetails.put("username", username);
			queryofUsersDetails.put("password", password);
			DBCursor cursorforUsersDetails = UserDetailsCollection
					.find(queryofUsersDetails);
			if (cursorforUsersDetails.hasNext()) {
				return true;
			} else {
				return false;
			}
		}

	}

}
