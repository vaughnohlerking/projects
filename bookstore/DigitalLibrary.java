package bookstore;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.Collections;
import java.util.Scanner;
import java.io.PrintWriter;

//Enumeration with all allowed publisher names
enum Publisher{ELSEVIER, SPRINGER, IEEE, TAYLORFRANCIS, WILEY, ACM}; 


public class DigitalLibrary {
    
    //array list of publications
   private ArrayList<Publication> publications = new ArrayList<>();
   //file path for stored publications.txt file
   String filePath = "/Users/vaughnohlerking/Library/Containers/com.moodle.moodledesktop/Data/Documents/com.moodle.moodlemobile/sites/0b8133dc71d62488ff322cec29c72ef2/filepool/publications.txt";
   
   //loads publications into publications array
   public void loadPublications() throws FileNotFoundException
    {
        
        File input = new  File(filePath); 
        Scanner in = new Scanner(input);
        
        //checks for remaining lines in file
        while(in.hasNext())
        {
            //splits lines into fields
            String[] fields = in.nextLine().split(";");
            //determines whether journal or proceeding by array length
            if (fields.length == 9){
                
                Journal journal = new Journal(fields[0],fields[1],fields[2],fields[3],Integer.parseInt(fields[4]),Integer.parseInt(fields[5]),Integer.parseInt(fields[6]),Integer.parseInt(fields[7]),Integer.parseInt(fields[8]));
                publications.add(journal);
            }
            else
            {
                Proceeding proceeding = new Proceeding(fields[0],fields[1],fields[2],fields[3],fields[4],Integer.parseInt(fields[5]),Integer.parseInt(fields[6]),Integer.parseInt(fields[7]));
                publications.add(proceeding);
            }
        
        }
        Collections.sort(publications);
    }
   
   
   //prints publications to console
   public void listPublications()      
   {
       int i = 0;
       for (Publication a: publications){
           i++;
           System.out.println("["+i+"] "+a.Cite());
       }   
   }
   
   //saves citations to file named by user
   public void saveCitations(String fileName) throws FileNotFoundException
   {
       PrintWriter prw = new PrintWriter(fileName + ".txt");
       int i = 0;
       for (Publication a: publications){
           i++;
           prw.println("["+i+"] "+a.Cite());
        }
       
        prw.close();
        System.out.println(i + " citations have been saved to the file " + fileName + ".txt"); 
   }
}
