package item;

/**
 * Created by MrThanhPhong on 3/7/2016.
 */
public class item_custom_fields {

    public String isRequired="";
    public String type="";
    public String key="";
    public String name="";
    public String isMultiple="";

    public item_custom_fields(
            String isRequired,
            String type,
            String key,
            String name,
            String isMultiple
    ){
        this.isRequired=isRequired;
        this.type=type;
        this.key=key;
        this.name=name;
        this.isMultiple=isMultiple;
    }
}
