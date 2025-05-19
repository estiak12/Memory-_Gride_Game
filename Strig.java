import java.util.Scanner;
import java.util.Random;

class NumberGuessingGame {
    private int lower;
    private int upper;
    private int numberToGuess;
    private int attempts;
    private Scanner scanner;

    public NumberGuessingGame(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
        this.scanner = new Scanner(System.in);
        reset();
    }

    public void start() {
        System.out.println("🎮 Welcome to the Number Guessing Game!");
        System.out.println("I'm thinking of a number between " + lower + " and " + upper + ".");

        while (true) {
            System.out.print("Enter your guess: ");
            try {
                int guess = Integer.parseInt(scanner.nextLine());
                attempts++;
                if (checkGuess(guess)) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("❗ Please enter a valid number.");
            }
        }
    }

    private boolean checkGuess(int guess) {
        if (guess < numberToGuess) {
            System.out.println("Too low! 🔽 Try again.");
            return false;
        } else if (guess > numberToGuess) {
            System.out.println("Too high! 🔼 Try again.");
            return false;
        } else {
            System.out.println("🎉 Correct! You guessed it in " + attempts + " attempts.");
            return true;
        }
    }

    public void reset() {
        Random rand = new Random();
        numberToGuess = rand.nextInt(upper - lower + 1) + lower;
        attempts = 0;
    }

    public boolean askToPlayAgain() {
        System.out.print("Do you want to play again? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes");
    }
}

public class Main {
    public static void main(String[] args) {
        NumberGuessingGame game = new NumberGuessingGame(1, 100);

        do {
            game.start();
        } while (game.askToPlayAgain());

        System.out.println("Thanks for playing! 👋");
    }
}
