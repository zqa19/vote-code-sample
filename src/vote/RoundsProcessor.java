package vote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class RoundsProcessor {
    private String winnerPrefix;
    private ArrayList<Round> rounds;
    private OptionsProcessor optionsProcessor;
    private BallotsProcessor ballotsProcessor;
    
    private void setWinner(String winnerPrefix) {
        this.winnerPrefix = winnerPrefix;
    }
    
    private Integer calculateQuota(Integer ballotNumber) {
        return Integer.valueOf((int)Math.ceil(ballotNumber / 2 + 1));
    }
    
    private Round createNextRound(Round round, String eliminatedOption) {
        ArrayList<String> newValidPrefixes = round.getValidPrefixes();
        newValidPrefixes.remove(eliminatedOption);
        HashMap<Integer, Integer> newBallotStatus = getUpdatedBallotStatus(
                eliminatedOption,
                newValidPrefixes,
                round.getBallotStatus());
        return this.createNewRound(round.getId() + 1, newValidPrefixes, newBallotStatus);
    }
    
    private Round eliminateLeastVotedOption(Round round) {
        // Get options with least votes
        Integer minVote = round.getBallotStatus().size() + 1;
        ArrayList<String> eliminatedOptions = new ArrayList<String>();
        for (String prefix : round.getVotes().keySet()) {
            Integer vote = round.getVote(prefix);
            if (vote < minVote) {
                minVote = vote;
                eliminatedOptions.clear();
                eliminatedOptions.add(prefix);
            } else if (vote == minVote) {
                eliminatedOptions.add(prefix);
            } 
        }

        // If there are multiple options, randomly select one
        Integer selectedOptionIndex = eliminatedOptions.size() > 1 ?
                (new Random()).nextInt(eliminatedOptions.size()) : 0;
        String eliminatedOption = eliminatedOptions.get(selectedOptionIndex);
        
        Round nextRound = this.createNextRound(round, eliminatedOption);
        System.out.printf(
                "\nCandidate \"%s. %s\" eliminated.\n",
                eliminatedOption,
                optionsProcessor.getOptionNameByPrefix(eliminatedOption));
        return nextRound;
    }    
    
    private Boolean checkWinner(Round round) {
        round.setQuota(calculateQuota(round.getBallotStatus().size()));
        
        // Get options with most votes
        Integer maxVote = 0;
        ArrayList<String> winnerOptions = new ArrayList<String>();
        for (String prefix : round.getVotes().keySet()) {
            Integer vote = round.getVote(prefix);
            if (vote > maxVote) {
                maxVote = vote;
                winnerOptions.clear();
                winnerOptions.add(prefix);
            } else if (vote == maxVote) {
                winnerOptions.add(prefix);
            }
        }

        displayRoundInfo(round);
        
        // Check whether there is only one option with votes more than quota
        if (maxVote >= round.getQuota() && winnerOptions.size() == 1) {
            this.setWinner(winnerOptions.get(0));
            return true;
        };
        
        return false;
    }
    
    private HashMap<Integer, Integer> getUpdatedBallotStatus(
            String eliminatedOption,
            ArrayList<String> validPrefixes,
            HashMap<Integer, Integer> ballotStatus) {
        HashMap<Integer, Integer> newBallotStatus = new HashMap<Integer, Integer>();
        for (Integer id : ballotStatus.keySet()) {
            Integer preferenceIndex = ballotStatus.get(id);
            ArrayList<String> preferences =
                    this.ballotsProcessor.getPreferencesById(id);
            
            // If the first preference is an eliminated option,
            // get the next non-eliminated option until the ballot is exhausted
            if (preferences.get(preferenceIndex).equals(eliminatedOption)) {
                while (++preferenceIndex < preferences.size()) {
                    if (validPrefixes.contains(preferences.get(preferenceIndex))) {
                        break;
                    }
                }
                
                // Only add ballot status if the ballot is not exhausted
                if (preferenceIndex >= preferences.size()) {
                   continue;
                }
            }
            newBallotStatus.put(id, preferenceIndex);
        }
        return newBallotStatus;
    }
    
    private void initialiseRoundPrefixesAndVotes(Round round, ArrayList<String> prefixes) {
        round.setValidPrefixes(prefixes);
        for (String prefix : prefixes) {
            round.setVote(prefix, 0);
        }
    }
    
    private void setVotesByBallotStatus(Round round) {
        // Add votes according to non-exhausted ballots
        HashMap<Integer, Integer> ballotStatus = round.getBallotStatus();
        for (Integer id : ballotStatus.keySet()) {
            Integer preferenceIndex = ballotStatus.get(id);
            ArrayList<String> preferences =
                    this.ballotsProcessor.getPreferencesById(id);
            round.addVote(preferences.get(preferenceIndex));
        }
    }
    
    private Round createNewRound(Integer roundId,
            ArrayList<String> prefixes,
            HashMap<Integer, Integer> ballotStatus) {
        Round round = new Round(roundId);
        this.initialiseRoundPrefixesAndVotes(round, prefixes);
        round.setBallotStatus(ballotStatus);
        this.setVotesByBallotStatus(round);
        
        return round;
    }
    
    private void displayRoundInfo(Round round) {
        System.out.printf("\nRound %d\n", round.getId());
        System.out.printf("Total candidates: %d\n", round.getValidPrefixes().size());
        System.out.printf("Quota: %d\n", round.getQuota());
        System.out.printf("Total votes: %d\n", round.getBallotStatus().size());
        for (String prefix : round.getVotes().keySet()) {
            System.out.printf("%s: %d\n", prefix, round.getVotes().get(prefix));
        }
    }
    
    public RoundsProcessor(
            OptionsProcessor optionsProcessor,
            BallotsProcessor ballotsProcessor) {
        this.rounds = new ArrayList<Round>();
        this.optionsProcessor = optionsProcessor;
        this.ballotsProcessor = ballotsProcessor;
    }
    
    public void startCounting() {
        ArrayList<String> prefixes = this.optionsProcessor.getAllPrefixes();
        ArrayList<Ballot> ballots = this.ballotsProcessor.getAllBallots();
        HashMap<Integer, Integer> ballotStatus = new HashMap<Integer, Integer>();
        for (Ballot ballot : ballots) {
            ballotStatus.put(ballot.getId(), 0);
        }
        Round round = createNewRound(1, prefixes, ballotStatus);
        this.rounds.add(round);
        
        while (!checkWinner(round)) {
            round = eliminateLeastVotedOption(round);
            this.rounds.add(round);
        }
    }
    
    public ArrayList<Round> getAllRounds() {
        return this.rounds;
    }
    
    public String getWinnerPrefix() {
        return this.winnerPrefix;
    }
}
