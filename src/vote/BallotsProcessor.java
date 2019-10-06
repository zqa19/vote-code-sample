package vote;

import java.util.ArrayList;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class BallotsProcessor {
    private ArrayList<Ballot> ballots;
    
    public BallotsProcessor() {
        this.ballots = new ArrayList<Ballot>();
    }
    
    public void addBallot(String userInput) {
        Ballot ballot = new Ballot(this.ballots.size() + 1, userInput);
        this.ballots.add(ballot);
    }
    
    public ArrayList<Ballot> getAllBallots() {
        return this.ballots;
    }
    
    public ArrayList<String> getPreferencesById(Integer id) {
        return this.ballots.get(id - 1).getPreferences();
    }
}
