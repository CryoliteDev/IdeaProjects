public class Name {

    //variable declarations
    private String fName;
    private String lName;

    // copy constructor
    public Name(Name name){
        setfName(name.fName);
        setlName(name.getlName());
    }

    public Name getName() {
        return new Name(this);
    }

    public String toString() {
        return String.format("%s %s", fName,lName);
    }
    //Constructor
    public Name(String fName, String lName) {
        this.fName = fName;
        this.lName = lName;
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
}