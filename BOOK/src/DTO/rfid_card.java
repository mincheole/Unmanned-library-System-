package DTO;

public class rfid_card {			// RFID 카드 테이블
	String card_key;				// RFID 카드 키 번호(기본키)
	
	public rfid_card(String p_card_key) {
		this.card_key = p_card_key;
	}
	
	public String getKey() {
		return card_key;
	}
	
	public void setKey(String p_card_key) {
		this.card_key = p_card_key;
	}
}
