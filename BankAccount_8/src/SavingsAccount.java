public class SavingsAccount extends Account {

    public SavingsAccount(String fName, String lName, String SSN, int acctNum, String acctType, Double acctBal, boolean matDate, String readAcctStat) {
        super(fName, lName, SSN, acctNum, acctType, acctBal, matDate, readAcctStat);
    }

    public void makeDeposit (double amountToDeposit) {
        setAcctBal(getAcctBal() + amountToDeposit);
    }

    public void makeWithdrawal (double amountToWithdraw) {
        setAcctBal(getAcctBal() - amountToWithdraw);
    }

}
