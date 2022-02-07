/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gausseidel;

/**
 * This program solves a given set of linear equations using Gauss-Seidel method
 * as arrays and puts solutions in an array x. It continually solves until error
 * is less than a given epsilon
 * 
 * CSC 2262 Programming project No 5?
 * 
 * @author vaughnohlerking
 * @since 11/22/2020
 */
public class GausSeidel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[][] a = {
            { 4,-1, 0,-1, 0, 0},
            {-1, 4,-1, 0,-1, 0},
            { 0,-1, 4, 0, 0,-1},
            {-1, 0, 0, 4,-1, 0},
            { 0,-1, 0,-1, 4,-1},
            { 0, 0,-1, 0,-1, 4}
        //    {9,1,1},//debugging
        //    {2,10,3},
        //    {3,4,11}
        };
        
        double[] b = { 0, 5, 0, 6,-2, 6};
        //{10,19,0};//debugging
        double[] xArr = { 0, 0, 0, 0, 0, 0};
        //{0,0,0};//debugging
        
        
        printHeader(a, xArr, b);//print header
        printBodyLine(a, xArr, b, 0, 0);//print first body line
        
        loopIterate(a, xArr, b, .0001);//run algorithm. will print body lines as it goes
        
        
    }
    
    /**
     * This method prints header
     * 
     * method: printHeader
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array to be printed from
     * @param b [double[]] array to be printed from
     * @param xArr [double[]] array to be printed from
     * 
     * @author vaughnohlerking
     * @since 11/22/2020
     * 
    */
    public static void printHeader(double[][] a, double[] xArr, double[] b){
        //header
        System.out.printf("%2s","k");//prints k
        for(int i = 0; i < xArr.length; i++){//loops for all Xs
            System.out.printf("%10s", "x "+i);
        }
        System.out.printf("%10s\n", "Diff");//prints diff     
    }
    
    /**
     * This method prints one line of body
     * 
     * method: printHeader
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array to be printed from
     * @param b [double[]] array to be printed from
     * @param xArr [double[]] array to be printed from
     * @param lineNo [int] line number, really just k
     * @param diff [double] diff value to be printed
     * 
     * @author vaughnohlerking
     * @since 11/22/2020
     * 
    */
    public static void printBodyLine(double[][] a, double[] xArr, double[] b, int lineNo, double diff){
        //body
        System.out.printf("%2s",lineNo);//prints k value
        for (int i = 0; i < xArr.length; i++){//prints out x values
            System.out.printf("%10.4f", xArr[i]);
        }
        System.out.printf("%10.4f\n", diff);//prints diff value
    }
    
    /**
     * This method gets the euclidean norm of an array
     * 
     * method: euclidNorm
     * 
     * @return double
     * 
     * parameters: 
     * @param xArr [double[]] array with values for calculations
     * 
     * @author vaughnohlerking
     * @since 11/22/2020
     * 
    */
    public static double euclidNorm(double[] xArr){
        double norm = 0;//stores answer
        for (double d: xArr){//adds the square of every value
            norm += d * d;
        }
        return java.lang.Math.sqrt(norm);//returns square root
    }
    
    /**
     * This method loops the iterate function, as well as prints body
     * 
     * method: loopIterate
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array to pull vals from
     * @param b [double[]] array to pull vals from
     * @param xArr [double[]] array to pull vals from
     * @param epsilon [double] error bound to be stopped by
     * 
     * @author vaughnohlerking
     * @since 11/22/2020
     * 
    */
    public static void loopIterate(double[][] a, double[] xArr, double[] b, double epsilon){
        
        int k = 0;//tracks iteration number
        double diff;//error
        double[] prevXArr = new double[xArr.length];//init prev x array
        
        do{
            k++;
            for(int i = 0; i < xArr.length; i++){//this is to save previous values of xArray for comparing error
                prevXArr[i] = xArr[i];
            }
            iterate(a, xArr, b);
            
            for(int j = 0; j < xArr.length; j++){
                prevXArr[j] = xArr[j] - prevXArr[j];//subtracts previous x from new x and stores in previous x
            }
            diff = euclidNorm(prevXArr) / (euclidNorm(xArr) + epsilon);//calculates error
            
            printBodyLine(a, xArr, b, k, diff);//prints body line
            
        }while(diff > epsilon);//while error is greater than maximum allowable error  
    }
    
    /**
     * This method computes one iteration of the Gauss-Seidel method
     * 
     * method: iterate
     * 
     * return type: void
     * 
     * parameters: 
     * @param a [double[][]] array to pull vals from
     * @param b [double[]] array to pull vals from
     * @param xArr [double[]] array to pull vals from
     * 
     * @author vaughnohlerking
     * @since 11/22/2020
     * 
    */
    public static void iterate(double[][] a, double[] xArr, double[] b){
        
        for(int i = 0; i < b.length; i++){//each iteration just solves each row one at a time, using the updated values as the come
            xArr[i] = solveFor(a, xArr, b, i);
        }
        
    }
    
    /**
     * This method solves and individual row for the desired x
     * 
     * method: solveFor
     * 
     * @return double
     * 
     * parameters: 
     * @param a [double[][]] array to pull vals from
     * @param b [double[]] array to pull vals from
     * @param xArr [double[]] array to pull vals from
     * @param x [int] the x to be solved for. Consequently, it is also the row to solve it on
     * 
     * @author vaughnohlerking
     * @since 11/22/2020
     * 
    */
    public static double solveFor(double[][] a, double[] xArr, double[] b, int x){
        double solution = b[x];//to hold solution
        for(int i = 0; i < b.length; i++){//runs through entire row
            if(i != x){
                solution -= a[x][i] * xArr[i];//values are subtracted from b to solve for given x
            }
        }
        solution /= a[x][x];//must move Xs coefficient as well
        return solution;//return answer
    }
    
}
