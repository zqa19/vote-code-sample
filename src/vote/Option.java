package vote;

/**
 * 
 * @author Qiao Zhao
 *
 */
public class Option {
    private String prefix;
    private String name;
    
    public Option(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
