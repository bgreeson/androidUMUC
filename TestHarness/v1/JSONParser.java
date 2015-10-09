package testharness;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONParser
{
    private Map<String, String> parsedMap = new LinkedHashMap();
    private String jsonString = "";
    
    public JSONParser(String json)
    {
        //System.out.println("Initializing....");
        jsonString = json;
        parseString();
        //System.out.println("Initialized!!");
    }
    
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
                parsedMap.put(jKey, jValue);
                jKey = "";
                jValue = "";
                begIndex = 0;
                endIndex = 0;
            }
            index++;
        }
    }
    
    public String getValue(String jKey)
    {
        return parsedMap.get(jKey);
    }
    
    public String getParse(boolean lineBreaks)
    {
        if (!lineBreaks)
        {
            //System.out.println(parsedMap.toString());
            return parsedMap.toString();
        }
        String parseValues = "";
        Iterator i = parsedMap.entrySet().iterator();
        
        while (i.hasNext())
        {
            Map.Entry pair = (Map.Entry)i.next();
            parseValues += pair.getKey() + " = " + pair.getValue() + "\n";
            i.remove();
        }
        return parseValues;        
    }
}
