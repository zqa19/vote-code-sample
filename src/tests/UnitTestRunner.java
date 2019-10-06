package tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import tests.unit.BallotTests;
import tests.unit.BallotsProcessorTests;
import tests.unit.OptionTests;
import tests.unit.OptionsProcessorTests;
import tests.unit.RoundTests;
import tests.unit.RoundsProcessorTests;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class UnitTestRunner {
    public static void main(String[] args) {
        Class<?>[] classes = {
                OptionTests.class,
                OptionsProcessorTests.class,
                BallotTests.class,
                BallotsProcessorTests.class,
                RoundTests.class,
                RoundsProcessorTests.class};
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream oldStream = System.out;
        
        System.setOut(printStream); // Hide application output
        Result result = JUnitCore.runClasses(classes);
        System.setOut(oldStream);
        
        for (Failure failure : result.getFailures()) {
           System.out.println(failure.toString());
        }
          
        System.out.println("\nUnit test result successful: " + result.wasSuccessful());
    }
}