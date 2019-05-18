public class CDAccount extends Account {

    private DateInfo dateInfo;

    public CDAccount(String fName, String lName, String SSN, int acctNum, String acctType, Double acctBal, boolean matDate, String readAcctStat) {
        super(fName, lName, SSN, acctNum, acctType, acctBal, matDate, readAcctStat);

    }

    public void makeDeposit (double amountToDeposit) {
        if (Bank.getTodaysDate().getDayOfMonth() < dateInfo.getDayOfMonth()){

        } else if (Bank.getTodaysDate().getDayOfMonth() <= (dateInfo.getDayOfMonth() + 15) && Bank.getTodaysDate().getDayOfMonth() >= (dateInfo.getDayOfMonth()) && Bank.getTodaysDate().getDayOfMonth() <=26) {
            setAcctBal(getAcctBal() + amountToDeposit);
        }
    }

    public void makeWithdrawal(double amountToWithdraw) {
        setAcctBal(getAcctBal() - amountToWithdraw);
    }
}
