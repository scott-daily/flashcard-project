import java.util.*;
import java.lang.Math; 

public class FlashCards {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map<String, String> cardMap = new HashMap<String, String>();

        String input = "";
        boolean exit = false;
       
        while (!exit) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            input = scanner.nextLine();

            if (input.equals("exit")) {
                exit = true;
                System.out.println("Bye bye!");
                break;
            }

            if (input.equals("add")) {
                System.out.println("The card:");
                String tempKey = scanner.nextLine();

                if (cardMap.containsKey(tempKey)) {
                    System.out.println("The card \"" + tempKey + "\" already exists.");
                    break;
                }

                System.out.println("The definition of the card:");
                String tempValue = scanner.nextLine();

                if (cardMap.containsValue(tempValue)) {
                    System.out.println("The definition \"" + tempValue + "\" already exists.");
                    break;
                }
                cardMap.put(tempKey, tempValue);
                System.out.println("The pair (" + "\"" + tempKey + "\":" + "\"" + tempValue + "\") has been added.");
            }

            if (input.equals("ask")) {
                // Don't allow ask if there are no key:value pairs in CardMap yet <------------****
                System.out.println("How many times to ask?");
                int askCount = scanner.nextInt();
                scanner.nextLine();
                while (askCount > 0) {
                    boolean correct = false;
                    List<String> keysArray = new ArrayList<String>(cardMap.keySet());
                    List<String> valuesArray = new ArrayList<String>(cardMap.values());

                    Random random = new Random();
                    String randomKey = cardMap.get(keysArray.get(random.nextInt(keysArray.size())));
                    int indexOfRandomValue = valuesArray.indexOf(randomKey);

                    System.out.println("Print the definition of \"" + randomKey + "\":");
                    String tempAnswer = scanner.nextLine();

                    if (keysArray.get(indexOfRandomValue).equals(tempAnswer)) {
                        System.out.println("Correct answer.");
                        askCount--;
                        correct = true;
                    }
                    else if (cardMap.containsKey(tempAnswer) && !correct) {
                            System.out.println("Wrong answer. The correct one is \"" + keysArray.get(indexOfRandomValue) +
                                        "\", you've just written the definition of \"" + cardMap.get(tempAnswer) + "\".");
                            askCount--;
                            }
                    else  {
                        System.out.println("Wrong answer. The correct one is \"" + keysArray.get(indexOfRandomValue) + "\"");
                        askCount--;
                    }
                }
            }

            if (input.equals("remove")) {

                System.out.println("The card:");
                String tempKey = scanner.nextLine();
                
                if (cardMap.containsKey(tempKey)) {
                    cardMap.remove(tempKey);
                    System.out.println("The card has been removed.");
                }
                else {
                    System.out.println("Can't remove " + "\"" + tempKey + "\": there is no such card.");
                }
            }
        }
    }
}