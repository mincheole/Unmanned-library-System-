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
String userId = null;
String query = null;
JSONArray jsonArray = new JSONArray();
System.out.println("Create userinfo obj");
userId = request.getParameter("userid");
try {
	Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클 드라이버 적재
	System.out.println("DB Driver On");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOKDB", "1234");
	System.out.println("DB Connecting"); 				// 도서위치추적시스템 데이터베이스에 연결
	query = "select 제목,대여일,반납일 from v_usage where 회원ID =" +"'"+userId+"'"; 
    stmt = conn.createStatement();
    rs = stmt.executeQuery(query);
	while(rs.next()) {
        JSONObject json = new JSONObject();
        json.put("제목",rs.getString(1));
        json.put("대여일",rs.getDate(2));
        json.put("반납일",(rs.getDate(3)==null)?"":rs.getDate(3));
        jsonArray.add(json);
     }
	out.println(jsonArray);
} catch (Exception e) {
	e.printStackTrace();
} finally {
	try {
		if (rs != null)	rs.close();
		if (stmt != null) stmt.close();
		if (conn != null) conn.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("usertotalinfo jsp end");
}
%>