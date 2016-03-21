package api;

/**
 * Created by MrThanhPhong on 2/22/2016.
 */
public class HTTP_API {
        //http://52.26.102.232:3000/
            public static String baseUrl = "http://52.26.102.232:3000/";
            public static String SOCKET = "http://52.26.102.232:3002";

    public static String LOGIN_SOICAL="52.26.102.232:3000/api/auth/login/local";
    public static String LOGIN_Google=baseUrl+"api/v1/auth/google/login";

    public static String REGISTER=baseUrl+"api/v1/users/create";
    public static String LOGIN=baseUrl+"api/v1/auth/login/local";
    public static String LOGOUT=baseUrl+"api/v1/auth/logout";
    // public static String SOCIAL =baseUrl+"v1/auth/{provider}/callback";

    public static String Request_Token=baseUrl+"api/v1/users/profile/me";
    public static String Request_Token_SOC=baseUrl+"api/v1/auth/facebook/verify";
    public static String Request_Token_SOC_Google=baseUrl+"api/v1/auth/google/verify";
    public static String GET_ALL_CATEGORIES=baseUrl+"api/v1/categories/get-all";
    public static String CREATE_ACTIVITIES=baseUrl+"api/v1/activities/create";

    public static String MY_ACTIVITY=baseUrl+"api/v1/activities/get-by-user/me";
    public static String SEARCH_ACTIVITY=baseUrl+"api/v1/activities/search";
    public static String GET_SINGLE_ACTIVITY=baseUrl+"api/v1/activities/get";

    public static String GET_SEND_ACTIVITY=baseUrl+"api/v1/activities/request/send";
    public static String GET_ACCEPT_ACTIVITY=baseUrl+"api/v1/activities/request/accept";

    public static String CHAT_GROUP=baseUrl+"api/v1/chat/send/group";
    public static String CHAT_PRIVATE=baseUrl+"api/v1/chat/send/private";
    public static String CHAT_GET_CONVERSATION=baseUrl+"/api/v1/chat/conversations/";
    public static String CHAT_LOADLIST=baseUrl+"api/v1/chat/conversation/";
    public static String LEAVE_GROUP=baseUrl+"api/v1/activities/group/leave";

    public static String DELETE_AC=baseUrl+"api/v1/activities/delete/";

    public static String GET_PROFILE=baseUrl+"api/v1/users/profile";
    public static String UPDATE_PROFILE=baseUrl+"api/v1/users/profile/update";

    public static String GET_FAVORITE=baseUrl+"api/v1/categories/get-favorites/";
    public static String GET_POPULAR=baseUrl+"api/v1/categories/get-popular/";
    public static String GET_SURPRISE=baseUrl+"api/v1/categories/get_surprise/";

    public static String GET_CUSTOM_FIELDS=baseUrl+"api/v1/categories/get-sub/";

    public static String RANK_USER=baseUrl+"api/v1/users/rank/";
    public static String USER_IDENTITY=baseUrl+"users/upload-user-identity";
    public static String USER_AVATAR=baseUrl+"users/change-avatar";
    public static String GET_ALL_NOTIFICATION=baseUrl+"api/v1/notifications/group/all";
    public static String GET_ALL_REASON=baseUrl+"api/v1/reports/reason/get-all";
    public static String REPORT_USER=baseUrl+"api/v1/reports/send/";


}
