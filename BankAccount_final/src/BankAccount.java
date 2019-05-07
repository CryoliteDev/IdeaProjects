import java.io.PrintWriter;

public abstract class BankAccount {

    protected int acctNum;
    protected double acctBal;

    //Constructor
    public BankAccount(int acctNum, double acctBal){
        this.acctNum = acctNum;
        this.acctBal = acctBal;
    }

    //Copy Constructor
    public BankAccount(BankAccount account){
        this.acctBal = acctBal;
        this.acctNum = acctNum;
    }

    public abstract double getBalance();

    public abstract void makeDeposit(double amountToDeposit, PrintWriter outFile) throws NegativeAmountEntered;

    public abstract void makeWithdrawal(double amountToWithdraw, PrintWriter outFile) throws NegativeAmountEntered,
            InsufficientFunds;
}
