package edu.nighthawks.soundwave;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
 
/**
 * This is a utility class for performing serialization and.
 * deserialization of POJO's
 */
public class SerializationUtility {
 
    /**
     * deserialize to Object from given file. Use the general Object so
     * that it can work for any Java Class.
     */
    public static List deserialize(String fileName) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        List list = (ArrayList)ois.readObject();
        ois.close();
        return list;
    }
 
    /**
     * serialize the given object and save it to given file
     */
    public static void serialize(List list, String fileName)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(list);
        oos.close();
    }
}