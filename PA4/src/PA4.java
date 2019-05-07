public class PA4 {
    public static void main(String[] args) {

        int[] randomNumbers = new int[30];
        int[] occurrence = new int[10];

        for (int i = 0; i < randomNumbers.length; i++) {
            int num = (int)(Math.random() * 10);
            randomNumbers[i] = num;
            occurrence[num]++;
        }

        occur(randomNumbers);

        System.out.println("---------------------\nNumber\t\tFrequency");

        for (int i = 0; i < occurrence.length; i++) {
            System.out.println("\t"+ i + "\t\t\t" + occurrence[i]);
        }
    }

    private static void occur(int[] randomNumbers) {
        for (int i = 0; i < randomNumbers.length; i++) {
            System.out.print(randomNumbers[i] + " ");
            if ((i + 1) % 10 == 0)
                System.out.println();
        }
    }
}
