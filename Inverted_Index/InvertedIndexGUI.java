/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package invertedindex;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author alireza
 */
public class InvertedIndexGUI extends JFrame
{
    
   
    private Object stopWords;//can be TST,AVL,Trie,ArrayList
    private DataStructure dictionary;//can be TST,AVL,Trie
    private Archive archive;
    
    private String lastDirectory;
    private String lastCommand;
    private String dicType;
    private String filesOfDicNodesType;
    
    private int cursorPos;//To illustrate the cursor position in 'input' JTextField
    private JTextArea terminal;
    private JTextField directory,input;
    
    private JLabel browseDescription,inputDescription;
    private JScrollPane terminalScroll; 
    private Container container;
    private JPanel browseDescriptionPanel,fileChoosingPanel,terminalPanel,
            dsPanel,inputPanel,buttonsPanel,togglesPanel;
    private JButton build,reset,help,exit,browse;
    private ButtonHandler bhandler;
    private InputHandler ihandler; 
    private RadioButtonHandler rhandler;
    
    private JRadioButton tst;
    private JRadioButton avl;
    private JRadioButton trie;
    private JRadioButton hash;//for the second phase
    
    private Stack forward,backward;
//    forward and backard make up the entire History of commands together.And they
//    are each other's complements
//    Up arrow pops from forward and pushes into backward
//    Down arrow pops from backward and pushes into forward
    private ButtonGroup group;
    private class RadioButtonHandler implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent event)
        {
            JRadioButton radioButton = (JRadioButton) event.getSource();
            if(radioButton.isSelected())
            {
                dicType = radioButton.getText();
            }           
        }
    }
    
    
    /**This is an Inner Class used for registering KeyListener Objects
     * 
     */
    private class InputHandler extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            ComplexInputHandler cih = new ComplexInputHandler(e,input,terminal,forward,backward);
//            parameters related to archive can be other options in the second phase
//            But we should be able to change dictionary parameters even during the first step  
            cih.setDicType (dictionary,dicType);
            cih.setStopWordsType (stopWords,"ArrayList");
            cih.setFilesOfDicNodesType(filesOfDicNodesType);
            cih.setArchiveType (archive,"ArrayList",filesOfDicNodesType);
            if(lastDirectory == null)
            {
                lastDirectory = "";
            }
            cih.buildPath(lastDirectory);
            cih.execute ();
        }
    }
    
    /**This is an Inner Class used for registering ActionListener Objects
     * 
     */
    private class ButtonHandler implements ActionListener 
    {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            ComplexButtonHandler cbh = new ComplexButtonHandler(((JButton)e.getSource()).getText()
                    ,terminal,forward,backward);
            
            if(lastDirectory == null)
            {
                lastDirectory = "";
            }       
            cbh.setDicType(dictionary,dicType);
            cbh.buildPath(lastDirectory);
            cbh.setStopWordsType(stopWords,"ArrayList");//other options are for the second phase
            cbh.setFilesOfDicNodeType(filesOfDicNodesType);
            cbh.setArchiveType(archive,"ArrayList"
            ,filesOfDicNodesType);//there can be other options for the second phase
            
            if(e.getSource() == build)
            {
//                To build the dictionary based on the Current Directory
//                Here is the point we use the SwingWorker subclass "ButtonHandler"
//                which is implemented in detail in it's relevant class
                
                cbh.execute ();
            }
            if(e.getSource() == reset)
            {
//                To build the dictionary based on the Current Directory
//                Here is the point we use the SwingWorker subclass "ButtonHandler"
//                which is implemented in detail in it's relevant class            
                int dialogButton = JOptionPane.YES_NO_OPTION; 
                dialogButton = 
                        JOptionPane.showConfirmDialog (null, "Are you willing to reset?"
                                + ".The entire dictionary would be deleted and "
                                + "all the data will be lost","Warning", 
                                JOptionPane.YES_NO_OPTION); 
                if(dialogButton == JOptionPane.YES_OPTION)
                {
                    cbh.execute();
                }   
            }
            if(e.getSource() == help)
            {
//            To display the help of the program.Which commands are valid and what they do
//                and all of the features of the program is documented here.Unfortunately It's not
//                implemented during the 1st phase
            }

            if(e.getSource() == exit)
            {
                int dialogButton = JOptionPane.YES_NO_OPTION; 
                dialogButton = 
                        JOptionPane.showConfirmDialog (null, "Are you sure?","Warning", 
                                JOptionPane.YES_NO_OPTION); 
                
                if(dialogButton == JOptionPane.YES_OPTION) 
                { 
                    System.exit(0); 
                }               
            }
            
            if(e.getSource() == browse)
            {
                
//                JFileChooser chooser = new JFileChooser();
//The following 2 are some dorectories in My Windows!
//                JFileChooser chooser = new JFileChooser("D:\\BACKUP\\Computer Science\\"
//                        + "Data Structures\\Homeworks\\Project1\\docs");
//                
//                JFileChooser chooser = new JFileChooser("C:\\Users\\alireza\\Documents\\"
//                        + "NetBeansProjects\\DS\\Inverted Index\\test");
                JFileChooser chooser = new JFileChooser(
                "D:\\BACKUP\\Computer Science\\Data Structures\\Homeworks\\Project1\\MyFile\\all");
                
                chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                if(lastDirectory != null)   
                {
                    chooser.setCurrentDirectory (new File(lastDirectory));
                }
                int returnVal = chooser.showOpenDialog(container);
                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    lastDirectory = chooser.getSelectedFile().getAbsolutePath ();
                    directory.setText(lastDirectory);
                }else
                {
                    directory.setText("");
                }
            }
        }
    }
   
    public InvertedIndexGUI()
    {
        super("Inverted Index");
        lastDirectory = null;
        lastCommand = null;
        container = getContentPane();
        setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
        createPanels();   
        setLocation (400,200);
        pack();
        getContentPane().validate();
    }
    
    private void createPanels()
    {
        browseDescriptionPanel = new JPanel();
        fileChoosingPanel = new JPanel(new GridLayout(1,2));
        terminalPanel = new JPanel();
        dsPanel = new JPanel(new GridLayout(1,2));
        inputPanel = new JPanel(new GridLayout(1,1));
        togglesPanel = new JPanel();     
        buttonsPanel = new JPanel();
        
        build = new JButton("Build");
//              Creating buttons
        reset = new JButton("Reset");
        help = new JButton("Help");
        exit = new JButton("Exit");
        browse = new JButton("Browse");
        
        buttonsPanel.setLayout(new GridLayout(1,4,15,0));
        
        buttonsPanel.add(build);
        buttonsPanel.add(reset);
        buttonsPanel.add(help);
        buttonsPanel.add(exit);
        
        bhandler = new ButtonHandler();
        
        build.addActionListener(bhandler);
        reset.addActionListener(bhandler);
        help.addActionListener(bhandler);
        exit.addActionListener(bhandler);
        browse.addActionListener(bhandler);
        
        ihandler = new InputHandler();
    
//        Creating Radio Buttons as options of Data Structures that can be used
//        during the program
        tst = new JRadioButton("TST",false);
        trie = new JRadioButton("Trie",false);
        hash = new JRadioButton("Hash",false);
        avl = new JRadioButton("AVL",true);
        
        group = new ButtonGroup();
        group.add(trie);
        group.add(avl);
        group.add(tst);
        group.add(hash);
        
        togglesPanel.add(tst);
        togglesPanel.add(trie);
        togglesPanel.add(avl);
        togglesPanel.add(hash);
        
        rhandler = new RadioButtonHandler();
        tst.addItemListener(rhandler);
        avl.addItemListener(rhandler);
        trie.addItemListener(rhandler);
        hash.addItemListener(rhandler);
        
//        Creating Labels
        browseDescription = new JLabel("Please enter folder address or use browse button"
        ,SwingConstants.LEFT);
        inputDescription = new JLabel("Please enter your command:",SwingConstants.LEFT);
        browseDescriptionPanel.add(browseDescription);
        dsPanel.add(inputDescription);
        dsPanel.add(togglesPanel);
//        Creating TextAreas and TextFields
        terminal = new JTextArea(18,50);
        terminal.setEditable (false);
        terminal.setLineWrap(true);
        terminal.setCaretPosition(terminal.getText().length());
        
        terminalScroll = new JScrollPane(terminal,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        add(terminalScroll);
        directory = new JTextField();
        directory.setEditable (false);
        
        input = new JTextField();
        inputPanel.add(input);
        input.addKeyListener (ihandler);
        
        
        fileChoosingPanel.add(directory);
        fileChoosingPanel.add(browse);
        
        add(browseDescriptionPanel);
        add(fileChoosingPanel);
        add(terminalPanel);
        add(dsPanel);
        add(inputPanel);
        add(buttonsPanel);
        
        forward = new Stack();
        backward = new Stack();
        
//        creating Dictionary Trees and Archives
        archive = new Archive();
        dicType = "AVL";//The default mode is set on AVL Trees
        filesOfDicNodesType = "LinkedList";
    }
}
