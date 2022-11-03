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

import com.dao.DBConnection;
import com.dao.Encryption;

/**
 * Servlet implementation class ownerUpdate
 */
@WebServlet("/ownerUpdate")
public class ownerUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ownerUpdate() {
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
		PrintWriter out=response.getWriter();
		String fid=request.getParameter("fid");
		String content=request.getParameter("content");
		String time=new Date().toLocaleString();
		String email=(String)request.getSession().getAttribute("email");
	
		try {
		Connection  con =DBConnection.connect();
		String sql="UPDATE data SET content =?, lastupdate = ?, updatedby = ? WHERE fid = ?;";
		PreparedStatement p=con.prepareStatement(sql);
		content=Encryption.encrypt(content);
		
		p.setString(1, content);
		p.setString(2, time);
		p.setString(3, email);
		p.setString(4, fid);
		
		int i=p.executeUpdate();
			if(i>0)
			{
				DBConnection.addActivity(email, "Data Updated Successfully!", new Date().toLocaleString());
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Data Updated Successfully!');");
				out.println("window.location='OwnerEdit.jsp?fid="+fid+"'</script>");
				
			}else
			{
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Failed To Update!);");
				out.println("window.location='OwnerEdit.jsp?fid="+fid+"'</script>");
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
