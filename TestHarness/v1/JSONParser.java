package testharness;

/*
 * FILE: JSONParser.java
 * Creates an object from a JSON string whose value pairs
 * have been parsed into a linkedhashmap for retrieval.
 */

import java.awt.List;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class JSONParser
{
    private LinkedHashMap<String, String> parsedMap = new LinkedHashMap();
    private String jsonString = "";
    private LinkedList<LinkedHashMap> parseList = new LinkedList();
    
    //Constructor
    public JSONParser(String json)
    {
        //System.out.println("Initializing....");
        jsonString = json;
        parseString();
        //System.out.println("Initialized!!");
    }
    
    //Method used to parse a JSON string
    private void parseString()
    {
        int index = 0, begIndex = 0, endIndex = 0;
        String jKey = "", jValue = "";
        boolean aKey = true, aValue = false;

        while (jsonString.charAt(index) != ']')
        {
            //System.out.println("Indices: begIndex = " + begIndex + " endIndex = " + endIndex);
            if (jsonString.charAt(index) == '"' && begIndex == 0) begIndex = index + 1;
            else if (jsonString.charAt(index) == '"' && begIndex != 0 && endIndex == 0) endIndex = index;
            else if (jsonString.charAt(index) == 'n' && begIndex == 0) 
            {
                aKey = true;
                aValue = false;
                //begIndex = 0;
                //endIndex = 0;
            }

            if (begIndex != 0 && endIndex != 0)
            {
                if (aKey) 
                {
                    //System.out.print("Setting Key (begIndex = " + begIndex + " endIndex = " + endIndex + "): ");
                    aKey = false;
                    aValue = true;
                    jKey = jsonString.substring(begIndex, endIndex);                    
                    //System.out.println(jKey);
                }
                else if (aValue)
                {
                    //System.out.print("Setting Value (begIndex = " + begIndex + " endIndex = " + endIndex + "): ");
                    aKey = true;
                    aValue = false;
                    jValue = jsonString.substring(begIndex, endIndex); 
                    //System.out.println(jValue);
                }
                begIndex = 0;
                endIndex = 0;
            }
            if (aKey && !aValue && !jKey.isEmpty())
            {
                //System.out.println("Setting Map: Key = " + jKey + " Value = " + jValue);
                if (parsedMap.containsKey(jKey)) 
                {
                    parseList.add(parsedMap);
                    parsedMap = new LinkedHashMap();
                }
                parsedMap.put(jKey, jValue);
                jKey = "";
                jValue = "";
                begIndex = 0;
                endIndex = 0;
            }
            index++;
        }
        parseList.add(parsedMap);
    }
    
    //Method used to get a value based on a key
    public String getValue(int listIndex, String jKey)
    {
        if (listIndex >= parseList.size()) return "Index and Value does not exist!";
        return parseList.get(listIndex).get(jKey).toString();
        //return parsedMap.get(jKey);
    }
    
    //Method used to return the key value pairs in a string
    public String getParse(boolean lineBreaks)
    {
        if (!lineBreaks)
        {
            //System.out.println(parsedMap.toString());
            return parseList.toString();
        }
        String parseValues = "";
        for (int i = 0; i < parseList.size(); i++)
        {
            //Iterator it = parsedMap.entrySet().iterator();
            Iterator it = parseList.get(i).entrySet().iterator();

            while (it.hasNext())
            {
                Map.Entry pair = (Map.Entry)it.next();
                parseValues += pair.getKey() + " = " + pair.getValue() + "\n";
                it.remove();
            }
            parseValues += "\n";
        }            
        return parseValues;        
    }
}
