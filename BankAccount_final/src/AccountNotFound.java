public class AccountNotFound extends Exception {

    public AccountNotFound(int requestedAcct) {
        super("Error: The account number: " + requestedAcct + " does not exist.");
    }
}
