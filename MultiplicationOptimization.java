/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplicationoptimization;

/**
 *
 * @author vaughnohlerking
 */
public class MultiplicationOptimization {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        double bas, nest, noise;
        bas = basic(-0.7);
        nest = nested(-0.7);
        noise = bas - nest;
        System.out.println("basic is: " + bas + "\nnested is: " + nest + "\nnoise is: " + noise);
        
    }
    
    public static double basic(double x){
        
        return x*x*x*x*x + 0.9*x*x*x*x - 1.62*x*x*x - 1.458*x*x + 0.6561*x + 0.59049;
    }
    
    public static double nested(double x){
        double mx = x;
        double ans = 0;
        
        ans += 0.5904;
        ans += mx*0.6561;
        mx *= x;
        ans -= mx*1.458;
        mx *= x;
        ans -= mx*1.62;
        mx *= x;
        ans += mx*0.9;
        ans += mx * x;
        
        return ans;
    }
}
