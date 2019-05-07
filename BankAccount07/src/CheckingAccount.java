import java.util.ArrayList;

public class CheckingAccount extends Account {

    public CheckingAccount(String fName, String lName, String SSN, int acctNum, String
            acctType, double acctBal, String acctStat) {
        super(fName, lName, SSN, acctNum,acctType, acctBal, acctStat);
    }

    public void makeDeposit(double amountToDeposit) {
        setAcctBal(getAcctBal() + amountToDeposit);
    }

    public void makeWithdrawl(double amountToWithdraw) {
        if (getAcctBal() < 2500 ) {
            setAcctBal(getAcctBal() - amountToWithdraw - 1.50);
        } else {
            setAcctBal(getAcctBal() - amountToWithdraw);
        }
    }
}
