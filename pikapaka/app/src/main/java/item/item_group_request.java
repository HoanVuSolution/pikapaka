package item;

import org.json.JSONArray;

/**
 * Created by MrThanhPhong on 3/23/2016.
 */
public class item_group_request {
    public String id;
    public JSONArray arr;
    public item_group_request(String id,JSONArray arr){
        this.id=id;
        this.arr=arr;
    }
}
