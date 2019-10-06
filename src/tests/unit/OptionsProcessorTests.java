package tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import vote.Option;
import vote.OptionsProcessor;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class OptionsProcessorTests {
    private OptionsProcessor optionsProcessor;
    private ArrayList<Option> options;
    private Option option;
    private String prefix;
    private String optionName;
    
    @Before
    public void setUp() {
        this.optionsProcessor = new OptionsProcessor();
        this.prefix = "A";
        this.optionName = "Test";
        this.option = new Option(this.prefix, this.optionName);
        this.options = new ArrayList<Option>();
        this.options.add(this.option);
    }
    
    @Test
    public void validateOptionsSuccess() {
        this.options.add(this.option);
        this.optionsProcessor.setOptions(this.options);
        assertTrue(this.optionsProcessor.validateOptions());
    }
    
    @Test
    public void validateOptionsFailure() {
        this.optionsProcessor.setOptions(this.options);
        assertFalse(this.optionsProcessor.validateOptions());
    }

    @Test
    public void getAllPrefixesTest() {
        String prefix2 = "B";
        Option option2 = new Option(prefix2, "Test2");
        ArrayList<String> expectedPrefixes = new ArrayList<String>();
        expectedPrefixes.add(this.prefix);
        expectedPrefixes.add(prefix2);
        this.options.add(option2);
        this.optionsProcessor.setOptions(this.options);
        assertEquals(expectedPrefixes, this.optionsProcessor.getAllPrefixes());
    }

    @Test
    public void getOptionNameByPrefixTest() {
        this.optionsProcessor.setOptions(this.options);
        assertEquals(this.optionName, this.optionsProcessor.getOptionNameByPrefix(this.prefix));
    }
    
    @Test
    public void setAndGetAllOptionsTest() {
        this.optionsProcessor.setOptions(this.options);
        assertEquals(1, this.optionsProcessor.getAllOptions().size());
        assertEquals(
                this.option.getPrefix(),
                this.optionsProcessor.getAllOptions().get(0).getPrefix());
        assertEquals(
                this.option.getName(),
                this.optionsProcessor.getAllOptions().get(0).getName());
    }
    
}
