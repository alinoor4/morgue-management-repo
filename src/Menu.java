import java.util.Scanner;

public class Menu {
    public static void loginMenu() {
        Scanner input = new Scanner(System.in);
        boolean mainMenuRunning = true;

        while (mainMenuRunning) {
            String status = UserLogin.login();

            switch (status) {
                case "admin":
                    while (true) {
                        System.out.println("========== Admin ==========");
                        System.out.println("1. User Settings");
                        System.out.println("2. Morgue ");
                        System.out.println("3. Logout");
                        System.out.println("4. Exit Program");
                        byte choice = input.nextByte();
                        switch (choice) {
                            case 1:
                                adminLoggedInMenu(input);
                                break;
                            case 2:
                                adminMorgueMenu(input);
                                break;
                            case 3:
                                System.out.println("Logging out...");
                                break;
                            case 4:
                                input.close();
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid input");
                        }
                        if (choice == 3) break;
                    }
                    break;

                case "worker":
                    while (true) {
                        System.out.println("========== Worker ==========");
                        System.out.println("1. Change user details");
                        System.out.println("2. Accept or Reject Requests");
                        System.out.println("3. Autopsy Update");
                        System.out.println("4. Return Body");
                        System.out.println("5. Logout");
                        System.out.println("6. Exit program");
                        byte choice1 = input.nextByte();
                        switch (choice1) {
                            case 1:
                                UserLogin.updateLogin();
                                break;
                            case 2:
                                TheMorgue.acceptanceStatus();
                                break;
                            case 3:
                                TheMorgue.editData();
                                break;
                            case 4:
                                TheMorgue.returnBody();
                                break;
                            case 5:
                                System.out.println("Logging out...");
                                break;
                            case 6:
                                input.close();
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid input");
                        }
                        if (choice1 == 5) break;
                    }
                    break;

                case "requester":
                    while (true) {
                        System.out.println("========== Requester ==========");
                        System.out.println("1. Change user details");
                        System.out.println("2. Make Request");
                        System.out.println("3. View Request");
                        System.out.println("4. Logout");
                        System.out.println("5. Exit Program");
                        byte choice2 = input.nextByte();
                        switch (choice2) {
                            case 1:
                                UserLogin.updateLogin();
                                break;
                            case 2:
                                BodyRequester.makeRequest();
                                break;
                            case 3:
                                BodyRequester.viewRequests();
                                break;
                            case 4:
                                System.out.println("Logging out...");
                                break;
                            case 5:
                                input.close();
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Invalid input");
                        }
                        if (choice2 == 4) break;
                    }
                    break;

                default:
                    System.out.println("Login failed or invalid user type.");
                    mainMenuRunning = false;
                    break;
            }
        }
    }

    static void adminLoggedInMenu(Scanner input) {
        boolean adminLoginRunning = true;

        while (adminLoginRunning) {
            System.out.println("1. Create new user");
            System.out.println("2. Update details of a user");
            System.out.println("3. Delete a user");
            System.out.println("4. Return to main menu");

            byte choice = input.nextByte();
            switch (choice) {
                case 1:
                    AdminLogin.createAccounts();
                    break;
                case 2:
                    UserLogin.updateLogin();
                    break;
                case 3:
                    UserLogin.deleteLogin();
                    break;
                case 4:
                    adminLoginRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void adminMorgueMenu(Scanner input) {
        boolean adminMorgueRunning = true;

        while (adminMorgueRunning) {
            System.out.println("1. Accept or Reject Requests");
            System.out.println("2. Autopsy Update");
            System.out.println("3. Return Body");
            System.out.println("4. Make Request");
            System.out.println("5. View Request");
            System.out.println("6. Return to main menu");

            byte choice = input.nextByte();
            switch (choice) {
                case 1:
                    TheMorgue.acceptanceStatus();
                    break;
                case 2:
                    TheMorgue.editData();
                    break;
                case 3:
                    TheMorgue.returnBody();
                    break;
                case 4:
                    BodyRequester.makeRequest();
                    break;
                case 5:
                    BodyRequester.viewRequests();
                    break;
                case 6:
                    adminMorgueRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}