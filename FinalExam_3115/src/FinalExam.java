import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FinalExam {

    public static void main(String[] args) throws FileNotFoundException{
        char choice;
        boolean notDone = true;

        ArrayList<SavingsAccount> accounts = new ArrayList<SavingsAccount>();

        File testFile = new File("testCases.txt");

        Scanner kybd = new Scanner(testFile);
        //Scanner kybd = new Scanner(System.in);

        PrintWriter outFile = new PrintWriter("output.txt");
        //PrinterWrite outFile = new PrintWriter("System.out);

        readAccts(accounts);
        printAccts(accounts, outFile);

        do {
            menu();
            choice = kybd.next().charAt(0);
            switch (choice) {
                case 'q':
                case 'Q':
                    notDone = false;
                    printAccts(accounts,outFile);
                    break;
                case 'b':
                case 'B':
                    getBalance(accounts,outFile,kybd);
                    break;
                case 'i':
                case 'I':
                    addInterest(accounts,outFile,kybd);
                    break;
                case 'd':
                case 'D':
                    makeDeposit(accounts,outFile,kybd);
                    break;
                case 'w':
                case 'W':
                    makeWithdrawal(accounts,outFile,kybd);
                    break;
            }
        }

    }
}