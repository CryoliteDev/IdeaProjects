import java.util.*;

public class PA1 {
    public static void main(String[] args) {

        //Variable declarations
        int ex1, ex2, ex3;
        double avg;

        Scanner kybd = new Scanner(System.in);

        System.out.println("Enter your grade for Exam no. 1");
        ex1 = kybd.nextInt();
        ex1 = validate(ex1,kybd);

        System.out.println("Enter your grade for Exam no. 2");
        ex2 = kybd.nextInt();
        ex2 = validate(ex2,kybd);

        System.out.println("Enter your grade for Exam no. 3");
        ex3 = kybd.nextInt();ex3 =  validate(ex3,kybd);

        avg = average(ex1,ex2,ex3);
        System.out.println("You have an average of " + avg);
        letterGrade(avg);
        System.out.println("You have Letter Grade of: " + letterGrade(avg));

    }

    public static int validate(int exam, Scanner kybd) {
        if(exam >= 0 && exam <= 100) {
            return exam;
        } else {
            System.out.println("The exam must be between 0 and 100\nPlease enter the exam score again");
            exam = kybd.nextInt();
            return exam;
        }
    }

    public static double average (int exam1, int exam2, int exam3){
        int sum = (exam1 + exam2 + exam3);
        double average = sum/3;
        return average;
    }

    public static String letterGrade(double average){

        if (average >= 90){
            return "A";
        } else if(average >= 80){
            return "B";
        } else if (average >=70) {
            return "C";
        } else if (average >= 60) {
            return "D";
        } else{
            return "F";
        }

        switch(average)
        {
            case 90: "A";
                break;
            case 8: letterGrade = "B";
                break;
            case 7: letterGrade = "C";
                break;
            case 6: letterGrade = "D";
            default:
                letterGrade = "F";
        }
        return letterGrade;

    }
}