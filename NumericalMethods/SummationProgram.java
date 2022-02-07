
package summationprogram;

/**
 * This program compares the accuracy of evaluating a summation function with largest values first versus smallest first
 * 
 * CSC 2262 Programming project No 1
 * 
 * @author vaughnohlerking
 * @since 9/17/2020
 */
public class SummationProgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int n = 0;//number of times to evaluate formulae
        double sl;//stores SmallestLargest(n)
        double ls;//stores LargestSmallest(n)
        double t;//stores TrueVal(n)
        int[] Ns = {10,50,100,500,1000,5000};//array of values to test error at
        
        //print statements to form header
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-6s%30s%30s%30s%30s%30s\n", "N","SmallestLargest","Error","LargestSmallest","Error","True");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
        
        //loop to calculate and print results
        for(int i = 0; i < 6; i++){
            n = Ns[i];
            sl = SmallestLargest(n);
            ls = LargestSmallest(n);
            t = TrueVal(n);
            System.out.printf("%-6d%30.25f%30.25f%30.25f%30.25f%30.25f\n", n,sl,t-sl,ls,t-ls,t);
            
        }
        //footer
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
    
    /**
     * This method evaluates the summation of the function largest to smallest
     * 
     * method: LargestSmallest
     * 
     * return type: double
     * 
     * parameters: 
     * n [int] number of times the function is added
     * 
     * @author vaughnohlerking
     * @since 9/17/2020
     * 
    */
    public static double LargestSmallest(int n){
        double s = 0;//number to be added to and returned at end of method
        double j = 0.0;//number to be added to s
        for(int i = 1; i <= n; i++){
            j ++;
            s += (1/(j*(j+2)));
            
        }
        
        return s;
    }
    
    /**
     * This method evaluates the summation of the function smallest to largest
     * 
     * method: SmallestLargest
     * 
     * return type: double
     * 
     * parameters: 
     * n [int] number of times the function is added
     * 
     * @author vaughnohlerking
     * @since 9/17/2020
     * 
    */
    public static double SmallestLargest(int n){
        double s = 0;//number to be added to and returned at end of method
        double j = n+1.0;//number to be added to s
        for(int i = n; i >= 1; i--){
            j --;
            s += (1/(j*(j+2)));
            
        }
        
        return s;
    }
    
    /**
     * This method evaluates the true value summations above
     * 
     * method: TrueVal
     * 
     * return type: double
     * 
     * parameters: 
     * n [double] number to be input in formula
     * 
     * @author vaughnohlerking
     * @since 9/17/2020
     * 
    */
    public static double TrueVal(double n){
        return (.75 - (2*n + 3)/(2*(n+1)*(n+2)));
    }
}
