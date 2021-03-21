
//package avl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author vaughnohlerking
 */

class Node {
    int key, height, data;
    Node left, right;
    
    Node(int d, int da){
        key = d;
        data = da;
        height = 1;
    }
}

public class AVL {

    /**
     * @param args the command line arguments
     */
    
        
    public static void main(String[] args) throws FileNotFoundException{
        
        AVL tree = new AVL(); 
        Scanner scann = new Scanner(System.in);
        //String path = "/Users/vaughnohlerking/Desktop/programming/NetBeans/Text files/inputFile.txt";
        String path = scann.nextLine();// + ".txt"//?
        String[] arr = loadCommands(path); //loads commands into array

        int len =(Integer.parseInt(arr[0]));//get commands total
        int min = 0;

        for(String s: arr){//runs through array and performs the operations commanded by list
            String[] str = s.split(" ");
            //System.out.println(s);
            if(str[0].compareTo("IN") == 0){//insert
                tree.root = tree.insert(tree.root, Integer.valueOf(str[1]),Integer.valueOf(str[2]));
                //System.out.println(tree.root.key);
                
            }else if(str[0].compareTo("RM") == 0){//get min in range

                min = tree.rangeMinData(tree.lca(tree.root, Integer.valueOf(str[1]),Integer.valueOf(str[2])),Integer.valueOf(str[1]),Integer.valueOf(str[2]));   
                System.out.print(min+"\n");
            } 
        }
        //System.out.println(tree.root.right.left.left.key);

        
         
    }
    //initiates first node
    Node root;
    //sets node heights
    int height(Node N) { 
        if (N == null) 
            return 0; 
  
        return N.height; 
    }
    //returns max of two ints
    int max(int a, int b) { 
        return (a > b) ? a : b; 
    } 
    //right rotation function
    Node rightRotate(Node y) { 
        Node x = y.left; 
        Node T2 = x.right; 
  
        // Perform rotation 
        x.right = y; 
        y.left = T2; 
  
        // Update heights 
        y.height = max(height(y.left), height(y.right)) + 1; 
        x.height = max(height(x.left), height(x.right)) + 1; 
  
        // Return new root 
        return x; 
    } 
    //left rotation function
    Node leftRotate(Node x) { 
        Node y = x.right; 
        Node T2 = y.left; 
  
        // Perform rotation 
        y.left = x; 
        x.right = T2; 
  
        //  Update heights 
        x.height = max(height(x.left), height(x.right)) + 1; 
        y.height = max(height(y.left), height(y.right)) + 1; 
  
        // Return new root 
        return y; 
    } 
    //gets balance of node
    int getBalance(Node node){
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }
    //inserts nodes with key and data into tree
    Node insert(Node node, int key,int data){
        
        if (node == null)
            return (new Node(key,data));
        
        if (key < node.key)
            node.left = insert(node.left, key,data);
        else if (key > node.key)
            node.right = insert(node.right, key,data);
        else
            return node;
        
        node.height = 1 + max(height(node.left),height(node.right));
        //check balance
        int balance = getBalance(node);
        //fix based on what balance is
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);
        if (balance > 1 && key > node.left.key){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && key < node.right.key){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    //gets min data in range k1-k2
    int rangeMinData(Node current, int k1, int k2){
        int lmin = current.data;
        int rmin = current.data;
        int min;
        //System.out.println("current key: " + current.key + " current data: "+current.data+ " lmin: " + lmin + " rmin: " + rmin);
        
        if (current.left == null && current.right == null){
            //System.out.println("left and right are null");
            return current.data;
        }
        //check left, recursively
        if (current.right != null){
            if(current.right.key <= k2){
                //System.out.println("going right");
                rmin = rangeMinData(current.right,k1,k2);
            }
            else if(current.right.left != null && current.right.left.key <= k2){
                //System.out.println("going rightLeft");
                rmin = rangeMinData(current.right.left,k1,k2);
                //System.out.println(rmin);
            }
            
        }
        //check right, also recursively
        if(current.left != null){
            //System.out.println("left??");
            if(current.left.key >= k1){
                //System.out.println("going left");
                lmin = rangeMinData(current.left,k1,k2);
            }
            else if(current.left.right != null && current.left.right.key >= k1){
                //System.out.println("going leftRight");
                lmin = rangeMinData(current.left.right,k1,k2);
            }
        }
        //System.out.println("current key: " + current.key + " current data: "+current.data+ " lmin: " + lmin + " rmin: " + rmin);
        min = min3(current.data,lmin,rmin);
        //System.out.println(min);
        
        //System.out.println("rangmdata called. min = "+min);
        return min;
    }
    //returns min of 3 vals
    int min3(int x, int y, int z){
        if(x <= y && x <= z)return x;
        
        if (y <= x && y <= z) return y;
        
        if (z <= x && z <= y) return z;
        return 1;
    }
    //least common ancestor
    Node lca(Node node, int key1, int key2){
        if (node == null)
            return null;
        
        if (node.key > key1 && node.key > key2)
            return lca(node.left, key1, key2);
        
        if (node.key < key1 && node.key < key2)
            return lca(node.right, key1, key2);
        
        return node;
    }
    
        //loads commands from file at String path
    public static String[] loadCommands(String path) throws FileNotFoundException{
        File input = new File(path);
        Scanner in = new Scanner(input);//file opened and scanner started
 
        //number of commands read first and stored
        int totalCommands = Integer.parseInt(in.nextLine());
        
        //array created based off above int
        String[] arr = new String[totalCommands+1];
        arr[0] = Integer.toString(totalCommands);
        
        //commands added to array
        for(int i = 1; i <= totalCommands; i++){
            arr[i] = in.nextLine();
        }
        return arr;
    }
    
}

