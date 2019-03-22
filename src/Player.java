import java.util.Scanner;

public class Player {
	static String name;
	static int chipTotal;
	static Scanner in = new Scanner(System.in);
	
	public static void askName() {
		System.out.println("What is your name?");
		name = in.nextLine();
	}
	
	public String getName() {
		return name;
	}
	
	public static void setChipTotal(int total) {
		chipTotal = total;
	}
	
	public static int getChipTotal() {
		return chipTotal;
	}
}
