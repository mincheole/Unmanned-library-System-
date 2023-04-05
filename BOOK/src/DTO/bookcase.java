package DTO;

public class bookcase {
	String bookcase_num;		// 책장 넘버(기본키)
	int bookcase_row;			// 행(층)
	int bookcase_col;			// 열(칸)
	int inisbn;					// 외래키(도서 isbn)
	
	public bookcase(String p_bookcase_num, int p_bookcase_row,
					int p_bookcase_col, int p_inisbn) {
		this.bookcase_num = p_bookcase_num;
		this.bookcase_row = p_bookcase_row;
		this.bookcase_col = p_bookcase_col;
		this.inisbn = p_inisbn;
	}
	
	public String getCasenum() {
		return bookcase_num;
	}
	
	public void setCasenum(String p_bookcase_num) {
		this.bookcase_num = p_bookcase_num;
	}
	public int getCaserow() {
		return bookcase_row;
	}
	
	public void setCaserow(int p_bookcase_row) {
		this.bookcase_row = p_bookcase_row;
	}
	public int getCasecol() {
		return bookcase_col;
	}
	
	public void setCasecol(int p_bookcase_col) {
		this.bookcase_col = p_bookcase_col;
	}
	public int getInisbn() {
		return inisbn;
	}
	
	public void setInisbn(int p_inisbn) {
		this.inisbn = p_inisbn;
	}
	
	
}
