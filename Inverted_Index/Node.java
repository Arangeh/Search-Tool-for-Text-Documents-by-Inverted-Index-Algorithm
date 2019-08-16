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
public class Node extends DSNode
{
      protected Node parent;
      protected Object fileContaining;
      protected Node lc,rc,mc;
      
      public Node(String type)
      {
          super(type);  
      }
      
      public Node(String s,String type)
      { 
          super(s,type); 
      }
}
