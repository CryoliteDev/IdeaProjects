import java.io.PrintWriter;

public class Transaction {

    private String transType, failureReason;
    private double transAmount;
    private boolean successIndicator;

    //copy constructor
    public Transaction(Transaction transaction) {
        transType = transaction.getTransType();
        failureReason = transaction.getFailureReason();
        transAmount = transaction.getTransAmount();
        successIndicator = transaction.successIndicator;
    }

    public Transaction getTransaction () {
        return new Transaction(this);
    }

    public String toString() {
        return String.format("Transaction Requested: %s \n" +
                "Transaction Amount %s \n" +
                "Transactions Success %s \n" +
                "Reason for Failure: %s \n",
                transType,transAmount,successIndicator,failureReason);
    }
    //constructor
    public Transaction(String transType, double transAmount, boolean successIndicator, String failureReason) {
        setTransType(transType);
        setTransAmount(transAmount);
        setTransSuccessIndicator(successIndicator);
        setFailureReason(failureReason);
    }

    public void printTransactions(Bank bank, int i, PrintWriter outFile) {
        if (i != -1) {
            outFile.println();
            outFile.println("----------------------------------------------------------------------------------");
            outFile.println("Print of Transactions for Account: " + bank.getAccounts().get(i).getAcctNum());
            for (int index = 0; index < bank.getAccounts().get(i).getTrans().size(); index++) {
                outFile.println("Transaction Requested" +
                        bank.getAccounts().get(i).getTrans().get(index).getTransType());
                if (bank.getAccounts().get(i).getTrans().get(index).getTransAmount() != -1) {
                    outFile.println("Transaction Amount: " +
                            bank.getAccounts().get(i).getTrans().get(index).getTransAmount());
                }
                outFile.println("Tranaction Success: " +
                        bank.getAccounts().get(i).getTrans().get(index).getTransSuccessIndicator());
                if (bank.getAccounts().get(i).getTrans().get(index).getTransSuccessIndicator() == false) {
                    outFile.println("Transaction Failure Reason: " +
                            bank.getAccounts().get(i).getTrans().get(index).getFailureReason());
                }
                outFile.println();
            }
            outFile.println("----------------------------------------------------------------------------------");
        } else {
            outFile.println();
            outFile.println("----------------------------------------------------------------------------------");
            outFile.println("\t\t\t\t\t\t**********************************");
            outFile.println("\t\t\t\t\t\t\tPrint of All Transactions");
            outFile.println("\t\t\t\t\t\t**********************************");
            for (int index = 0; index < bank.getAccounts().size(); index++) {
                outFile.println("Account Number: " + bank.getAccounts().get(index).getAcctNum());
                for (int j = 0; j < bank.getAccounts().get(index).getTrans().size(); j++) {
                    outFile.println("Transaction Requested: " +
                            bank.getAccounts().get(index).getTrans().get(j).getTransType());
                    if (bank.getAccounts().get(index).getTrans().get(j).getTransAmount() != -1) {
                        outFile.println("Transaction Amount: " +
                                bank.getAccounts().get(index).getTrans().get(j).getTransAmount());
                        outFile.println("Transaction Success: " +
                                bank.getAccounts().get(index).getTrans().get(j).getTransSuccessIndicator());
                    }
                    if (bank.getAccounts().get(index).getTrans().get(j).getTransSuccessIndicator() == false)
                        outFile.println("Transaction Failure Reason: " +
                                bank.getAccounts().get(index).getTrans().get(j).getFailureReason());
                }
                outFile.println();
                outFile.println();
            }
            outFile.println("----------------------------------------------------------------------------------");
        }
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(double transAmount) {
        this.transAmount = transAmount;
    }

    public boolean getTransSuccessIndicator() {
        return successIndicator;
    }

    public void setTransSuccessIndicator(boolean successIndicator) {
        this.successIndicator = successIndicator;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

}
