package edu.nighthawks.soundwave.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ContactData
{
    public ContactData mContctData;

    //"{\"USER_ID\":\"22\",\"FRST_NME\":\"joe9\",\"LAST_NME\":\"\",\"EMAIL_ADDR\":\"joe.keefe@gmail.com\",\"USER_TYPE\":\"basic\",\"USER_PW\":\"pass\",\"DATE_MODIFIED\":\"2015-10-01 04:32:57\",\"DATE_EFFECTIVE\":\"2015-10-01 04:32:57\",\"DATE_EXPIRED\":\"9999-12-31 23:59:59\"}";
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
    public ContactData(String jsonString) throws JsonParseException, JsonMappingException, IOException{
        mContctData = new ContactData();
        ObjectMapper mapper = new ObjectMapper();
        mContctData = mapper.readValue(jsonString, ContactData.class);
        //TODO
        //call copy  constructor
        this.user_id = mContctData.user_id;
        this.name = mContctData.name;
        this.email_addr = mContctData.email_addr;
        this.user_type = mContctData.user_type;
        this.user_pw = mContctData.user_pw;
        this.date_modified = mContctData.date_modified;
        this.date_effective = mContctData.date_effective;
        this.date_expired= mContctData.date_expired;

        //test code

        System.out.println(mContctData.getDate_effective()
                        + mContctData.getDate_expired()
                        + mContctData.getDate_modified()
                        + mContctData.getEmail_addr()
                        + mContctData.getName()
                        + mContctData.getUser_id()
                        + mContctData.getUser_pw()
                        + mContctData.getUser_type()

        );
    }

    //no arg
    public ContactData() {

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
        String json = "[{\"USER_ID\":\"22\",\"FRST_NME\":\"joe9\",\"LAST_NME\":\"\",\"EMAIL_ADDR\":\"joe.keefe@gmail.com\",\"USER_TYPE\":\"basic\",\"USER_PW\":\"pass\",\"DATE_MODIFIED\":\"2015-10-01 04:32:57\",\"DATE_EFFECTIVE\":\"2015-10-01 04:32:57\",\"DATE_EXPIRED\":\"9999-12-31 23:59:59\"}]";
        new ContactData(json);
    }

}
