package tests.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import vote.Ballot;
import vote.BallotsProcessor;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class BallotsProcessorTests {
    private BallotsProcessor ballotsProcessor;
    private Integer id;
    private String preferenceString;
    private ArrayList<String> preferences;
    private Ballot ballot;
    
    @Before
    public void setUp() {
        this.id = 1;
        this.preferenceString = "ABC";
        this.preferences = new ArrayList<String>();
        this.preferences.add("A");
        this.preferences.add("B");
        this.preferences.add("C");
        
        this.ballotsProcessor = new BallotsProcessor();        
        this.ballot = new Ballot(this.id, this.preferenceString);
    }
    
    @Test
    public void addAndGetAllBallotTest() {
        this.ballotsProcessor.addBallot(this.preferenceString);
        assertEquals(1, this.ballotsProcessor.getAllBallots().size());
        assertEquals(
                this.ballot.getId(),
                this.ballotsProcessor.getAllBallots().get(0).getId());
        assertEquals(
                this.ballot.getPreferences(),
                this.ballotsProcessor.getAllBallots().get(0).getPreferences());
        assertEquals(
                this.ballot.getPreferenceString(),
                this.ballotsProcessor.getAllBallots().get(0).getPreferenceString());
    }
    
    @Test
    public void getPreferencesByIdTest() {
        this.ballotsProcessor.addBallot(this.preferenceString);
        assertEquals(this.preferences, this.ballotsProcessor.getPreferencesById(this.id));
    }
}
