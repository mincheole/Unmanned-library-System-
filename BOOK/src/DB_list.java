import java.sql.SQLException;

public class DB_list {
	public static void main(String arg[]) throws SQLException {

		DB_conn_query db_con_query = new DB_conn_query();
		db_con_query.DB_Conn_Query_driver();
		String[][] list = db_con_query.sqlrun(1);
		// db_con_query.arrayListTo2DArray(list);
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(list[i][j] + " ");
			}
			System.out.println("");

		}
	}
}