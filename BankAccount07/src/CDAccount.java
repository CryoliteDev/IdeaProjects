import java.util.Date;

public class CDAccount extends Account{

    private Date date;

    public CDAccount(String fName, String lName, String SSN, int acctNum, String acctType,
                     double acctBal, String acctStat, int month, int day, int year) {
        super(fName, lName, SSN, acctNum,acctType, acctBal, acctStat);
        setDate(month, day, year);
    }

    public void makeDeposit(double amountToDeposit) {
        if (Bank.getTodaysDate().getDay < date.getDay()) {

        } else if(Bank.getTodaysDate().getDay() <= (date.getDay() + 5) && Bank.getTodaysDate().getDay() >=
                (date.getDay()) && Bank.getTodaysDate().getDay() <= 26) {
            setAcctBal(getAcctBal() + amountToDeposit);
        }
    }

    public void makeWithdrawl(double amountToWithdraw) {
            setAcctBal(getAcctBal() - amountToWithdraw);
    }
}

