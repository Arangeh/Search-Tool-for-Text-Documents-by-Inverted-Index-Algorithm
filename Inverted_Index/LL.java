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
public class LL extends DataStructure
{
    private LLNode start;
    public int concatFactor;
    public LL(String s)
    {
        start = new LLNode(null,s);
    }
    
    @Override
    public DataStructure copy()
    {
        LL res = new LL(null);
        LLNode t = start;
        while(t != null)
        {
            if(t.fileName != null)
            {
                res.add(t.fileName);
            }
            t = t.next;
        }
        
        return res;
    }
    
    @Override
    public int getLength()
    {
        return length;
    }
    
       //        Here we search the Linked List.In the case of not finding the containing Text File
    //        we add the file and some other information as a node to the Linked List
    //        Otherwise the Linked List would be updated in the best case.

    
    @Override
    public void add(String s)
    {
        if(s == null)
        {
            return;
        }
        boolean flag = true;
        if(length == 0)
        {
            start.next = new LLNode(s,null);
            length = 1;
            return;
        }else
        {
            LLNode current = start,q = null;
            while(current != null)
            {
                if(current != start)
                {
                    if(current.fileName.equals(s))
                    {
                        flag = false;
                    }
                }
                q = current;
                current = current.next;   
            }
            if(flag)
            {
                q.next = new LLNode(s,"LinkedList");
                length++;
            }       
        }   
    }
    

    /**
     * 
     * //    'delete' mothod is based on search.It deletes the first occurace of key
     *  //    In the first phase of the project there is only one occurance of each key in the Linked List
     *  //    However in the second phase it would be entirely different
     * @param key 
     */
    @Override
    public void delete(String key)
    {
        if(key == null)
        {
            return;
        }
        LLNode t = (LLNode)search(key);
        if(t != null)//the node after t should be deleted
        {
            LLNode cur = t.next;
            t.next = cur.next;
            cur = null;//delete cur
            length--;
        }
        
    }
    
    @Override
    public DSNode pureSearch(String key)
    {
        return search(key);
    }
    
//    searchs a given key(File Name) in the entire Linked List . It's used fo intersection
    @Override
    public DSNode search(String key)
    {
        LLNode res = null;
        LLNode current = start,q = null;
        if(current.next != null)
        {
            q = current;//start
            current = current.next;
        }else
        {
            return null;
        }
        while(current != null)
        {        
            if(current.fileName.equals(key))
            {
                res = q;//return the node that precedes the desired node having the key
                break;//while should be terminated
            }
            q = current;
            current = current.next;
        }
        return res;
    }
    
    
    @Override
    public  String[] showWords()
    {
        return showWords(start.next);
    }
    
    @Override
    public String[] showWords(DSNode r)
    {   
        String[] res = new String[length];
        int i = 0;
        while(r != null)
        {
            res[i] = ((LLNode)r).fileName;
            r = ((LLNode)r).next;
            i++;
        }
        return res;
    }
}
