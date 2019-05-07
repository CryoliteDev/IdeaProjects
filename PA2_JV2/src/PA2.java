import java.io.*;
import java.util.*;

public class PA2 {
    public static void main(String[] args) throws FileNotFoundException {

        PrintWriter outFile = new PrintWriter("output.txt");

        File dbFile = new File("input.txt");
        Scanner sc = new Scanner(dbFile);


        String text= null;
        while (sc.hasNext()) {
            text = sc.nextLine();
        }

        String replaceString= text.replace('o','0');

        int count = 0;
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) != ' '){
                count++;
                while(text.charAt(i) != ' ' && i < text.length()-1){
                    i++;
                }
            }
        }

        outFile.println(replaceString);
        outFile.println(count);

        sc.close();
        outFile.close();
    }

}