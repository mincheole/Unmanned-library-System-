<%@page import="java.sql.*"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="oracle.jdbc.OracleTypes"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.io.*"%>
<%@page import="org.json.simple.*"%>
<%@page import="java.util.Base64"%>
<%@page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
Connection conn = null;
Statement stmt = null;
String query,query1 = null;
String booknumber=null;
ResultSet rs1=null;
ResultSet rs2=null;
JSONArray jsonArray = new JSONArray();
System.out.println("Create locationtrack obj");
String search_bookisbn = request.getParameter("search_bookisbn");
System.out.println("parameter receive success");
//search_bookisbn ="9788970504773";						// 통신 실패시 테스트용
try {
	Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클 드라이버 적재
	System.out.println("DB Driver On");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOKDB", "1234");
	System.out.println("DB Connecting"); // 도서위치추적시스템 데이터베이스에 연결
	query = "Select 도서번호,위치 from v_도서위치 where ISBN =" +search_bookisbn;
	stmt = conn.createStatement();
	rs1 = stmt.executeQuery(query);
	while (rs1.next()) {
		JSONObject json = new JSONObject(); // json 객체 생성
		booknumber = rs1.getString(1);
		json.put("도서번호", booknumber);
		if (rs1.getString(2)==null) {
			json.put("위치", "위치추적불가");
			query1 = "Select 도서번호 from v_loan where 도서번호 = "+"'"+booknumber+"'";
			stmt = conn.createStatement();
			rs2 = stmt.executeQuery(query1);
			while(rs2.next()){
				if(rs2.getString(1).equals(booknumber)) {
					json.put("위치", "대여중");
				}				
			}
		}
		else {
			json.put("위치", rs1.getString(2));
		}
		
		jsonArray.add(json);
	}
	out.println(jsonArray);
}catch(Exception e)	{
	e.printStackTrace();
} finally{
	try {
		if (rs1 != null)	rs1.close();
		if (rs2 != null)	rs2.close();
		if (stmt != null) stmt.close();
		if (conn != null)	conn.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
		System.out.println("locationtrack jsp end");
}
%>