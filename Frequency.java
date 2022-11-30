import java.io.*;
import java.util.*;

//Class declaration
public class Frequency
{
    //Constructor
    public Frequency ()
    {
        //Storing the string in an array
        String[] words = getString("words.txt").split("\\s+");
        //Initializing the Map
        Map<String, Integer> wordCount = new HashMap<>();
        //for loop to fill the map
        for (String word : words)
        {
            //place the word in map only if it's not empty string
            if (!word.equals(""))
                wordCount.merge(word, 1, Integer::sum); //if element exist add sum key with one
        }
        //Creating set froms keys
        Set<String> keySet = wordCount.keySet();
        for (String keyValue : keySet)
        {
            Integer value = wordCount.get(keyValue);
            System.out.println(keyValue + " " + value);
        }
    }

    //a function to read the file
    public static String getString (String filename)
    {
        String str = "";
        try
        {
            File file = new File(filename);
            Scanner infile = new Scanner(file);
            String check;
            while (infile.hasNext())
            {

                check = cleanString(infile.next());
                if (check != null && !check.equals("")) {
                    str = str + " " + check;
                }
            }
            infile.close();
        }
        catch (FileNotFoundException fileException)
        {
            System.out.println("Files does not exist.");
            fileException.printStackTrace();
        }

        return str;
    }
    //A function to clean the string
    public static String cleanString(String str){
        String rtr_str = "";
        for (int i = 0; i < str.length(); i++){
            //if letter add it to the cleaned string
            if (Character.isLetter(str.charAt(i))){
                rtr_str = rtr_str + str.charAt(i);
            }
            else{
                if (rtr_str.length()>0) break;
            }

        }
        //if length of the sting is 0 return null
        if (rtr_str.length() == 0){
            return null;
        }
        //returng the result in lowercase
        return  rtr_str.toLowerCase();
    }




    public static void main (String[] args){
        Frequency test = new Frequency();
    }
}