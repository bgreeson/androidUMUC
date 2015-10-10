package edu.nighthawks.soundwave.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nighthawks.soundwave.contacts.Contact;

/**
 * Created by joe.keefe on 10/9/2015.
 */
public class JsonArrayParser
{
    public static ArrayList<Contact> parseArray(String jsonStringArray) throws IOException
    {

        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();

        ArrayList<Contact> list = null;
        list = mapper.readValue(jsonStringArray, typeFactory.constructCollectionType(List.class, Contact.class));

        return list;
    }
}
