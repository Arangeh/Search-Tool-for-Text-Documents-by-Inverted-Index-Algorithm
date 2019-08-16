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
public class Stack 
{
    static int maxSize = 100;
    ArrayList<Object> s; 
    //constructor
    public Stack()
    {
        s = new ArrayList< Object >();
    }
    public void push(Object o)
    {
        if(o != null)
        {
            s.add(o);
        }
    }
    
    public Object pop()
    {
        Object res = null;
        if(!s.isEmpty())
        {
            res = s.remove(s.size() - 1);
        }
        return res;
    }
    
    /**To see the top of the stack without popping from it
     * 
     * @return 
     */
    public Object peak()
    {
        Object res = null;
        if(!isEmpty())
        {
            res = s.get(s.size() - 1);
        }
        return res;
    }
    public boolean isFull()
    {
        boolean flag = false;
        if(s.size() >= maxSize)
        {
            flag = true;
        }
        return flag;
    }
    
    public boolean isEmpty()
    {
        boolean flag = true;
        if(s.size() != 0)
        {
            flag = false;
        }
        return flag;
    }
}
