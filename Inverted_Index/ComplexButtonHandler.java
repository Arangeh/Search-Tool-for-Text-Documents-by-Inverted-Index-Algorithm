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
public class ComplexButtonHandler extends SwingWorker < Void , State >
{
    private String lastDirectory,button,dicType,archiveType;//Can be one of these : Build , Reset 
    private String stopType,filesOfDicNodesType;
    
    private File sample;
    private File dirFile;//files located in that special directory
    
    private JTextArea terminal;
    private Stack forward,backward;
    private Tree dic;
    private State sent,received;
    private final Random generator = new Random();

    private Archive archive;
    private Object stopWords;
    private DataStructure dictionary;
    
    public ComplexButtonHandler(String button,JTextArea terminal,
            Stack forward,Stack backward)
    {
        this.button = button;
        this.terminal = terminal;
        this.forward = forward;
        this.backward = backward;
    }

    public void buildPath(String s)
    {
        lastDirectory = s;
        sample = new File(lastDirectory);
    }
    
    
    public void setArchiveType(Archive a,String aType,String filesType)
    {
        archiveType = aType;
        archive = a;
        archive.setDirecory (lastDirectory);
        archive.stopWordsType = stopType;
        archive.archiveType = aType;
        archive.dicType = dicType; 
        archive.setFilesOfDicNodesType(filesType);
    }
    
    public void setDicType(DataStructure d,String dType)
    {
        dictionary = d;
        dicType = dType;
        
    }
    
    public void setFilesOfDicNodeType(String h)
    {
        filesOfDicNodesType = h;
    }
    
    public void setStopWordsType(Object s,String sType)
    {
        stopWords = s;
        stopType = sType;
        
    }
        
    public void setStates()
    {
        if(sample.exists())           
        {
//                    to build the dictionary based on the current directory
            try
            {                
                Thread.sleep(generator.nextInt (5));
            }
            catch(InterruptedException ex)
            {
                Logger.getLogger (ComplexInputHandler.class.getName()).log (Level.SEVERE,null,ex);
            }

            if(!sample.isDirectory())
            {   

                sample = sample.getParentFile();
            }
    //                now We are sure about that sample is an actual directory!
            sent = new State(3,button , sample.getAbsolutePath ());
            
            received = archive.stateAnalyze (sent);
            publish(received);   
        }else
        {
            State s = new State(2,"Either file or directory or both don't exist");
            publish(s);  
        }
    }
    
//    Methods with huge processing functions are implemented here
    @Override
    public Void doInBackground()
    {
        if(button.equals("Build"))
        {
            
            setStates();
           
        }
        if(button.equals("Reset"))
        {
//        to remove all the previous divtionaries and start everything from scratch
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
    protected void process (List< State > chunks) 
    {
        for(int i = 0;i < chunks.size ();i++)
        {
            
            if(chunks.get(i).dest == 2)//for terminal
            {
                terminal.append("\n" + chunks.get(i).s);
                terminal.setCaretPosition(terminal.getText().length());
            }
        }
    }
 
    
}