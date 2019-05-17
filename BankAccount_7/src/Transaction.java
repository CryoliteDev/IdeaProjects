public class Transaction {
    private String transactionType;
    private String failureReason;
    private boolean successIndicator;
    private double transactionAmount;

    //Constructor
    public Transaction(String transactionType, double transactionAmount, boolean successIndicator, String failureReason) {
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.successIndicator = successIndicator;
        this.failureReason = failureReason;
    }

    //Copy Constructor
    public Transaction(Transaction transaction) {
        transactionType = transaction.getTransactionType();
        transactionAmount = transaction.getTransactionAmount();
        successIndicator = transaction.isSuccessIndicator();
        failureReason = transaction.getFailureReason();
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public boolean isSuccessIndicator() {
        return successIndicator;
    }

    public void setSuccessIndicator(boolean successIndicator) {
        this.successIndicator = successIndicator;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String toString() {
        return String.format("Transaction Requested: %s \n" +
                        "Transaction Amount %s \n" +
                        "Transaction Success:%s \n" +
                        "Reason for Failure: %s \n" ,
                transactionType,transactionAmount,successIndicator,failureReason);
    }

}
