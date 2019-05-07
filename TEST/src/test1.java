public class test1{
    public static void main(String... args) {

        int playerMode = 2;
        int playerTurn = 2;
        String test = "";

        if (playerMode == 2) {
            if (playerTurn == 2) {
                test = "O";
                playerTurn = 1;
            }

            if (playerTurn == 1) {
                test = "X";
                playerTurn = 2;
            }
        }

        System.out.println(test);
    }
}