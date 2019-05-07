public class Depositor {

    //variable declarations
    private Name name;
    private String SSN;



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
