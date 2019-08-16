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
public class AVLNode extends Node
   
{
    public String key;    
//    public AVLNode lc,rc;//Left child and Right child pointers respectively        
    public AVLNode(String s,String type)
    {
        super(type);
        key = s;
    }
}
