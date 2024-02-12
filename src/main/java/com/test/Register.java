package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {
	private static final Exception nullvalue = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter pw=response.getWriter();
		response.setContentType("text/html");
		String userName=request.getParameter("username");
		String passWord=request.getParameter("password");
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(
					"jdbc:mysql://@localhost:3306/test",
					"root","root");
			PreparedStatement pst=con.prepareStatement(
					"insert into credentials values(?,?) ");
			pst.setString(1, userName);
			pst.setString(2, passWord);
			pst.executeUpdate();
			pst.close();
			pw.println("Registered Successfully ");
			RequestDispatcher rd=request.getRequestDispatcher("login.html");
			rd.include(request, response);
		}
		catch(Exception e) {
			pw.println(e);
			pw.println("failed to register, select other username");
			RequestDispatcher rd=request.getRequestDispatcher("register.html");
			rd.include(request, response);
		}
		
		
		
		
		
	}
}
