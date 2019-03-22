import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "serial" })
public class Deck extends ArrayList{
	private int size;
	
	public Deck(String[] ranks, String[] suits, int[] values) {
		for (int j = 0; j < ranks.length; j++) {
			for (String suitString : suits) {
				this.add(new Card(ranks[j], suitString, values[j]));
			}
		}
		size = this.size();
		shuffle();
	}
	
	public void shuffle() {
		Card temp;
		for(int k = this.size()-1; k > 0; k--) {
			int r = Integer.valueOf((int) (Math.random() * k));
			temp = (Card) this.get(r);
			this.set(r, this.get(k));
			this.set(k, temp);
		}
		size = this.size();
	}
	
	public Card deal() {
		if (isEmpty()) {
			return null;
		}
		size--;
		Card c = (Card) this.get(size);
		return c;
	}
	
	@Override
	public String toString() {
		String rtn = "size = " + size + "\nUndealt cards: \n";

		for (int k = size - 1; k >= 0; k--) {
			rtn = rtn + this.get(k);
			if (k != 0) {
				rtn = rtn + ", ";
			}
			if ((size - k) % 2 == 0) {
				// Insert carriage returns so entire deck is visible on console.
				rtn = rtn + "\n";
			}
		}

		rtn = rtn + "\nDealt cards: \n";
		for (int k = this.size() - 1; k >= size; k--) {
			rtn = rtn + this.get(k);
			if (k != size) {
				rtn = rtn + ", ";
			}
			if ((k - this.size()) % 2 == 0) {
				// Insert carriage returns so entire deck is visible on console.
				rtn = rtn + "\n";
			}
		}

		rtn = rtn + "\n";
		return rtn;
	}
}
