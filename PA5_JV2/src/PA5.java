public class PA5 {
    public static void main(String [] args) {

        /*
         * Tassadaq Hussain
         * CP 6200
         * PA5
         */

        //Creates two student objects
        Student s1 = new Student();
        Student s2 = new Student();

        //sets the info for student 2
        s2.setName("William Johnson");
        s2.setId(124987123);
        s2.setCredits(36);
        s2.setGPA(3.2);

        //Displays info of Student 1
        System.out.println("Info of Student 1: \nName: " + s1.getName() + "\nID: " +
                s1.getId() + "\nCredits: " + s1.getCredits() + "\nGPA: " + s1.getGPA());

        //Displays info of Student 2
        System.out.println("\nInfo of Student 2: \nName: " + s2.getName() + "\nID: " +
                s2.getId() + "\nCredits: " + s2.getCredits() + "\nGPA: " + s2.getGPA());

        //Displays the Average GPA of the two student
        double avg = (s1.getGPA() + s2.getGPA()) / 2;
        System.out.println("\nAverage GPA: " + avg);

        //Displays the name of the student with higher credits
        if (s1.getCredits() > s1.getCredits())
            System.out.println("\nStudent 1: " + s1.getName() + " has more Credits");
        else
            System.out.println("\nStudent 2: " + s2.getName() + " has more credits");

    }
}
