/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heap;


/**
 *
 * @author vaughnohlerking
 */
public class MinHeap {
    
    private int array [];
    private int arraySize; // max array size 
    private int heapSize = 0; // current heap size
    
    // init method
    public MinHeap(int arraysize){
        arraySize = arraysize;
        array = new int[arraySize];      
    }
    
    // Class methods
    
    //inserts value and bubble up to position
    public void insert (int element){
        heapSize ++;//increment heap size
        array[heapSize-1] = element;//add new value
        bubbleUp(heapSize-1);//bubble up to fix order
        
    }
    
    //decrease value at key: i and bubbles down
    public void decreaseKey (int index, int newElement){
        array[index] = newElement;
        bubbleUp(index);
    }
    
    //extracts min, places last value in top position and bubbles down to proper place
    public int extractMin (){
        int min = array[0];//get min
        array[0] = array[heapSize-1];//set new value for top of array
        array[heapSize-1] = 0;//remove value at end of array
        heapSize--;//decrement heap size since last value has been removed
        bubbleDown(0);//fix order by bubbling down
        return min;
    }
    
    //for debugging
    public void printArr(){
        
        for(int i: array){
            System.out.print(i+", ");
        }
        System.out.println();
    }
    ///////////////private methods for performing above tasks
    
    //swaps values upward until in order
    private void bubbleUp(int index){
        while(array[index] < array[parent(index)]){
            swap(index,parent(index));
            index = parent(index);
        }
    }
    
    //swaps values downward until in order
    private void bubbleDown(int index){
        while (array[index] > array[theLeastChild(index)] && array[theLeastChild(index)] != 0 ){
            swap(index,theLeastChild(index));
            index = theLeastChild(index);
        }
    }
    
    //determines child of least value (excluding 0s)
    private int theLeastChild(int i){
        int min = 0;//initialize min
        if (child(i,0) != 0){//set to value of first child if they are not zero
            min = child(i,0);
            
        }else{return 0;}//otherwise return 0, i has no real children
        
        for(int j = 1; j < 3; j++){//find lowest value child and set min to them
            if(array[min] > array[child(i,j)] && array[child(i,j)] != 0){
                min = child(i,j);
            }
        }
        return min;
    }
    
    //determines parent of index i
    private int parent(int i){
        return (i-1)/3;
    }
    //determines jth child at i
    private int child(int i, int j){
        return (i*3)+1+j;
    }
    
    //swaps values at a and b in array
    private void swap(int a, int b){
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
    
}
