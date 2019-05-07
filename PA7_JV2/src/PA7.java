//imports
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class PA7 {
    public static void main(String [] args) throws IOException {

        /*
            Tassadaq Hussain
            CP 6200
            PA7
         */

        FileInputStream fileStream = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;

        ArrayList<Integer> list = new ArrayList<>();

        try {
            fileStream = new FileInputStream("input.txt");
            rdr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(rdr);
            //Read in the numbers
            String line = bufRdr.readLine();
            while (line != null) {
                process(line, list );
                line = bufRdr.readLine();
            }
        } catch (IOException ioex) {
            if (fileStream != null) {
                fileStream.close();
            }
        }
        //Prints the original List
        System.out.println("The original List: " + list);

        //Prints the list without duplicates
        occurrences(list);
    }

    /* Method occurrences()
     * Input:
     *  ArrayList<Integer> list - reference to array list of integers
     * Process:
     *  Reads the initial list of integers and deletes any duplicates
     * Output:
     *  Prints out the Array list with out the duplicates
     */
    private static void occurrences(ArrayList<Integer> list) {
        LinkedHashSet<Integer> set = new LinkedHashSet<>(list);
        ArrayList<Integer> listNoDuplicates = new ArrayList<>(set);
        System.out.println("The list with out duplicates: " + listNoDuplicates);
    }

    /* Method process()
     * Input:
     *  String line - The read in number from the txt file
     *  ArrayList<Integer> list - reference to array list of integers
     * Process:
     *  Parses the inputted the String to an int
     * Output:
     *  none.
     */
    private static void process(String line, ArrayList<Integer> list) {
        list.add(Integer.parseInt(line));
    }
}