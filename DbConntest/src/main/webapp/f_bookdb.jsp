<%@page import="java.sql.*" %>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.PreparedStatement" %>
<%@page import="java.sql.Connection" %>
<%@page import="oracle.jdbc.OracleTypes" %>
<%@page import="java.sql.SQLException" %>
<%@page import="java.io.*"%>
<%@page import="org.json.simple.*" %>
<%@page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	Connection conn = null;
	CallableStatement cstmt = null;
	ResultSet rs = null;
	byte[ ] imgData = null;
	Blob image = null;
	JSONArray jsonArray = new JSONArray();
	System.out.println("create obj");

try{
	Class.forName("oracle.jdbc.driver.OracleDriver"); 	// 오라클 드라이버 적재
	System.out.println("DB Driver On");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOK", "1234");
	System.out.println("DB Connecting");				// 도서위치추적시스템 데이터베이스에 연결
	cstmt = conn.prepareCall("{call p_search(?,?)}"); 	// 검색기능을 가진 프로시저 호출
	cstmt.setString(1, "심플한");								// ""안의 문자열(검색키워드)이 프로시저의 IN 파라미터로 전달
	cstmt.registerOutParameter(2, OracleTypes.CURSOR);	// OUT 파라미터로 커서가 반환
	cstmt.executeQuery(); 								// 프로시저 실행
	rs = (ResultSet)cstmt.getObject(2);					// 변수 rs에 OUT파라미터인 커서를 반환	
	while(rs.next()){ 									//조회되는 커서(행) 반복
		JSONObject json = new JSONObject();
		json.put("ISBN",rs.getString(1));
		json.put("제목",rs.getString(2));
		json.put("저자",rs.getString(3));
		json.put("출판사",rs.getString(4));
		json.put("발행년월",rs.getDate(5));
		json.put("카테고리",rs.getString(6));
		image = rs.getBlob(7);
		imgData = image.getBytes(1,(int)image.length());
		json.put("이미지",imgData);
		json.put("도서ID",rs.getString(8));
		jsonArray.add(json);
	
	}
	out.println(jsonArray);
	rs.close();
	cstmt.close();
	conn.close();
}catch(Exception e){
	e.printStackTrace();
}finally{
	try{
		if(rs!=null) rs.close();
		if(cstmt!=null) cstmt.close();
		if(conn!=null) conn.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
%>
