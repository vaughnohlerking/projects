
package gaussandpivot;

/**
 * This program solves a given set of linear equations using Gaussian Elimination
 * as arrays and puts solutions in an array
 * 
 * CSC 2262 Programming project No 4?
 * 
 * @author vaughnohlerking
 * @since 10/27/2020
 */
public class GaussAndPivot {


    public static void main(String[] args) {
        
        //Note: Program can solve matrices of any size as long as width and height
        // are the same, and height of b matches
        
        double[][] a = {// array to store given equations
            { 2, 4,3},
            { 4, -8, 3},
            {-6,-6, 1},
            //{-2, 1, 8},
        };
        
        double[] b = {3,4,7};// array to store equations given constants
        
        double[] x = new double[b.length];// array to store answers
        
        double[][] ca = new double[a.length][a[0].length];// copied array for printing later
        double[] cb = new double[b.length];// copied array for printing later
        
        fillCopies(a,b,ca,cb);// copies a and b into ca and cb
        
        Solve(a,b,x);// solves matrices
        printAll(ca,cb,x);// prints results
        
    }
    
    
    /**
     * This method solves array a and b and stores them in x
     * 
     * method: Solve
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array with linear equations to be solved
     * @param b [double[]] array with constants for linear equations
     * @param x [double[]] array to be filled with solution
     * 
     * 
     * @author vaughnohlerking
     * @since 11/09/2020
     * 
    */
    public static void Solve(double[][] a, double[] b, double[] x){
        MakePivots(a,b);// must make matrices into upper triangle first
        BackSub(a,b,x);// next back sub to solve for x's
    }
    
    /**
     * This method performs the back substitution portion of solving the system of linear equations
     * 
     * method: BackSub
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array with linear equations to be solved
     * @param b [double[]] array with constants for linear equations
     * @param x [double[]] array to be filled with solution
     * 
     * @author vaughnohlerking
     * @since 11/09/2020
     * 
    */
    public static void BackSub(double[][] a, double[] b, double[] x){
         
        for(int i = b.length - 1; i >= 0; i--){// loop counts back from length of b to 0
             SimplifyLine(a, b, x, i, i);// simplify line from i on row i
             x[i] = b[i] / a[i][i];// solve and store x
             UpdateColumn(a,b,x,i,x[i]);// update column of matrix with new x
             
         }
    }
    
     /**
     * This method assists BackSub by updating the columns with values based on the solved values
     * 
     * method: UpdateColumn
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array with linear equations to be solved
     * @param b [double[]] array with constants for linear equations
     * @param x [double[]] array to be filled with solution
     * @param column [int] column to be given updated values
     * @param constant [int] constant to modify values in column by
     * 
     * @author vaughnohlerking
     * @since 11/09/2020
     * 
    */
    public static void UpdateColumn(double[][] a, double[] b, double[] x, int column, double constant){
        for(int i = a.length - 1; i >= 0; i--){// runs from length of a down to 0
            a[i][column] *= constant;// changes values in column by a given constant
        }
    }
    
     /**
     * This method assists BackSub by simplifying a given line of the equation by pushing the
     * values from left to right, and into the b array
     * 
     * method: SimplifyLine
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array with linear equations to be solved
     * @param b [double[]] array with constants for linear equations
     * @param x [double[]] array to be filled with solution
     * @param row [int] row to be simplified
     * @param startPos [int] position to start pushing values over from. Left to right
     * 
     * @author vaughnohlerking
     * @since 11/09/2020
     * 
    */
    public static void SimplifyLine(double[][] a, double[] b, double[] x, int row, int startPos){
        
        for(int i = startPos + 1; i <= a[row].length - 1; i++){// loop starts at given start pos + 1 and pushes values over to b[i]
            b[row] -= a[row][i];
        }
    }
    
    /**
     * This method sets up the equations with 0s on the lower triangle
     * 
     * method: MakePivots
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array with linear equations to be solved
     * @param b [double[]] array with constants for linear equations
     * 
     * @author vaughnohlerking
     * @since 11/09/2020
     * 
    */
    public static void MakePivots(double[][] a, double[] b){
        
        double constant;// constant to modify rows by will be stored here
        
        for(int j = 0; j < a.length - 1; j++){// iterates one row at a time, 0th to Nth
            for(int i = j+1; i < a.length; i++){// iterates one column at a time, from pivot value to Nth
                constant = a[i][j] / a[j][j];// sets constant
                RowMultAdd(a,b,j,i,constant);// modifies row by constant
            }
        }
    }
    
    /**
     * This method assists MakePivots by modifying a given row by a constant * different row
     * 
     * method: RowMultAdd
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array with linear equations to be solved
     * @param b [double[]] array with constants for linear equations
     * @param rA [int] row to modify rA
     * @param rB [int] row to be modified by rA * constant
     * @param constant [double] constant to be multiplied by rA
     * 
     * @author vaughnohlerking
     * @since 11/09/2020
     * 
    */
    public static void RowMultAdd(double[][] a, double[] b, int rA, int rB, double constant){
        
        //rB = (rA * const) - rB
        for(int i = 0; i < a[rA].length; i++){// iterates 0 to given row's length
            a[rB][i] -= a[rA][i] * constant;// updates value in row B col i
        }
        b[rB] -= b[rA] * constant;// modifies array b
        
    }
    
    /**
     * This method fills copy arrays so that the results can be shown later beside the
     * original conditions
     * 
     * method: fillCopies
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array with linear equations to be solved
     * @param b [double[]] array with constants for linear equations
     * @param ca [double[][]] array to hold copy of a
     * @param cb [double[]] array to hold copy of b
     * 
     * @author vaughnohlerking
     * @since 11/09/2020
     * 
    */
    public static void fillCopies(double[][] a, double[] b, double[][] ca, double[] cb){
        
        for (int i = 0; i < a.length; i++){// iterates 0th to Nth
            for (int j = 0; j < a[0].length; j++){// iterates 0th to Nth
                ca[i][j] = a[i][j];// updates val in ca
            }
            cb[i] = b[i];// updates val in cb
        }
    }
    
    /**
     * This method prints a given 2d array (for debugging purposes)
     * 
     * method: printMatrix2d
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array to be printed
     * 
     * @author vaughnohlerking
     * @since 11/09/2020
     * 
    */
    public static void printMatrix2d(double[][] a){
        
        System.out.println("\n------------------------------------------");//line for formatting
        for(int i = 0; i < a[0].length; i++){// 0 to n
            for(int j = 0; j < a.length; j++){// 0 to n
                System.out.printf(" %2.1f " ,a[i][j]);// prints vals in matrix
            }
            System.out.println();// line for formatting
        }
        System.out.println("------------------------------------------\n");// line for formatting
    }
    
    /**
     * This method prints a given array (for debugging purposes)
     * 
     * method: printMatrix
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[]] array to be printed
     * 
     * @author vaughnohlerking
     * @since 11/09/2020
     * 
    */
    public static void printMatrix(double[] a){
        System.out.println("\n------------------------------------------");// line for formatting
        for (int i = 0; i < a.length; i++){// 0 to n
            System.out.printf(" %2.1f ",a[i]);// prints vals
        }
        System.out.println("\n------------------------------------------\n");// line for formatting
    }

    /**
     * This method prints final table
     * 
     * method: printAll
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array to be printed
     * @param b [double[]] array to be printed
     * @param x [double[]] array to be printed
     * 
     * @author vaughnohlerking
     * @since 11/09/2020
     * 
    */
    public static void printAll(double[][] a, double[] b, double[] x){

        System.out.printf("%"+7*a[0].length+"s%11s%11s\n","A","B","X");// header line. also allows for differently sized matrix
        
        for (int i = 0; i < a.length; i++){// 0 to n
            for (int j = 0; j < a[i].length; j++){// 0 to n
                System.out.printf("%7.3f",a[i][j]);// prints vals from a
            }
            System.out.printf("  | %7.3f  | %7.3f\n",b[i],x[i]);// prints vals from b and x
            
        }
    }
}