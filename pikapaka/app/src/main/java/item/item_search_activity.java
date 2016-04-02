package item;

import org.json.JSONArray;

public class item_search_activity {
    public String _id = "";
    public String activityType = "";
    public String activityName = "";

    public String type = "";
    public String activityTypeColor = "";
    public String active ="";
    public String id_user = "";
    public String firstName = "";
    public String gender_ = "";
    public String lastName = "";
    public String dob = "";
    public String displayName = "";
    public String age = "";
    public String imageUrl = "";
    public String rank = "";
    public String hasRequest = "";
    public JSONArray arr_group;
    public item_search_activity(
            String _id ,
            String activityType,
            String activityName,
            String type,
            String activityTypeColor,
            String active,
            String id_user,
            String firstName,
            String gender_,
            String lastName,
            String dob,
            String displayName,
            String age,
            String imageUrl,
            String rank,
            String hasRequest,
            JSONArray arr_group

    ){

        this._id=_id;
        this.activityType=activityType;
        this.activityName=activityName;
        this.type=type;
        this.activityTypeColor=activityTypeColor;
        this.active=active;
        this.firstName=firstName;
        this.id_user=id_user;
        this.gender_=gender_;
        this.lastName=lastName;
        this.dob=dob;
        this.displayName=displayName;
        this.age=age;
        this.imageUrl=imageUrl;
        this.rank=rank;
        this.hasRequest=hasRequest;
        this.arr_group=arr_group;
    }
}
