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

public class DSNode
{
    protected DataStructure appendix;
    String appendixType;
    String test;
    public DSNode(String type)
    {
        this.appendixType = type;
        if(appendixType.equals("LinkedList"))
        {
            
            appendix = new LL("LinkedList");//to make sure that appendix is 
//            instantiated before use
                
        }//else
    }
    public DSNode(String s,String type)
    {
        this.appendixType = type;
        if(appendixType == null)
        {
            return;
        }
        if(appendixType.equals("LinkedList"))
        {
            appendix = new LL(null);//to make sure that appendix is 
//            instantiated befor use

        }//else
    }
    
    
    public DSNode()
    {
    }
    
    public void traverse(DataStructure y)
    {
        
        String[] h = y.showWords();
        for(int i = 0;i < h.length;i++)
        {
            if(appendix.pureSearch(h[i]) == null)//h[i] is not found in the appendix hence should be deleted
            {
                y.delete(h[i]);
            }
        }
    }
    
    public void addDocument(String docName)
    {
        appendix.add(docName);
    }

    public void deleteDocument(String docName)
    {
        appendix.delete(docName);
    }

    public void updateDocument(String docName)
    {
        appendix.add(docName);
        appendix.delete(docName);
    }
}
