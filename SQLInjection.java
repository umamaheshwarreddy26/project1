package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.DBConnection;

/**
 * Servlet implementation class SQLInjection
 */
@WebServlet("/SQLInjection")
public class SQLInjection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SQLInjection() {
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
		PrintWriter pw=response.getWriter();
		String uid=request.getParameter("email");
		String pwd=request.getParameter("password");
		String sql="SELECT name1 FROM owner WHERE email='"+uid+"' AND password='"+pwd+"' OR 1=1";
		String sql1="SELECT name1 FROM owner WHERE email='"+uid+"' AND password='"+pwd+"'";
		InetAddress ia = InetAddress.getLocalHost();
		String ip = ia.getHostAddress();
		Connection con=DBConnection.connect();
		if(DBConnection.getData1(sql)!=true)
		{
			
			if(DBConnection.getData(sql1)){
				response.sendRedirect("OwnerHome1.jsp");
			}else
			{ 
				try {
					DBConnection.addActivity1(uid, "SQL Injection Attacked Detected", new Date().toLocaleString(),ip);
					pw.println("<script type=\"text/javascript\">");
					pw.println("alert('SQL Injection Attack Detected');");
					pw.println("window.location='index.jsp';</script>");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			
			//response.sendRedirect("OwnerHome.jsp");
		}
	}
}
