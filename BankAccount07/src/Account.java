import java.util.ArrayList;
import java.util.Date;

public class Account {

    //Variable declarations
    private ArrayList<Transaction> trans = new ArrayList<Transaction>();
    private Depositor depositor;
    private Name name;
    private int acctNum;
    private String acctType;
    private Double acctBal;
    private boolean acctStat;
    private Date date;

    //Copy Constructor
    public Account(Account acct){
        setDepositor(acct.getDepositor().getName().getfName(), acct.getDepositor().getName().getlName(),
                acct.getDepositor().getSSN());
        setAcctNum(acct.acctNum);
        setAcctType(acct.acctType);
        setAcctBal(acct.acctBal);
        acctBal = acct.getAcctBal();
        if (acct.getAcctStat().equals("Open")) {
            setAcctStat("Open");
        } else if (acct.getAcctStat().equals("Closed")) {
            setAcctStat("Closed");
        }
    }

    public Account getAccount() {
        return new Account(this);
    }
    public String toString() {
        return String.format("%s %-12s | %-8s | $%7.2f | %-12s", depositor, acctBal, acctNum, acctBal, acctStat);
    }
    //Constructor for an Account
    public Account(String fName, String lName, String SSN, int acctNum, String acctType,
                   Double acctBal, String acctStat) {
        setDepositor(fName, lName, SSN);
        setAcctNum(acctNum);
        setAcctType(acctType);
        setAcctStat(acctStat);
        setAcctBal(acctBal);
        addTrans("New Account", acctBal, true,null);

    }

    public Account(String fName, String lName, String SSN, int acctNum, String acctType, double acctBal,
                   String acctStat, int year, int month, int day) {
        setDepositor(fName, lName, SSN);
        setAcctNum(acctNum);
        setAcctType(acctType);
        setAcctBal(acctBal);
        setAcctStat(acctStat);
        addTrans("New Account Creation", acctBal, true, null);
         }
    public void setAcctStat(String acctStat) {
        if (acctStat.equals("Open")) {
            this.acctStat = true;
        } else if (acctStat.equals("Closed")) {
            this.acctStat = false;
        }
    }

    public String getAcctStat() {
        if (acctStat == true) {
            return "Open";
        } else if (acctStat == false) {
            return "Closed";
        }
        return null;
    }

    /*
     * Method makeDeposit()
     * input:
     * amountToDeposit - the amount the user is trying to deposit to the account.
     * Process:
     * adds the amountToDeposit to the current balance of the account.
     */
    public void makeDeposit(double amountToDeposit){
        acctBal += amountToDeposit;
    }

    /*
     * Method makeWithdrawal()
     * input:
     * amountToDeposit - the amount the user is trying to deposit to the account.
     * Process:
     * adds the amountToDeposit to the current balance of the account.
     */
    public void makeWithdrawal(double amountTowithdraw){
        acctBal -= amountTowithdraw;
    }

    public void closeAcct() {
        acctStat = false;
    }

    public void reopenAcct() {
        acctStat = true;
    }

    public ArrayList<Transaction> getTrans() {
        return trans;
    }

    public void addTrans(String transType, double transAmount, boolean successIndicator, String failureReason) {
        trans.add(new Transaction(transType, transAmount, successIndicator,failureReason));
    }
    //Getter and Setters

    public Depositor getDepositor() {
        return depositor;
    }

    public void setDepositor(String fName, String lName, String SSN) {
        depositor = new Depositor(fName, lName, SSN);
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
        return acctType;
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

    public void setDate(int year, int month, int day) {
        date = new Date(month, day, year);
    }
    public Date getDate(){
        return date;
    }
}