package fragment;

import item.item_activity_types;

public class GridItems {
	public int id;

	public item_activity_types ac_type;
	public GridItems(int id, item_activity_types ac_type) {
		this.id = id;
		this.ac_type = ac_type;
	}
}
