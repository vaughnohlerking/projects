/*
this program takes in a list of flights and airports, and returns the flight schedule with the minimum time for each using
dijkstra's algorithm, a heap, and a hash table
vohler1
11/21/2020
*/
package route;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;

public class Route {

    public static String airportsFilePath = "airports.txt";
    public static String flightsFilePath = "flights.txt";
    
    public static void main(String[] args)  throws FileNotFoundException{

        Vertex[] hasharray = new Vertex[1000];
        HeapFunction pq = new HeapFunction(1000);
        
        loadVertices(hasharray, airportsFilePath);//load airports into hash
       
        loadEdges(hasharray, flightsFilePath);//load edges into airports
        
        String origin = args[0];//get origin
        String dest = args[1];//get dest
        
        dijkstra(hasharray, pq, getVertex(hasharray,origin),getVertex(hasharray,dest));//run algorithm
        //print path
        System.out.println(getPath(hasharray,getVertex(hasharray,dest)) + "-" + getVertex(hasharray,dest).getParent().getArrTime());
        
    }
    
    private static Vertex dijkstra(Vertex[] arr, HeapFunction pq, Vertex motherAirPort, Vertex targetAirPort){
        boolean found = false;//when target airport is found this is set to true
        Vertex next;//next is airport to be added
        
        //initialize base airport with artificial parent edge
        Edge mOther = new Edge("000", motherAirPort.getName(), "", 0, 0, 0, 0);
        motherAirPort.setParent(mOther);//set base airport parent edge to artificial edge
        addFlights(arr, pq, motherAirPort);//add flights from mother airport
        motherAirPort.setVisited(true);//set visited to true
        
        
        while (!found){//while target hasn't been found
            
            next = pq.extractMin();//get next min from heap
            next.setVisited(true);//set it to visited
            
            if (next.getName().equals(targetAirPort.getName())){//if found
                found = true;//set found to true
                break;
            }
            addFlights(arr, pq, next);//add flights of new airport
        }
        
        return targetAirPort;//return, though unecessary
    }
    
    private static String getPath(Vertex[] arr, Vertex v){
            if(v.getParent().getOrigin().equals("000")){//recursive function prints flights in order
                return v.getName();
            }
        return getPath(arr, getVertex(arr, v.getParent().getOrigin())) + "-" + v.getName();
    }
    
    private static void addFlights(Vertex[] arr, HeapFunction pq, Vertex v){
        
        LinkedList<Edge> flightsToBeAdded = v.getEdges();//load flights into local linked list
        for (int i = 0; i < flightsToBeAdded.size(); i++){//for whole linked list
            possiblyAddToHeap(arr, pq,flightsToBeAdded.get(i));//add flights to heap
        }
    }
    
    private static void possiblyAddToHeap(Vertex[] arr, HeapFunction pq, Edge e){
        Vertex dest = getVertex(arr,e.getDest());//save val for efficiency
        Vertex origin = getVertex(arr,e.getOrigin());
        
        if (dest.getHeapPos() == -1){//if not in array/heap
            if (origin.getParent().getArrTime() <= e.getDepTime()){//if before next flight
                if (!dest.getVisited()){
                    //dest.setVisited(true);//set destination to visited so that they don't get hit twice
                    dest.setParent(e);//set vertex' parent to edge e
                    pq.insert(dest);//insert vertex
                }
            }        
        }
        else if (e.getArrTime() < dest.getParent().getArrTime()){//if found better arrival time
            if (origin.getParent().getArrTime() <= e.getDepTime()){//if before next flight
                pq.decreaseKey(dest, e);
            }
        }
    }
    
    private static void loadVertices(Vertex[] arr, String path) throws FileNotFoundException{
        File input1 = new File(path);//initiate reader
        Scanner in1 = new Scanner(input1);
        
        int totalCommands = Integer.parseInt(in1.nextLine());//get total command length
        
        for (int i = 0; i < totalCommands; i++){//run for whole file length
            fillVertexProbing(arr, in1.nextLine());//add vertex to correct position
        }
    }
    
    private static void loadEdges(Vertex[] vertices, String path) throws FileNotFoundException{
        File input2 = new File(path);//initiate file reader
        Scanner in2 = new Scanner(input2);
        
        String airline, depAirP, arrAirP;//strings for different input fields
        int p, j, fltNo, depTime, arrTime, distance;//ints for different input fields
        
        int totalCommands = Integer.parseInt(in2.nextLine());//get commands length
        String[] arr = new String[totalCommands+1];//initiate array for flights
        in2.nextLine();//skip first two lines
        in2.nextLine();
        for (int i = 0; i < totalCommands-1; i++){//for all of the rest lines, get the fields
            airline = in2.next();
            fltNo = in2.nextInt();
            depAirP = in2.next();
            arrAirP = in2.next();
            depTime = in2.nextInt();
            arrTime = in2.nextInt();
            distance = in2.nextInt();
            p = myHash(depAirP);
            j = p;
            while (j != p - 1){//add flights to respective airports and initiate (with wrap-around)
                if(vertices[j] != null && vertices[j].getName().equals(depAirP)){
                    //System.out.println(j);
                    vertices[j].addFlight(new Edge(depAirP, arrAirP, airline, fltNo, depTime, arrTime,distance));
                    break;
                }
                j++;
                if (j >= vertices.length-4){
                    j=0;
                }
            }
            
            
        }
    }
    
    private static Vertex getVertex(Vertex[] arr, String airport){//returns vertex to circumvent dealing with probing every time
        int p, i;
        p = myHash(airport);
        i = p;//starts at hash code
        while (i != p - 1){//iterates until back to start (wrap-around)
            if(arr[i] != null && arr[i].getName().equals(airport)){//if equal to searched airport
                return arr[i];//returns vertex
            }
            i++;
            if (i >= arr.length-4){
                i=0;
            }
        }
        return arr[i];//return vertex
    }
  
    private static void fillVertexProbing(Vertex[] arr, String airPCode){
        int p = myHash(airPCode);
        int len = arr.length;
        int i = p;
        
        while (i != p - 1){//fills hash array via probing (with wraparound)
            if(arr[i] == null){
                arr[i] = new Vertex(airPCode);
                break;
            }else
            i++;
                if (i >= len){
                i = 0;
            }
        }
    }
    
    private static int myHash(String arpt){
        //gets hash code from Dr.Shah's given polynomial
        int p0 = arpt.charAt(0) - 'A' + 1;
        int p1 = arpt.charAt(1) - 'A' + 1;
        int p2 = arpt.charAt(2) - 'A' + 1;
        
        int p3 = p0 * 467 * 467 + p1 * 467 + p2;
        
        int p4 = p3 % 7193;
        
        return p4 % 1000;
    }

    private static class HeapFunction {

        private Vertex array [];//init array
        private int arraySize;//will hold size when initiated
        private int heapSize = 0;//tracks heapSize

        public HeapFunction(int arraySize){//constructor builds array
            this.arraySize = arraySize;
            array = new Vertex[arraySize];
        }

        public int getHeapSize(){//returns heapSize (debugging)
            return heapSize;
        }

        public Vertex getElement(int i){//returns element value (debugging)
            return array[i];
        }

        public void printHeap(){//prints whole heap, for debugging. Heads up, this will take too long to print and break your program. only took me 4 little hours to figure that one out
            for(int i = 0; i < heapSize; i++){
                System.out.print(array[i].getName() + "-" + array[i].getParent().getArrTime() + " ");
            }
            System.out.println();
        }

        public void insert (Vertex v){//inserts given vertex
            heapSize++;//increase size
            v.setHeapPos(heapSize -1);//sets stored heapPos
            array[heapSize - 1] = v;//places vertex
            bubbleUp(heapSize-1);//bubble up into position
        }

        public void decreaseKey (Vertex v, Edge newParent){//sets new parent of vertex v
            array[v.getHeapPos()].setParent(newParent);//set parent
            bubbleUp(v.getHeapPos());//bubble up
        }

        public Vertex extractMin(){
            Vertex min = array[0];//store min
            min.setHeapPos(-1);//since it is being extracted, treat it as it doesn't exist in the heap
            swap(0,heapSize-1);
            array[heapSize-1] = null;
            array[0].setHeapPos(0);//adjust heap postion
            heapSize--;//decrement heap size
            bubbleDown(0);//bubble down
            return min;//return min
        }

        public void printArr(){//tbh i don't even know what I made this for
            for(Vertex v: array){
                System.out.print(v.getName()+", ");
            }
            System.out.println();
        }

        //index is given by array position and heapPos
        //key is given by arrTime
        //value is Vertex / airport

            //swaps values upward until in order
        private void bubbleUp(int index){
            //swap while parent is greater than child
            while(array[index].getParent().getArrTime() < array[parent(index)].getParent().getArrTime()){
                swap(index,parent(index));
                index = parent(index);
            }
        }

            //swaps values downward until in order
        private void bubbleDown(int index){
            //swap whil less than child and child is valid
            while (theLeastChild(index) != -1 && array[theLeastChild(index)] != null && array[index].getParent().getArrTime() > array[theLeastChild(index)].getParent().getArrTime() ){
                swap(index,theLeastChild(index));
                index = theLeastChild(index);
            }
        }

        private int theLeastChild(int i){// handles int / position in array
            int min = 0;//initialize min
            if (array[child(i,0)] == null){//set to value of first child if they are not zero
                return -1;
            }
            if(array[child(i,1)] == null || array[child(i,0)].getParent().getArrTime() < array[child(i,1)].getParent().getArrTime()){
                return child(i,0);//first child is smaller
            }
            else{
                return child(i,1);//second child is smaller
            }
        }

        //determines parent of index i
        private int parent(int i){
            return (i-1)/2;
        }
        
        //determines jth child at i
        private int child(int i, int j){
            return (i*2)+1+j;
        }

            //swaps values at a and b in array
        private void swap(int a, int b){
            Vertex temp = array[a];

            array[a].setHeapPos(b);
            array[a] = array[b];

            array[b].setHeapPos(a);
            array[b] = temp;
        }
    }


    private static class Vertex {

        public Vertex(String airportCode){//constructor sets airport code string
            this.airportCode = airportCode;
        }
        private String airportCode;
        private int heapPos = -1;
        private Edge parent;
        private boolean visited = false;

        LinkedList<Edge> flights=new LinkedList<Edge>();


        public void setName(String airPort){
            airportCode = airPort;
        }

        public String getName(){
            return airportCode;
        }

        public void setHeapPos(int pos){
            heapPos = pos;
        }

        public int getHeapPos(){
            return heapPos;
        }

        public void setParent(Edge e){
            parent = e;
        }

        public Edge getParent(){
            return parent;
        }

        public void addFlight(Edge e){
            flights.add(e);
        }

        public Edge[] getFlights(){
            Edge[] flightsArr = new Edge[flights.size()];

            for (int i = 0; i < flights.size(); i++){
                flightsArr[i] = flights.get(i);
            }
            return flightsArr;
        }

        public LinkedList<Edge> getEdges(){
            return this.flights;
        }

        public int totalFlights(){//for debuggin
            int i = 0;
            for(Edge e: this.flights){
                i++;
            }
            return i;
        }

        public void setVisited(boolean bool){
            visited = bool;
        }

        public boolean getVisited(){
            return visited;
        }
    }


    private static class Edge {

        public Edge(String origin, String dest, String airlines, int fltNo, int depTime, int arrTime, int distance){//constructor sets values
            this.origin = origin;
            this.dest = dest;
            this.depTime = depTime;
            this.arrTime = arrTime;
        }
        
        private String origin;
        private String dest;
        private String airlines;

        private int depTime;
        private int arrTime;

        public String getOrigin(){
            return origin;
        }

        public String getDest(){
            return dest;
        }
        
        public int getDepTime(){
            return depTime;
        }

        public int getArrTime(){
            return arrTime;
        }
    }
}
