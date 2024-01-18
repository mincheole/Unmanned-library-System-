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
String phone_rfid=null;
String user_id = null;
String use_mode = null;
CallableStatement cstmt = null;
System.out.println("Create usage obj");
phone_rfid = request.getParameter("phone_rfid"); 	//리더기에서 넘긴 정보 받음
user_id = request.getParameter("userid"); 			//리더기에서 넘긴 정보 받음
use_mode = request.getParameter("use_mode");
System.out.println(phone_rfid);
System.out.println(user_id);
System.out.println(use_mode);

try{
	Class.forName("oracle.jdbc.driver.OracleDriver"); 	// 오라클 드라이버 적재
	System.out.println("DB Driver On");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOKDB", "1234");
	System.out.println("DB Connecting");
	System.out.println("rfid2 = " + phone_rfid);
	if (use_mode.equals("1")){
		cstmt = conn.prepareCall("{call p_return(?,?)}"); 	// 검색기능을 가진 프로시저 호출
	} else if(use_mode.equals("2")){
		cstmt = conn.prepareCall("{call p_usage(?,?)}"); 	// 검색기능을 가진 프로시저 호출
	}
	cstmt.setString(1, user_id);								// ""안의 문자열(검색키워드)이 프로시저의 IN 파라미터로 전달
	cstmt.setString(2, phone_rfid);								// ""안의 문자열(검색키워드)이 프로시저의 IN 파라미터로 전달
	cstmt.executeQuery(); 										// 프로시저 실행
	
} catch (Exception e) {
	e.printStackTrace();
} finally {
	try {
		if (conn != null) conn.close();
		if (cstmt != null) cstmt.close();
	}
	 catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("usage jsp end");
}
%>