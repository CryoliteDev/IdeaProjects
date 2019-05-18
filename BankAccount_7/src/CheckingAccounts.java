public class CheckingAccounts extends Account {

    public CheckingAccounts(String fName, String lName, String SSN, int acctNum, String acctType, Double acctBal, boolean matDate, String readAcctStat) {
        super(fName, lName, SSN, acctNum, acctType, acctBal, matDate, readAcctStat);
    }

    public void makeDeposit (double amountToDeposit) {
        setAcctBal(getAcctBal() + amountToDeposit);
    }

    public void makeWithdrawal (double amountToWithdraw) {
        if (getAcctBal() < 2500) {
            setAcctBal(getAcctBal() - amountToWithdraw - 1.50);
        } else {
            setAcctBal(getAcctBal() - amountToWithdraw);
        }
    }
}