import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FlashCards {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map<String, String> cardMap = new HashMap<String, String>();
        List<String> consoleLog = new LinkedList<String>();

        String input = "";
        boolean exit = false;

        while (!exit) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            consoleLog.add("Input the action (add, remove, import, export, ask, exit):");
            input = scanner.nextLine();
            consoleLog.add(input);

            if (input.equals("exit")) {
                exit = true;
                System.out.println("Bye bye!");
                consoleLog.add("Bye bye!");
                break;
            }

            if (input.equals("add")) {
                System.out.println("The card:");
                consoleLog.add("The card:");
                String tempKey = scanner.nextLine();
                consoleLog.add(tempKey);
                String tempValue = "";
                boolean dupeItem = false;

                if (cardMap.containsKey(tempKey)) {
                    System.out.println("The card \"" + tempKey + "\" already exists.");
                    consoleLog.add("The card \"" + tempKey + "\" already exists.");
                    dupeItem = true;
                }

                if (!dupeItem) {
                    System.out.println("The definition of the card:");
                    consoleLog.add("The definition of the card:");
                    tempValue = scanner.nextLine();
                    consoleLog.add(tempValue);
                }

                if (cardMap.containsValue(tempValue) && !dupeItem) {
                    System.out.println("The definition \"" + tempValue + "\" already exists.");
                    consoleLog.add("The definition \"" + tempValue + "\" already exists.");
                    dupeItem = true;
                }

                if (!dupeItem) {
                    cardMap.put(tempKey, tempValue);
                    System.out.println("The pair (" + "\"" + tempKey + "\":" + "\"" + tempValue + "\") has been added.");
                    consoleLog.add("The pair (" + "\"" + tempKey + "\":" + "\"" + tempValue + "\") has been added.");
                }
            }

            if (input.equals("ask")) {

                if (cardMap.isEmpty()) {
                    System.out.println("Can't use ask, there are no cards yet.");
                    consoleLog.add("Can't use ask, there are no cards yet.");
                }
                else {
                    System.out.println("How many times to ask?");
                    consoleLog.add("How many times to ask?");
                    int askCount = scanner.nextInt();
                    consoleLog.add(String.valueOf(askCount));
                    scanner.nextLine();

                    while (askCount > 0) {
                        boolean correct = false;
                        List<String> keysArray = new ArrayList<String>(cardMap.keySet());
                        List<String> valuesArray = new ArrayList<String>(cardMap.values());

                        Random random = new Random();
                        String randomKey = cardMap.get(keysArray.get(random.nextInt(valuesArray.size())));
                        int indexOfRandomValue = valuesArray.indexOf(randomKey);

                        System.out.println("Print the definition of \"" + keysArray.get(indexOfRandomValue) + "\":");
                        consoleLog.add("Print the definition of \"" + keysArray.get(indexOfRandomValue) + "\":");
                        String tempAnswer = scanner.nextLine();
                        consoleLog.add(tempAnswer);

                        if (valuesArray.get(indexOfRandomValue).equals(tempAnswer)) {
                            System.out.println("Correct answer.");
                            consoleLog.add("Correct answer.");
                            askCount--;
                            correct = true;
                        }
                        else if (cardMap.containsValue(tempAnswer) && !correct) {
                            String defFor = "";
                            for (Map.Entry<String, String> entry : cardMap.entrySet()) {
                                if (Objects.equals(tempAnswer, entry.getValue())) {
                                    defFor = entry.getKey();
                                }
                            }
                            System.out.println("Wrong answer. The correct one is \"" + valuesArray.get(indexOfRandomValue) +
                                    "\", you've just written the definition of \"" + defFor + "\".");
                            consoleLog.add("Wrong answer. The correct one is \"" + valuesArray.get(indexOfRandomValue) +
                            "\", you've just written the definition of \"" + defFor + "\".");
                            askCount--;
                        }
                        else  {
                            System.out.println("Wrong answer. The correct one is \"" + valuesArray.get(indexOfRandomValue) + "\"");
                            consoleLog.add("Wrong answer. The correct one is \"" + valuesArray.get(indexOfRandomValue) + "\"");
                            askCount--;
                        }
                    }
                }
            }

            if (input.equals("remove")) {

                System.out.println("The card:");
                consoleLog.add("The card:");
                String tempKey = scanner.nextLine();
                consoleLog.add(tempKey);

                if (cardMap.containsKey(tempKey)) {
                    cardMap.remove(tempKey);
                    System.out.println("The card has been removed.");
                    consoleLog.add("The card has been removed.");
                }
                else {
                    System.out.println("Can't remove " + "\"" + tempKey + "\": there is no such card.");
                    consoleLog.add("Can't remove " + "\"" + tempKey + "\": there is no such card.");
                }
            }

            if (input.equals("export")) {

                System.out.println("File name:");
                consoleLog.add("File name:");
                String fileName = scanner.nextLine();
                consoleLog.add(fileName);
                File file = new File(fileName);
                try (PrintWriter printWriter = new PrintWriter(file)) {
                    for (Map.Entry<String, String> entry : cardMap.entrySet()) {
                        printWriter.println(entry.getKey());
                        printWriter.println(entry.getValue());
                    }
                    System.out.println(cardMap.size() + " cards have been saved.");
                    consoleLog.add(cardMap.size() + " cards have been saved.");
                } catch (IOException e) {
                    System.out.printf("An exception occurs %s", e.getMessage());
                    consoleLog.add("An exception occurs " + e.getMessage());
                }
            }

            if (input.equals("import")) {

                System.out.println("File name:");
                consoleLog.add("File name:");
                String fileName = scanner.nextLine();
                consoleLog.add(fileName);
                File file = new File(fileName);

                boolean notFound = false;
                int count = 0;
                try {
                    count = (int) Files.lines(Paths.get(fileName)).count();
                } catch (IOException ioe) {
                    System.out.println("File not found.");
                    consoleLog.add("File not found.");
                    notFound = true;
                }

                if (!notFound) {
                    int loaded = count;
                    try (Scanner fileScanner = new Scanner(file)) {
                        while (count > 0) {
                            cardMap.put(fileScanner.nextLine(), fileScanner.nextLine());
                            count -= 2;
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("No file found: " + fileName);
                        consoleLog.add("No file found: " + fileName);
                    }
                    System.out.println(loaded / 2 + " cards have been loaded.");
                    consoleLog.add(loaded / 2 + " cards have been loaded.");
                }
            }

            if (input.equals("log")) {
                System.out.println("File name:");
                consoleLog.add("File name:");

                String fileName = scanner.nextLine();
                consoleLog.add(fileName);
                File file = new File(fileName);

                try (PrintWriter printWriter = new PrintWriter(file)) {
                    for (String io : consoleLog) {
                        printWriter.println(io);
                    }
                    System.out.println("The log has been saved.");
                    consoleLog.add("The log has been saved.");
                } catch (IOException e) {
                    System.out.printf("An exception occurs %s", e.getMessage());
                    consoleLog.add("An exception occurs " + e.getMessage());
                }
            }
        }
    }
}