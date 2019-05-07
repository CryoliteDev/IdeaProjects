public class Account {

    //Variable declarations
    private String fName;
    private String lName;
    private String SSN;
    private int acctNum;
    private String acctType;
    private Double acctBal;

    //Constructor
    public Account(String fName, String lName, String SSN, int acctNum,
                   String acctType, Double acctBal) {
        this.fName = fName;
        this.lName = lName;
        this.SSN = SSN;
        this.acctNum = acctNum;
        this.acctType = acctType;
        this.acctBal = acctBal;
    }

    //Getters and Setters

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
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
}