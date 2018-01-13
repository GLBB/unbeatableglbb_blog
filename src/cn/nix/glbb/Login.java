package cn.nix.glbb;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user = request.getParameter("user");
		String password = request.getParameter("password");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			if(user!=null&&password!=null){
				db = dbf.newDocumentBuilder();
				ServletContext context = this.getServletContext();
				String path = context.getRealPath("/user/user.xml");

				Document document = db.parse(path);

				NodeList list = document.getElementsByTagName("user");
				for (int i = 0; i < list.getLength(); i++) {
					Element element = (Element) list.item(i);
					Element userElement = (Element) element.getElementsByTagName("name").item(0);
					Element passwordElement = (Element) element.getElementsByTagName("password").item(0);
					if(user.equals(userElement.getTextContent())&&password.equals(passwordElement.getTextContent())){
						response.sendRedirect("/Blog/index.jsp");
						return;
					}
				}
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}else{
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
