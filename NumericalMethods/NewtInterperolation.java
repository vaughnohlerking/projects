

package newtinterperolation;

/**
 *
 * @author vaughnohlerking
 */
public class NewtInterperolation {

 /**
 * This program prints two tables showing the accuracy of Newtons Divided Difference Interpolation method for different degrees of accuracy
 * 
 * CSC 2262 Programming project No 3
 * 
 * @author vaughnohlerking
 * @since 9/30/2020
 */
    public static void main(String[] args) {
        
        double[] knownPoints = {0.0,0.2,0.4,0.6,0.8,1.0,1.2};//given known points for calculations
        
        System.out.println("Table 1");
        line(62);
        System.out.printf("%-6s%6s%25s%25s\n", "i","x_i","sin(x_i)","D_i");
        line(62);
        
        double[] a = {0.0,0.2};//i apologize this is crummy but I couldn't figure out how to make my shrink method work with targetLen = 1
        System.out.printf("%-6d%6.1f%25.20f%25.20f\n",0,knownPoints[0],f(knownPoints[0]),DivDif(a));
        
        for(int i = 1; i <= 6; i++){//prints first table
            System.out.printf("%-6d%6.1f%25.20f%25.20f\n",i,knownPoints[i],f(knownPoints[i]),DivDif(shrink(knownPoints,i+1,0)));
        }
        
        line(62);
        line(0);
        System.out.println("Table 2");
        line(81);
        System.out.printf("%-6s%25s%25s%25s\n","n","Pn(0.1)","Pn(0.3)","Pn(0.5)");
        line(81);
        
        for (int i = 1; i <= 6; i++){//prints second table
            System.out.printf("%-6d%25.20f%25.20f%25.20f\n",i,Newt(i,0.1,knownPoints),Newt(i,0.3,knownPoints),Newt(i,0.5,knownPoints));
        }
        line(81);
    }
    
        /**
     * This method recursively finds Newton's Divided Difference formula at x
     * 
     * method: Newt
     * 
     * return type: double
     * 
     * parameters: 
     * n [int] degree formula is to be calculated to
     * val [double] x to plug into formula
     * x [double[]] array of known points on graph to calculate with
     * 
     * @author vaughnohlerking
     * @since 9/30/2020
     * 
    */
    public static double Newt(int n, double val, double[] x){
        
        double vals = 1;//for calculating Pn
        
        if (n == 1){//when recursive function reaches bottom return P1
            return f(x[0])+ (DivDif(shrink(x,2,0))*(val-x[0]));
        }else{
            for(int i = 0; i < n; i++){
                vals *= val - x[i];
            }
            return Newt(n-1,val,shrink(x,x.length-1,0)) + vals * DivDif(x);//calls self for previous degrees of function
        }
    }
    
     /**
     * This method prints a line of given length len
     * 
     * method: line
     * 
     * return type: none
     * 
     * parameters: 
     * len [int] length of line to be printed
     * 
     * @author vaughnohlerking
     * @since 9/30/2020
     * 
    */
    public static void line(int len){
        for(int i = 0; i < len; i++){
            System.out.print("-");
        }
        System.out.println();//newline
    }
    
     /**
     * This method recursively finds the Divided Difference formula for given points in array x
     * 
     * method: DivDif
     * 
     * return type: double
     * 
     * parameters: 
     * x [double[]] points to interpolate from
     * 
     * @author vaughnohlerking
     * @since 9/30/2020
     * 
    */
    public static double DivDif(double[] x){
        
        if (x.length == 2){
            return (f(x[1])-f(x[0]))/(x[1]-x[0]);//when recursive function reaches bottom, return slope function of x1 and x2
            
        }else{
            return (DivDif(shrink(x,x.length-1,1)) - DivDif(shrink(x,x.length-1,0)))/(x[x.length-1] - x[0]);
        }
    }
    
     /**
     * This method calculates sine of x
     * 
     * method: f
     * 
     * return type: double
     * 
     * parameters: 
     * x [double] for calculation
     * 
     * @author vaughnohlerking
     * @since 9/30/2020
     * 
    */
    public static double f(double x){
        return Math.sin(x);
    }
 
     /**
     * This method copies an array but shortens it to targetLen. Optional shift of values copied as well
     * 
     * method: shrink
     * 
     * return type: double[]
     * 
     * parameters: 
     * x [double[]] array to be copied
     * targetLen [int] desired length of new array
     * shift [int] shifts values copied
     * 
     * @author vaughnohlerking
     * @since 9/30/2020
     * 
    */
    public static double[] shrink(double[] x, int targetLen, int shift){
        double[] a = new double[targetLen];//new array
        for(int i = 0; i < targetLen; i++){
            a[i] = x[i+shift];//copies array with optional shift
        }
        return a;
    }
}
