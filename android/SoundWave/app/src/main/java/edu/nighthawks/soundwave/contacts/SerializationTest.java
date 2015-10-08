package edu.nighthawks.soundwave.contacts;

import java.util.ArrayList;
import java.util.List;

public class SerializationTest {
 
    public static void main(String[] args) {
        Contact contact1 = new Contact ("Steve", "steve@gmail.com");
        System.out.println(contact1.toString());
        
        Contact contact2 = new Contact ("Joe", "joe@gmail.com");
        System.out.println(contact2.toString());
        
        Contact contact3 = new Contact ("Chad", "chad@gmail.com");
        System.out.println(contact3.toString());
        
        Contact[] contacts = new Contact[3];
        contacts[0]=contact1;
        contacts[1]=contact2;
        contacts[2]=contact3;
        
        List <Contact> con = new ArrayList<Contact>();
        for (int i = 0; i < contacts.length; i++) {
			con.add(contacts[i]);
		}

        

 
        try {
            /**
             *  Serializing the object
             */
            SerializationUtility.serialize(con, "serialization.txt");
 

            List <Contact> newCon = new ArrayList();
            newCon = SerializationUtility.deserialize("serialization.txt");
            System.out.println(newCon.toString());
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}