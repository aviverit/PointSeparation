import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File dir = new File("input");
        File solDir = new File("output_greedy");
        int fileCounter = 0;

        int coordCounter=0;
        int lineCounter = 0;
        int i=0;
        int j=0;

        //Optionally format filenames before opening so as to
        for (File file : dir.listFiles()) {
            fileCounter++;
            List<int[]> coordList = new ArrayList<int[]>();

            Scanner scan = null;
            try {
                scan = new Scanner(file);
                coordCounter=scan.nextInt();

                while(scan.hasNextInt()) {
                    coordList.add(new int[]{scan.nextInt(), scan.nextInt()});
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            scan.close();

            Separator sep = new Separator(coordCounter, coordList);


            Writer wr = null;
            try {
                wr = new FileWriter("Instance"+new Integer(fileCounter).toString()+".txt");
                wr.write(new Integer(lineCounter).toString());
                wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
