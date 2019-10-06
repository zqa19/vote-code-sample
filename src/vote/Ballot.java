package vote;

import java.util.ArrayList;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class Ballot {
    private Integer id;
    
    // the string containing preferences by order. E.g. ACDB
    private String preferenceString;
    private ArrayList<String> preferences;
    
    public Ballot(Integer id, String preferenceString) {
        this.id = id;
        this.setPreferenceString(preferenceString);
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setPreferenceString(String preferenceString) {        
        this.preferenceString = preferenceString;
        
        // Convert String to ArrayList and set preferences
        ArrayList<String> preferences = new ArrayList<String>();        
        for (char prefix : preferenceString.toCharArray()) {
            preferences.add(String.valueOf(prefix));
        }
        this.setPreferences(preferences);
    }
    
    public String getPreferenceString() {
        return this.preferenceString;
    }
    
    public void setPreferences(ArrayList<String> preferences) {
        this.preferences = preferences;
    }
    
    public ArrayList<String> getPreferences() {
        return this.preferences;
    }
}
