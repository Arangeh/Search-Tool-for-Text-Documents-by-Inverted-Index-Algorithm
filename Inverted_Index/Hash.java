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



public class Hash extends DataStructure
{
//    Hash by chaining usign AVLs as the Array Elements
    private int htSize;//defines the size of the Hash Table
    private String chainType;//Defines the DataStructure used for chaining.It is set to be 
//    AVL in the program
 
    private DataStructure[] table;
    public Hash(int len,String s)
    {
//       super(s);//It doesn't matter for us.see line 34.the variable s is used there
       chainType = "AVL";
       htSize = len;
       table = new DataStructure[len];
       if(chainType.equals("AVL"))
       {
           for(int i = 0;i < htSize;i++)
           {    
               table[i] = new AVL(null,s);
           }
       }
       no = 0;
    }
    
    private int hashFunc(String s)
    {
        int res = 0;
        s = s.toLowerCase();//All the words are stored in LowerCase mode
        for(int i = 0;i < s.length();i++)   
        {
            res += s.charAt(i);
        }
        res = res % htSize;
        return res;
    }
   
    @Override
    public String getWords()
    {
        String res = "";
        for(int i = 0;i < htSize;i++)
        {
            res = res + table[i].getWords();//get the Words of the i-th chain
        }
        return res;
    }
    
    @Override
    public String[] showWords()
    {
        String[] h = getWords().split("\\s+");
        return h;
    }
    
    @Override
    public void delete(String s)
    {
        
        delete(s,null);
    }
    
    @Override
    public void delete(String s,String fileName)
    {
        int index = hashFunc(s);
        no -= table[index].no;
        table[index].delete(s,fileName);
        no += table[index].no;
    }
    
    @Override
    public void add(String s)
    {
        add(s,null);
    }
    
    @Override
    public void add(String s,String fileName)
    {
        int index = hashFunc(s);
        no -= table[index].no;
        table[index].add(s,fileName);
        no += table[index].no;
    }
    
    @Override
    public DSNode search(String s)
    {
        return table[hashFunc(s)].search(s);
    }

    @Override
    public DSNode pureSearch(String s)
    {
        return table[hashFunc(s)].pureSearch(s);
    }
}
