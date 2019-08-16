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


public class TST extends Tree 
{    
    public TST (TSTNode r,String s) 
    {
        super (r,s);       
    }
    
  
    /**Just as createRemaining in Trie
     * 
     * @param current
     * @param k
     * @param s
     * @return 
     */
    
    @Override
    public int getHeight(Node t)
    {
        if(t == null)
        {
            return 0;
        }
        TSTNode a = (TSTNode)t;
        return 1 + Math.max(
        Math.max(getHeight(a.lc),getHeight(a.rc))
        ,getHeight(a.mc));
    }
    
    private TSTNode createRemaining(TSTNode current,int k,String s)
    {
        TSTNode q = null;
        for(int i = k;i < s.length();i++)
        {
            q = current;
            current = new TSTNode(Character.toLowerCase(s.charAt(i)),appendixType);
            q.mc = current;
            current.parent = q;
            current.dos = i;
        }
        return current;
    }
    
    /**Just does what transplant did in AVL , but it's a little bit different
  * 
  * @param u
  * @param v 
  */
    public void transplant(TSTNode u,TSTNode v)
    {
        if(u == root)
        {
            root = v;
            if(v != null)    
            {
                v.parent = null;
            }
            u = null;
            return;
        }else if(u == ((TSTNode)u.parent).lc)
        {
            ((TSTNode)u.parent).lc = v;
        }else if(u == ((TSTNode)u.parent).rc)
        {
            ((TSTNode)u.parent).rc = v;
        }else if(u == ((TSTNode)u.parent).mc)
        {
            ((TSTNode)u.parent).mc = v;
        }
        if(v != null)
        {
            v.parent = u.parent;
        }
        u = null;
        //if u is not the root change the parent's pointer
    }
    
    /**Predecessor of t is the Node having the largest key less than t 
     * 
     * @param t can be any Node from the Tree
     * @return 
     */
    public TSTNode predecessor(TSTNode t)
    {
        TSTNode res = t;
        res = (TSTNode)res.lc;
        TSTNode q = null;    
        while(res != null)
        {
            q = res;
            res = (TSTNode)res.rc;
        }
        res = q;
        return res;
    }
    
    
    public String getWords(DSNode t,String prefix)
    {
        String res = "";
        TSTNode u = (TSTNode)t;
        if(t == null)
        {
            return "";
        }
        if(u.flag == true)
        {
            res =  prefix + u.key + "\n";       
        }
        return getWords(u.lc,prefix) + res + getWords(u.rc,prefix) +
        getWords(u.mc,prefix + Character.toString(u.key)) ;
    }
    
    @Override
    public void add(String s)
    {
        if(s == null)
        {
            return;
        }
        add(s,null);
    }
   
    @Override
    public String[] showWords()
    {
        return showWords((TSTNode)root ,"");
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
        TSTNode suspicious = null;
        
        if(root == null)
        {   
            
            root = new TSTNode(Character.toLowerCase(s.charAt(0)),appendixType);
            ((TSTNode)root).dos = 0;   
            root.parent = null;
            TSTNode current = (TSTNode)root,q = null; 
            current = createRemaining(current,1,s);
            current.flag = true;
            current.addDocument(file);
            no++;
            return;
            
        }else
        {
            TSTNode q = (TSTNode)search(s);
            int dos = q.dos;
            if(Character.toLowerCase(q.key) > Character.toLowerCase(s.charAt(dos)))//add as lc
            {
                q.lc = new TSTNode(Character.toLowerCase (s.charAt(dos)),appendixType);
                ((TSTNode)q.lc).dos = dos;//dos doesn't change
                q.lc.parent = q;
                suspicious = (TSTNode)q.parent;
                TSTNode current = (TSTNode)q.lc , r = null;
//                creating remaining nodes giving each node it's correct dos
                current = createRemaining(current,dos + 1,s);
                current.flag = true;
                no++;
                current.addDocument(file);
            }else if (Character.toLowerCase(q.key) < Character.toLowerCase(s.charAt(dos)))//add as rc
            {
                q.rc = new TSTNode(Character.toLowerCase(s.charAt(dos)),appendixType);
                ((TSTNode)q.rc).dos = dos;//dos doesn't change
                q.rc.parent = q;
                suspicious = (TSTNode)q.parent;
                TSTNode current = (TSTNode)q.rc;
                current = createRemaining(current,dos + 1,s);
                current.flag = true;
                no++;
                current.addDocument(file);
            }else
            {
//                case of equality
                if(q.dos != s.length())
                {
                    q = createRemaining(q,dos + 1,s);
                }
                q.addDocument(file);
                if(q.flag == false)
                {
                    no++;
                    q.flag = true;
                }
            }
            //After adding we should examine the suspicious Node in order to Rotate(if wants any)
            if(suspicious != null)
            {
                while(suspicious != null)
                {
                    if(needsRotation(suspicious))
                    {
                        TSTNode v = (TSTNode)tallestChild(suspicious);
                        TSTNode w = (TSTNode)tallestChild(v);
                        
                        rotate(suspicious,v,w);
//                        System.out.println(s);
                        
                        return;
                    }
                    if(suspicious != root)//It should have some parent
                    {
                        TSTNode par = (TSTNode)suspicious.parent;
                        if(par.mc == suspicious)
                        {
                            return;
                        }
                    }
                    suspicious = (TSTNode)suspicious.parent;
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
    public int noNodes()
    {
        return no;
    }
    
    @Override
    public void delete(String s,String file)
    {
//                It's similar to delete procedure written in AVL class
//        There are a few changes . And for making difference we have implemented
//        another method to get predecessor!
//for the basic delete method
        TSTNode res = null;
        TSTNode physic = null;
        res = (TSTNode)pureSearch(s);
        if(s == null || res == null || res.dos != s.length() - 1)
        {
            
            return;
        }
        res.deleteDocument (file);
        if(res.appendix.getLength () == 0)
        {
            res.flag = false;
            no--;
//            make sure there is no other word based on this word.We can delete that just as 
//                we did in AVL
            if(res.mc == null)
            {
                if(res.lc == null && res.rc == null)
                {                       
//                       maximum iterations = length - 1
//                       one iteration is used during transplant
                    for(int j = 1; j < s.length();j++)
                    {
                        if(res.flag == false && res.rc == null && res.lc == null)
                        {
                            if(res == root)
                            {
                                root = null;
                            }else
                            {
                                TSTNode y = null;
                                y = (TSTNode)res.parent;
                                res = null;
                                res = y; 
                            }        
                        }else
                        { 
                            break; 
                        } 
                    }
                    if(res.flag == true)
                    {
                        return;
                    }     
                }
               if(res.rc == null)
               {
                   physic = (TSTNode)res.parent;
                   transplant(res,(TSTNode)res.lc);
//but we are not finished yet.we should clean the way up to the first proper character!   
               }else if(res.lc == null)
               {
                   physic = (TSTNode)res.parent;
                    transplant(res,(TSTNode)res.rc);
               }else
               {
                   TSTNode predecessor = predecessor(res);
                   physic = (TSTNode)predecessor.parent;
                   predecessor.rc = res.rc;
                   predecessor.rc.parent = predecessor;     
                   if(predecessor == res.lc)//case 3
                   {
                       transplant(res,predecessor);
                   }else if(predecessor != res.lc)//case 4
                   {
                       predecessor.lc = res.lc;
                       predecessor.lc.parent = predecessor;
                       transplant(res,predecessor);
                   }      
               }
            }
        }
        
        TSTNode help = null , par , v = null, w = null;
       
        while(physic != null)   
        {
            if(physic.parent != null)
            {
                par = (TSTNode)physic.parent;
                if(par.mc == physic)
                {
                    break;
                }
            }
            
            if(needsRotation(physic))
            { 
                help = (TSTNode)physic.parent;
                v = (TSTNode)tallestChild(physic);
                w = (TSTNode)tallestChild(v);
                rotate(physic,v,w);
            }
            
            physic = help;
        }
        
    }
    
    @Override
    public DSNode pureSearch(String s)
    {
        TSTNode res = null;
        res = (TSTNode)search(s);
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
    
    
//    The following method searches up to the last node similar with the given string 
//    and then returns that node as the returning value
   
    @Override
    public DSNode search(String s)
    {
        TSTNode res = null;
        int i = 0;
        TSTNode current = (TSTNode)root , q = null;
        while(current != null)
        {
            q = current;
            char u,p;
//            We should ignore case.As we did in AVL and Trie
            u = Character.toLowerCase (s.charAt(i));
            p = Character.toLowerCase (current.key);
            if(p == u)
            {
                current = (TSTNode)current.mc;
                i++;
                if(i >= s.length())
                {
                    break;
                }
            }else if(p < u)//should goto right subtree 
            {
                current = (TSTNode)current.rc;
            }else if(p > u)//should goto left subtree
            {
                current = (TSTNode)current.lc;
            }
        }
//        checking the value of i before everything
        res = q;
        return res;
    }
       
}
