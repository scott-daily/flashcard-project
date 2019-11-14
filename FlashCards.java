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

            if (input.equals("exit")) {
                exit = true;
                System.out.println("Bye bye!");
                break;
            }

            if (input.equals("ask")) {
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
                    if (cardMap.containsKey(tempAnswer) && !correct) {
                            System.out.println("Wrong answer. The correct one is \"" + keysArray.get(indexOfRandomValue) +
                                        "\", you've just written the definition of \"" + cardMap.get(tempAnswer) + "\".");
                            askCount--;
                            }
                    if (!cardMap.containsKey(tempAnswer) && !correct) {
                        System.out.println("Wrong answer. The correct one is \"" + keysArray.get(indexOfRandomValue) + "\"");
                        askCount--;
                    }
                }
            }

            if (input.equals("remove")) {
                /* To Do
                
                > remove
                The card:
                > France
                The card has been removed.

                Input the action (add, remove, import, export, ask, exit):
                > remove
                The card:
                > Wakanda
                Can't remove "Wakanda": there is no such card.
                */
            }
        }
    }
}