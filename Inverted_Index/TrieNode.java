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
public class TrieNode extends Node
{
    protected ArrayList< TrieNode > childs;
    protected char key;//The character that would be stored in each Node
    boolean flag;//shows if there is a valid LinkedList from this node or not
    public int dos;//depth of search!   
    /**
     * 
     * @param k
     * @param type defines The type of appendix(see DSNode)
     */
    public TrieNode (char k,String type) 
    {
        super (type);
        flag = false;
        childs = new ArrayList < TrieNode >(); 
        key = k;
    }

    /**Deletes all childs of a Node if there is any
     * 
     */
    public void deleteChilds()
    {
        for(int i = 0;i < childs.size();i++)
        {
            childs.remove(0);
        }
        childs = null;
    }
    
    
    public boolean hasChild()
    {
        boolean res = true;
        if(childs.size() == 0)
        {
            res = false;
        }
        return res;
    }
    
    @Override
    public boolean equals (Object obj) 
    {
        boolean res = false;    
        if(obj == null) return false;
         else if (!(obj instanceof TrieNode)) return false;
         else 
         {
             if(((TrieNode)obj).key == key)
             {
                 res = true;
             }
         }
        return res;
    }
}
