package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.OwnerBean;
import com.dao.DBConnection;

/**
 * Servlet implementation class OwnerReg
 */
@WebServlet("/OwnerReg")
public class OwnerReg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OwnerReg() {
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
		String name=request.getParameter("name");
		String password=request.getParameter("password");
	//	String repassword=request.getParameter("repassword");
		String email=request.getParameter("email");
		String address=request.getParameter("address");
		String mobile=request.getParameter("mobile");
		InetAddress ia = InetAddress.getLocalHost();
		String ip = ia.getHostAddress();
		OwnerBean ob=new OwnerBean();
		ob.setName(name);
		ob.setPassword(password);
	//	ob.setRepassword(repassword);
		ob.setEmail(email);
		ob.setAddress(address);
		ob.setMobile(mobile);
		ob.setIp(ip);
		String sql="insert into owner values(?,?,?,?,?,?,?)";
		int i=DBConnection.setOwner(sql,ob);
		if(i>0)
		{
			try {
				DBConnection.addActivity(email, "registered successfully", new Date().toLocaleString());
				response.sendRedirect("index.jsp");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			pw.println("<script type=\"text/javascript\">");
			pw.println("alert('Please enter valid Details');");
			pw.println("window.location='index.jsp';</script>");
		}
	}

}
