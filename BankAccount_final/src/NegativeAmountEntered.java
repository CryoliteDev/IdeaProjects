public class NegativeAmountEntered extends Exception {

    public NegativeAmountEntered(double amount){
        super(String.format("Error: A negative number was entered $%7.2f", amount));
    }
}
