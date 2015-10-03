package edu.nighthawks.soundwave;

import java.io.IOException;

public class SerializationTest {
 
    public static void main(String[] args) {
    	Contact contact = new Contact();
        contact.setName("Steve");
        contact.setEmail("steve@gmail.com");
 
        try {
            /**
             *  Serializing the object
             */
            SerializationUtility.serialize(contact, "serialization.txt");
 
            /**
             * Deserializing the object
             */
            Contact newContact = (Contact) SerializationUtility.deserialize("serialization.txt");
            System.out.println(newContact.toString());
 
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}