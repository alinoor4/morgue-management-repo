import java.util.Scanner;

public class AdminLogin{
    static void createAccounts(){
        while(true) {
            System.out.println("Enter the type of account you want to create admin, worker or a requester account");
            Scanner input = new Scanner(System.in);
            String status = input.nextLine().toLowerCase();
            if (status.equalsIgnoreCase("admin") || status.equalsIgnoreCase("worker") || status.equalsIgnoreCase("requester")) {
                UserLogin.createAccount(status);
                break;
            } else {
                System.out.println("Invalid type of account");
            }
        }
    }
}