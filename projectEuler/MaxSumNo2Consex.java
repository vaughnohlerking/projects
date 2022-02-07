/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maxsumno2consex;

/**
 *
 * @author vaughnohlerking
 */
public class MaxSumNo2Consex {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int a[] = { 3, 2, 1, 4, 2, 6, 3, 1, 8};
                //3 4 6 8 is ans
        no2some(a);
        //printArr(a, included);
    }
    
    private static int no2some(int[] a){
        //program ouptuts array in the form of print, but returns max as value. also prints max

        //initial condiions
        boolean[] included = new boolean[a.length];//bool array to track which values are in final subarray
        int sumWith = a[0];//sum with a[i]
        int sumWithout = 0;//sum without a[i]
        int withoutNew;// this will go into sumWithout, it is only a temporary value to be considered for sumWithout
        
        //loop to add values in optimum way.
        //essentially builds two sums at the same time, picking the best and adjusting both as it goes along
        for(int i = 1; i < a.length; i++){
            
            if (sumWith >= sumWithout){
                withoutNew = sumWith;
                included[i-1] = true;//sets value to true in included
                if(i > 1)//ensures no out of bounds error
                    included[i-2] = false;//if a[i] is in sum, a[i-1] cant be
            }
            else{
                withoutNew = sumWithout;
                included[i-1] = false;// same as above
            }
            
            sumWith = sumWithout + a[i];//sumWith is updated to be "with" a[i]
            sumWithout = withoutNew;//sumWithout is updated as per the conditional above
            
        }
        //these are here to correct the last value
        included[a.length-1] = (sumWith > sumWithout);
        included[a.length-2] = !(included[a.length-1]);
        
        //print sum
        sumWith =((sumWith > sumWithout)? sumWith : sumWithout);
        System.out.println(sumWith);
        
        //print array values
        for (int i = 0; i < a.length; i++){
            if (included[i]){
                System.out.print(" "+a[i]);
            }
        }
        
        //return sum
        return sumWith;
    }
    
    private static void printArr(int[] a, boolean[] included){
        
        for (int i = 0; i < a.length; i++){
            if (included[i]){
                System.out.print(" "+a[i]);
            }
        }
    }
    
}
