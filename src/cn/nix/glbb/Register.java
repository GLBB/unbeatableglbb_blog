package cn.nix.glbb;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();

			ServletContext context = this.getServletContext();
			String path = context.getRealPath("/user/user.xml");

			Document document = db.parse(path);
			Element root = document.getDocumentElement();

			String user = request.getParameter("user");
			String password = request.getParameter("password");
			if (user != null && password != null) {
				NodeList userlist = document.getElementsByTagName("name");
				for (int i = 0; i < userlist.getLength(); i++) {
					Node userNode = userlist.item(i);
					System.out.println(userNode.getTextContent());
					if (user.equals(userNode.getTextContent())) {
						request.getRequestDispatcher("/login.jsp").forward(request, response);
						return;
					}
				}
			} else {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}

			Element userNew = document.createElement("user");
			Element userName = document.createElement("name");
			userName.setTextContent(user);
			Element userPassword = document.createElement("password");
			userPassword.setTextContent(password);
			userNew.appendChild(userName);
			userNew.appendChild(userPassword);

			root.appendChild(userNew);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(path)));

			response.sendRedirect("/Blog/login.jsp");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
