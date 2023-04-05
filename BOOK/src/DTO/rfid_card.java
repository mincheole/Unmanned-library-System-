package DTO;

public class rfid_card {
	String card_key;
	
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
