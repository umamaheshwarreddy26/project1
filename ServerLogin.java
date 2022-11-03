package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.DBConnection;

/**
 * Servlet implementation class ServerLogin
 */
@WebServlet("/ServerLogin")
public class ServerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();
		String uid = request.getParameter("servername");
		String pwd = request.getParameter("serverpassword");
		HttpSession session = request.getSession();
	System.out.println("username and password"+uid+pwd);
		String sql = "select * from servers where servername='"+uid+"' and serverpassword='"+pwd+"'";
		boolean b = DBConnection.getData(sql);
		if(b == true){
			try {
				DBConnection.addActivity(uid, "Server Logged successfully", new Date().toLocaleString());
				session.setAttribute("sid", uid);
				sql = "select servername from servers where serverid='"+uid+"'";
				String name = DBConnection.getName(sql);
				session.setAttribute("name", name);
				response.sendRedirect("ServerHome.jsp");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			pw.println("<script type=\"text/javascript\">");
			pw.println("alert('Please enter valid Details');");
			pw.println("window.location='index.jsp';</script>");
		}
	}
	}
