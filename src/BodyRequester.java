import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class BodyRequester {
    private static final String filePath = "src\\Data\\Requests.txt";
    private static final File requestsFile = new File(filePath);
    private static final String fieldSeparater = "`";
    private static ArrayList<String> guardians = new ArrayList<>();
    private static ArrayList<String> ids = new ArrayList<>();
    private static ArrayList<String> names = new ArrayList<>();
    private static ArrayList<String> dateOfDeaths = new ArrayList<>();
    private static ArrayList<String> causeOfDeaths = new ArrayList<>();
    private static ArrayList<String> postmortemStatus = new ArrayList<>();
    private static ArrayList<String> postmortemResult = new ArrayList<>();


    private static void createFileIfNotFound() {
        if (!requestsFile.exists()) {
            try {
                requestsFile.getParentFile().mkdirs();
                requestsFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static boolean checkRequests() {
        boolean flag = false;
        try {
            FileReader fr = new FileReader(filePath);
            Scanner fileReader = new Scanner(fr);
            if (fileReader.hasNext()) {
                flag = true;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

    private static void loadRequests() {
        createFileIfNotFound();
        guardians.clear();
        ids.clear();
        names.clear();
        dateOfDeaths.clear();
        causeOfDeaths.clear();
        postmortemStatus.clear();
        postmortemResult.clear();

        try {
            FileReader fr = new FileReader(filePath);
            Scanner fileReader = new Scanner(fr);
            int i = 0;
            while (fileReader.hasNext()) {
                String[] requests = fileReader.nextLine().split(fieldSeparater);
                guardians.add(i,requests[0]);
                ids.add(i, requests[1]);
                names.add(i, requests[2]);
                dateOfDeaths.add(i, requests[3]);
                causeOfDeaths.add(i, requests[4]);
                postmortemStatus.add(i, requests[5]);
                postmortemResult.add(i, requests[6]);
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String validateInput(String prompt) {
        Scanner scan = new Scanner(System.in);
        String input;
        while (true) {
            System.out.print(prompt);
            input = scan.nextLine();
            if (input.contains(fieldSeparater)) {
                System.out.println("Input cannot contain \" " + fieldSeparater + " \"\n");
            } else if (input.isEmpty()) {
                System.out.println("Input cannot be empty\n");
            } else {
                break;
            }
        }
        return input;
    }

    static int validateDate(String prompt, int min, int max, String fieldName) {
        Scanner input = new Scanner(System.in);

        int value = 0;
        boolean flag = true;

        System.out.print(prompt);
        while (flag) {
            if (input.hasNextLine()) {
                String userInput = input.nextLine();

                if (userInput.isEmpty()) {
                    System.out.println("Empty input. Please enter a valid number.");
                    System.out.print(prompt);
                    continue;
                }
                try {
                    value = Integer.parseInt(userInput);
                    if (value >= min && value <= max) {
                        flag = false;
                    } else {
                        System.out.println("Invalid " + fieldName + ". Please enter a number between " + min + "-" + max + ".");
                        System.out.print(prompt);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    System.out.print(prompt);
                }
            }
        }
        return value;
    }

    public static void makeRequest() {
        loadRequests();

        System.out.println("\n=== Make a request ===");
        try {
            FileWriter writer = new FileWriter(filePath, true);
            String guardian = validateInput("Enter the name of the deceased guardian: ");
            String id;
            while (true) {
                id = validateInput("Enter deceased person's ID: ");
                if (ids.contains(id)) {
                    System.out.println("ID already exists\n");
                } else {
                    break;
                }
            }
            String name = validateInput("Enter deceased person's full name: ");

            String dateOfdeath;
            System.out.println("Enter the date of death: ");
            int day = validateDate("Enter the day: ", 1, 31, "day");
            int month = validateDate("Enter the month: ", 1, 12, "month");
            int year = validateDate("Enter the year: ", 1900, 2025, "year");
            dateOfdeath = day + "/" + month + "/" + year;


            String causeOfDeath = validateInput("Enter the cause of death: ");
            writer.write(guardian + fieldSeparater + id + fieldSeparater + name + fieldSeparater + dateOfdeath + fieldSeparater + causeOfDeath + fieldSeparater + "false" + fieldSeparater + "\u200E\n");
            writer.close();
            System.out.println("\nRequest made");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        loadRequests();
    }

    public static void viewRequests() {
        loadRequests();
        if (checkRequests()) {

            Scanner input = new Scanner(System.in);
            boolean flag = true;

            while (flag) {
                System.out.println("======================================== Body Requests ========================================");
                System.out.printf("%-20s %-10s %-20s %-20s %-30s%n","Guardian", "ID", "Name", "Date of Death", "Cause of Death");
                System.out.println("===============================================================================================");
                for (int i = 0; i < ids.size(); i++) {
                    System.out.printf("%-20s %-10s %-20s %-20s %-30s%n",guardians.get(i), ids.get(i), names.get(i), dateOfDeaths.get(i), causeOfDeaths.get(i));
                }
                System.out.print("\nEnter id of deceased: ");
                String id = input.nextLine();
                if (!id.isEmpty()) {
                    if (ids.contains(id)) {
                        int index = ids.indexOf(id);

                        if (!postmortemStatus.get(index).contains("rejected")) {
                            if (postmortemStatus.get(index).contains("accepted")) {
                                System.out.println("\nBody accepted, waiting for autopsy results");
                                System.out.println();
                                flag = false;
                            }
                            if (postmortemStatus.get(index).contains("completed")) {
                                System.out.println("Autopsy results: " + postmortemResult.get(index));
                                System.out.println();
                                flag = false;
                            }
                            if (postmortemStatus.get(index).contains("returned")) {
                                System.out.println("Autopsy results : " + postmortemResult.get(index));
                                System.out.println("\nBody also returned");
                                System.out.println();
                                flag = false;
                            }
                            if (postmortemStatus.get(index).contains("false")) {
                                System.out.println("\nWaiting for a mortician's response");
                                System.out.println();
                                flag = false;
                            }
                        } else {
                            System.out.println("Request has been rejected");
                            System.out.println();
                            flag = false;
                        }
                    } else {
                        System.out.println("ID not found\n");
                    }
                } else {
                    System.out.println("ID cannot be empty\n");
                }
            }

        } else {
            System.out.println("No data available");
        }
    }
}