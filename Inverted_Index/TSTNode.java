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
public class TSTNode  extends Node
{    
//    TSTNode mc;//Nodes for the left,right and mid-child respectively
    
    public char key;//just Like key in TrieNode
    public boolean flag;//shows if there is a valid LinkedList from this node or not
    public int dos;//depth of search!
    
    public TSTNode(char c,String type)
    {
        super(type);
        key = c;
        flag = false;
    }
}
