import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Dealer{
	static Scanner in = new Scanner(System.in);
	static int playerScore = 0;
	static int dealerScore = 0;
	static String[] ranks1 = {"jack", "queen", "king", "ace", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	static String[] suits1 = {"hearts", "diamonds", "spades", "clubs"};
	static int[] pointValues1 = {10, 10, 10, 11, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	static Deck d = new Deck(ranks1, suits1, pointValues1);
	static boolean cashout = false;
	static ArrayList<Card> playerhand = new ArrayList<Card>();
	static ArrayList<Card> dealerhand = new ArrayList<Card>();
	static int count = 0;
	static String total;
	static int atotal;
		
	public static void main(String[] args) {
		System.out.println("Welcome to Blackjack!");
		Player.askName();
		d.shuffle();
		boolean first = true;
		while(!(d.isEmpty())&& cashout == false) {
			if(first == true) {
				System.out.println("How many chips do you have?");
				total = in.nextLine();
				while(!(checkforint(total))) {
					System.out.println("Please enter a number.");
					total = in.nextLine();
				}
				atotal = Integer.parseInt(total);
				Player.setChipTotal(atotal);
				first = false;
			}
			else {
				System.out.println("Do you want to keep playing or do you want to cash out?");
				String option = in.nextLine();
				while(!(option.equals("keep playing") || option.equals("cash out"))) {
					System.out.println("Please select 'keep playing' or 'cash out'.");
					option = in.nextLine();
				}
				if(option.equals("cash out")) {
					cashout = true;
					break;
				}
				playerScore = 0;
				dealerScore = 0;
				playerhand.clear();
				dealerhand.clear();
				count = 0;
			}
			System.out.println("How many chips do you want to bet?");
			
			String bet = in.nextLine();
			while(!(checkforint(bet))) {
				System.out.println("Please enter a number.");
				bet = in.nextLine();
			}
			int abet = Integer.parseInt(bet);
			while(abet > Player.getChipTotal()) {
				System.out.println("You only have " + Player.getChipTotal() + " chips. Please enter a number less than " + (Player.getChipTotal()+1));
				bet = in.nextLine();
				while(!(checkforint(bet))) {
					System.out.println("Please enter a number.");
					bet = in.nextLine();
				}
				abet = Integer.parseInt(bet);
			}
			boolean result = game();
			if(result == false) {
				System.out.println("You lose");
				Player.setChipTotal(Player.getChipTotal()-abet);
				System.out.println("Your now have " + Player.getChipTotal() + " chips.");
			}
			else if(result == true && playerScore == dealerScore) {
				tie();
				System.out.println("Your now have " + Player.getChipTotal() + " chips.");
			}
			else {
				System.out.println("You WIN!");
				Player.setChipTotal(Player.getChipTotal()+abet);
				System.out.println("Your now have " + Player.getChipTotal() + " chips.");				
			}
		}
		System.out.println(Player.name + " thanks for playing blackjack! You have " + Player.getChipTotal() + " chips!");
	}
	
	public static boolean checkforint(String input) {
		for(int i = 0; i < input.length(); i++) {
			if(Character.isAlphabetic(input.charAt(i))) {
				return false;
			}
			else if(Character.isWhitespace(input.charAt(i))){
				return false;
			}
			else if(!(Character.isDigit(input.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	
	public static Card playerDeal(Deck d) {
		if(!(d.isEmpty())) {
			Card dealt = d.deal();
			playerhand.add(dealt);
			Collections.sort(playerhand);
			return dealt;
		}
		return null;
	}
	
	public static Card dealerDeal(Deck d) {
		if(!(d.isEmpty())) {
			Card dealt = d.deal();
			dealerhand.add(dealt);
			Collections.sort(dealerhand);
			return dealt;
		}
		return null;
	}
	
	public static boolean stand() {
		d.shuffle();
		while(count == 1 || dealerScore < 17) {
			Card tdeal = dealerDeal(d);
			dealerScore = dealerScore + tdeal.pointValue();
		}
		count++;
		printPlayerHand();
		printPlayerScore();
		printDealerHand();
		printDealerScore();
		if(playerScore == 21 || (playerScore > dealerScore && playerScore < 21) || dealerScore > 21 || playerScore == dealerScore) {
			return true;
		}
		return false;
	}
	
	public static void tie() {
		System.out.println("You tie.");
	}
	
	public static void hit() {
		d.shuffle();
		if(count == 1) {
			Card tdeal = dealerDeal(d);
			dealerScore = dealerScore + tdeal.pointValue();
		}
		count++;
		Card three = playerDeal(d);
		if(three.rank().equals("ace")) {
			if(playerScore + 11 > 21) {
				playerScore = playerScore + 1;
			}
			else {
				playerScore = playerScore + 11;
			}
		}
		else {
			playerScore = playerScore + three.pointValue();
		}
		if(dealerScore < 17) {
			Card hdeal = dealerDeal(d);
			dealerScore = dealerScore + hdeal.pointValue();
		}
		if(playerScore > 21){
			for(Card c : playerhand) {
				if(c.rank().equals("ace")) {
					playerScore = playerScore - 10;
				}
			}
		}
		printPlayerHand();
		printPlayerScore();
	}
	
	public static boolean game() {
		while(playerScore <= 21) {
			Card one = playerDeal(d);
			Card two = playerDeal(d);
			playerScore = one.pointValue() + two.pointValue();
			Card dealer = dealerDeal(d);
			dealerScore = dealer.pointValue();
			printPlayerHand();
			printPlayerScore();
			printDealerHand();
			printDealerScore();
			while(playerScore <= 21) {
				System.out.println("What do you want to do.");
				String option = null;
				option = in.nextLine();
				while(option == null) {
					option = in.nextLine();
				}
				while(!(option.equals("hit") || option.equals("stand"))) {
					System.out.println("Please enter a valid action: hit or stand.");
					option = in.nextLine();
				}
				if(option.equals("stand")) {
					return stand();
				}
				else {
					hit();
				}
			}
			
		}
		return false;
	}
	
	public static void printPlayerHand(){
		Collections.sort(playerhand);
		System.out.println("Your hand is " + playerhand);
	}
	
	public static void printDealerHand() {
		Collections.sort(dealerhand);
		System.out.println("The dealer's hand is " + dealerhand);
	}
	
	public static void printPlayerScore() {
		System.out.println("Your score is: " + playerScore);
	}
	
	public static void printDealerScore() {
		System.out.println("The dealer's score is: " + dealerScore);
	}
}
