package item;

/**
 * Created by MrThanhPhong on 3/2/2016.
 */
public class item_user_group {
    public String _id;
    public String firstName;
    public String gender;
    public String lastName;
    public String dob;
    public String displayName;
    public String age;
    public String imageUrl;
    public String rank;


    public item_user_group(

            String _id,
            String firstName,
            String gender,
            String lastName,
            String dob,
            String displayName,
            String age,
            String imageUrl,
            String rank
    ){
        this._id=_id;
        this.firstName=firstName;
        this.gender=gender;
        this.lastName=lastName;
        this.dob=dob;
        this.displayName=displayName;
        this.age=age;
        this.imageUrl=imageUrl;
        this.rank=rank;

    }
}
