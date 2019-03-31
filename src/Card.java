public class Card implements Comparable<Card>{
	private String suit;
	private String rank;
	private int pointValue;
	
	public Card(String cardRank, String cardSuit, int cardPointValue) {
		rank = cardRank;
		suit = cardSuit;
		pointValue = cardPointValue;
	}
	
	public int compareTo(Card C) {
		return this.pointValue - C.pointValue;
	}
	
	public String suit() {
		return suit;
	}
	
	public String rank() {
		return rank;
	}
	
	public int pointValue() {
		return pointValue;
	}
	
	@Override
	public String toString() {
		return rank + " of " + suit + " (point value = " + pointValue + ")";
	}
}
