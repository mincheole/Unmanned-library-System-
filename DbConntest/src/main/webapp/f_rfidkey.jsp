<%@page import="java.sql.*"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="oracle.jdbc.OracleTypes"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.*, java.net.*" %>
<%
Connection conn = null;
String rfid_key,query,query1;
String bookisbn=null;
String past_bookisbn=null;
Statement stmt = null;
Statement stmt1 = null;
PreparedStatement pstmt = null;
PreparedStatement pstmt1 = null;
PreparedStatement pstmt2 = null;
System.out.println("Create rfid obj");
rfid_key = request.getParameter("rfid"); //리더기에서 넘긴 정보 받음
System.out.println(rfid_key);
out.println(rfid_key);
ResultSet rs = null;
ResultSet rs1 =null;
int count =0;
System.out.println("rfid jsp end");
/*
try {
	Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클 드라이버 적재
	System.out.println("DB Driver On");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOKDB", "1234");
	System.out.println("DB Connecting"); // 도서위치추적시스템 데이터베이스에 연결
	
	query =  "Select ISBN from RFID카드 join 도서 on 도서.도서RFID태그 = RFID카드.RFID태그 where RFID태그 ="+"'"+rfid_key+"'" ;
	stmt = conn.createStatement();
	rs = stmt.executeQuery(query);
	while(rs.next()) {
		bookisbn=rs.getString(1);
	}
	query1 ="select 꽂힌도서 from 책장 where 책장번호 = 'bc1'";
	stmt1 = conn.createStatement();
	rs1 = stmt.executeQuery(query1);
	while(rs1.next()) {
		past_bookisbn=rs1.getString(1);
	}
	pstmt2 = conn.prepareCall("Delete From 책장 Where 꽂힌도서 ="+"'"+bookisbn+"'");
	pstmt2.executeUpdate();
	pstmt = conn.prepareCall("Update 책장 set 꽂힌도서 = (?) where 책장번호 = 'bc1'");
	pstmt1 = conn.prepareCall("Update 도서 set 위치추적 = '0' where ISBN ="+"'"+past_bookisbn+"'");
	pstmt.setString(1, bookisbn);
	count = pstmt.executeUpdate();
	pstmt1.executeUpdate();
	if (count==1){
		System.out.println("clear");
		out.println(bookisbn);
		System.out.println(bookisbn);
	} else{
		System.out.println("fail");
	}
	conn.close();
} catch (Exception e) {
	e.printStackTrace();
} finally {
	try {
		if (rs != null)	rs.close();
		if (pstmt != null)	pstmt.close();
		if (stmt != null)	stmt.close();
		if (conn != null) conn.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("rfid jsp end");
}*/
%>