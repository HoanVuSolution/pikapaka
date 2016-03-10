package item;

/**
 * Created by MrThanhPhong on 3/1/2016.
 */
public class item_search_activity {
    public String _id = "";
    public String minNumOfParticipants = "";
    public String ageTo = "";
    public String maxNumOfParticipants = "";
    public String distance = "";
    public String ageFrom = "";
    public String plan = "";
    public String publishToSocial = "";
    public String expiredHours = "";
    public String meetConfirm = "";
    public String gender = "";
    public String activityType = "";
    public String userId = "";
    public String status = "";
    public String type = "";
    public String activityTypeName = "";
    public String activityTypeColor = "";
    public String createdAt = "";
    public String active ="";
    public String firstName = "";
    public String gender_ = "";
    public String lastName = "";
    public String dob = "";
    public String displayName = "";
    public String age = "";
    public String hasRequest = "";

    public item_search_activity(
            String _id ,
            String minNumOfParticipants,
            String ageTo,
            String maxNumOfParticipants,
            String distance,
            String ageFrom,
            String plan,
            String publishToSocial,
            String expiredHours,
            String meetConfirm,
            String gender,
            String activityType,
            String userId,
            String status,
            String type,
            String activityTypeName,
            String activityTypeColor,
            String createdAt,
            String active,
            String firstName,
            String gender_,
            String lastName,
            String dob,
            String displayName,
            String age,
            String hasRequest

    ){

        this._id=_id;
        this.minNumOfParticipants=minNumOfParticipants;
        this.ageTo=ageTo;
        this.maxNumOfParticipants=maxNumOfParticipants;
        this.distance=distance;
        this.ageFrom=ageFrom;
        this.plan=plan;
        this.publishToSocial=publishToSocial;
        this.expiredHours=expiredHours;
        this.meetConfirm=meetConfirm;
        this.gender=gender;
        this.activityType=activityType;
        this.userId=userId;
        this.status=status;
        this.type=type;
        this.activityTypeName=activityTypeName;
        this.activityTypeColor=activityTypeColor;
        this.createdAt=createdAt;
        this.active=active;
        this.firstName=firstName;
        this.gender_=gender_;
        this.lastName=lastName;
        this.dob=dob;
        this.displayName=displayName;
        this.age=age;

        this.hasRequest=hasRequest;
    }
}
