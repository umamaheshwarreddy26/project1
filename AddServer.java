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

import com.bean.OwnerBean;
import com.bean.ServerBean;
import com.dao.DBConnection;
import com.dao.RandomeString;

/**
 * Servlet implementation class AddServer
 */
@WebServlet("/AddServer")
public class AddServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddServer() {
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
		String serverid=RandomeString.getServerID();
		String servername=request.getParameter("servername");
		String serverip=request.getParameter("serverip");
		String serversize=request.getParameter("serversize");
		String serverpassword=request.getParameter("serverpassword");
		
		ServerBean sb=new ServerBean();
		sb.setServerid(serverid);
		sb.setServername(servername);
		sb.setServerip(serverip);
		sb.setServersize(serversize);
		sb.setServerpassword(serverpassword);

		String sql="insert into servers values(?,?,?,?,?)";
		int i=DBConnection.setServer(sql,sb);
		if(i>0)
		{
			try {
				DBConnection.addActivity(servername, "Server Added successfully", new Date().toLocaleString());
				response.sendRedirect("CloudHome.jsp");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			pw.println("<script type=\"text/javascript\">");
			pw.println("alert('Please enter valid Details');");
			pw.println("window.location='CloudHome.jsp';</script>");
		}
	}
	}
