public class App {
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello, World!");

        int lastPrimePos = 0;
        boolean prime;
        long[] primes = new long[10001];
        primes[0] = 2;
        long niq = 1; // number in question
        while (primes[10000] == 0){
            prime = true;
            niq += 2;
            for (long i: primes){
                if (i >= niq / 2 + 1){// only check up to a certain number
                    break;
                }
                if (niq % i == 0){// determine if respectively prime
                    prime = false;
                    break;
                }

            }
            if(prime){// add prime to list
                primes[lastPrimePos + 1] = niq;
                lastPrimePos ++;
            }
        }
        System.out.println("\n10001st prime is: " + primes[10000] + "\n");
    }
}
