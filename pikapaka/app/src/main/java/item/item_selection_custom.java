package item;

/**
 * Created by MrThanhPhong on 3/7/2016.
 */
public class item_selection_custom {
    public  String key="";
    public  String name="";
    public  String value="";
    public  String name_sub="";
    public  String isDefault="";

    public item_selection_custom(
            String key,
            String name,
            String value,
            String name_sub,
            String isDefault
    ){
        this.key=key;
        this.name=name;
        this.value=value;
        this.name_sub=name_sub;
        this.isDefault=isDefault;
    }

    @Override
    public String toString() {
        return name_sub;
    }
}
