import java.util.Scanner;

public class PA3 {
    public static void main(String [] args){
        int[] array = new int[5];
        outputResults(array);
    }

    public static void getScores(int[] array){
        Scanner kybd = new Scanner(System.in);

        for(int i = 0; i < 5; i++){
            System.out.println("What is the score for the student number " + (i+1) + ": ");
            Double grd = kybd.nextDouble();
            if(grd>100 || grd<0){
                System.out.println("Invalid input!");
                System.exit(1);
            }
            array[i] = grd.intValue();
        }
    }

    public static double getAverage(int[] array){
        double sum = 0;
        for(int i = 0; i < 5; i++){
            sum += array[i];
        }
        return sum/5;
    }

    public static String getLetterScore(int score){

        String letterGrade;

        switch (score/10) {
            case 9: letterGrade = "A";
                break;
            case 8: letterGrade = "B";
                break;
            case 7: letterGrade = "C";
                break;
            case 6: letterGrade = "D";
                break;
            default:
                letterGrade = "F";
        }
        return letterGrade;
    }

   public static void outputResults(int[] array){
        getScores(array);
        System.out.println("Average of scores is " + getAverage(array)+ "\n");
        for(int i =0; i<5; i++){
            System.out.println("Student " + (i+1) + " got an " + getLetterScore(array[i]));
        }
    }
}