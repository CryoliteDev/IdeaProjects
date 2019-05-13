import java.util.ArrayList;

public class Account {

    //Variable declarations
    private Depositor depositor;
    private Name name;
    private int acctNum;
    private String acctType;
    private Double acctBal;
    private boolean matDate;
    private boolean acctStat;
    private ArrayList<Transaction> Transactions = new ArrayList<Transaction>();


    //Constructor

    public Account(String fName, String lName, String SSN, int acctNum, String acctType, Double acctBal, boolean matDate, String readAcctStat) {
        depositor = new Depositor(fName, lName, SSN);
        name = new Name(fName, lName);
        this.acctNum = acctNum;
        this.acctType = acctType;
        this.acctBal = acctBal;
        this.matDate = matDate;
        setAcctStat(readAcctStat);
        addTransaction("", acctBal, true,null);
    }
    //Getters and Setters
    public void addTransaction(String transactionType, double transactionAmount, boolean successIndicator, String failureReason) {
        Transactions.add(new Transaction(transactionType, transactionAmount,successIndicator,failureReason));
    }

    public ArrayList<Transaction> getTransactions() {
        return Transactions;
    }

    public Depositor getDepositor() {
        return depositor;
    }
    public void setDepositor(Depositor depositor) {
        this.depositor = depositor;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getAcctNum() {
        return acctNum;
    }

    public void setAcctNum(int acctNum) {
        this.acctNum = acctNum;
    }

    public String getAcctType() {
        return acctType.toUpperCase();
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public Double getAcctBal() {
        return acctBal;
    }

    public void setAcctBal(Double acctBal) {
        this.acctBal = acctBal;
    }

    public boolean getMatDate() {
        return matDate;
    }

    public void setMatDate(boolean matDate) {

        this.matDate = matDate;
    }

    public boolean getAcctStat() {
        return acctStat;
    }

    public void setAcctStat(String readAcctStat) {
        this.acctStat = readAcctStat.equals("OPEN") ? true : false;
    }
}