package item;

/**
 * Created by MrThanhPhong on 2/27/2016.
 */
public class item_profile_user {
    public String _id;
    public String firstName;
    public String lastName;
    public String provider;
    public String displayName;
    public String username;
    public String summary;
    public String shareContact;
    public String minNumOfParticipants;
    public String maxNumOfParticipants;
    public String ageFrom;
    public String ageTo;
    public String gender;
    public String distance;
    public String expiredHours;

    public item_profile_user(
            String _id,
            String firstName,
            String lastName,
            String provider,
            String displayName,
            String username,
            String summary,
            String shareContact,
            String minNumOfParticipants,
            String maxNumOfParticipants,
            String ageFrom,
            String ageTo,
            String gender,
            String distance,
            String expiredHours
    ) {
        this._id=_id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.provider=provider;
        this.displayName=displayName;
        this.username=username;
        this.summary=summary;
        this.shareContact=shareContact;
        this.minNumOfParticipants=minNumOfParticipants;
        this.maxNumOfParticipants=maxNumOfParticipants;
        this.ageFrom=ageFrom;
        this.ageTo=ageTo;
        this.gender=gender;
        this.distance=distance;
        this.expiredHours=expiredHours;

    }
}
