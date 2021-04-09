/*
 Name: Vaughn Ohlerking
 Project: PA-2
 File: App.java
 Instructor: Fench Chen
 Class: cs4103-sp21
 LogonID: cs410360
*/

import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        // processing string[] args

        int frameCount = 20;// Integer.parseInt(args[0]);
        String inputFileName = "pageref.txt";// args[1];
        int pageAccessTime = 1;// Integer.parseInt(args[2]);
        int swapInTime = 10;// Integer.parseInt(args[3]);
        int swapOutTime = 20;// Integer.parseInt(args[4]);
        String outputFileName = "trace.txt";// args[5];

        // init tracking information
        int[] frames = new int[frameCount];
        byte[] secondChanceBit = new byte[frameCount];
        byte[] modifiedBit = new byte[frameCount];
        int totalPageRefs = 0;
        int totalPageFaultOnReadRef = 0;
        int totalPageFaultOnWriteRef = 0;
        int totalTimeAccessingPagesInMemory = 0;
        int totalTimeSwappingInPages = 0;
        int totalTimeSwappingOutPages = 0;
        
        // CLOCK hand
        int[] clockHand = {0};


        String inFilePath = 
        "/Users/vaughnohlerking/Library/Mobile Documents/com~apple~CloudDocs/LSU/2021 _spring/CSC 4103/homeworks/prog2/" + 
        inputFileName;
        
        String outFilePath = 
        "/Users/vaughnohlerking/Library/Mobile Documents/com~apple~CloudDocs/LSU/2021 _spring/CSC 4103/homeworks/prog2/" + 
        outputFileName;
        
        File input = new File(inFilePath);
        Scanner in = new Scanner(input);
        
        FileWriter myWriter = new FileWriter(outFilePath);

        int found;// holds frame position of desired page once found
        String output;// holds output to be printed to file
        int victim;// page to be swapped out
        int openSlot; // to aid finding open slots
        
        // set frames each to -1 so empty and full are clearly distinguished
        setToNegOne(frames);
        
        // loop runs while file has more lines left
        while(in.hasNext()){
            // read lines one by one
            String[] line = in.nextLine().split(" ");
            String action = line[0];
            int pageNum = Integer.parseInt(line[1]);
            // increment page refs
            totalPageRefs++;
            
            // find page
            found = findPage(pageNum, frames);
            // start building output string
            output = action + " " + pageNum;
            
            if (found != -1){// a memory hit
                // add H and times
                output += " H -1 " + pageAccessTime + " 0 0";
                
            }else{// page fault
                // add F, access time, and swap in time
                output += " F " ;// + pageAccessTime + " " + swapInTime;
                totalTimeSwappingInPages += swapInTime;
                
                if(action.equals("R")){// record what the fault was on
                    totalPageFaultOnReadRef++;
                }else{
                    totalPageFaultOnWriteRef++;
                }
                openSlot = findSlot(frames);
                if (openSlot == -1){// make sure pages are full
                    victim = selectVictim(secondChanceBit,clockHand);// choose victim
                    output += frames[victim] + " " + pageAccessTime + " " + swapInTime;
                    
                    // check if modified to see if swap out necessary
                    if (modifiedBit[victim] == 1){
                        swapInOut(pageNum, victim, frames, secondChanceBit,modifiedBit);// swap victim out of frame and target page in;
                        totalTimeSwappingOutPages += swapOutTime;// increment time and add to output
                        output += " " + swapOutTime;
                    }else{
                        swapIn(pageNum, victim, frames);// just swap in
                        output += " 0";
                    }
                    found = victim;
                }else{
                    
                    swapIn(pageNum, openSlot, frames);
                    output += "-1 " + pageAccessTime + " " + swapInTime + " 0";
                    found = openSlot;
                }
                
            }
            
            totalTimeAccessingPagesInMemory += pageAccessTime;
            
            if (action.equals("R")){// if reading
                secondChanceBit[found] = 1;
                
            }else{// if writing
                modifiedBit[found] = 1;
            }
            myWriter.write("\n" + output);
            System.out.println(output);
        }
        in.close();
        
        myWriter.write(totals(totalPageRefs,totalPageFaultOnReadRef,totalPageFaultOnWriteRef,totalTimeAccessingPagesInMemory,totalTimeSwappingInPages,totalTimeSwappingOutPages));
        System.out.println(totals(totalPageRefs,totalPageFaultOnReadRef,totalPageFaultOnWriteRef,totalTimeAccessingPagesInMemory,totalTimeSwappingInPages,totalTimeSwappingOutPages));
        
        myWriter.close();
    }

    public static int findPage(int p, int[] frames){
        int found = -1;
        for(int i = 0; i < frames.length; i++){// finds page 
            if (frames[i] == p){
                found = i;
                break;
            }
        }
        return found;
    }

    public static int selectVictim(byte[] secondChanceBit, int[] clockHand){
        // this returns the position of the frame to be evicted
        int victim = 0;
        boolean found = false;
        while(!found){
            if(clockHand[0] == secondChanceBit.length - 1){// makes for wraparound effect of a clock
                clockHand[0] = 0;
            }else{
                clockHand[0]++;
            }
            
            if (secondChanceBit[clockHand[0]] == 0){
                found = true;
                victim = clockHand[0];
            }else{
                secondChanceBit[clockHand[0]] = 0;
            }
        }
        return victim;
    }
    
    public static int findSlot(int[] frames){// finds open position in frames
        int num = -1;
        for(int i = 0; i < frames.length; i++){
            if (frames[i] == -1){
                num = i;
            }
        }
        return num;
    }
    
    public static void setToNegOne(int[] arr){
        for (int i = 0; i < arr.length; i++){
            arr[i] = -1;
        }
    }
    
    public static void swapInOut(int target, int victim, int[] frames, byte[] secondChanceBit, byte[] modifiedBit){// swaps target page into victim's old spot and sets modified bit
        modifiedBit[victim] = 0;
        secondChanceBit[victim] = 0;
        frames[victim] = target;// replaces victim page with target one
    }
    
    public static void swapIn(int target, int openSlot, int[] frames){
        frames[openSlot] = target;
    }
    
    public static String totals(int pageRefs, int faultsOnRead, int faultsOnWrite, int accessing, int swapIn, int swapOut){
        return "\ntotal page references: " + pageRefs
         + "\ntotal page faults on read references: " + faultsOnRead
         + "\ntotal page faults on write references: " + faultsOnWrite
         + "\ntotal access time: " + accessing
         + "\ntotal time swapping in pages: " + swapIn
         + "\ntotal time swapping out pages: " + swapOut;
    }
}
