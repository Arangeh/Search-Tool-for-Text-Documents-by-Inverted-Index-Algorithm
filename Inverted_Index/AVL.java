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


public class AVL extends Tree 
{
    

    public AVL (AVLNode root,String s) 
    {
        super (root,s);
    }
    
   
    
    @Override
    protected Node tallestChild(Node t)
    {
        return super.tallestChild(t);
    }
    
    @Override
    public int getHeight(Node t)
    {
       
        return getRTHeight(t);
    }
    
    
    
    public void trace(AVLNode y)
    {
        if(y.lc != null)
        {
//            System.out.println("LC = " + y.lc.key + "; Number of files connected = "
//            + y.lc.appendix.getLength());
        }
        if(y.rc != null)
        {
//            System.out.println("RC = " + y.rc.key + "; Number of files connected = "
//            + y.rc.appendix.getLength());
        }
        if(y.lc == null && y.rc == null)
        {
            System.out.println("It doesn't have a child!");
        }
    }
    
    public AVLNode successor(AVLNode t)
    {
        AVLNode res = t;
        res = (AVLNode)res.rc;
        AVLNode q = null;    
        while(res != null)
        {
            q = res;
            res = (AVLNode)res.lc;
        }
        res = q;
        return res;
    }
    
    /**The following method brings v in u's place.
     * 
     * @param u
     * @param v 
     */
    public void transplant(AVLNode u,AVLNode v)
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
        }else if(u == ((AVLNode)u.parent).lc)
        {
            ((AVLNode)u.parent).lc = v;
        }else if(u == ((AVLNode)u.parent).rc)
        {
            ((AVLNode)u.parent).rc = v;
        }
        if(v != null)
        {
            v.parent = u.parent;
        }
        u = null;
        //if u is not the root change the parent's pointer       
    }

    
    @Override
    public int noNodes()
    {
        return no;
    }
    
    
    
    public String getWords(DSNode r)//just like In Order traversal
    {
        AVLNode u = (AVLNode)r;
        if(r == null) 
        {
            return "";
        }
        return getWords(u.lc) +  u.key + "\n" + getWords(u.rc);
    }
    
    @Override
    public String getWords()
    {
        return getWords(root);
    }
    
    
//    showing words in the AVL Tree can be done by even a simple preorderTraversal
    @Override
    public String[] showWords()
    {
        return showWords((AVLNode)root);
    }
    
    @Override
    public String[] showWords(DSNode r)
    {
        String[] h = getWords(r).split("\\s+");
        return h;
    }
    
//    We have overloaded the method add so that it can create only the nodes (Linked Lists are not build)
    
    @Override
    public void add(String s)
    {
        add(s,null);
    }
 
//    s is the key that we want to be added
//    file is the file Name that has the given word as the first parameter
    @Override
    public void add(String s,String file)
    {
//        System.out.println(file);
        if(root == null)
        {
            root = new AVLNode(s.toLowerCase(),appendixType);
            root.parent = null;
            root.addDocument(file);
            no++;
        }else
        {
            AVLNode q = (AVLNode)search(s.toLowerCase());
            AVLNode added = new AVLNode(s.toLowerCase(),appendixType);
            added.addDocument(file);
            if(q.key.compareToIgnoreCase (s) > 0)
            {
                q.lc = added;
                no++;
                added.parent = q;
            }else if(q.key.compareToIgnoreCase (s) < 0)
            {
                q.rc = added;
                no++;
                added.parent = q;
            }else if(q.key.compareToIgnoreCase (s) == 0)
            {
//                equality holds.Linked List should be updated
                q.addDocument(file);
            }
            if(q.key.compareToIgnoreCase(s) != 0)
            {
                AVLNode u = (AVLNode)q.parent;
                while(u != null)
                {
                    //Check the rotation condition
                    if(needsRotation(u))
                    {
                        AVLNode v = (AVLNode)tallestChild(u);
                        AVLNode w = (AVLNode)tallestChild(v);
                        rotate(u,v,w);
                        return;
                    }
                    u = (AVLNode)u.parent;
                }
            }
        }   
    }  
 
    
    @Override
    public void delete(String s,String fileName)
    {
        
        AVLNode g = (AVLNode)root;  
        AVLNode res = null;
        AVLNode physic = null;//refers to the parent of the node that is 'physically deleted'
//        The physically deleted node is the Node that causes the topological change of the tree
        if((AVLNode)pureSearch(s) == null)
        {
            return;
        }
        res = (AVLNode)search(s);
        res.deleteDocument (fileName);
        if(res.appendix.getLength() == 0)//It's not in a file anymore
        {
            
            no--;
            if(res.key.equals(s))//now we should actually implement delete!
            {
                if(res.lc == null)//case 1
                {
                    
                    physic = (AVLNode)res.parent;
                    transplant(res,(AVLNode)res.rc);
                    
                }else if(res.rc == null)//case 2
                {
                    physic = (AVLNode)res.parent;
                    transplant(res,(AVLNode)res.lc);
                }else
                {
                    AVLNode successor = successor(res);
                    physic = (AVLNode)successor.parent;
                    successor.lc = res.lc;
                    successor.lc.parent = successor;
                    if(successor == res.rc)//case 3
                    {   
                        transplant(res,successor);
                    }else if(successor != res.rc)//case 4
                    {
                        transplant(successor,(AVLNode)successor.rc);
                        successor.rc = res.rc;
                        successor.rc.parent = successor;
                        transplant(res,successor);
                        res = successor;     
                    }
                    
                }
                AVLNode v = null,w = null,help = null;
                
                while(physic != null)
                {
                    help = (AVLNode)physic.parent;
                    if(needsRotation(physic))
                    {
                        v = (AVLNode)tallestChild(physic);
                        w = (AVLNode)tallestChild(v);
                        rotate(physic,v,w);
                    }
                    
                    physic = help;
                }
            }
        }
        
    }
//    It's based on the search method
    
    @Override
    public void delete(String s)
    {
        AVLNode res = null;
        
        res = (AVLNode)search(s);
        if(s == null || res == null)
        {
            return;
        }
        if(res.key.equals(s))
        {
            delete(s,null);
        }
    }
    
    @Override
    public DSNode pureSearch(String s)
    {
        AVLNode res = (AVLNode)search(s);
        if(res != null)
        {
            if(res.key.compareToIgnoreCase(s) != 0)
            {
                res = null;
            }
        }
        return res;
    }
    
    
//    The following method searches the entire tree to find a node that has s as the key
//    This is used for adding and deleting Nodes.We have also a 'pureSearch' method which searches
//    more precise

    @Override
    public DSNode search(String s)
    {
        AVLNode res = null;
        if(root == null || s == null)
        {
            return res;
        }
        AVLNode current = (AVLNode) root , q = null;//q is following root
        while(current != null)
        {
            q = current;
            if(current.key.compareToIgnoreCase(s) > 0)//should go to left subtree
            {
                current = (AVLNode)current.lc;
            }else if (current.key.compareToIgnoreCase(s) < 0)//should go to right subtree
            {
                current = (AVLNode)current.rc;
            }else if(current.key.compareToIgnoreCase (s) == 0)
            {
                break;
            }         
        }
        res = q;
        return res;
    }
}
