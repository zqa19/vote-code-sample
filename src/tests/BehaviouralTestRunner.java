package tests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import vote.VoteRunner;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class BehaviouralTestRunner {
    
    private static Boolean runBehaviouralTest(String []arguments, String expectedResult)
            throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream oldStream = System.out;
        System.setOut(printStream); // Redirect console output to printStream for validation
        VoteRunner.main(arguments);
        System.setOut(oldStream);
        String actualResult = outputStream.toString();
        System.out.println(actualResult);
        return actualResult.contains(expectedResult);
    }
    
    private static HashMap<String[], String[]> getTestCases() {
        HashMap<String[], String[]> testcases = new HashMap<String[], String[]>();
        String[] arguments;
        String expectedResult;
        String scenario;
        String classPath = BehaviouralTestRunner.class.getProtectionDomain().
                getCodeSource().getLocation().getPath();
        
        // Test case 1
        arguments = new String[1];
        arguments[0] = classPath + "tests/TestOptions1";
        expectedResult = "There must be more than 1 option to start voting.";
        scenario = "Given the option file is provided\n" +
                "When the application is reading an empty file\n" +
                "Then warning should be displayed\n" +
                "And the application should exit";
        testcases.put(arguments, new String[] {expectedResult, scenario});

        // Test case 2
        arguments = new String[1];
        arguments[0] = classPath + "tests/TestOptions2";
        expectedResult = "There must be more than 1 option to start voting.";
        scenario = "Given the option file is provided\n" +
                "When the application is reading a file with only one option\n" +
                "Then warning should be displayed\n" + 
                "And the application should exit";
        testcases.put(arguments, new String[] {expectedResult, scenario});

        // Test case 3
        arguments = new String[4];
        arguments[0] = classPath + "tests/TestOptions3";
        arguments[1] = "aa";
        arguments[2] = "a";
        arguments[3] = "tally";
        expectedResult = "Ballot discarded due to duplicate or invalid preference: A";
        scenario = "Given the application is ready for accepting commands\n" +
                "When a duplicate preference is entered as user input\n" +
                "Then the application should display warnings";
        testcases.put(arguments, new String[] {expectedResult, scenario});

        // Test case 4
        arguments = new String[4];
        arguments[0] = classPath + "tests/TestOptions3";
        arguments[1] = "z";
        arguments[2] = "a";
        arguments[3] = "tally";
        expectedResult = "Ballot discarded due to duplicate or invalid preference: Z";
        scenario = "Given the application is ready for accepting commands\n" +
                "When an invalid preference is entered as user input\n" +
                "Then the application should display warnings";
        testcases.put(arguments, new String[] {expectedResult, scenario});

        // Test case 5
        arguments = new String[10];
        arguments[0] = classPath + "tests/TestOptions3";
        arguments[1] = "abdc";
        arguments[2] = "bad";
        arguments[3] = "cabd";
        arguments[4] = "cdab";
        arguments[5] = "da";
        arguments[6] = "db";
        arguments[7] = "bac";
        arguments[8] = "cbad";
        arguments[9] = "tally";
        expectedResult = "Candidate \"B. Ten pin bowling\" selected as winner.";
        scenario = "Given the option file is provided\n" +
                "When the user enter preferences\n" +
                "And the user enter \"tally\"\n" +
                "Then the application should start counting\n" +
                "And the application should display round information\n" +
                "And the application should display winner option";
        testcases.put(arguments, new String[] {expectedResult, scenario});
        
        return testcases;
    }
    
    public static void main(String []args) throws IOException {
        Integer i = 1;
        Integer totalSuccess = 0;
        for (Map.Entry<String[], String[]> entry : getTestCases().entrySet()) {
            System.out.printf("\nTest %d:\n\n", i++);
            System.out.println("Scenario:");
            System.out.println(entry.getValue()[1]);
            System.out.println();
            System.out.println("Actual result:");
            Boolean result = runBehaviouralTest(entry.getKey(), entry.getValue()[0]);
            System.out.println("Expected result: " + entry.getValue()[0].toString());
            totalSuccess = result ? totalSuccess + 1 : totalSuccess;
            System.out.println("Result: " + (result ? "Successful" : "Failed"));
        }
        System.out.printf("\nSuccessful tests: %s/%d", totalSuccess.toString(), getTestCases().size());
    }
}
