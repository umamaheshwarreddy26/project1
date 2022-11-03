package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
 * Servlet implementation class ViewConent
 */
@WebServlet("/ViewConent")
public class ViewConent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewConent() {
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
		String email=(String)session.getAttribute("email");
		String fid=request.getParameter("fid");
		String dkey=DBConnection.getkey(fid);
		String filename=DBConnection.getFName(fid);
		String key=request.getParameter("key");
		System.out.println("uid"+email);
		System.out.println("Fid"+fid);
		System.out.println("Fdkey"+dkey);
		System.out.println("Filename"+filename);
		String sql = "select * from data where fid='"+fid+"' and kid='"+key+"'";	
		if(DBConnection.getData(sql)==true)
		{
			try {
				DBConnection.addActivity(email, "User Provided File Keys Matched Successfully!", new Date().toLocaleString());
				response.sendRedirect("Download.jsp?fid=" + fid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	} 
		else {
			sql = "insert into bruteattack(fid,filename, email,status1) values('" + fid
					+ "','" + filename + "','" + email + "','Attacked')";
			int i = DBConnection.update(sql);
			if (i > 0) {
				try {
					DBConnection.addActivity(email, "Attack Detected!", new Date().toLocaleString());
					pw.println("<script type=\"text/javascript\">");
					pw.println("alert('File Keys are Wrong... Brute Force Attack Detected');");
					pw.println("window.location='OwnerView1.jsp';</script>");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
		}
	}

}
