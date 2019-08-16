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

public class Tree extends DataStructure
{
    
    protected Node root;    
    private int count;//helping variable while searching the tree in TST and Trie
    public Tree(Node r,String s)
    {
        super(s);
        no = 0;
        count = 0;
        root = r;
        if(root != null)
        {
            root.parent = null;
            no = 1;//It'll be modified during the 
//            construction of a typical Trie  because the root won't be counted
        }
    }
   
    public int getHeight(Node t)
    {
        return 0;
    }
    
    
    //    the most important method . But will be implemented in the next phases of the project
    //q is consedered to be the p's tallest child.And r is considered to be the q's tallest child
    public void rotate(Node p,Node q,Node r)
    {

//      case 1 : LL
        if(q == p.lc && r == q.lc)
        {
            p.lc = q.rc;
            q.rc = p;
            q.parent = p.parent;
            correctParent(q,p);

            if(p == root)
            {
                root = q;
            }
        }
        
//      case 2 : RR
        if(q == p.rc && r == q.rc)
        {
            p.rc = q.lc;
            q.lc = p;
            q.parent = p.parent;
            correctParent(q,p);
            if(p == root)
            {
                
                root = q;
            }
        }
        
//      case 3 : LR
        if(q == p.lc && r == q.rc)
        {
            q.rc = r.lc;
            r.lc = q;
            p.lc = r.rc;
            r.rc = p;
            r.parent = p.parent;
            correctParent(r,p);
           
            if(p == root)
            {
                root = r;
            }
         
        }
        
//      case 4 : RL
        if(q == p.rc && r == q.lc)
        {
            p.rc = r.lc;
            r.lc = p;
            q.lc = r.rc;
            r.rc = q;
            r.parent = p.parent;
            correctParent(r,p);
            
            if(p == root)
            {
                root = r;
            }
        }
        
        correctChilds(p);
        correctChilds(q);
        correctChilds(r);
        
    }
    
    //    by calling the following method y's parent doesn't refer to y anymore!
//    It refers to x as it's new Left or Right child
    private void correctParent(Node x,Node y)
    {
        if(y.parent != null)
        {
            Node par = y.parent;
            if(y == par.lc)
            {
                par.lc = x;
            }else if(y == par.rc)
            {
                par.rc = x;
            }else if(y == par.mc)
            {
                par.mc = x;
            }
        }
    }
    
    private void correctChilds(Node t)
    {
        if(t.lc != null)
        {
            t.lc.parent = t;
        }
        if(t.rc != null)
        {
            t.rc.parent = t;
        }
    }
    
    protected boolean needsRotation(Node t)    
    {
        boolean res = false;
        if(Math.abs(getRTHeight(t.lc) - getRTHeight(t.rc)) >= 2)//problems with balance factor
        {
            res = true;
        }
        return res;
    }
    
    protected int bf(Node t)
    {
        return Math.abs(getRTHeight(t.lc) - getRTHeight(t.rc));
    }
    
    protected Node tallestChild(Node t)
    {
        Node res = null;
        res = (getRTHeight(t.rc) > getRTHeight(t.lc)) ? t.rc : t.lc ;
        return res;
    }
  
    public int getRTHeight(Node t)
    {
         if(t == null)
        {
            return 0;
        }

        return 1 + Math.max(getRTHeight(t.lc),getRTHeight(t.rc));
    }
    
    
}
