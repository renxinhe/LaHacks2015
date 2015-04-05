package com.wynd.grapher;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GrapherServlet extends HttpServlet {

	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		String action = req.getParameter("action");
		if( action != null){
			resp.getOutputStream().println("Test");
		}
	}
	
}
