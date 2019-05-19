public class Recursion {
    public static void main (String[] args) {

        /*
         * Tassadaq Hussain
         * CP 6200
         * PA9
         */
        System.out.println("f(0,0) = " + f(0,0));
        System.out.println("f(0,1) = " + f(0,1));
        System.out.println("f(1,1) = " + f(1,1));
        System.out.println("f(1,2) = " + f(1,2));
        System.out.println("f(1,3) = " + f(1,3));
        System.out.println("f(2,2) = " + f(2,2));
        System.out.println("f(3,2) = " + f(3,2));
    }
    public static int f(int m, int n) {
        if (m == 0 ) {
            return n + 1;
        } else if (n == 0) {
            return f(m-1,1);
        } else {
            return f(m-1, f(m, n-1));
        }
    }
}
