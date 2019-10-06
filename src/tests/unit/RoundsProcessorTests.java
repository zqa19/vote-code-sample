package tests.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import vote.BallotsProcessor;
import vote.Option;
import vote.OptionsProcessor;
import vote.Round;
import vote.RoundsProcessor;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class RoundsProcessorTests {
    private OptionsProcessor optionsProcessor;
    private BallotsProcessor ballotsProcessor;
    private RoundsProcessor roundsProcessor;
    
    @Before
    public void setUp() {
        this.optionsProcessor = new OptionsProcessor();
        this.ballotsProcessor = new BallotsProcessor();
        this.roundsProcessor = new RoundsProcessor(
                this.optionsProcessor,
                this.ballotsProcessor);
    }
    
    @Test
    public void processBallotsAndGetWinnerPrefixTest() {
        ArrayList<Option> options = new ArrayList<Option>();
        Character prefix = 'A';
        String optionName = "Test";
        for (int i = 1; i <= 4; i++) {
            options.add(
                    new Option(String.valueOf(prefix++), optionName.concat(String.valueOf(i))));
        }
        this.optionsProcessor.setOptions(options);
        
        ArrayList<String> preferences = new ArrayList<String>();
        preferences.add("A");
        preferences.add("B");
        preferences.add("C");
        preferences.add("D");
        this.ballotsProcessor.addBallot("ABC");
        this.ballotsProcessor.addBallot("AB");
        this.ballotsProcessor.addBallot("A");
        this.roundsProcessor.startCounting();

        Round actualRound = this.roundsProcessor.getAllRounds().get(0);
        assertEquals(1, this.roundsProcessor.getAllRounds().size());
        assertEquals(1, actualRound.getId().intValue());
        assertEquals(2, actualRound.getQuota().intValue());
        assertEquals(preferences, actualRound.getValidPrefixes());
        assertEquals(3, actualRound.getBallotStatus().size());
        assertEquals(0, actualRound.getBallotStatus().get(1).intValue());
        assertEquals(0, actualRound.getBallotStatus().get(2).intValue());
        assertEquals(0, actualRound.getBallotStatus().get(3).intValue());
        assertEquals(4, actualRound.getVotes().size());
        assertEquals(3, actualRound.getVote("A").intValue());
        assertEquals(0, actualRound.getVote("B").intValue());
        assertEquals(0, actualRound.getVote("C").intValue());
        assertEquals(0, actualRound.getVote("D").intValue());
        
        assertEquals("A", this.roundsProcessor.getWinnerPrefix());
    }
}
