/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trapezoidal_simpson;

import java.lang.Math;
/**
 * This program gives the values of the Trapezoidal and Simpson method approximation for a given interval and No of sub-intervals
 * 
 * CSC 2262 Programming project No 3?
 * 
 * @author vaughnohlerking
 * @since 10/27/2020
 */

public class Trapezoidal_Simpson {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.printf("%30s%25.20f\n", "Trapezoidal Rule: T512(f) = ", Trapezoidal(512,1,3));
        System.out.printf("%30s%25.20f\n", "Simpson Rule: T512(f) = " , Simpson(512,1,3));
    }
    
    /**
     * This method gives f(x) with given function f(x) = ln(x)
     * 
     * method: f
     * 
     * return type: double
     * 
     * parameters: 
     * x [double] number to be input to function
     * 
     * @author vaughnohlerking
     * @since 10/27/2020
     * 
    */
    public static double f(double x){
        //return 1/(1 + x * x);//for testing purposes
        return Math.log(x);//the only necessary computation
    }
    
    /**
     * This method evaluates the Trapezoidal summation from a to b with subInts # of intervals
     * 
     * method: Trapezoidal
     * 
     * return type: double
     * 
     * parameters: 
     * subInts [double] number intervals to calculate for
     * a [double] bottom of interval. converted to double for purpose of later calculations
     * b [double] top of interval. converted to double for purpose of later calculations
     * 
     * @author vaughnohlerking
     * @since 10/27/2020
     * 
    */
    public static double Trapezoidal(double subInts, double a, double b){
        double dx = (b - a) / subInts;//calculates and stores rate of change
        double xj = 0;//inits first x
        double sum = .5 * f(a);//adds first term since it has a different constant
        
        for (int i = 1; i <= subInts - 1; i++){//loop adds each term to sum
            
            xj = a + i*dx;//calculates x_j
            sum += f(xj);//adds f(x_j)
        }
        sum += .5 * f(b);//adds last term since it has a different constant
        sum *= dx;//multiplies entire sum by rate of change
 
        return sum;
    }
    
    /**
     * This method evaluates the Simpson summation from a to b with subInts # of intervals
     * 
     * method: Simpson
     * 
     * return type: double
     * 
     * parameters: 
     * subInts [double] number intervals to calculate for
     * a [double] bottom of interval. converted to double for purpose of later calculations
     * b [double] top of interval. converted to double for purpose of later calculations
     * 
     * @author vaughnohlerking
     * @since 10/27/2020
     * 
    */
    public static double Simpson(double subInts, double a, double b){
        double dx = (b - a) / subInts;//calculates and stores rate of change
        double xj = 0;//inits first x
        double sum = f(a);//adds first term since it has no constant
        int simpConst;//alternates between 4 and 2 for sum
        
        for (int i = 1; i <= subInts - 1; i++){//loop adds each term to sum
            
            xj = a + i*dx;//calculates x_j
            simpConst = i % 2 == 0 ? 2 : 4; //adjusts simpConst to either 4 or 2, based on even or odd i
            sum += simpConst*f(xj);//adds f(x_j) * simpConst
        }
        sum += f(b);//adds last term since it has no constant
        sum *= dx/3;//multiplies entire sum by rate of change over 3

        return sum;
    }
    
}
