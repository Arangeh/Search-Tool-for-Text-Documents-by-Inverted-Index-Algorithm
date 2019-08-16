/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package invertedindex;

import java.util.ArrayList;

/**
 *
 * @author alireza
 */
public class FileRecord 
{
    private Object stopWords;
    private String recordType;
    public String name;//name of the file
    public Object o;//data Structure that we use to save the file
    public FileRecord(String name)
    {
        this.name = name;
    }
    private boolean isStopWord(String s)
    {
        boolean res = false;
        if(recordType.equals("ArrayList"))
         {
             ArrayList < String > u = (ArrayList)stopWords;
             if(u.indexOf(s) != -1)//s is stopword
             {
                 res = true;
             }
         }//else!
        return res;
    }
  
    public void setStopWords(Object o)
    {
        stopWords = o;
    }
    public void setType(String type)
    {
        recordType = type;
        if(type.equals("ArrayList"))
        {
            o = new ArrayList < String >();
        }else if(type.equals("Hash"))
        {
        }else if(type.equals("AVL"))
        {
        }else if(type.equals("TST"))
        {
        }else if(type.equals("Trie"))
        {
        }else if(type.equals("R-B"))
        {
        }
    }
    public void add(String s)//adding a word to the file
    {
        
        if(recordType.equals("ArrayList"))
        {
            ArrayList u = (ArrayList)o;
            if(u.indexOf(s) == -1 && !isStopWord(s))
            {
                u.add(s);
            }//else we know there is such a word in the file
        }else if(recordType.equals("Hash"))
        {
        }else if(recordType.equals("AVL"))
        {
        }else if(recordType.equals("TST"))
        {
        }else if(recordType.equals("Trie"))
        {
        }else if(recordType.equals("R-B"))
        {
        }
    }
    public void delete(String s)//deleting a word from file
    {
        System.out.println(s);
        if(recordType.equals("ArrayList"))
        {
            ArrayList u = (ArrayList)o;
            if(u.indexOf(s) != -1)//the word exists in the ArrayList
            {
                u.remove(u.indexOf(s));
            }//else we know there is such a word in the file
        }else if(recordType.equals("Hash"))
        {
        }else if(recordType.equals("AVL"))
        {
        }else if(recordType.equals("TST"))
        {
        }else if(recordType.equals("Trie"))
        {
        }else if(recordType.equals("R-B"))
        {
        }
    }
}
