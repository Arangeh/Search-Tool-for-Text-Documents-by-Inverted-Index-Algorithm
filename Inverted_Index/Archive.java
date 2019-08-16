/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package invertedindex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author alireza
 */
public class Archive 
{
//  these variables starting with 'all' 
//    store the files .Only one of them can be used.It's determined by 'archiveType'
    private ArrayList < FileRecord > allArrayList;
//    The following 3 will be implemented for the second phase of the project
    private TST allTST;
    private AVL allAVL;
    private Trie allTrie;
    
    private FileRecord f;//each File Record object is a DataStructure Object that stores the meaningful
//    words of a file ( It's clear that they 're absolutely not Stop Words! )
    
    private int filesOnDirectory,filesProcessed;
    DataStructure dictionary;
    Object stopWords;
    String lastDirectory,dicType,stopWordsType,archiveType
    ,filesOfDicNodesType;//archive type defines the actual type of 'allFiles'
    
//    Below we have listed some useful Strings that would be needed somewhere else in this class
    private static String punc = ";,.:?!",rebuilt = "Successfully rebuilt the dictionary"
    ,updated = "Successfully updated",alreadyExists = "Err : Already exists,You may want to update",
    notFound = "Err : Document not found",delete = "Successfully removed from dictionary",
    add = "Successfully added",appearsIn = "appears In",
    invalid = "Invalid Command.Please enter a valid command";
//    stopWordsType defines how we save all the words from a given file(stopwords.txt is also a file!)
//    dicType is clear
//    ArchiveType is the way we archive the file names in order to process them later
//    In the first phase ArchiveType and stopWordsType are both ArrayList for the ease of use
//    But in the second phase user can select between wider range of data structures
    
    boolean flagBuild,hasBuilt;
//    flagBuild show us whether we are in the middle of Build process or not!
    
    public Archive()
    {
        flagBuild = false;
        hasBuilt = false;
        filesOnDirectory = 0;
        filesProcessed = 0;    
    }
    
    /**To know whether the file with a given name exists in the Archive or not
     * 
     * @param name the Name of the file.Example:"Test Case.txt"
     * @return returns FileRecord Object if found any , and null otherwise 
     */
    public FileRecord search(String name)
    {
        FileRecord res = null;
        if(archiveType.equals("ArrayList"))
        {
            for(int i = 0;i < allArrayList.size();i++)
            {
                if(allArrayList.get(i).name.equals(name) )
                {
                    res = allArrayList.get(i);
                }
            }
        }
        return res;
    }
    

    private String heightLog()
    {
        String res = "";
        if(dictionary instanceof Tree)
        {
            Tree t = (Tree)dictionary;
            res = String.format("\nHeight = %d",t.getHeight(t.root));
        }
        return res;
    }
    
    
/**After creating the stop words we should add all the words from all the files into the dictionary
    we add the existing file on the given path to our dictionary
 * 
 * @return 
 */
    public State build()
    {
        State res = null;
        if(hasBuilt)
        {
            
            reset();
        }
        flagBuild  = true;
        
        createStopWords("StopWords.txt",System.getProperty ("user.dir"));
        
        File sample = new File(lastDirectory);
        String[] g = sample.list();
        String fileName = null;
        String s = "";
        String[] h;
        
        
        if(archiveType.equals("ArrayList"))    
        {
            allArrayList = new ArrayList < FileRecord >();   
        }//else!
        
//        Instantiating the dictionary . dicType is used to determine the type of dictionary
        
        if(dicType.equals("AVL"))
        {
            dictionary = new AVL(null,"LinkedList");
        }else if(dicType.equals("TST"))
        {
            dictionary = new TST(null,"LinkedList");
            
        }else if(dicType.equals("Trie"))
        {
            dictionary = new Trie(null,"LinkedList");
        }else if(dicType.equals("Hash"))
        {
            dictionary = new Hash(150,"LinkedList");
        }
        
        for(int i = 0;i < g.length;i++)//amongst files
        {            
            add(g[i]);
            filesOnDirectory++;
        }
        int noNodes = 0;
        noNodes = dictionary.no;
        if(hasBuilt)
        {
            res = new State(2,rebuilt +"\nBuild was successful" + "\nAdded " + noNodes + " words"
            + "\nAdded" + filesOnDirectory + " files" + heightLog());
        }else
        {
            res = new State(2,"Build was successful" + "\nAdded " + noNodes + " words"
            + "\nAdded" + filesOnDirectory + " files" + heightLog());
        }
        flagBuild = false;
        return res;
    }
    
    private boolean isStopWord(String s)
    {
        boolean res = false;
        if(stopWordsType.equals("ArrayList"))
         {
             ArrayList < String > u = (ArrayList)stopWords;
             for(String st:u)   
             {
                 if(st.compareToIgnoreCase(s) == 0)
                 {
                     res = true;
                 }
             }
         }//else!
        return res;
    }
    
    public String adder(String name,String dir)        
    {
        String s = "";
        State res = null;
        File sample = new File(dir);
        String fileName = null;
            sample = new File(dir + "\\" + name);
//            For Reading unformatted Plain text we have used Buffered Reader as follows
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(sample));
                int count;
                char[] buffer = new char[2048];
                while (reader.ready()) 
                {
                    count = reader.read(buffer);
                    s = s + (new String(buffer, 0, count));
                }
            } catch (FileNotFoundException ex) 
            {
                ex.printStackTrace(System.err);
            } catch (IOException ex) 
            {
                ex.printStackTrace(System.err);
            } finally 
            {
                try 
                {
                    if (reader != null)
                        reader.close();
                } catch (IOException ex) 
                {
                    ex.printStackTrace(System.err);
                }
            }
        return s;
    }
    
    
       
  /**Create the Stopwords in order not to add them later in the dictionary
   * 
   * @param name The name of the File for which the StopWords should be ignored
   * @param dir The directory in which the file mentioned exists
   */
    public void createStopWords(String name,String dir)
    {
        String s = adder(name,dir);   
        String[] h = s.split("\\s+");//All white Space characters are considered as delimiter
        if(stopWordsType.equals("ArrayList"))
        {
            stopWords = new ArrayList < String >();
            ArrayList < String > u = (ArrayList)stopWords;
            for(int i = 0;i < h.length;i++)
            {
                if(h[i] != null) u.add(h[i]);
            }
        }//else!
        
    }
    
    /**The simplest Method to write
     * 
     * @return 
     */
    public State reset()
    {
//        assigning null value causes the garbage collector to remove these items after a short while
        filesOnDirectory = 0;
        filesProcessed = 0;
        dictionary = null;
        allAVL = null;
        allTST = null;
        allArrayList = null;
        allTrie = null;
        stopWords = null;
        hasBuilt = false;
        flagBuild = false;
        State res = new State(2,"Reset was done successfully.There is no dictionary and there are "
                + "no files set in the program");
        return res;
    }
    
    
    
   
    /**The most important method
     * 
     * @param in The initial State made by either clicking on a button or pressing Enter in input TestField
     * @return returns The final State.Final State determines which text to be shown on terminal
     */
    public State stateAnalyze(State in)
    {
        
        State out = null;
        long time = System.currentTimeMillis();
        
        if(in.dest == 3)//related to ComplexButtonHandler    
        {
            lastDirectory = in.dir;
            out = stateButtonAnalyze(in);
        }
        if(in.dest == 4)//related to ComplexInputHandler
        {
            out = stateInputAnalyze(in);
        }
        time = System.currentTimeMillis() - time;
        out.s = String.format("Duration (Milliseconds) : %d\n",time) + out.s ;
        return out;
    }
   
    public State stateButtonAnalyze(State in)
    {
        State out = null;
        if(in.s.equals("Build"))//Build Button clicked
        {   
            out = build();
            hasBuilt = true;
        }else if(in.s.equals("Reset"))//Reset Button clicked
        {
            out = reset();
            hasBuilt = false;
        }
        return out;
    }
    
    
    public State stateInputAnalyze(State in)
    {
        State out = null;
        String input = in.s;
        if(input == null)
        {
            out = new State(2,invalid);
            return out;
        }
        if(occurences(input,"\"") == 0 || occurences(input,"\"") == 2)
        {   
            String[] h = input.split(" ");   
            if(h[0].equals("add"))//Example:add -Test Case.txt
            {
                if(h[1].charAt(0) == '-')
                {       
                    String g = String.join(" ",h).substring(5);

                    if(archiveType.equals("ArrayList"))
                    {
//                    to check if such a file exists ?
//                        to check if such a file has been added before?
//                        They can all be done by simply calling add method!
                        out = add(g);
                        return out;
                    }//else!
                }else
                {
                    out = new State(2,invalid);
                    return out;
                }
                return out;
            }else if(h[0].equals("del"))//Example:del -Test Case.txt
            {
                if(h[1].charAt(0) == '-')
                {
                    String g = String.join(" ",h).substring(5);
                    out = delete(g);
                }else
                {
                    out = new State(2,invalid);
                    return out;
                }
                return out;
            }else if(h[0].equals("list"))//Eample: list -w(-f)(-l) 
            {
                if(h.length == 2)
                {
                    if(h[1].equals("-f"))
                    {
                        out = showCurrentDirectory();
                        return out;
                    }else if(h[1].equals("-l"))
                    {
                        out = showProcessedFiles();
                        return out;
                    }else if(h[1].equals("-w"))
                    {
                        out = showWords();
                        return out;
                    }else
                    {
                        out = new State(2,invalid);
                        return out;
                    }
                }else
                {
                    out = new State(2,invalid);
                }
                return out;
            }else if(h[0].equals("update"))//Example:update -Test Case.txt
            {
                if(h.length >= 2)
                {
                    if(h[1].charAt(0) == '-')   
                    {
                        String g = String.join(" ",h).substring(8);//update and - should be ignored
                        out = delete(g);
                        if(out.s.equals(notFound))
                        {
                            return out;
                        }
                        add(g);
                        out = new State(2,updated);
                        return out;
                    }else
                    {
                        out = new State(2,invalid);
                    }
                }else
                {
                    out = new State(2,invalid);
                    return out;
                }
            }else if(h[0].equals("search"))//Example:search -w "hello"
            {
                int len = h.length;
                if(len >= 3)
                {
                    String g = String.join(" ",h);
                    g = g.substring(8);//search and -w or -s should be ignored
                    out = makeInterception(g,h[1]);
                    return out;
                }else
                {
                    out = new State(2,invalid);
                    return out;
                }
            }else
            {
                out = new State(2,invalid);
            }
        }else
        {
            out = new State(2,invalid);    
        }
        
        return out;
    }
    
    /**
     * 
     * @param g g would be something like that: "word1 word2 word3 ..."
     * @param type type can be either "-w" or "-s"
     * @return 
     */
    public State makeInterception(String g,String type)
    {
        State res = null;
        String h = g.substring(3,g.length() - 1);
        String[] u = h.split(" ");
        if(type.equals("-w"))
        {   
            if(u.length != 1)
            {
                res = new State(2,invalid);
                return res;
            }
        }else if(type.equals("-s"))
        {
            if(u.length <= 1)
            {
                res = new State(2,invalid);
                return res;
            }
        }
        h = "";
        for(int i = 0;i < u.length;i++)
        {
            if(!isStopWord(u[i]))
            {
               h = h + u[i] + " ";
            }
        }
        u = h.split(" ");
        res = dictionary.intercept(u);       
        return res;
    }
    
    
    
    public State showProcessedFiles()
    {
        State res = null;
        String log = "";
        if(archiveType.equals("ArrayList"))
        {
            for(int i = 0;i < allArrayList.size();i++)
            {
                log = log + allArrayList.get(i).name + "\n";
                    
            }
            log = log + String.format("Number of processed docs = %d",filesProcessed);
            res = new State(2,log);
        }//else!
        
        return res;
    }
    
    public State showWords()
    {
        State res = null;
        String z = "";
        if(dictionary.showWords() != null)
        {
            z = String.join("\n",dictionary.showWords());
        }
        res = new State(2,z);
        return res;
    }
    
    public State showCurrentDirectory()
    {
        State res = null;
        String log = "";
        File sample = new File(lastDirectory);
        String[] g = sample.list();
        filesOnDirectory = g.length;
        for(int i = 0;i < g.length;i++)
        {
            log = log + "\n" + g[i];
        }
        log = log + String.format("\nNumber of Files on Directory = %d",filesOnDirectory);
        res = new State(2,log);    
        return res;
    }
    
    public State delete(String fileName)
    {
        State res = null;   
        if(search(fileName) != null)
        {
            filesProcessed--;
            if(archiveType.equals("ArrayList"))
            {
                f = allArrayList.remove(allArrayList.indexOf(search(fileName)));
                
            }//else!
            if(f.o instanceof ArrayList)
            {
                ArrayList < String > a = (ArrayList)f.o;
                String key = null;
                int size = a.size();
                
                for(int i = 0;i < size ;i++)
                {
                    
                    key = a.get(i);
                    dictionary.delete(key.toLowerCase(),fileName);
                    
                }
                
            }//else!
            
            res = new State(2,delete);
        }else
        {
            res = new State(2,notFound);
        }
        
        return res;
    }
 
    /**To add a File with a given fileName to the Archive
     * 
     * @param fileName It's clear
     * @return 
     */
    public State add(String fileName)
    {
        State res = null;
        String[] h;
        File sample = new File(lastDirectory + "\\" + fileName);
        
        if(!sample.exists())
        {
            res = new State(2,notFound);
            return res;
        }
        String s = adder(fileName,lastDirectory);
        h = s.split("[^a-zA-Z]+");
        if(flagBuild == false && search(fileName)!= null)
		{
			res = new State(2,alreadyExists);
			return res;
		}
		f = new FileRecord(fileName);
        f.setType(stopWordsType);
        f.setStopWords(stopWords);
//		System.out.println(fileName);
        for(int j = 0;j < h.length;j++)//amongst words of each file
		{
			if(h[j] != null && h[j].length() != 0)
			{
				if(!(isStopWord(h[j])) && !Pattern.matches("\\p{Punct}",h[j]))//should be added
				{
					//adding to all files in a proper place
					f.add(h[j]);
                    dictionary.add(h[j],fileName);
                }   
			}
		}
        if(archiveType.equals("ArrayList"))
        {
            allArrayList.add(f);
        }//else!
        filesProcessed++;
        if(flagBuild == false)
        {
            res = new State(2,add);
        }
        return res;
    }
    
    
    /**
         * //counts the number of occurences of char a in String b
         * @param b
         * @param a character is convertred to String here.We mean a String containing 1 character
         * @return returns the final number
         */
    private int occurences(String b,String a)//counts the number of occurences of char a in String b
    {
        return b.length() - b.replace(a,"").length();
    }
    
    public void setDirecory(String s)
    {
        lastDirectory = s;
    }
    public void setFilesOfDicNodesType(String h)
    {
        filesOfDicNodesType = h;
    }
}
