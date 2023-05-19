<%@page import="java.sql.*" %>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.Connection" %>
<%@page import="java.sql.SQLException" %>
<%@ page import="java.io.*"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	byte[ ] imgData = null;
	Blob image = null;
	System.out.println("ok1");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신상명세서 출력 현황</title>
</head>
<body>
<center><h2>도서 목록</h2></center>
<table width="800" border="1" align="center">
<tr>
<th>ISBN</th>
<th>제목</th>
<th>저자</th>
<th>출판사</th>
<th>발행년월</th>
<th>카테고리</th>
<th>도서이미지</th>
<th>줄거리</th>
<th>도서ID</th>
</tr>
<%
try{
	Class.forName("oracle.jdbc.driver.OracleDriver"); //driver
	System.out.println("ok2");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOK", "1234"); //username, password는 개인 Oracle 계정의 것으로 하면 됨
	System.out.println("ok3");
	String sql = "select * from 도서"; //DB를 조회할 select문
	pstmt = conn.prepareStatement(sql); //sql문으로 conn
	rs = pstmt.executeQuery(); //pstmt 실행 후 결과를 rs에 할당
	
	while(rs.next()){ //조회되는 로우(행) 반복
		out.print("<tr>");
		out.print("<td>" + rs.getString(1) + "</td>"); //DB에서 sabun 컬럼에 해당하는 레코드 값을 불러옴
		out.print("<td>" + rs.getString(2) + "</td>");
		out.print("<td>" + rs.getString(3) + "</td>");
		out.print("<td>" + rs.getString(4) + "</td>");
		out.print("<td>" + rs.getDate(5) + "</td>");
		out.print("<td>" + rs.getString(6) + "</td>");
//		out.print("<td>" + rs.getBlob(7) + "</td>");
		image = rs.getBlob(7);
		imgData = image.getBytes(1,(int)image.length());
		out.print("<td>" + imgData + "/td");
		out.print("<td>" + rs.getString(8) + "</td>");
		out.print("</tr>");
	}
	
	rs.close();
	pstmt.close();
	conn.close();
}catch(Exception e){
	e.printStackTrace();
}finally{
	try{
		if(rs!=null) rs.close();
		if(pstmt!=null) pstmt.close();
		if(conn!=null) conn.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
%>
</table>
</body>
</html>