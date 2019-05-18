public class Depositor extends genDepositor {

    //Variable Declarations
    private Name name;
    private String SSN;

    //Copy Constructor
    public Depositor(Depositor depositor){
        name = getName();
        SSN = getSSN();
    }

    //Constructor
    public Depositor(String fName, String lName, String SSN) {
        name = new Name(fName, lName);
        this.SSN = SSN;
    }

    //Getters and Setters
    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getSSN() {
        return SSN;
    }

    @Override
    public void setName(String fName, String lName) {

    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    @Override
    public Depositor getDepositor() {
        return null;
    }

    public String toString() {
        return String.format("%-20s | %-10 |", name, SSN);
    }
}
