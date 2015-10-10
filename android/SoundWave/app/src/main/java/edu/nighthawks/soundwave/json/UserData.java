package edu.nighthawks.soundwave.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;



public class UserData
{
	public UserData mUserData;


	@JsonProperty("USER_ID")
	private int user_id;
	@JsonProperty("FRST_NME")
	private String name;
	@JsonProperty("LAST_NME")
	private String lastname;
	@JsonProperty("EMAIL_ADDR")
	private String email_addr;
	@JsonProperty("USER_TYPE")
	private String user_type;
	@JsonProperty("USER_PW")
	private String user_pw;
	@JsonProperty("DATE_MODIFIED")
	private String date_modified;
	@JsonProperty("DATE_EFFECTIVE")
	private String date_effective;
	@JsonProperty("DATE_EXPIRED")
	private String date_expired;


	/**
	 * 
	 * @param jsonString
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@JsonIgnoreProperties(ignoreUnknown = true)
	public UserData(String jsonString) throws JsonParseException, JsonMappingException, IOException{
		mUserData = new UserData();
		ObjectMapper mapper = new ObjectMapper();
		mUserData = mapper.readValue(jsonString, UserData.class);

		//call copy  constructor
		this.user_id = mUserData.user_id;
		this.name = mUserData.name;
		this.email_addr = mUserData.email_addr;
		this.user_type = mUserData.user_type;
		this.user_pw = mUserData.user_pw;
		this.date_modified = mUserData.date_modified;
		this.date_effective = mUserData.date_effective;
		this.date_expired= mUserData.date_expired;

		//test code 
		
		System.out.println(mUserData.getDate_effective()
		+ mUserData.getDate_expired()
		+ mUserData.getDate_modified()
		+ mUserData.getEmail_addr()
		+ mUserData.getName()
		+ mUserData.getUser_id()
		+ mUserData.getUser_pw()
		+ mUserData.getUser_type()

				);
	}

	//no arg
	public UserData() {
		
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail_addr() {
		return email_addr;
	}
	public void setEmail_addr(String email_addr) {
		this.email_addr = email_addr;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getDate_modified() {
		return date_modified;
	}
	public void setDate_modified(String date_modified) {
		this.date_modified = date_modified;
	}
	public String getDate_effective() {
		return date_effective;
	}
	public void setDate_effective(String date_effective) {
		this.date_effective = date_effective;
	}
	public String getDate_expired() {
		return date_expired;
	}
	public void setDate_expired(String date_expired) {
		this.date_expired = date_expired;
	}

	public static void main(String[] args) throws Exception
	{
		String json = "{\"USER_ID\":\"22\",\"FRST_NME\":\"gg\",\"EMAIL_ADDR\":\"hv\",\"USER_PW\":\"gv\",\"USER_TYPE\":\"basic\",\"DATE_EFFECTIVE\":\"2015-10-08 04:07:27\",\"DATE_MODIFIED\":\"2015-10-08 04:07:27\",\"DATE_EXPIRED\":\"9999-12-31T23:59:59\"}";
		//String json =        "{\"USER_ID\":\"34\",\"FRST_NME\":\"temp1\",\"LAST_NME\":\"\",\"EMAIL_ADDR\":\"temp1@gmail.com\",\"USER_TYPE\":\"basic\",\"USER_PW\":\"password\",\"DATE_MODIFIED\":\"2015-10-03 17:15:02\",\"DATE_EFFECTIVE\":\"2015-10-03 17:15:02\",\"DATE_EXPIRED\":\"9999-12-31 23:59:59\"}" ;
		new UserData(json);
	}
	

}
