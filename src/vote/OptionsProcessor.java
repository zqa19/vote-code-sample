package vote;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class OptionsProcessor {
    private ArrayList<Option> options;
    
    private ArrayList<String> read(String fileName) {
        ArrayList<String> optionNames = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String optionName;
            while ((line = bufferedReader.readLine()) != null) {
                optionName = line.trim();
                if (!optionName.isEmpty()) {
                    optionNames.add(optionName);
                }
            }
        } catch(IOException e) {
            System.out.print(e.getMessage());
        }
        
        return optionNames;
    }
    
    public OptionsProcessor() {
        this.options = new ArrayList<Option>();
    }
    
    public void listOptions() {
       for (Option option : this.options) {
          System.out.printf("%s. %s\n", option.getPrefix(), option.getName());
       }      
    }
   
    public ArrayList<Option> getOptionsFromFile(String fileName) {
       ArrayList<Option> options = new ArrayList<Option>();
      
       // Assign prefix and save the option
       Character prefix = 'A';
       for (String optionName : this.read(fileName)) {
          Option option = new Option(String.valueOf(prefix++), optionName);
          options.add(option);
          if (prefix > 'Z') {
              System.out.println("\nWarning: Only 26 options are accepted" +
          " and the remaining is discarded for the current vote.\n");
              break;
          }
       }
      
       return options;
    }
    
    public Boolean validateOptions() {
        if (this.options.size() < 2) {
            System.out.println("There must be more than 1 option to start voting.");
            return false;
        }
        return true;
    }

    public ArrayList<String> getAllPrefixes() {
        ArrayList<String> prefixes = new ArrayList<String>(); 
        for (Option option : this.options) {
            prefixes.add(option.getPrefix());
        }
        
        return prefixes;
    }
    
    public String getOptionNameByPrefix(String prefix) {
        // Transfer character to index and get the option name. E.g. A to 0, B to 1, etc. 
        return this.options.get(Character.getNumericValue(prefix.charAt(0)) - 10).getName();
    }
    
    public void setOptions(ArrayList<Option> options) {
        this.options = options;    
    }
    
    public ArrayList<Option> getAllOptions() {
        return this.options;
    }
}
