package item;

/**
 * Created by MrThanhPhong on 2/27/2016.
 */
public class item_my_activity {
    public String _id = "";
    public String activityTypeIcon = "";
    public String activityType = "";
    public String plan = "";
    public String minNumOfParticipants = "";
    public String maxNumOfParticipants = "";
    public String ageFrom = "";
    public String ageTo = "";
    public String gender = "";
    public String distance = "";
    public String expiredHours = "";
    public String meetConfirm = "";
    public String publishToSocial = "";
    public String userId = "";
    public String status = "";
    public String type = "";
    public String activityTypeName = "";
    public String activityTypeColor = "";
    public String createdAt = "";

    public item_my_activity(
            String _id,
            String activityTypeIcon,
            String activityType,
            String plan,
            String minNumOfParticipants,
            String maxNumOfParticipants,
            String ageFrom,
            String ageTo,
            String gender,
            String distance,
            String expiredHours,
            String meetConfirm,
            String publishToSocial,
            String userId,
            String status,
            String type,
            String activityTypeName,
            String activityTypeColor,
            String createdAt
    ) {

        this._id=_id;
        this.activityTypeIcon=activityTypeIcon;
        this.activityType=activityType;
        this.plan=plan;
        this.minNumOfParticipants=minNumOfParticipants;
        this.maxNumOfParticipants=maxNumOfParticipants;
        this.ageFrom=ageFrom;
        this.ageTo=ageTo;
        this.gender=gender;
        this.distance=distance;
        this.expiredHours=expiredHours;
        this.meetConfirm=meetConfirm;
        this.publishToSocial=publishToSocial;
        this.userId=userId;
        this.status=status;
        this.type=type;
        this.activityTypeName=activityTypeName;
        this.activityTypeColor=activityTypeColor;
        this.createdAt=createdAt;


    }
}
