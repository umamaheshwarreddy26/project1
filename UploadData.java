package com.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import com.dao.RandomeString;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.Part;
import com.sun.org.apache.xerces.internal.parsers.IntegratedParserConfiguration;
/**
 * Servlet implementation class UploadData
 */
@WebServlet("/UploadData")
public class UploadData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadData() {
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
		FilePart filePart = null;
		Part part;
		String fid=RandomeString.getFid();
		String kid=RandomeString.getSaltString();
		String filename=request.getParameter("filename");
		String content=request.getParameter("content");
		String servername=request.getParameter("servername");
		String time=new Date().toLocaleString();
		String email=(String)request.getSession().getAttribute("email");
		//int filesize = Integer.parseInt(filePart.getFileName());
		String fileName = "words.txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
	    writer.write(content);
	    writer.close();
        File f = new File(fileName);
        int fileSize = (int) f.length();
		System.out.println(fileSize);	
		try {
		Connection  con =DBConnection.connect();
		String sql="insert into data values(?,?,?,?,?,?,?,?,?);";
		PreparedStatement p=con.prepareStatement(sql);
		content=Encryption.encrypt(content);
		
		p.setString(1, fid);
		p.setString(2, email);
		p.setString(3, filename);
		p.setString(4, content);
		p.setString(5, time);
		p.setString(6, email);
		p.setString(7, servername);
		p.setInt(8, fileSize);
		p.setString(9, kid);
		int i=p.executeUpdate();
		int len=DBConnection.getLengthone(servername);
		if(len >fileSize)
		{
			if(i>0)
			{
				DBConnection.addActivity(email, "Data Uploaded Successfully!", new Date().toLocaleString());
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Data Uploaded Successfully!');");
				out.println("window.location='OwnerHome.jsp'</script>");
			}else
			{
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Failed To Upload!);");
				out.println("window.location='OwnerHome.jsp'</script>");
			}
		}
		else
		{
			DBConnection.addActivity(email, "Data Uploaded To Other Server Successfully!", new Date().toLocaleString());
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Data Uploaded Successfully!');");
			out.println("window.location='OwnerHome.jsp'</script>");
		} 
			}
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}