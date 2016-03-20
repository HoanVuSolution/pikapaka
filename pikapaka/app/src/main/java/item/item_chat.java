package item;

public class item_chat {

	public String _id="";
	public String conversationId="";
	public String id_user="";
	public String firstName="";
	public String gender="";
	public String imageUrl="";
	public String lastName="";
	public String content="";
	
	public item_chat(
			String _id,
			String conversationId,
			String id_user,
			String firstName,
			String gender,
			String lastName,
			String imageUrl,
			String content

			){
		this._id=_id;
		this.conversationId=conversationId;
		this.id_user=id_user;
		this.firstName=firstName;
		this.gender=gender;
		this.lastName=lastName;
		this.imageUrl=imageUrl;
		this.content=content;

		
	}
	
}
