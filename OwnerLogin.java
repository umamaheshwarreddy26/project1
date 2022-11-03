package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * Servlet implementation class OwnerLogin
 */
@WebServlet("/OwnerLogin")
public class OwnerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OwnerLogin() {
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
		HttpSession session=request.getSession();
		
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		int countAttempt = 1;
		if(session.getAttribute("FailedCount")==null||((int) (session.getAttribute("FailedCount")))==0)
		{
			countAttempt = 1;
		}else{
			countAttempt = (int) (session.getAttribute("FailedCount"));
		}
	//	HttpSession hs=request.getSession();
		String sql="select * from owner where email='"+email+"' and password='"+password+"' and status1='Approved'";
		if(DBConnection.getData(sql) == true )
		{
			try {
				DBConnection.addActivity(email, "Logged in successfully", new Date().toLocaleString());
				HttpSession hs=request.getSession();
				session.setAttribute("email", email);
				sql = "select name1 from owner where email='"+email+"'";
				String name = DBConnection.getName(sql);
				session.setAttribute("name", name);
				System.out.println("hii2:"+email);
				response.sendRedirect("OwnerHome.jsp");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(DBConnection.getData(sql) == false )
		{
			System.out.println(countAttempt);
			session.setAttribute("FailedCount", countAttempt+1);			
			if(countAttempt == 1){
				
		     	System.out.println("H 1");
		     	pw.println("<script type=\"text/javascript\">");
		     	pw.println("alert('Please enter valid Details First');");
		     	pw.println("window.location='index.jsp'</script>");
		     
		}	else if(countAttempt == 2){
	     
	     	System.out.println("H 2");
	     	pw.println("<script type=\"text/javascript\">");
	     	pw.println("alert('Please enter valid Details Second');");
			pw.println("window.location='index.jsp'</script>");
	}
		else if(countAttempt >= 3){
		 sql="update  owner set  status1='Blocked' where  email='"+email+"' ";
		 Connection con = DBConnection.connect();
		 try {
			PreparedStatement ps = con.prepareStatement(sql);
			int i = ps.executeUpdate();
			DBConnection.addActivity(email, "Logged in successfully", new Date().toLocaleString());
			session.setAttribute("FailedCount", 0);
			System.out.println("H 3");
			pw.println("<script type=\"text/javascript\">");
			pw.println("alert('SQL INJECTION ATTACK DETECTED');");
			pw.println("window.location='index.jsp'</script>");
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		}
	}
	}
