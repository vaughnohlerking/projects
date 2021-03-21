
package bookstore;

import java.util.Collections;

/**
 *
 * @author vaughnohlerking
 */
public class Proceeding extends Publication implements Citable
{
    private String city;
    //these are greyed out because they create problems
    //protected int startingPage;
    //protected int endPage;
    //protected int year;
    
    //constructor calls superclass to setup all but city
    Proceeding(String inAuthors, String Title, String Venue, String inPublisher,String City, int StartingPage, int EndPage, int Year){
        super(inAuthors, Title, Venue, inPublisher, StartingPage, EndPage, Year);
        city = City;
    }
    
    //cites publication as proceeding
    @Override
    public String Cite(){
        
        return String.format("%s%s, %d pp:%d - %d",super.Cite(), city,year,startingPage,endPage);
    }
}
