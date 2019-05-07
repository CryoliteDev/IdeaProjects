public class Student {

    //Variable
    private String name;
    private int id;
    private int credits;
    private double GPA;

    //Constructor
    public Student() {
        name = "John Doe";
        id = 245894893;
        credits = 33;
        GPA = 3.4;
    }

    //Setters and Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }
}
