
package bookstore;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author vaughnohlerking
 */
public class Publication implements Citable, Comparable<Publication>
{
    
    private ArrayList<Author> authors = new ArrayList<>();
    private Publisher publisher;
    private String venue;
    private String title;
    protected int startingPage;
    protected int endPage;
    protected int year;
    private String authorsListed = "";
    
    //constructor initializes and sorts authors, sets enum value, and sets variables
    Publication(String inAuthors, String Title, String Venue, String inPublisher, int StartingPage, int EndPage, int Year){

        String[] StrLisauthors = inAuthors.split(",");
        
        for(String i: StrLisauthors){

            Author author = new Author(i);
            authors.add(author);
            
        } 
        //sort authors arrayList
        Collections.sort(authors);
        
        
        title = Title;
        venue = Venue;
        
        try{
            publisher = Publisher.valueOf(inPublisher.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw e;
        }
        startingPage = StartingPage;
        endPage = EndPage;
        year = Year;
        
    }
    
    //implements comparable. sorts by author > venue > year
    public int compareTo(Publication other){

        if (authors.get(0).lastName.compareTo(other.authors.get(0).lastName) != 0){
            return authors.get(0).lastName.compareTo(other.authors.get(0).lastName);
        }else if (venue.compareTo(other.venue) != 0){
            return venue.compareTo(other.venue);
        }else{
            return Integer.compare(year, other.year);
        }
          
        
    }
    
    //cites publication
    public String Cite(){
        
        //for counting position in list
        int i =0;
        //builds authors list
        
        for (Author a: authors){
            i++;
            switch (authors.size()){
                case 1:authorsListed += a.firstName.charAt(0) + ". " + a.lastName;
                break;
                
                case 2:
                    if (i == 1){
                        authorsListed += a.firstName.charAt(0) + ". " + a.lastName + " and ";
                    }else authorsListed += a.firstName.charAt(0) + ". " + a.lastName;
                    break;
                
                default:
                    if (i == authors.size()){
                        authorsListed += "and " + a.firstName.charAt(0) + ". " + a.lastName;
                    }else authorsListed += a.firstName.charAt(0) + ". " + a.lastName + ", ";
                    break;
            }  
        }
        
        //builds acronym of venue
        String[] sepVenue = venue.split(" ");
        String acrVenue = "";
        
        for (String a:sepVenue){
            acrVenue += a.toUpperCase().charAt(0);
        }
        
        return String.format("%s, \"%s\", %s(%s), %s, ",authorsListed,title,venue,acrVenue,publisher);
        
    }
}
