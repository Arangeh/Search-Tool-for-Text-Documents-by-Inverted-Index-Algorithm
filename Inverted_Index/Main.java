/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package invertedindex;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author alireza
 */
public class Main 
{
    
    
    public static void main(String[] args)
    {
//setting Nimbus as default Look And Feel which is platform independant
        
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
//                 nothing is handled !
            }
        }

//        In order to eliminate the Unresponsive states and eliminating
//        The possibility of misleading delays posed by simple Event Handling in Swing
//        SwingWorker is used below.The phase 1 is to create the JFrame in a Thread instead of 
//        creating it simply and directly in main method which can be harmful.

//        One thing beforehand:StopWords are stored in the "StopWords.txt" which is located on the 
//        project 's main directory . You can modify the words but do not delete the File completely
//        deleting this file causes the other parts of the program to face with undexpected Error or
//        behaviour.
        EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() 
            {
                InvertedIndexGUI iigui = new InvertedIndexGUI();
                iigui.setVisible(true);
                iigui.setResizable(false);

                class WindowHandler extends WindowAdapter
                {
                    @Override
                    public void windowClosing(WindowEvent e)
                    {
                        iigui.setVisible(false);
                        System.exit(0);
                    }
                }

                WindowHandler winHandler = new WindowHandler();
                iigui.addWindowListener(winHandler);
            }
        });
     
    }
        
}
