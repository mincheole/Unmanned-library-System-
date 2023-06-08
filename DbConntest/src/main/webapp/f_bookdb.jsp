<%@page import="java.sql.*"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.CallableStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="oracle.jdbc.OracleTypes"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.io.*"%>
<%@page import="org.json.simple.*" %>
<%@page import="java.util.Base64"%>
<%@page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	Connection conn = null;
	CallableStatement cstmt = null;
	CallableStatement cstmt1 = null;
	ResultSet rs =null;
	ResultSet rs1 = null;
	JSONArray jsonArray = new JSONArray();
	System.out.println("Create bookdb obj");

	String keyword = request.getParameter("keyword");
	String mode = request.getParameter("mode");
	//mode = "1";
	//keyword ="";


try{
	Class.forName("oracle.jdbc.driver.OracleDriver"); 	// 오라클 드라이버 적재
	System.out.println("DB Driver On");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOKDB", "1234");

	System.out.println("DB Connecting");			// 도서위치추적시스템 데이터베이스에 연결
	if (mode.equals("1")){
		cstmt = conn.prepareCall("{call p_search1(?,?)}"); 	// 검색기능을 가진 프로시저 호출
	} else if(mode.equals("2")){
		cstmt = conn.prepareCall("{call p_search2(?,?)}"); 	// 검색기능을 가진 프로시저 호출
	}
	cstmt.setString(1, keyword);								// ""안의 문자열(검색키워드)이 프로시저의 IN 파라미터로 전달
	cstmt.registerOutParameter(2, OracleTypes.CURSOR);	// OUT 파라미터로 커서가 반환
	cstmt.executeQuery(); 								// 프로시저 실행
	rs = (ResultSet)cstmt.getObject(2);					// 변수 rs에 OUT파라미터인 커서를 반환	
	while(rs.next()){ 									//조회되는 커서(행) 반복
		JSONObject json = new JSONObject();
		json.put("ISBN",rs.getString(1));
		json.put("제목",rs.getString(2));
		json.put("저자",rs.getString(3));
		json.put("출판사",rs.getString(4));
		json.put("발행년도", rs.getDate(5).toString().substring(0,7));
		json.put("카테고리",rs.getString(6));
		json.put("도서소개",rs.getString(7));
		json.put("도서이미지",rs.getString(8));
		json.put("층",rs.getString(9));
		json.put("줄",rs.getString(10));
		json.put("칸",rs.getString(11));
		jsonArray.add(json);
	}
	if (mode.equals("1")){
		cstmt = conn.prepareCall("{call p_search3(?,?)}"); 	// 검색기능을 가진 프로시저 호출
	} else if(mode.equals("2")){
		cstmt = conn.prepareCall("{call p_search4(?,?)}"); 	// 검색기능을 가진 프로시저 호출
	}
	cstmt.setString(1, keyword);								// ""안의 문자열(검색키워드)이 프로시저의 IN 파라미터로 전달
	cstmt.registerOutParameter(2, OracleTypes.CURSOR);	// OUT 파라미터로 커서가 반환
	cstmt.executeQuery(); 								// 프로시저 실행
	rs1 = (ResultSet)cstmt.getObject(2);
	while(rs1.next()){ 									//조회되는 커서(행) 반복
		JSONObject json = new JSONObject();
		json.put("ISBN",rs1.getString(1));
		json.put("제목",rs1.getString(2));
		json.put("저자",rs1.getString(3));
		json.put("출판사",rs1.getString(4));
		json.put("발행년도", rs1.getDate(5).toString().substring(0,7));
		json.put("카테고리",rs1.getString(6));
		json.put("도서소개",rs1.getString(7));
		json.put("도서이미지",rs1.getString(8));
		json.put("층","0");
		json.put("줄","0");
		json.put("칸","0");
		jsonArray.add(json);
	}
	out.println(jsonArray);
	rs.close();
	rs1.close();
	cstmt.close();
	conn.close();
}catch(Exception e){
	e.printStackTrace();
}finally{
	try{
		if(rs!=null) rs.close();
		if(rs1!=null) rs1.close();
		if(cstmt!=null) cstmt.close();
		if(conn!=null) conn.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	System.out.println("bookdb jsp end");
}
%>
