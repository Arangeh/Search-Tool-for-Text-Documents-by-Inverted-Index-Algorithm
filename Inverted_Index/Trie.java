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
public class Trie extends Tree
{

    public Trie (Node r,String s) 
    {
        super (r,s);
    }
    
    //\\///\\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\//\\//\\//\\//\\//\\//\\//\\//\\//\\
    
    
    @Override
    public int getHeight(Node t)
    {
        if(t == null)
        {
            return 0;
        }
        TrieNode a = (TrieNode)t;
        int res = 0;
        for(TrieNode b:a.childs)
        {
            res = Math.max(res,getHeight(b));
        }
        return res + 1;
    }
    
    public String getWords(DSNode t,String prefix)
    {
        TrieNode u = (TrieNode)t;
        if(t == null)
        {
            return "";
        }
        String res = "";
        if(u.flag == true)
        {
            res = res + prefix + "\n";
        }
        for(TrieNode y:u.childs)
        {
            res = res + getWords(y,prefix + Character.toString(y.key));
        }
        return res;
    }
    
    /**The following method acts like the method with the same name in TST,but a little change on it
     * 
     * @param current All the remaining Characters will be built from this Node
     * @param s The main String having a proper word
     * @param index is the index from which creating the remaining Nodes begins
     * @return Returns the last Node built.It's needed somewhere else(flagging,adding Document and...)
     */
    private TrieNode createRemaining(TrieNode current,String s,int index)
    {
        TrieNode c = null;
        for(int i = index;i < s.length();i++)
        {
            c = new TrieNode(Character.toLowerCase(s.charAt(i)),appendixType);
            c.dos = i;
            current.childs.add(c);
            c.parent = current;
            current = c;
        }
        return current;
    }
    
    
    //\\///\\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\\//\//\\//\\//\\//\\//\\//\\//\\//\\//\\
    
    //All the Overridden methods are explained at "DataStructure.java"
    @Override
    public int noNodes()
    {
        return no;
    }
    
    @Override
    public String[] showWords()
    {
        return showWords((TrieNode)root,"");
    }
    
    @Override
    public String[] showWords(DSNode t,String prefix)
    {
        String[] h = getWords(t,prefix).split("\\s+");
        return h;
    }
    
    @Override
    public void add(String s,String file)
    {
        if(s == null)
        {
            return;
        }
        if(root == null)
        {
            root = new TrieNode('-',appendixType);
            root.parent = null;
            ((TrieNode)root).dos = -1;
            TrieNode current = (TrieNode)root;
            current = createRemaining(current,s,0);
            current.flag = true;
            current.addDocument(file);
            no++;
            return;
        }
        TrieNode res = (TrieNode)search(s);
        res = createRemaining(res,s,res.dos + 1);
        if(res.flag == false)
        {
            res.flag = true;
            res.addDocument(file);
            no++;
        }else//There existed something before.We just try to update the files    
        {
            res.addDocument(file);
        }
    }

    /**Adds just a simple String to the Trie(not having any kind of appendixes) 
     *
     * @param s The key to be added to The Trie
     */
    
    @Override
    public void add(String s)
    {
        add(s,null);
    }
    
    @Override
    public void delete(String s,String file)
    {
        
        TrieNode res = (TrieNode)pureSearch(s);
        if(res == null)
        {
            return;
        }
        if(s == null || res.dos != s.length() - 1)
        {
            return;
        }
        res.deleteDocument (file);
        if(res.appendix.getLength () == 0)
        {
            res.flag = false;
            no--;
//            at the first step check if there is another proper word based on this word

            while(!res.hasChild() && res.flag == false)
            {
                if(res == root)
                {
                    ((TrieNode)root).deleteChilds();
                    root = null;
                    return;
                }else
                {
                    TrieNode y = (TrieNode)res.parent;
//                                           deleting res childs;   
                    y.childs.remove(y.childs.indexOf(res)); 
                    res = y;
                }                
            }
        }
    }
    
    @Override
    public void delete(String s)
    {
        delete(s,null);
    }
    
    @Override
    public DSNode pureSearch(String s)    
    {
        TrieNode res = null;
        res = (TrieNode)search(s);
        if(res != null)
        {
            if(res.key != s.charAt(s.length() - 1)
            || res.flag == false)
            {
                res = null;
            }
        }
        return res;
    }
    
    @Override
    public DSNode search(String s)
    {
        TrieNode res = null,q = null;
        res = (TrieNode)root;
        int loc = -1;//the location of next position of res
        while(res != null)
        {
            q = res;
            
//            if a character of string s matches with one of res childs key 
            if(res.dos >= s.length() - 1)
            {
                return res;
            }
            loc = res.childs.indexOf(new TrieNode(Character.toLowerCase(s.charAt(res.dos + 1)),
            appendixType)); 
            
            if(loc != -1)
            {
                res = res.childs.get(loc);
            }else    
            {
                break;
            }
            
        }
        return res;
    }
 
    
}
