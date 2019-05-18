import java.util.ArrayList;

public abstract class genAccount {

    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    public Depositor depositor;
    public int acctNum;
    public String acctType;
    public boolean acctStatus;
    public double acctBal;

    public abstract String toString();
    public  abstract Account getAccount();
    public abstract void setAcctStatus(String acctStatus);
    public  abstract String getAcctStatus();
    public abstract void makeDeposit(double amountToDeposit);
    public abstract void makeWithdrawal(double makeWithdrawal);
    public abstract void closeAcct();
    public abstract void reOpenAcct();
    public abstract double getAcctBal;
    public abstract Depositor getDepositor;
    public abstract int getAcctNum();
    public abstract String getAcctType();
    public abstract ArrayList<Transaction> getTransactions();
    public abstract void setDepositor(String fName, String lName, String SSN);
    public abstract void setAcctNum(int acctNum);
    public abstract void setAcctType(String acctType);
    public abstract void setAcctBal(double acctBal);
    public abstract void addTransaction(String transactionType, double transactionAmount, boolean successIndicator, String failureReason);

}
