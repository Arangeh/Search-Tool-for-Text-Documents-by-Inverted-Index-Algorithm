/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package invertedindex;

/**
 *
 * @author alireza
 */
public class DataStructure
{
    protected String appendixType;//behaves as the same as filesOfDicNodes which is used in many classes
    protected int no;//number of nodes that refer to a correct word
    protected int length;
    protected String test;
    
    public DataStructure()
    {
    }
    public DataStructure(String type)
    {
        this.appendixType = type;
        length = 0;//The number of files having a specific word . In other words it's the 
//        total number of nodes belonging to 'appendix' DataStructure . All nodes have meaning
    }
    
    public DataStructure(String type,String test)
    {
        this(type);
        this.test = test;
        System.out.println(test);
    }
    
    public DSNode pureSearch(String s)
    {
        return null;
    }
    
    public DataStructure copy()
    {
        return null;
    }
    
    public State intercept(String[] s)//s is the String of words to be searched
    {
        String noFile = "There is(are) no file(s) containing this word of phrase";
        DataStructure f = null;
        State res = null;
        for(int i = 0;i < s.length;i++)
        {
            if(pureSearch(s[i]) == null)
            {
                res = new State(2,noFile);
                return res;
            }
           
            if(i == 0)//During the first iteration
            {       
                f = pureSearch(s[i]).appendix.copy();   
                continue;
            }
            
            if(f != null)
            {
                search(s[i]).traverse(f);
            }else
            {
                break;
            }
        }
        
        if(f == null)
        {
            res = new State(2,noFile);
            return res;
        }

        String k = String.join(",\n",f.showWords());

        if(k.length() == 0)    
        {
            res = new State(2,noFile);
            return res;
        }else
        {
            k = "Folder(s) containing that word(phrase):\n" + k;
        }
        
        res = new State(2,k);
        return res;
    }
    
    
    public void setFilesOfDicNodeTypes(String type)
    {
        this.appendixType = type;
    }
       
    public DSNode search(String s)
    {
        return null;
    }
    
    public int getLength()
    {
        return length;
    }
    
    public void add(String s,String fileName)
    {
    }
    
    public void add(String s)
    {
    }
    
    public void delete(String s)
    {
    }
    
    public void delete(String s,String fileName)
    {
    }
    
    public int noNodes()
    {
        return 0;
    }
    public String[] showWords()
    {
        return null;
    }
    
    public String[] showWords(DSNode t)
    {
        return null;
    }
   
    public String[] showWords(DSNode t,String s)
    {
        return null;
    }
    
    public String getWords()
    {
        return "";
    }
    
}
