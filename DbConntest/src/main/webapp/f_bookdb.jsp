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
CallableStatement cstmt = null;
CallableStatement cstmt1 = null;
String query = null;
ResultSet rs = null;
ResultSet rs1 = null;
JSONArray jsonArray = new JSONArray();
System.out.println("Create bookdb obj");
String keyword = request.getParameter("search_keyword");
String search_mode = request.getParameter("search_mode"); // mode의 값이 1 = 제목, 2면 저자

String search_bookisbn = request.getParameter("search_bookisbn");

try {
	Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클 드라이버 적재
	System.out.println("DB Driver On");
	conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BOOKDB", "1234");

	System.out.println("DB Connecting"); // 도서위치추적시스템 데이터베이스에 연z결

	if (search_bookisbn == null) {
		if (search_mode.equals("1")) {
	cstmt = conn.prepareCall("{call p_search1(?,?)}"); // 검색기능을 가진 프로시저 호출
		} else if (search_mode.equals("2")) {
	cstmt = conn.prepareCall("{call p_search2(?,?)}"); // 검색기능을 가진 프로시저 호출
		}
		cstmt.setString(1, keyword); // ""안의 문자열(검색키워드)이 프로시저의 IN 파라미터로 전달
		cstmt.registerOutParameter(2, OracleTypes.CURSOR); // OUT 파라미터로 커서가 반환
		cstmt.executeQuery(); // 프로시저 실행
		rs = (ResultSet) cstmt.getObject(2); // 변수 rs에 OUT파라미터인 커서를 반환	
		while (rs.next()) { //조회되는 커서(행) 반복
	JSONObject json = new JSONObject(); // json 객체 생성
	json.put("ISBN", rs.getString(1));
	json.put("제목", rs.getString(2));
	json.put("저자", rs.getString(3));
	json.put("출판사", rs.getString(4));
	json.put("발행년도", rs.getDate(5).toString().substring(0, 7));
	json.put("도서소개", rs.getString(6));
	json.put("도서이미지", rs.getString(7));
	jsonArray.add(json); // json 객체에 행 추가
		}

		if (search_mode.equals("1")) {
	cstmt = conn.prepareCall("{call p_search1(?,?)}"); // 검색기능을 가진 프로시저 호출
		} else if (search_mode.equals("2")) {
	cstmt = conn.prepareCall("{call p_search2(?,?)}"); // 검색기능을 가진 프로시저 호출
		} else {
	query = "Select 도서번호,위치 from v_도서위치 where ISBN =" + "'" + search_bookisbn + "'";
	stmt = conn.createStatement();
	rs1 = stmt.executeQuery(query);
	while (rs1.next()) {
		JSONObject json = new JSONObject(); // json 객체 생성
		json.put("도서번호", rs.getString(1));
		if (rs.getString(2) == null) {
			json.put("위치", "위치추적불가");
		} else {
			json.put("위치", rs.getString(2));
		}
		jsonArray.add(json);
	}
		}
		out.println(jsonArray);
		rs.close();
		rs1.close();
		stmt.close();
		cstmt.close();
		conn.close();
	}
} catch (Exception e) {
	e.printStackTrace();
} finally {
	try {
		if (rs != null)
	rs.close();
		if (rs1 != null)
	rs1.close();
		if (cstmt != null)
	cstmt.close();
		if (conn != null)
	conn.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("bookdb jsp end");
}
%>
