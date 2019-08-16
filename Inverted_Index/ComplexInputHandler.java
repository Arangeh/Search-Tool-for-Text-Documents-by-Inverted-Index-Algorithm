/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package invertedindex;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author alireza
 */
public class ComplexInputHandler extends SwingWorker < Void , State >
{
    
    private final KeyEvent e;
    private final JTextField input;
    private final JTextArea terminal;
    private String lastCommand,dicType,stopType,archiveType,lastDirectory
    ,filesOfDicNodesType;
    
    private final Stack forward,backward;
    private final Random generator = new Random();
    private File sample;
    private State sent,received;
    
    private DataStructure dictionary;//dictionary tree
    private Archive archive;//archieve of FileStructure objects
    private Object stopWords;
    
//    Constructor
//    dicType is the dictionary type that we want to be implemented
    public ComplexInputHandler(KeyEvent e,JTextField input,JTextArea terminal,
            Stack forward,Stack backward)
    {
        this.e = e;
        this.input = input;
        this.forward = forward;
        this.backward = backward;
        this.terminal = terminal;    
    }
    
    public void setArchiveType(Archive a,String aType,String filesType)
    {
        archiveType = aType;
        archive = a;
        archive.setFilesOfDicNodesType(filesType);
    }
    
    public void setDicType(DataStructure d,String dType)
    {
        dictionary = d;
        dicType = dType;
    }
    
    public void setFilesOfDicNodesType(String h)
    {
        filesOfDicNodesType = h;
    }
    
    public void setStopWordsType(Object s,String sType)
    {
        stopWords = s;
        stopType = sType;
        
    }
    
    public void buildPath(String s)
    {
        lastDirectory = s;
        
        sample = new File(lastDirectory);
    }
    
    
    private void setStates()
    {
        sent = new State(4,lastCommand,lastDirectory);
        received = archive.stateAnalyze (sent);
        try
        {               
            Thread.sleep(generator.nextInt (5)); 
        }
        catch(InterruptedException ex)
        {
            Logger.getLogger (ComplexInputHandler.class.getName()).log (Level.SEVERE,null,ex);
        }   
        
        publish(received);
    }
    
//    Methods with huge processing functions are implemented here
    @Override
    public Void doInBackground()
    {
        if(e.getKeyCode () == KeyEvent.VK_UP)//Upward arrow pressed
            {
                lastCommand = (String)forward.pop();
                backward.push(lastCommand);
                State s = new State(1,lastCommand);
                try
                {                
                    Thread.sleep(generator.nextInt (5));
                }
                catch(InterruptedException ex)
                {
                    Logger.getLogger (ComplexInputHandler.class.getName()).log (Level.SEVERE,null,ex);
                }
            
                publish(s);
                if(forward.isEmpty())
                {
                    forward.push(lastCommand);
                }
                
            }else if(e.getKeyCode() == KeyEvent.VK_DOWN)//Downward arrow pressed
            {
                lastCommand = (String)backward.pop();
                forward.push(lastCommand);
                State s = new State(1,lastCommand);
                try
                {               
                    Thread.sleep(generator.nextInt (5)); 
                }
                catch(InterruptedException ex)
                {
                    Logger.getLogger (ComplexInputHandler.class.getName()).log (Level.SEVERE,null,ex);
                }   
                publish(s);
                if(backward.isEmpty())
                {
                    backward.push(lastCommand);
                }
            }else if(e.getKeyChar() == '\n')
            {
//                now we should save the Command in the history as the first step
                lastCommand = input.getText();
                String top = null;
                if(forward.peak() != null)
                {
                    top = (String)forward.peak();
                }
                if(lastCommand != null)
                {
                    if(!lastCommand.equals(top))//doesn't create error event if 'top' is 'null'
                    {
                        forward.push(lastCommand);
                    }
                }
                publish(new State(1,""));//clearing the input TextField
        
//                as the second step e should process the command.which type is it.
//                we do it as we did similarly in ComplexButtonHandler : by States
                setStates(); 
            }
        return null;
    }

//    The 'doInBackground' method will be culminated in running this method
    @Override
    protected void done()
    {
    }
    
//    To process the published values from the 'doInBackground' method we have implmented
//    the following method
    @Override
    protected void process (List <State> chunks) 
    {
        for(int i = 0;i < chunks.size ();i++)
        {
            if(chunks.get(i).dest == 1)
            {
                input.setText(chunks.get(i).s);
            }
            if(chunks.get(i).dest == 2)
            {
                terminal.append("\n" + chunks.get(i).s);
                terminal.setCaretPosition(terminal.getText().length());
            }
        }
    }
}
