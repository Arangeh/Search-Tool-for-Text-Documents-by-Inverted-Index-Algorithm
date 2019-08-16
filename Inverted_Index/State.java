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
public class State 
{
    public String s;
    public String dir;
    public int dest;//Defines the destination
//1 <-> String is to be processed in input ; 2<-> String is to be processed in terminal 
//    3 <-> String is to be processed in archive and the returned back another state
//    from ComplexButtonHandler (CBH)
//    4 <-> String is to be processed in archive and the returned back another state
//    from ComplexInputHandler (CIH)
    public State(int num,String str)
    {
        s = str;
        dest = num;
    }
    public State(int num,String str,String dir)
    {
        this(num,str);
        this.dir = dir;//defines directory for states created for CBH and CIH
    }
}
