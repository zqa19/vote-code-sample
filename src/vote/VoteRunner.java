package vote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class VoteRunner {
    private static Boolean validateUserInput(String userInput, ArrayList<String> prefixes) {
        if (userInput.length() == 0) {
            System.out.println("Ballot discarded due to empty input.\n");
            return false;
        }
        
        ArrayList<String> existingPreferences = new ArrayList<String>();
        for (char choice : userInput.toCharArray()) {
            String preference = String.valueOf(choice);
            if (existingPreferences.contains(preference) ||
                    !prefixes.contains(preference)) {
                System.out.printf(
                        "Ballot discarded due to duplicate or invalid preference: %s\n\n",
                        preference);
                return false;
            }
            existingPreferences.add(preference);
        }
      
        return true;
    }
    
    private static void collectBallotsFromUserInput(
            OptionsProcessor optionsProcessor,
            BallotsProcessor ballotsProcessor,
            String []arguments) throws IOException {
        String userInput = "";
        ArrayList<String> prefixes = optionsProcessor.getAllPrefixes();
        Scanner scanner = new Scanner(System.in);
        Integer i = 0;
        while (true) {
           optionsProcessor.listOptions();
           System.out.printf(">");         
           
           if (arguments == null) {
               // Get user input from console if not provided
               userInput = scanner.hasNextLine() ? scanner.nextLine() : "";
           } else {
               userInput = arguments[i++];
               System.out.println(userInput);
           }
           System.out.println();
           // Format user input in upper case without spaces
           userInput = userInput.toUpperCase().replaceAll("\\s+", "");
           
           if (userInput.equals("TALLY")) {
               if (ballotsProcessor.getAllBallots().size() == 0) {
                   System.out.println("Please enter ballots.\n");
                   continue;
               }
               System.out.println("Vote counting started.");
               break;
           } else if (validateUserInput(userInput, prefixes)) {
               ballotsProcessor.addBallot(userInput);
           };
        }
        scanner.close();
    }
   
    public static void processFile(String fileName, String []args) throws IOException {
        OptionsProcessor optionsProcessor = new OptionsProcessor();
        BallotsProcessor ballotsProcessor = new BallotsProcessor();
        RoundsProcessor roundsProcessor = new RoundsProcessor(optionsProcessor, ballotsProcessor);
        optionsProcessor.setOptions(optionsProcessor.getOptionsFromFile(fileName));
        if (!optionsProcessor.validateOptions()) {
            return;
        }
        collectBallotsFromUserInput(optionsProcessor, ballotsProcessor, args);
        roundsProcessor.startCounting();
        
        String winnerPrefix = roundsProcessor.getWinnerPrefix();
        System.out.printf(
                "\nCandidate \"%s. %s\" selected as winner.\n",
                winnerPrefix,
                optionsProcessor.getOptionNameByPrefix(winnerPrefix));
    }
    
    public static void main(String []args) throws IOException {
       String classPath = VoteRunner.class.getProtectionDomain().
               getCodeSource().getLocation().getPath();
       String fileName = args.length == 0 ? classPath + "vote/Options" : args[0];
       // Allow ballots as arguments to facilitate behavioural tests
       processFile(
               fileName,
               args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : null);
    }
}
