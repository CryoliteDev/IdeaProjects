public class Recursion {
    public static void main(String[] args) {

        Sign(5);
        System.out.println(Reverse("JAVA"));
    }

    public static void Sign(int n) {
        if (n == 0) {
            System.out.println("Can Park");
        } else {
            System.out.println("No Parking");
            Sign(n - 1);
        }
    }

    public static String Reverse(String n) {
        if (n.length() <= 1) {
            return n;
        } else {
            return Reverse(n.substring(1)) + n.charAt(0);
        }
    }
}
