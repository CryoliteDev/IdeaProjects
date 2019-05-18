import java.io.PrintWriter;

public abstract class genTransaction {

    public String transactionType;
    public double transactionAmount;
    public  boolean successIndicator;
    public String failureReason;

    public abstract void printTransactions(Bank bank, PrintWriter outFile);
    public abstract String toString();
    public abstract String getTransactionType();
    public abstract double getTransactionAmount();
    public abstract boolean getTransactionSuccessIndicator();
    public abstract String getTransactionFailureReason();
    public abstract void  setTransactionType(String transactionType);
    public abstract void setTransactionAmount(double transactionAmount);
    public abstract void setTransactionSuccessIndicator(boolean successIndicator);
    public abstract void setTransactionFailureReason(String failureReason);


}
