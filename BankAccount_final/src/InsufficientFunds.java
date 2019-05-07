public class InsufficientFunds extends Exception {

    public InsufficientFunds(double amount) {
        super(String.format("Error: You do not have sufficient funds to make a withdrawal $%7.2f", amount));
    }
}
