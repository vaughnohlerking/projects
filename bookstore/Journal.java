
package bookstore;

import java.util.Collections;

/**
 *
 * @author vaughnohlerking
 */
public class Journal extends Publication implements Citable 
{
    private int volume;
    private int number;
    //these are greyed out because they create problems
    //protected int startingPage;
    //protected int endPage;
    //protected int year;
    
    //constructor calls superclass to set up all but volume and number
    Journal(String inAuthors, String Title, String Venue, String inPublisher,int Volume, int Number, int StartingPage, int EndPage, int Year){
        super(inAuthors, Title, Venue, inPublisher, StartingPage, EndPage, Year);
        volume = Volume;
        number = Number;
    }
    
    //calls publication and cites Journal
    @Override
    public String Cite(){
        
        return String.format("%s%s(%d): %d - %d, %d",super.Cite(), volume,number,startingPage,endPage,year );
    }
}
