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
public class LLNode extends DSNode
{
    public String fileName;
    public String phrase;//for the next phases of the project
    public int line;//for the next phases of the projet
    public int position;       
    public LLNode next;
    public LLNode dlink;//for the next phases of the project
    public LLNode(String docName,String type)
    {
        super();
        fileName = docName;
    }
}
