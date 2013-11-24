/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 24.11.13
 * Time: 20:06
 * To change this template use File | Settings | File Templates.
 */

import java.util.Scanner;

/**
 * @author Evgeniy Tucha
 */
public class Main {
    public static void main(String[] args) {
        System.out
                .println("Press '1' if you want to worked with console version, else press '2'\n"
                        + "Please enter your choise: ");
        Scanner scan = new Scanner(System.in);
        int choise = scan.nextInt();
        if (choise == 1) {
            try {
                System.out.println("Please enter your expression: ");
                Scanner sc = new Scanner(System.in);
                String data = sc.nextLine();
                System.out.println(Calculator.calculate(data));
            } catch (IllegalArgumentException e) {
                System.err.println("Wrong string format!");
                e.printStackTrace();
            }
        } else
            Calculator.Window();
    }
}
