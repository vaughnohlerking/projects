
package bookstore;

/**
 *
 * @author vaughnohlerking
 */
public class Author  implements Comparable<Author> {
    protected String firstName;
    protected String lastName;
    protected String institution;
    private String[] full;
    
    //constructor splits name and sets variables
    Author(String Authors){
        full = Authors.split(" ");
        firstName = full[0];
        lastName = full[1];
    }
    
    //no idea
    public void setInstitution(){
    }
    
    //implements comparable to be compared by last name
    @Override
    public int compareTo(Author other){
        return  lastName.compareTo(other.lastName);
    }
    
    
}
