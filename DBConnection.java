package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bean.OwnerBean;
import com.bean.ServerBean;

public class DBConnection {
	public static Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mjnw06", "root", "root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return con;
	}

	public static int setOwner(String sql, OwnerBean ob) {
		// TODO Auto-generated method stub
		int i=0;
		Connection con=connect();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, ob.getName());
			ps.setString(2, ob.getPassword());
		//	ps.setString(3, ob.getRepassword());
			ps.setString(3, ob.getEmail());
			ps.setString(4, ob.getAddress());
			ps.setString(5, ob.getMobile());
			ps.setString(6, "Approved");
			ps.setString(7, ob.getIp());
			i=ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	public static boolean getData(String sql) {
		boolean b=false;
		Connection con=connect();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			b=rs.next();
			rs.close();con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	public static boolean getData1(String sql) {
		boolean b=false;
		Connection con=connect();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			int size =0;
			if (rs != null) 
			{
			  rs.last();    // moves cursor to the last row
			  size = rs.getRow(); // get row id 
			}
			
			System.out.println("sizeeee is"+size);
			if(size==1){
				b=true;
			}else{
				b=false;
				
			}
		//	b=rs.next();
			rs.close();con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public static int setServer(String sql, ServerBean sb) {
		// TODO Auto-generated method stub
		int i=0;
		Connection con=connect();
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, sb.getServerid());
			ps.setString(2, sb.getServername());
			ps.setString(3, sb.getServerip());
			ps.setString(4, sb.getServersize());
			ps.setString(5, sb.getServerpassword());
			i=ps.executeUpdate();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
}
	public static String getName(String sql) {
		// TODO Auto-generated method stub
		String name ="";
		Connection con = connect();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				name = rs.getString(1);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;

	}
	public static int addActivity(String email,String activity,String time) throws SQLException
	{	Connection con=null;
	int i=0;	
	try{
		 con =DBConnection.connect();
		String sql="insert into logrecords values(0,?,?,?);";
		PreparedStatement p=con.prepareStatement(sql);

		p.setString(3, time);
		p.setString(1, email);
		p.setString(2, activity);
		
		i=p.executeUpdate();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
	public static int addActivity1(String email,String activity,String time,String ip) throws SQLException
	{	Connection con=null;
	int i=0;	
	try{
		 con =DBConnection.connect();
		String sql="insert into sqlinjection values(0,?,?,?,?);";
		PreparedStatement p=con.prepareStatement(sql);
		p.setString(4, ip);
		p.setString(3, time);
		p.setString(1, email);
		p.setString(2, activity);
		
		i=p.executeUpdate();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
	 public static ResultSet getUser() throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from owner where status1='Approved' ";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static ResultSet getServers() throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from servers";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static List<String> getServerName(String sql) {
			// TODO Auto-generated method stub
			List<String> lt = new ArrayList<String>();
			Connection con = connect();
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					lt.add(rs.getString(1));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lt;
		}
	 public static ResultSet getMyFiles(String email) throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from data where email='"+email+"'";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static ResultSet getMyFiles7(String email) throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from data where email='"+email+"'";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static ResultSet getMyFiles2( ) throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from data";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static ResultSet getMyFiles5() throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from data ";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static ResultSet getMyFiles3(String fid) throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from data where fid='"+fid+"'";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static int update(String sql) {
			// TODO Auto-generated method stub
			int i = 0;
			Connection con = connect();
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				i = ps.executeUpdate();
				ps.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return i;
		}
	 public static ResultSet getFiles(String servername) throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from data where servername='"+servername+"' ";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static ResultSet getBlockedUser() throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from owner where status1='Blocked'";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static ResultSet getBlockedUser1() throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from sqlinjection";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static int getLengthone(String servername)
	 {
		 Statement stm;
		 ResultSet rs;
		 int len=0;
		 try {
				stm=connect().createStatement();
				 rs=stm.executeQuery("select serversize from servers where servername='"+servername+"'");
				 while(rs.next())
				 {
					  len=rs.getInt(1);
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("The size of use file in dbside"+len);
		 return len;
	 }
	 public static String getkey (String fid)
		{
		String key="";
			Connection con =DBConnection.connect();
			String sql="select kid from data where fid='"+fid+"'";
			Statement s;
		try {
			s = con.createStatement();
			ResultSet r=s.executeQuery(sql);
			r.next();
			key=r.getString(1);
			r.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
			return key;
		}
	 public static String getFName (String fname)
		{
		String key="";
			Connection con =DBConnection.connect();
			String sql="select filename from data where fid='"+fname+"'";
			Statement s;
		try {
			s = con.createStatement();
			ResultSet r=s.executeQuery(sql);
			r.next();
			key=r.getString(1);
			r.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
			return key;
		}
	 public static ResultSet getActivities() throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from logrecords ";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static ResultSet getOwners() throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from owner ";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 public static ResultSet getBruteAttackUser() throws SQLException
		{
			Connection con =DBConnection.connect();
			String sql="select * from bruteattack ";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			return r;
		}
	 
	 public static String getAttackerStatus() throws SQLException{
		 Connection con =DBConnection.connect();
			String sql="select * from attakcer ";
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery(sql);
			if(r.next()){
				return r.getString(1);
			}else{
				return null;
			}
		//	return r;
		 
	 }
	 
	 
	 public static int getAttackerupdate() throws SQLException{
		 Connection con =DBConnection.connect();
		 String sql="update attakcer set status1='0' ";
			Statement s=con.createStatement();
			int i=s.executeUpdate(sql);
			return i;
			
		//	return r;
		 
	 }
}