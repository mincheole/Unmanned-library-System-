<%@page import="java.sql.*"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="oracle.jdbc.OracleTypes"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.io.*"%>
<%@page import="org.json.simple.*" %>
<%@page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Connection conn = null;
Statement stmt = null;
ResultSet rs = null;
ResultSet rs1 = null;
int result = 0;
String userId = null;
String userPw = null;
String mode = null;
String query = null;
JSONArray jsonArray = new JSONArray();
System.out.println("Create userinfo obj");
mode = request.getParameter("see_mode");// 1 현재 대출 기록, 2 전체 이용 기록
userId = request.getParameter("userid");

try {
	Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클 드라이버 적재
	System.out.println("DB Driver On");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOKDB", "1234");
	System.out.println("DB Connecting"); 				// 도서위치추적시스템 데이터베이스에 연결
	if (mode.equals("1")){
		query = "select 제목,대여일,반납예정일 from v_loan where 회원ID =" +"'"+userId+"'";
	    stmt = conn.createStatement();
	    rs1 = stmt.executeQuery(query);
		while(rs1.next()) {
	        JSONObject json = new JSONObject();
	        json.put("제목",rs1.getString(1));
	        json.put("대여일",rs1.getDate(2));
	        json.put("반납예정일",rs1.getDate(3));
	        jsonArray.add(json);
	    }
	} else if(mode.equals("2")){
		query = "select 제목,대여일,반납일 from v_usage where 회원ID =" +"'"+userId+"'"; 
	    stmt = conn.createStatement();
	    rs = stmt.executeQuery(query);
		while(rs.next()) {
	        JSONObject json = new JSONObject();
	        json.put("제목",rs.getString(1));
	        json.put("대여일",rs.getDate(2));
	        json.put("반납일",rs.getDate(3));
	        jsonArray.add(json);
	     }
	}
        out.println(jsonArray);
	conn.close();
} catch (Exception e) {
	e.printStackTrace();
} finally {
	try {
		if (rs != null || rs1 != null){
	rs.close();
	rs1.close();
	}
		if (conn != null)
	conn.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("userinfo jsp end");
}
%>