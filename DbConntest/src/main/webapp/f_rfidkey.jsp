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
String rfid_key,query,bookisbn = null;
Statement stmt = null;
PreparedStatement pstmt = null;
System.out.println("Create rfid obj");
rfid_key = request.getParameter("rfid");
System.out.println(rfid_key);
out.println(rfid_key);
ResultSet rs = null;
int count =0;
System.out.println("rfid jsp end");

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
	pstmt = conn.prepareCall("Update 책장 set 꽂힌도서 = (?) where 책장번호 = 'bc1'");
	pstmt.setString(1, bookisbn);
	count = pstmt.executeUpdate();
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
}
%>