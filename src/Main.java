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
        int lineCounter;

        for(File ofile: solDir.listFiles()){
            if(!ofile.isDirectory()) {
                ofile.delete();
            }
        }

        for (File ifile : dir.listFiles()) {
            fileCounter++;
            List<int[]> coordList = new ArrayList<int[]>();

            try {
                Scanner scan =  new Scanner(ifile);
                coordCounter=scan.nextInt();

                while(scan.hasNextInt()) {
                    coordList.add(new int[]{scan.nextInt(), scan.nextInt()});
                }
                scan.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Separator sep = new Separator(coordCounter, coordList);
            List<String> lineList = new ArrayList<String>();

            sep.setLines();
            lineCounter=sep.getLineCount();
            lineList=sep.getLines();

            try {

                String fCFormat= String.format("%02d", fileCounter);
                Writer wr = new FileWriter( "./output_greedy/greedy_solution"+fCFormat+".txt");

                for(int k=0;k<lineCounter;k++) {

                    wr.write(lineList.get(k));
                }
                wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
