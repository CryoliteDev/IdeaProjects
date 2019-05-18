public abstract class genDepositor {

    private Name name;
    private String SSN;

    public abstract Depositor getDepositor();
    public abstract String toString();
    public abstract Name getName();
    public abstract String getSSN();
    public abstract void setName(String fName, String lName);
    public abstract void setSSN(String SSN);

}
