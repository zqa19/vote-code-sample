package tests.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import vote.Ballot;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class BallotTests extends AbstractObjectTests {
    private Integer ballotId;
    private String preferenceString;
    private ArrayList<String> preferences;
    private Ballot ballot;

    @Override
    protected Object getObject() {
        return this.ballot;
    }

    @Override
    protected HashMap<String, Object> getTestCases() {
        HashMap<String, Object> testCases = this.getConstructorTestCases();
        testCases.put("preferences", this.preferences);
        return testCases;
    }
    
    @Override
    protected HashMap<String, Object> getConstructorTestCases() {
        HashMap<String, Object> testCases = new HashMap<String, Object>();
        testCases.put("id", this.ballotId);
        testCases.put("preferenceString", this.preferenceString);
        return testCases;
    }
    
    @Before
    public void setUp() {
        this.ballotId = 1;
        this.preferenceString = "ABC";
        this.preferences = new ArrayList<String>();
        preferences.add("A");
        preferences.add("B");
        preferences.add("C");

        this.ballot =  new Ballot(this.ballotId, this.preferenceString);
    }
    
    @Test
    public void setPreferenceStringTest() {
        this.ballot.setPreferenceString(this.preferenceString);
        assertEquals(this.preferences, this.ballot.getPreferences());
    }
}
