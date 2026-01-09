import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserLogin {

    private static final String filePath = "src\\Data\\Login.txt";
    private static final File loginFile = new File(filePath);
    private static final String fieldSeparater = "`";
    private static ArrayList <String> userNames = new ArrayList<>();
    private static ArrayList <String> passwords = new ArrayList<>();
    private static ArrayList <String> status = new ArrayList<>();


    public static String login(){
        createFileIfNotFound();

        if(!checkLogins()) {
            createAccount("admin");
        } else {
            loadLogins();
        }

        String status = confirmLogin();

        if (status.equalsIgnoreCase("admin")) {
            return "admin";
        } else if (status.equalsIgnoreCase("worker")) {
            return  "worker";
        } else if (status.equalsIgnoreCase("requester")) {
            return "requester";
        }
        return "null";
    }

    private static void createFileIfNotFound(){
        if (!loginFile.exists()){
            try {
                loginFile.getParentFile().mkdirs();
                loginFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static boolean checkLogins(){
        boolean flag = false;
        try {
            FileReader fr = new FileReader(filePath);
            Scanner fileReader = new Scanner(fr);
            if (fileReader.hasNext()){
                flag = true;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

    private static void loadLogins(){
        userNames.clear();
        passwords.clear();
        status.clear();

        try {
            FileReader fr = new FileReader(filePath);
            Scanner fileReader = new Scanner(fr);
            int i = 0;
            while (fileReader.hasNext()) {
                String line = fileReader.nextLine();
                String[] logins = line.split(fieldSeparater);
                userNames.add(i,logins[0]);
                passwords.add(i,logins[1]);
                status.add(i,logins[2]);
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createAccount(String status){
        loadLogins();

        Scanner input = new Scanner(System.in);
        boolean flag = true;
        System.out.println("\n=== Create new account ===");

        try {
            FileWriter writer = new FileWriter(filePath, true);

            while (flag) {
                System.out.print("Enter your username: ");
                String userName = input.nextLine();

                if (userName.isEmpty()) {
                    System.out.println("Username cannot be empty\n");
                } else if (userName.contains(fieldSeparater)) {
                    System.out.println("Username cannot contain \"" + fieldSeparater + "\"\n");
                } else if (userNames.contains(userName)) {
                    System.out.println("Username already exists\n");
                } else {
                    while (true) {
                        System.out.print("Enter your password: ");
                        String password = input.nextLine();

                        if (password.isEmpty()) {
                            System.out.println("Password cannot be empty\n");
                        } else if (password.contains(fieldSeparater)) {
                            System.out.println("Invalid password: password cannot contain \"" + fieldSeparater + "\"\n");
                        } else {
                            System.out.println("Account created successfully!");
                            writer.write(userName + fieldSeparater + password + fieldSeparater + status + "\n");
                            writer.close();
                            flag = false;
                            break;
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
        loadLogins();
    }

    private static String confirmLogin() {

        Scanner input = new Scanner(System.in);
        String returnStatus;

        System.out.println("============ Login ============");

        while (true) {
            System.out.print("Enter Username: ");
            String username = input.nextLine();

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty\n");
                continue;
            }

            System.out.print("Enter Password: ");
            String password = input.nextLine();

            if (password.isEmpty()) {
                System.out.println("Password cannot be empty\n");
                continue;
            }

            if (userNames.contains(username)) {
                int index = userNames.indexOf(username);
                if (password.equals(passwords.get(index))) {
                    returnStatus = status.get(index);
                    System.out.println("Login successful!\n");
                    break;
                } else {
                    System.out.println("Incorrect username or password\n");
                }
            } else {
                System.out.println("Incorrect username or password\n");
            }

        }
        return returnStatus;
    }

    public static void deleteLogin(){
        loadLogins();

        Scanner input = new Scanner(System.in);
        boolean flag = true;

        while(flag) {
            System.out.print("Enter the username of the account you want to delete: ");
            String username = input.next();

            if (userNames.contains(username)) {
                int delIndex = userNames.indexOf(username);
                while (true) {
                    System.out.print("Enter password to confirm deletion: " );
                    String password = input.next();
                    if (passwords.get(delIndex).equals(password)){
                        userNames.remove(delIndex);
                        passwords.remove(delIndex);
                        status.remove(delIndex);
                        flag = false;
                        break;

                    } else {
                        System.out.println("Incorrect password\n");
                    }
                }
            } else {
                System.out.println("Incorrect username\n");
            }
        }
        saveLogins();
    }

    private static void saveLogins(){
        try {
            FileWriter writer = new FileWriter(filePath);
            for (int i = 0; i < userNames.size(); i++) {
                writer.write(userNames.get(i) + fieldSeparater + passwords.get(i) + fieldSeparater + status.get(i) + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateLogin(){
        loadLogins();

        Scanner input = new Scanner(System.in);
        boolean flag = true;

        while(flag) {
            System.out.print("Enter the username of the account you want to update: ");
            String username = input.next();

            if (userNames.contains(username)) {
                int updateIndex = userNames.indexOf(username);
                boolean flag1 = true;
                while (flag1) {

                    System.out.println("1. Change username\n2. Change password\n");
                    byte choice = input.nextByte();

                    switch (choice) {
                        case 1:
                            boolean flag2 = true;
                            while (flag2) {
                                System.out.print("Enter new username: ");
                                String newUsername = input.next();
                                if (!newUsername.contains(fieldSeparater)) {
                                    if (!userNames.contains(newUsername)) {
                                        while (true) {
                                            System.out.print("Enter password to confirm: ");
                                            String password = input.next();
                                            if (passwords.get(updateIndex).equals(password)) {
                                                userNames.set(updateIndex, newUsername);
                                                flag = false;
                                                flag1 = false;
                                                flag2 = false;
                                                break;

                                            } else {
                                                System.out.println("Incorrect password\n");
                                            }
                                        }
                                    } else {
                                        System.out.println("Username already exists\n");
                                    }
                                } else {
                                    System.out.println("Username cannot contain \" " + fieldSeparater + " \"\n");
                                }
                            }
                            break;
                        case 2:
                            boolean flag3 = true;
                            while (flag3) {
                                System.out.print("Enter new password: ");
                                String newPassword = input.next();
                                if (!newPassword.contains(fieldSeparater)) {
                                    while (true) {
                                        System.out.print("Enter previous password to confirm: ");
                                        String password = input.next();
                                        if (passwords.get(updateIndex).equals(password)) {
                                            passwords.set(updateIndex, newPassword);
                                            flag = false;
                                            flag1 = false;
                                            flag3 = false;
                                            break;

                                        } else {
                                            System.out.println("Incorrect previous password\n");
                                        }
                                    }
                                } else {
                                    System.out.println("Password cannot contain \" " + fieldSeparater + " \"\n");
                                }
                            }
                            break;
                        default:
                            System.out.println("Invalid input\n");
                    }
                }
            } else {
                System.out.println("Incorrect username\n");
            }
        }
        saveLogins();
    }
}