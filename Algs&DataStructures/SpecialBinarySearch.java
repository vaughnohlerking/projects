
package special.binary.search;

/**
 *
 * @author vaughnohlerking
 */
public class SpecialBinarySearch {

    
    public static void main(String[] args) {
        //array has odd numbers on left and even on right for purpose of debugging
        int[] arrayOfNums = {1,3,5,7,9,11,13,15,17,19,21,23,25,25,25,25,25,25,25,27,29,31,33,35,37,39,71,81,100,100,98,98,18,16,14,12,10,8,6,4,2};
        int max = binaryMax(arrayOfNums,0,arrayOfNums.length,2);
        System.out.println("max: pos " + max + " = val " + arrayOfNums[max] + ". arr length = " + arrayOfNums.length);
    }
    
    public static int binaryMax(int[] arr, int l, int r, int max){
        int mid = ((r - l) / 2)+l;
        
        while (arr[mid] == arr[mid-1]){
            mid -= 1;
        }
        int midLeft = mid - 1;
        
        if (r - l == 1){
            if (arr[l]>arr[r]){
                max = l;
            }else{
                max = r;
            }
            return max;
        }
        if (arr[mid] > arr[midLeft]){
            if (arr[mid] > arr[max]){
                max = mid;//max updated   
            }
            max = binaryMax(arr,mid,r,max);
        }else{
            if (arr[midLeft]> arr[max]){
                max = midLeft;//max updated
            }
            max = binaryMax(arr,l,midLeft,max);
        }
        return max;
    }
}
