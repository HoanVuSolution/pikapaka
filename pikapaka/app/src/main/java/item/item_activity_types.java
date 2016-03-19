package item;

/**
 * Created by MrThanhPhong on 2/26/2016.
 */
public class item_activity_types {

    public String _id;
    public String name;
    public String description;
    public String categoryId;
    public String defaultValue;
    public String customField;
    public String color;
    public String updatedAt;

    public item_activity_types(){

    }

    public item_activity_types(
            String _id,
            String name,
            String description,
            String categoryId,
            String defaultValue,
            String customField,
            String color,
            String updatedAt

    ){
        super();
        this._id=_id;
        this.name=name;
        this.description=description;
        this.categoryId=categoryId;
        this.defaultValue=defaultValue;
        this.customField=customField;
        this.color=color;
        this.updatedAt=updatedAt;

    }


}
