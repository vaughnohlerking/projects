
package cubedspineinflation;

/**
 *
 * @author vaughnohlerking
 */
public class CubedSpineInflation {

    /**
* This program computes the coefficients of the natural cubic spline interpolation of the given known points*
* CSC 2262 Programming project No 3 *
* @author vaughn ohlerking
* @since 10/5/2020
*/
    public static void main(String[] args) {
        
        double[] x = {17,20,23,24,25,27,27.7};//given known points for calculations
        double[] a = {4.5,7.0,6.1,5.6,5.8,5.2,4.1};//given known points for calculations
        
        int len = x.length;//length of array
        
        //each of the following arrays are for the calculation of the cubic splines
        double[] h = new double[len];
        double[] alpha = new double[len];
        double[] l = new double[len];
        double[] u = new double[len];
        double[] z = new double[len];
        double[] b = new double[len];
        double[] c = new double[len];
        double[] d = new double[len];
        
        //sets values of h
        for(int i = 0; i < len - 1; i++){
            h[i] = x[i+1] - x[i];
        }
        
        //sets values of alpha
        for(int i = 1; i < len - 1; i++){
            alpha[i] = (3*(a[i+1]*h[i-1]-a[i]*(x[i+1] - x[i-1])+a[i-1]*h[i]))/(h[i-1]*h[i]);
        }
        
        //more set values
        l[0] = 1;
        u[0] = 0;
        z[0] = 0;
        
        //sets l, u, and z
        for(int i = 1; i < len - 1; i++){
            l[i] = 2*(x[i+1] - x[i-i]-h[i-1]*u[i-1]);
            u[i] = h[i]/l[i];
            z[i] = (alpha[i] - h[i-1]*z[i-1])/l[i];
        }
        
        //adjusts values
        l[len-1] = 1;
        z[len-1] = 0;
        c[len-1] = 0;
        
        //final calculations for b, c, and d
        for (int i = len - 2; i >= 0; i--){
            c[i] = z[i] - u[i]*c[i+1];
            b[i] = ((a[i+1] - a[i])/h[i]) - (h[i]*(c[i+1]+(2*c[i])))/3;
            d[i] = (c[i+1] - c[i])/(3*h[i]);
        }
        
        //print all info
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-6s%25s%25s%25s%25s%25s\n","j","x[i]","a[i] = f(x[j])","b[i]","c[i]","d[i]");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < len; i++){
            System.out.printf("%-6d%25.20f%25.20f%25.20f%25.20f%25.20f\n",i,x[i],a[i],b[i],c[i],d[i]);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        
    }
    
}
