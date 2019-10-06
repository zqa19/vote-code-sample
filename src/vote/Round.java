package vote;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class Round {
    private Integer id;
    private Integer quota;
    private HashMap<String, Integer> votes;
    
    // only prefixes of non-eliminated options are stored
    private ArrayList<String> validPrefixes;
    
    // Only non-exhausted ballots are stored where ballot ids are stored as keys
    // and preference indexes starting from 0 are stored as values
    private HashMap<Integer, Integer> ballotStatus;
    
    public Round(Integer id) {
        this.id = id;
        this.votes = new HashMap<String, Integer>();
        this.validPrefixes = new ArrayList<String>();
        this.ballotStatus = new HashMap<Integer, Integer>();
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setQuota(Integer quota) {
        this.quota = quota;
    }
    
    public Integer getQuota() {
        return this.quota;
    }
    
    public void addVote(String prefix) {
        this.votes.put(prefix, this.votes.containsKey(prefix) ? this.votes.get(prefix) + 1 : 1);
    }
    
    public void setVote(String prefix, Integer vote) {
        this.votes.put(prefix, vote);
    }
    
    public Integer getVote(String prefix) {
        return this.votes.get(prefix);
    }
    
    public void setVotes(HashMap<String, Integer> votes) {
        this.votes = votes;
    }
    
    public HashMap<String, Integer> getVotes() {
        return this.votes;
    }
    
    public void setValidPrefixes(ArrayList<String> prefixes) {
        this.validPrefixes = prefixes;
    }
    
    public ArrayList<String> getValidPrefixes() {
        return this.validPrefixes;
    }
    
    public void addBallotStatus(Integer id, Integer preferenceIndex) {
        this.ballotStatus.put(id, preferenceIndex);
    }
    
    public void setBallotStatus(HashMap<Integer, Integer> ballotStatus) {
        this.ballotStatus = ballotStatus;
    }
    
    public HashMap<Integer, Integer> getBallotStatus() {
        return this.ballotStatus;
    }
}
