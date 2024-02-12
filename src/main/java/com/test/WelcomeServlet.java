package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String uname=request.getParameter("username");
		String pwd=request.getParameter("password");
		String cuname ="";
		String cpwd = "";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://@localhost:3306/test",
					"root","root");
			PreparedStatement pst=con.prepareStatement(
					"select * from credentials where username = '"+uname+"'");
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				cuname = rs.getString(1);
				cpwd = rs.getString(2);
			}
			if(cuname.equals(uname)&& cpwd.equals(pwd)) {
				pw.println("Login is Success");
				RequestDispatcher rd=request.getRequestDispatcher("/servlet1");
				rd.include(request, response);    //use forward to dont display login success meg
			}
			else {
				pw.println("Login failed & Please check your credentials");
				RequestDispatcher rd=request.getRequestDispatcher("login.html");
				rd.include(request, response);
			}
			
		}
		catch(Exception e) {
			pw.println(e);
			pw.println("Login is failed & Please check your credentials");
			RequestDispatcher rd=request.getRequestDispatcher("login.html");
			rd.include(request, response);
		}
		
		
	}

}