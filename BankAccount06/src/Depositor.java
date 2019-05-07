public class Depositor {

    //variable declarations
    private Name name;
    private String SSN;

    //Copy constructor
    public Depositor(Depositor depos) {
        setName(depos.getName().getfName(),depos.getName().getlName());
        this.SSN = SSN;
    }

    public  Depositor getDepositor() {
        return new Depositor(this);
    }

    public String toString() {
        return String.format("%-20s | %-10s | ", name, SSN);
    }
    //Constructor for Depositor
    public Depositor(String fName, String lName, String SSN) {
        setName(fName, lName);
        this.SSN = SSN;
    }

    //Getter and Setter
    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public Name getName() {
        return name;
    }

    public void setName(String fName, String lName) {
        name = new Name(fName,lName);
    }
}
