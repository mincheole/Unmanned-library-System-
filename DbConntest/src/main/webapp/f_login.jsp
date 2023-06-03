<%@page import="java.sql.*"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="oracle.jdbc.OracleTypes"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.io.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- import -->
<%
Connection conn = null;
CallableStatement cstmt = null;
ResultSet rs = null;
int result = 0;
System.out.println("create obj");
%>
<!-- 자바 언어 사용 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서위치추적시스템 로그인</title>
<!-- 웹(홈페이지) 이름 -->
</head>
<body>
	<center>
		<h2>로그인</h2>
	</center>
	<!-- 홈페이지 대 제목? -->
	<!-- 자바 언어 사용 -->
	<%
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클 드라이버 적재
		System.out.println("DB Driver On");
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOK", "1234");
		System.out.println("DB Connecting"); // 도서위치추적시스템 데이터베이스에 연결
		cstmt = conn.prepareCall("{call p_login(?,?,?)}");
		cstmt.setString(1, "김민철");
		cstmt.setString(2, "a002");
		cstmt.registerOutParameter(3, Types.INTEGER);
		cstmt.executeQuery();
		//rs = (ResultSet)cstmt.getInt(3);
		result = cstmt.getInt(3);
		System.out.println(result);
		out.println(result);
		
		cstmt.close();
		conn.close();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			if (rs != null)
		rs.close();
			if (cstmt != null)
		cstmt.close();
			if (conn != null)
		conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	%>
</body>
</html>