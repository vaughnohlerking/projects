public class App {
    public static void main(String[] args) throws Exception {
        int ceiling = 999;
        int num = 0;
        
        System.out.println(palendromize(ceiling));
        for(int i = ceiling; i > 100; i--){
            num = checkFactors(palendromize(i));
            if (num != 0){
                System.out.println("factors of " + palendromize(i) + " are: " + num + " and " + palendromize(i) / num);
                break;
            }
        }
    }

    public static int checkFactors(int num){
        int ret = 0;
        for (int i = 999; i > 99; i--){
            if (num % i == 0 && num / i > 99 && num / i < 1000){
                ret = i;
                break;
            }
        }
        return ret;
    }

    public static int palendromize(int x){
        String s = Integer.toString(x);
        s = s + s.charAt(2) + s.charAt(1) + s.charAt(0);
        return Integer.valueOf(s);
    }

}
