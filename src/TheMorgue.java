import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TheMorgue {
    private static final String filePath = "src\\Data\\Requests.txt";
    private static final File bodiesFile = new File(filePath);
    private static final String fieldSeparater = "`";
    private static ArrayList<String> guardians = new ArrayList<>();
    private static ArrayList<String> ids = new ArrayList<>();
    private static ArrayList<String> names = new ArrayList<>();
    private static ArrayList<String> dateOfDeaths = new ArrayList<>();
    private static ArrayList<String> causeOfDeaths = new ArrayList<>();
    private static ArrayList<String> postmortemStatus = new ArrayList<>();
    private static ArrayList<String> postmortemResults = new ArrayList<>();
    private static final int coldStorage = 5;




    private static void createFileIfNotFound(){
        if (!bodiesFile.exists()){
            try {
                bodiesFile.getParentFile().mkdirs();
                bodiesFile.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static boolean checkData(){
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

    private static void loadData(){
        createFileIfNotFound();

        guardians.clear();
        ids.clear();
        names.clear();
        dateOfDeaths.clear();
        postmortemStatus.clear();
        postmortemResults.clear();

        try {
            FileReader fr = new FileReader(filePath);
            Scanner fileReader = new Scanner(fr);
            int i = 0;
            while (fileReader.hasNext()) {
                String[] bodyDetails = fileReader.nextLine().split(fieldSeparater);
                guardians.add(i, bodyDetails[0]);
                ids.add(i, bodyDetails[1]);
                names.add(i, bodyDetails[2]);
                dateOfDeaths.add(i, bodyDetails[3]);
                causeOfDeaths.add(i, bodyDetails[4]);
                postmortemStatus.add(i, bodyDetails[5]);
                postmortemResults.add(i, bodyDetails[6]);
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        saveData();
    }

     private static int acceptedBodies(){
        loadData();

        int storage = 0;
        try{
            FileReader fr = new FileReader(filePath);
            Scanner fileReader = new Scanner(fr);
            int i = 0;
            while(fileReader.hasNext()){
                if(i == postmortemStatus.size()){
                    break;
                }
                if(postmortemStatus.get(i).contains("accepted")){
                    storage++;
                }
                if(postmortemStatus.get(i).contains("completed")){
                    storage++;
                }
                i++;
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return storage;
    }


    public static void acceptanceStatus(){
            loadData();

            if(checkData() && postmortemStatus.contains("false") ) {
                ArrayList<String> availableBodies = new ArrayList<>();
                if (acceptedBodies() < coldStorage) {
                    try {
                        FileReader fr = new FileReader(filePath);
                        Scanner fileReader = new Scanner(fr);

                        int i = 0;
                        System.out.println("======================================== Body Requests ========================================");
                        System.out.printf("%-20s %-10s %-20s %-20s %-30s%n","Guardian", "ID", "Name", "Date of Death", "Cause of Death");
                        System.out.println("===============================================================================================");
                        while (fileReader.hasNext()) {
                            if (i == postmortemStatus.size()) {
                                break;
                            }
                            if (postmortemStatus.get(i).contains("false")) {
                                System.out.printf("%-20s %-10s %-20s %-20s %-30s%n",guardians.get(i), ids.get(i), names.get(i), dateOfDeaths.get(i), causeOfDeaths.get(i));
                                availableBodies.add(ids.get(i));
                            }
                            i++;
                        }
                        System.out.println();

                        boolean flag = true;

                        while (flag) {
                            String id = validateInput("Enter ID: ");
                            if (availableBodies.contains(id)) {
                                int index = ids.indexOf(id);
                                String choice = validateInput("0. Accept\n1. Reject\n");
                                switch (choice) {
                                    case "0":
                                        postmortemStatus.set(index, "accepted");
                                        System.out.println("Body accepted");
                                        flag = false;
                                        break;
                                    case "1":
                                        postmortemStatus.set(index, "rejected");
                                        System.out.println("Body rejected");
                                        flag = false;
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                }
                            } else {
                                System.out.println("Invalid ID\n");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Storage not available to accept, return bodies first");
                }
            } else {
                System.out.println("No requests available");
            }
        saveData();
    }

    public static void editData(){
        loadData();
        if(checkData() && postmortemStatus.contains("accepted")) {
            ArrayList<String> availableBodies = new ArrayList<>();
            try {
                FileReader fr = new FileReader(filePath);
                Scanner fileReader = new Scanner(fr);

                int i = 0;
                System.out.println("======================================== Body Requests ========================================");
                System.out.printf("%-20s %-10s %-20s %-20s %-30s%n","Guardian", "ID", "Name", "Date of Death", "Cause of Death");
                System.out.println("===============================================================================================");
                while (fileReader.hasNext()) {
                    if (i == postmortemStatus.size()) {
                        break;
                    }
                    if (postmortemStatus.get(i).contains("accepted")) {
                        availableBodies.add(ids.get(i));
                        System.out.printf("%-20s %-10s %-20s %-20s %-30s%n",guardians.get(i), ids.get(i), names.get(i), dateOfDeaths.get(i), causeOfDeaths.get(i));
                    }
                    i++;
                }
                System.out.println();

                while (true) {
                    String id = validateInput("Enter ID: ");
                    if (availableBodies.contains(id)) {
                        int index = ids.indexOf(id);
                        String postmortemResult = validateInput("Enter the autopsy results: \n");
                        postmortemResults.set(index, postmortemResult);
                        postmortemStatus.set(index, "completed");
                        System.out.println("Autopsy result updated");
                        saveData();
                        break;
                    } else {
                        System.out.println("Invalid ID\n");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("No bodies available\n");
        }
    }

    static void returnBody() {
        loadData();
        if (checkData() && postmortemStatus.contains("completed")) {
            ArrayList<String> availableBodies = new ArrayList<>();

            try {
                FileReader fr = new FileReader(filePath);
                Scanner fileReader = new Scanner(fr);
                int i = 0;
                System.out.println("============================================================ Bodies to be Returned ============================================================");
                System.out.printf("%-20s %-10s %-20s %-20s %-30s %-30s%n","Guardian", "ID", "Name", "Date of Death", "Cause of Death", "Autopsy Results");
                System.out.println("===============================================================================================================================================");
                while (fileReader.hasNext()) {
                    if (i == postmortemStatus.size()) {
                        break;
                    }
                    if (postmortemStatus.get(i).contains("completed")) {
                        System.out.printf("%-20s %-10s %-20s %-20s %-30s %-30s%n",guardians.get(i),ids.get(i) , names.get(i), dateOfDeaths.get(i), causeOfDeaths.get(i),postmortemResults.get(i));
                        availableBodies.add(ids.get(i));
                    }
                    i++;
                }
                System.out.println();
                while (true) {
                    String id = validateInput("Enter ID: ");
                    if (availableBodies.contains(id)) {
                        int index = ids.indexOf(id);
                        postmortemStatus.set(index, "returned");
                        System.out.println("Body returned\n");
                        saveData();
                        break;
                    } else {
                        System.out.println("Invalid ID\n");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("No bodies available to return\n");
        }

    }

    private static void saveData(){
        try {
            FileWriter writer = new FileWriter(filePath);
            for (int i = 0; i < ids.size(); i++) {
                writer.write(guardians.get(i) + fieldSeparater + ids.get(i) + fieldSeparater + names.get(i) + fieldSeparater + dateOfDeaths.get(i) + fieldSeparater + causeOfDeaths.get(i) + fieldSeparater + postmortemStatus.get(i) + fieldSeparater + postmortemResults.get(i) + "\n");
            }
            writer.close();
        } catch (Exception e) {
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
}