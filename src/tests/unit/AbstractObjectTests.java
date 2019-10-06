package tests.unit;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Qiao Zhao
 *
 */
abstract public class AbstractObjectTests {
    abstract protected Object getObject();
    
    abstract protected HashMap<String, Object> getConstructorTestCases();
    
    abstract protected HashMap<String, Object> getTestCases();

    @Before
    abstract public void setUp();
    
    @Test
    public void constructorTest() {
        Object object = this.getObject();
        for (Map.Entry<String, Object> entry : this.getConstructorTestCases().entrySet()) {
            String propertyName = entry.getKey();
            propertyName =
                    propertyName.substring(0,  1).toUpperCase() + propertyName.substring(1);
            try {
                Method getMethod = object.getClass().getMethod(
                        String.format("get%s", propertyName));
                assertEquals(entry.getValue(), getMethod.invoke(object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Test
    public void setterAndGetterTest() {
        Object object = this.getObject();
        for (Map.Entry<String, Object> entry : this.getTestCases().entrySet()) {
            String propertyName = entry.getKey();
            propertyName =
                    propertyName.substring(0,  1).toUpperCase() + propertyName.substring(1);
            try {
                Method setMethod = object.getClass().getMethod(
                        String.format("set%s", propertyName),
                        entry.getValue().getClass());
                setMethod.invoke(object, entry.getValue());
                Method getMethod = object.getClass().getMethod(
                        String.format("get%s", propertyName));
                assertEquals(entry.getValue(), getMethod.invoke(object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
