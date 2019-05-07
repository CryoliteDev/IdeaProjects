public class SavingsAccount extends Account {

    public SavingsAccount(String fName, String lName, String SSN, int acctNum, String acctType,
                          double acctBal, String acctStat) {
        super(fName, lName, SSN, acctNum,acctType, acctBal, acctStat);
    }

    public void makeDeposit(double amountToDeposit) {
        setAcctBal(getAcctBal() + amountToDeposit);
    }

    public void makeWithdrawl(double amountToWithdraw) {
            setAcctBal(getAcctBal() - amountToWithdraw);
    }
}
