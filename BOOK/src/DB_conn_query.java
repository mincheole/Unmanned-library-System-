import java.sql.*;
import java.util.ArrayList;

import DTO.*;
import oracle.jdbc.internal.OracleTypes;

class DB_conn_query {

	Connection con = null;

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String id = "BOOK";
	String password = "1234";

	public void DB_Conn_Query_driver() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 적재 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("No Driver.");
		}
	}

	public void Oracle_Connect() {
		try {
			con = DriverManager.getConnection(url, id, password);
			System.out.println("DB 연결 성공");
		} catch (SQLException e) {
			System.out.println("Connection Fail");
		}
	}
	
	public String[][] sqlrun(int p_case) throws SQLException {
		ArrayList<String[]> row_array_list = new ArrayList<>();
		String[] result_row = null;
		
		int db_col_count = 0;
		String db_select_query = "";
		String db_table_name = "";
		switch(p_case) {
		case 1:													// 도서 테이블 데이터 조회
			db_col_count = 7;
			db_select_query = "ISBN, 제목, 저자, 출판사, 발행년도, 카테고리, 카드키번호";
			db_table_name ="도서";
			System.out.println("2");
			break;
			
		case 2:													// 책장 테이블 데이터 조회
			db_col_count = 5;
			db_select_query = "책장넘버, 층, 줄, 칸, 꽂힌도서";
			db_table_name ="책장";
			break;
			
		case 3:													// RFID카드 테이블 데이터 조회
			db_col_count = 1;
			db_select_query = "*";
			db_table_name ="RFID카드";
			break;
			
		//case 4:
		
		}
		
		result_row = new String[db_col_count];
		
		String query = "select " + db_select_query + " from " + db_table_name;
		try {
			Oracle_Connect();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// 각 행을 배열 형태로 저장한 뒤, 배열을 ArrayList에 저장함 (2차원 구조)
			while (rs.next()) {
				for (int i = 0; i < result_row.length; i++)
					result_row[i] = rs.getString(i + 1);
				row_array_list.add(result_row.clone()); // clone() 메소드가 없으면 깊은 복사가 되지 않음
			}

			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
		}
		return arrayListTo2DArray(row_array_list);
		
	}
	
	public String[][] arrayListTo2DArray(ArrayList<String[]> aryList) {
		String array2D[][] = null;
		array2D = new String[aryList.size()][4];
		for (int i = 0; i < array2D.length; i++) {
			array2D[i] = aryList.get(i);
		}
		return array2D;
	}

}