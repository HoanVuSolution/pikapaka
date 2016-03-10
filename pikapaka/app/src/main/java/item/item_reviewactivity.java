package item;

/**
 * Created by MrThanhPhong on 2/18/2016.
 */
public class item_reviewactivity {

    public String id="";
    public String name="";
    public String caterogy="";
    public boolean search=false;
    public String frend="";

    public item_reviewactivity(
            String id,
            String name,
            String caterogy,
            boolean search,
           String frend

    ){
        this.id=id;
        this.name=name;
        this.caterogy=caterogy;
        this.search=search;
        this.frend=frend;

    }
}
