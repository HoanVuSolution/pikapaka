package request_activity;

import item.item_search_activity;

/**
 * Created by MrThanhPhong on 3/31/2016.
 */
public class GridItem_RQ {
    public int id;

    public item_search_activity ac_type;
    public GridItem_RQ(int id, item_search_activity ac_type) {
        this.id = id;
        this.ac_type = ac_type;
    }
}
