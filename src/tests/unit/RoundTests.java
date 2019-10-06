package tests.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import vote.Round;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class RoundTests extends AbstractObjectTests {
    private Integer id;
    private Integer quota;
    private HashMap<String, Integer> votes;
    private ArrayList<String> validPrefixes;
    private HashMap<Integer, Integer> ballotStatus;
    private String prefix;
    private Round round;

    @Override
    protected Object getObject() {
        return this.round;
    }

    @Override
    protected HashMap<String, Object> getConstructorTestCases() {
        HashMap<String, Object> testCases = new HashMap<String, Object>();
        testCases.put("id", this.id);
        return testCases;
    }

    @Override
    protected HashMap<String, Object> getTestCases() {
        HashMap<String, Object> testCases = this.getConstructorTestCases();
        testCases.put("quota", this.quota);
        testCases.put("votes", this.votes);
        testCases.put("validPrefixes", this.validPrefixes);
        testCases.put("ballotStatus", this.ballotStatus);
        return testCases;
    }
    
    @Before
    public void setUp() {
        this.id = 1;
        this.quota = 5;
        this.votes = new HashMap<String, Integer>();
        this.votes.put("test", 3);
        
        this.prefix = "C";
        
        this.validPrefixes = new ArrayList<String>();
        this.validPrefixes.add(this.prefix);
        this.validPrefixes.add("D");
        
        this.ballotStatus = new HashMap<Integer, Integer>();
        this.ballotStatus.put(2, 9);
        this.ballotStatus.put(6, 7);
        this.round = new Round(this.id);
    }
    
    @Test
    public void roundConstructorTest() {
        assertEquals(new HashMap<String, Integer>(), this.round.getVotes());
        assertEquals(new ArrayList<String>(), this.round.getValidPrefixes());
        assertEquals(new HashMap<Integer, Integer>(), this.round.getBallotStatus());
    }
    
    @Test
    public void addAndGetVoteTest() {
        this.round.addVote(this.prefix);
        assertEquals(1, this.round.getVote(this.prefix).intValue());
    }

    @Test
    public void setAndGetVoteTest() {
        int vote = 9;
        this.round.setVote(this.prefix, vote);
        assertEquals(vote, this.round.getVote(this.prefix).intValue());
    }

    @Test
    public void addBallotStatusTest() {
        Integer preferenceIndex = 111;
        this.round.addBallotStatus(this.id, preferenceIndex);
        assertEquals(
                preferenceIndex.intValue(),
                this.round.getBallotStatus().get(this.id).intValue());
    }
}
