import java.util.Scanner;

public class Main {
    public static int scanInt() {
        int N = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            while (!scanner.hasNextInt()){
                System.out.println("Попробуйте еще раз: ");
                scanner.next();
            }
            N = scanner.nextInt();
        } while (N <= 0);
        return N;
    }

    public static double scanDouble() {
        Scanner scanner = new Scanner(System.in);
        double input;

        do {
            while (!scanner.hasNextDouble()) {
                System.out.println("Это не число! Попробуйте еще раз.");
                scanner.next();
            }
            input = scanner.nextDouble();
        } while (input < 0);

        return input;
    }

    public static int scanString() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        int type = 0;

        if(input.equals("an")){
            type = 0;
        } else if (input.equals("dif")) {
            type = 1;
        }else{
            System.out.println("Введите корректный тип кредита: ");
            scanString();
        }
        return type;
    }

    public static void main(String[] args) {

        Credit credit =  new Credit(0, 0, 0, Credit.PaymentType.ANNUITY);

        credit.getCredit(credit);
    }
}