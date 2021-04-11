import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Progetto {

    public static void main(String[] args) {

        try {
        int A = 1000;
        double end;
        double start;
        int[] N = new int[130];
        double a = (Math.log(500000) - Math.log(A)) / 129;
        double Err = 0.001;
        Scanner in = new Scanner(System.in);
        int length = 1274;
        int count = 181;
        ArrayList<Integer> periodi = new ArrayList<Integer>();
        File smart = new File("smart.txt");
        File distribution = new File("distribuzione.txt");
        File naive = new File("naive.txt");
        FileWriter writerSmart = new FileWriter("smart.txt");
        FileWriter writerDis = new FileWriter("distribuzione.txt");
        FileWriter writerNaive = new FileWriter("naive.txt");
        double[] num_periodi = new double[101];
        double[] num_periodi_worst = new double[187];
        int reps = 0;

        System.out.print("Inserire 1 se si vuole usare il metodo di generazione 1, 2 se si vuole usare il secondo, 3 per il terzo e 4 per il caso peggiore: ");
        int mode = in.nextInt();
        System.out.print("\n" + "Inserire un numero per iniziare la stima dei tempi per il periodo frazionario smart: ");
        writerSmart.write("Tempi smart: \n");
        

        if(mode <=3) {
            if (in.hasNextInt()) {
                System.out.println("Avvio calcolo:" + "\n");
                for (int j = 0; j <= 129; j++) {
                    double r = getResolution();
                    double Tmin = r * (1 / Err + 1);
                    N[j] = (int) (A * (Math.pow(Math.exp(a), j))); //A*(B^j)
                    start = System.currentTimeMillis();
                    int k = 0;
                    do {
                        StringBuilder s = generateString(N[j], mode);
                        if(N[j] == 1000) {
                            periodi.add(periodSmart(s));
                        } else {
                            periodSmart(s);
                        }
                        end = System.currentTimeMillis();
                        k++;
                    } while (end - start < Tmin);
                    double Tn = ((end - start) / k);
                    writerSmart.write(Tn + ", ");
                    System.out.print(Tn + ", ");
                }
            }
        } else {
            if (in.hasNextInt()) {
                System.out.println("Avvio calcolo:" + "\n");
                while(length < 500000) {
                    double r = getResolution();
                    double Tmin = r * (1 / Err + 1);
                    start = System.currentTimeMillis();
                    int k = 0;
                    do {
                        StringBuilder s = generateWorstCase(length);
                        if(length == 1274) {
                            periodi.add(periodSmart(s));
                        } else {
                            periodSmart(s);
                        }
                        end = System.currentTimeMillis();
                        k++;
                    } while (end - start < Tmin);
                    double Tn = ((end - start) / k);
                    writerSmart.write(Tn + ", ");
                    System.out.print(Tn + ", ");
                    count = count + 30;
                    length = length + 2*count; 
                }
            }
        }
    
        length = 1274;
        count = 181;

        System.out.println("\n" + "Processo terminato");
        
        writerDis.write("\nDistribuzione periodi: \n");

        int temp;
        double r = getResolution();
        double Tmin = r * (1 / Err + 1);
        start = System.currentTimeMillis();
        int k = 0;
        do {
            StringBuilder s;
            if(mode <= 3) {
                s = generateString(100, mode);
                temp = periodSmart(s);
                num_periodi[temp]++;
            } else {
                s = generateWorstCase(186);
                temp = periodSmart(s);
                num_periodi_worst[temp]++;
            }
            end = System.currentTimeMillis();
            k++;
        } while (end - start < Tmin);
        reps = k;

        if(mode <= 3) {
            for(int i=1; i<=100; i++) {
            writerDis.write(num_periodi[i]/reps + ",\n");
            }
        } else {
            for(int i=1; i<=186; i++) {
                writerDis.write(num_periodi_worst[i]/reps + ",\n");
            }
        }
        

        Scanner scan1 = new Scanner(System.in);

        writerNaive.write("\nTempi naive: \n");

        System.out.println("Inserire un numero per iniziare la stima dei tempi per il periodo frazionario naive: ");

        if(mode <=3) {
            if (scan1.hasNextInt()) {
                System.out.println("Avvio calcolo:" + "\n");
                for (int j = 0; j <= 129; j++) {
                    r = getResolution();
                    Tmin = r * (1 / Err + 1);
                    N[j] = (int) (A * (Math.pow(Math.exp(a), j))); //A*(B^j)
                    start = System.currentTimeMillis();
                    k = 0;
                    do {
                        StringBuilder s = generateString(N[j], mode);
                        if(N[j] == 1000) {
                            periodi.add(periodNaive(s));
                        } else {
                            periodNaive(s);
                        }
                        end = System.currentTimeMillis();
                        k++;
                    } while (end - start < Tmin);
                    double Tn = ((end - start) / k);
                    writerNaive.write(Tn + ",");
                    System.out.print(Tn + ", ");
                }
            }
        } else {
            if (in.hasNextInt()) {
                System.out.println("Avvio calcolo:" + "\n");
                while(length < 500000) {
                    r = getResolution();
                    Tmin = r * (1 / Err + 1);
                    start = System.currentTimeMillis();
                    k = 0;
                    do {
                        StringBuilder s = generateWorstCase(length);
                        if(length == 1274) {
                            periodi.add(periodNaive(s));
                        } else {
                            periodNaive(s);
                        }
                        end = System.currentTimeMillis();
                        k++;
                    } while (end - start < Tmin);
                    double Tn = ((end - start) / k);
                    writerNaive.write(Tn + ",");
                    System.out.print(Tn + ", ");
                    count = count + 30;
                    length = length + 2*count;
                }
            }
        }
        System.out.println("\n" + "Processo terminato");
        writerSmart.close();
        writerNaive.close();
        writerDis.close();
        } catch(IOException e) {
            System.out.println("Errore nella creazione/scrittura del file: \n");
            e.printStackTrace();
        }
        
    }



    public static StringBuilder generateString(int N, int mode) {

        StringBuilder s = new StringBuilder(N);

        switch (mode) {
            case 1:
                for (int i = 0; i < N; i++) {
                    if (Math.random() <= 0.5) {
                        s.append('a');
                    } else {
                        s.append('b');
                    }
                }
                break;
            case 2:
                Random rand = new Random();
                int q = rand.nextInt(N - 1) + 1;
                for (int i = 0; i < q; i++) {
                    if (Math.random() <= 0.5) {
                        s.append('a');
                    } else {
                        s.append('b');
                    }
                }

                for (int i = q; i < N; i++) {
                    s.append(s.charAt(i % q));
                }
                break;
            case 3:
                Random rand2 = new Random();
                int p = rand2.nextInt(N - 1) + 1;
                for (int i = 0; i < p; i++) {
                    if (Math.random() <= 0.5) {
                        s.append('a');
                    } else {
                        s.append('b');
                    }
                }

                s.append('c');

                for (int i = p + 1; i < N; i++) {
                    s.append(s.charAt(i %( p+1)));
                }
                break;
            case 4:
                
        }
        return s;
    }
    
    public static StringBuilder generateWorstCase(int length) {
        int i = 1;
        int current = 0;
        StringBuilder s = new StringBuilder(length);
        while(current < length) {
            for(int j = 0; j<i; j++) {
                s.append('a');
            }
            for(int j = 0; j<i; j++) {
                s.append('b');
            }
            current += 2*i;
            i = i+30;
        }
        
        return s;
    }

    public static double getResolution() {
        double end;
        double start = System.currentTimeMillis();
        do {
            end = System.currentTimeMillis();
        } while (start == end);
        return (end - start);
    }

    public static int periodNaive(StringBuilder s) {
        int n = s.length();
        for (int i = 1; i < n; i++) { //costo: O(n)
            String s1 = s.substring(0, n - i);
            String s2 = s.substring(i, n);
            if (s1.equals(s2)) { //costo: O(n)
                return i;
            }
        } //costo tot: O(n^2)
        return n;
    }

    public static int periodSmart(StringBuilder s) { //costo tot: O(n)
        int n = s.length();
        int[] r = new int[n];
        r[0] = 0;
        for (int i = 1; i < n; i++) {
            int z = r[i - 1];
            while ((s.charAt(i) != s.charAt(z)) && z > 0) {
                z = r[z - 1];
            }
            if (s.charAt(i) == s.charAt(z)) {
                r[i] = z + 1;
            } else {
                r[i] = 0;
            }
        }
        return n - r[n - 1];  //r[n-1]=bordo max
    }
}
