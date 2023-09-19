<%@page import="java.sql.*"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="oracle.jdbc.OracleTypes"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.io.*"%>
<%@page import="org.json.simple.*" %>
<%@page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Connection conn = null;
CallableStatement cstmt = null;
ResultSet rs = null;
int result = 0;
String userId = null;
String userPw = null;
System.out.println("Create login obj");
userId = request.getParameter("userid");		// 어플에서 사용자가 ID칸에 입력한 데이터 수신
userPw = request.getParameter("userpw");		// PW

try {
	Class.forName("oracle.jdbc.driver.OracleDriver");												// 오라클 드라이버 적재
	System.out.println("DB Driver On");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOKDB", "1234");    // 오라클 DB 스키마 연동
	System.out.println("DB Connecting");															// 도서위치추적시스템 데이터베이스에 연결
	cstmt = conn.prepareCall("{call p_login(?,?,?)}");												// 로그인 기능 프로시저 호출
	cstmt.setString(1, userId);																		// 프로시저에 사용자 입력 ID 전달(in parameter)
	cstmt.setString(2, userPw);																		// PW 전달
	cstmt.registerOutParameter(3, Types.INTEGER);													// 결과값 정수형 형변환 설정
	cstmt.executeQuery();																			// 쿼리 실행(프로시저 실행)
	result = cstmt.getInt(3);																		// 결과값 (1(성공),0(실패))
	out.println(result);
	System.out.println(result);
	System.out.println(userId + " login");
	System.out.println(userPw + " login");
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
	System.out.println("login jsp end");
}
%>