import java.io.*;
import java.util.*;

/*
README

Magic Square
program sums every column, row, and diagonal of a matrix (in this case two matrices imported from disk), and compares
them against each other to see if they are equal, or "magic"

@author Vaughn Ohlerking
@since 3/31/21
*/
public class App {
    public static void main(String[] args) throws FileNotFoundException {
        // file paths
        String filePathLuna = "/Users/vaughnohlerking/Library/Mobile Documents/com~apple~CloudDocs/LSU/2021 _spring/CSC 2610/homeworks/InClassExercise3-30/Luna.txt";
        String filePathMerc = "/Users/vaughnohlerking/Library/Mobile Documents/com~apple~CloudDocs/LSU/2021 _spring/CSC 2610/homeworks/InClassExercise3-30/Mercury.txt";

        // init scanners and file readers
        File inputLuna = new File(filePathLuna);
        Scanner inLuna = new Scanner(inputLuna);

        File inputMerc = new File(filePathMerc);
        Scanner inMerc = new Scanner(inputMerc);
        
        // init array for matrix
        ArrayList<String[]> matLuna = new ArrayList<String[]>();
        // fill matrix
        while(inLuna.hasNext()){
            String[] row = inLuna.nextLine().split("\t");
            matLuna.add(row);
        }
        // repeat for merc
        ArrayList<String[]> matMerc = new ArrayList<String[]>();

        while(inMerc.hasNext()){
            String[] row = inMerc.nextLine().split("\t");
            matMerc.add(row);
        }

        // print arrays
        for(int i = 0; i < 8; i ++){
            for(int j = 0; j < 8; j++){
                System.out.print(matMerc.get(i)[j] + " ");
            }
            System.out.println();
        }
        // print
        System.out.println();
        for(int i = 0; i < 9; i ++){
            for(int j = 0; j < 9; j++){
                System.out.print(matLuna.get(i)[j] + " ");
            }
            System.out.println();
        }
        sumAndCheck(matMerc);
        sumAndCheck(matLuna);
    }

    // Sums and checks input array
    public static void sumAndCheck(ArrayList<String[]> arr){
        // init row, col, and diagonals
        int[] rowSum = new int[arr.get(0).length];
        int[] colSum = new int[arr.size()];
        int diaSum = 0;
        int di2Sum = 0;
        int num;

        // sum using nested for loops
        for (int i = 0; i < arr.size(); i++){
            for (int j = 0; j < arr.get(0).length; j++){
                
                num = Integer.parseInt(arr.get(i)[j]);

                rowSum[i] += num;
                colSum[j] += num;

                if(i == j)
                    diaSum += num;

                if(i == rowSum.length - j - 1)
                    di2Sum += num;
            }
        }
        
        boolean equal = true;
        num = rowSum[0];

        for (int i = 0; i < rowSum.length; i++){
            //check each row, col, and dia
            if (// if not equal
                rowSum[i] != colSum[i] ||
                colSum[i] != diaSum ||
                diaSum != di2Sum
            ){
                System.out.println("NOT EQUAL AT i = " + i);
                equal = false;
            }
        }
        if (equal){
            System.out.println("equal, summing to: " + rowSum[0]);
        }else {
            System.out.println("not equal");
        }

    }
}
