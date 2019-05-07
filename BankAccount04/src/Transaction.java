public class Transaction {

    private String transType, failureReason;
    private double transAmount;
    private boolean successIndicator;

    public Transaction(String transType, double transAmount, boolean successIndicator, String failureReason) {
        setTransType(transType);
        setTransAmount(transAmount);
        setTransSuccessIndicator(successIndicator);
        setFailureReason(failureReason);
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
