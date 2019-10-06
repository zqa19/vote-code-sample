package tests.unit;

import java.util.HashMap;

import org.junit.Before;

import vote.Option;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class OptionTests extends AbstractObjectTests {
    private String prefix;
    private String name;
    
    @Override
    protected Object getObject() {
        return new Option(this.prefix, this.name);
    }

    @Override
    protected HashMap<String, Object> getConstructorTestCases() {
        HashMap<String, Object> testCases = new HashMap<String, Object>();
        testCases.put("prefix", this.prefix);
        testCases.put("name", this.name);
        return testCases;
    }

    @Override
    protected HashMap<String, Object> getTestCases() {
        return this.getConstructorTestCases();
    }
    
    @Before
    public void setUp() {
        this.prefix = "A";
        this.name = "Test Name";
    }
}