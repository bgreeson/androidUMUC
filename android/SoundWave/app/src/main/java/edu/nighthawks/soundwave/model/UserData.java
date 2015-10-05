package edu.nighthawks.soundwave.model;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

//import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class UserData {

	
	private int user_id;
	private String name;
	private String email_addr;
	private String user_type;
	private String user_pw;
	private String date_modified;
	private String date_effective;
	private String date_expired;
	
	
	/**
	 * 
	 * @param jsonString
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public UserData(String jsonString) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		UserData aUser = mapper.readValue(jsonString, UserData.class);
		//TODO
		//call copy  constructor
		//test code 
		
		System.out.println(aUser.getDate_effective()
		+ aUser.getDate_expired()
		+ aUser.getDate_modified()
		+ aUser.getEmail_addr()
		+ aUser.getName()
		+ aUser.getUser_id()
		+ aUser.getUser_pw()
		+ aUser.getUser_type()
		
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
	
	public static void main(String[] args) throws Exception{
		System.out.println("YOLO");
		
		String json = "[{\"USER_ID\":\"34\",\"FRST_NME\":\"temp1\",\"LAST_NME\":\"\",\"EMAIL_ADDR\":\"temp1@gmail.com\",\"USER_TYPE\":\"basic\",\"USER_PW\":\"password\",\"DATE_MODIFIED\":\"2015-10-03 17:15:02\",\"DATE_EFFECTIVE\":\"2015-10-03 17:15:02\",\"DATE_EXPIRED\":\"9999-12-31 23:59:59\"}]" ;
		new UserData(json);
		
	
	}
	

}
