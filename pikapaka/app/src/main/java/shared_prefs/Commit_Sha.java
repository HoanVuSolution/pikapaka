package shared_prefs;

import android.content.Context;
import android.content.SharedPreferences;

import util.dataString;

public class Commit_Sha {

	public static void Clear_Token(Context context) {
		SharedPreferences sha_IDuser;
		sha_IDuser = context.getSharedPreferences("ID_user", 0);
		sha_IDuser.edit().remove("ID_user").commit();

		SharedPreferences sha_Token;
		sha_Token = context
				.getSharedPreferences("auth_token", 0);
		sha_Token.edit().remove("auth_token").commit();


		SharedPreferences sha_user;
		sha_user = context
				.getSharedPreferences("user", 0);
		sha_user.edit().remove("user").commit();

		SharedPreferences sha_password;
		sha_password = context
				.getSharedPreferences("password", 0);
		sha_password.edit().remove("password").commit();

		dataString.TAG_DISPLAYNAME="";
		dataString.TAG_FIRSTNAME="";
		dataString.TAG_LASTNAME="";
		dataString.TAG_EMAIL="";
		dataString.TAG_USERID="";
		dataString.TAG_IMAGE_URL="";

	}

	public static void Write_TokenID(Context context,String userID,String auth_token,String user,String password){
		SharedPreferences sha_IDuser;
		sha_IDuser = context.getSharedPreferences("ID_user", 0);
		SharedPreferences.Editor editor = sha_IDuser.edit();
		editor.putString("ID_user", userID);
		editor.commit();

		SharedPreferences sha_Token;
		sha_Token = context.getSharedPreferences("auth_token", 0);
		SharedPreferences.Editor editor1 = sha_Token.edit();
		editor1.putString("auth_token", auth_token);
		editor1.commit();


		SharedPreferences sha_user;
		sha_user = context.getSharedPreferences("user", 0);
		SharedPreferences.Editor editor2 = sha_user.edit();
		editor2.putString("user", user);
		editor2.commit();

		SharedPreferences sha_password;
		sha_password = context.getSharedPreferences("password", 0);
		SharedPreferences.Editor editor3 = sha_password.edit();
		editor3.putString("password", password);
		editor3.commit();



	}

	public static void Checking_sha(){
		 SharedPreferences sha_loai_taikhoan;
		 SharedPreferences sha_id_congty;
	}

}
