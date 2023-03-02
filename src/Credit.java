import java.util.Scanner;

public class Credit {
    private double sum;
    private int time;
    private double percents;
    private PaymentType paymentType;

    public enum PaymentType {
        DIFFERENTIATED,
        ANNUITY
    }

    public Credit(double sum, int time, double percents, PaymentType paymentType){
        this.sum = sum;
        this.time = time;
        this.percents = percents;
        this.paymentType = paymentType;
    }

    private static int scanInt() {
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

    private static double scanDouble() {
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

    private static int scanString() {
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

    private void setSum(double sum) {
        if (sum > 0){
            this.sum = sum;
        }else{
            System.out.println("Попробуйте еще раз: ");
        }
    }

    private void setSum() {
        System.out.print("Введите сумму долга: ");
        this.sum = scanDouble();
    }

    private void setTime(int time) {
        if (time > 0){
            this.time = time;
        }else{
            System.out.println("Попробуйте еще раз: ");
        }
    }

    private void setTime() {
        System.out.print("Введите целое положительное кол-во месяцев: ");
        this.time = scanInt();
    }

    private void setPercents(double percents) {
        if (percents > 0){
            this.percents = percents;
        }else{
            System.out.println("Попробуйте еще раз: ");
        }
    }

    private void setPercents() {
        System.out.print("Введите проценты по кредиту: ");
        this.percents = scanDouble();
    }

    private void setPaymentType(PaymentType paymentType) {
        if (paymentType.equals(PaymentType.ANNUITY)){
            this.paymentType = PaymentType.ANNUITY;
        } else if (paymentType.equals(PaymentType.DIFFERENTIATED)) {
            this.paymentType = PaymentType.DIFFERENTIATED;
        }else {
            System.out.println("Попробуйте еще раз: ");
        }
    }

    private void setPaymentType() {
        System.out.print("Введите тип кредита(dif, если дифференцированный и an, если аннуитетный): ");

        int inputType = scanString();

        PaymentType type;

        if (inputType == 0){
            type = PaymentType.ANNUITY;
        }else{
            type = PaymentType.DIFFERENTIATED;
        }

        this.paymentType = type;
    }

    public double getSum() {
        return sum;
    }

    public int getTime() {
        return time;
    }

    public double getPercents() {
        return percents;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    private double calculateMonthlyPayment(int monthNumber) {
        double monthlyPayment = 0.0;

        switch (paymentType) {
            case DIFFERENTIATED:
                monthlyPayment = calculateDifferentiatedPayment(monthNumber);
                break;
            case ANNUITY:
                monthlyPayment = calculateAnnuityPayment();
                break;
        }

        return monthlyPayment;
    }

    private double calculateTotalPayment() {
        double totalPayment = 0.0;

        switch (paymentType) {
            case DIFFERENTIATED:
                for (int i = 1; i <= time; i++) {
                    totalPayment += calculateDifferentiatedPayment(i);
                }
                break;
            case ANNUITY:
                double monthlyPayment = calculateAnnuityPayment();
                totalPayment = monthlyPayment * time;
                break;
        }

        return totalPayment;
    }

    private double calculateDifferentiatedPayment(int monthNumber) {
        double ost = sum;

        for (int i=1; i < monthNumber; i++){
            ost -= sum / time + ost * percents / 100 / 12;
        }

        double perc = ost * percents / 100 / 12.0;
        double debt = sum / time;

        return Math.round(perc + debt);
    }

    private double calculateAnnuityPayment() {
        double monthlyInterestRate = percents / 100 / 12;
        double annuity = sum * (monthlyInterestRate + monthlyInterestRate / (Math.pow(1 + monthlyInterestRate, time) - 1));

        return Math.round(annuity);
    }

    public void getCredit(Credit credit){

        credit.setSum();
        credit.setTime();
        credit.setPercents();
        credit.setPaymentType();

        if(getPaymentType().equals(Credit.PaymentType.DIFFERENTIATED)) {
            System.out.print("Введите месяц для расчета долга: ");
            System.out.println("Сумма выплат за месяц: " + calculateMonthlyPayment(scanInt()));
        }else {
            System.out.println("Сумма выплат за месяц: " + calculateAnnuityPayment());
        }

        System.out.print("Общая сумма выплат: ");
        System.out.println(calculateTotalPayment());
    }
}