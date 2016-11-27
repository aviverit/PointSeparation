import java.util.ArrayList;
import java.util.List;

public class Separator {

    private int lineCount=0;
    private int coordCount=0;
    private int line;
    private int orientation=0; //0 = h, 1=v
    private List<int[]> coordList = new ArrayList<int[]>();
    private List<String> lineList = new ArrayList<String>();
    List<int[]> tempCoordList = new ArrayList<int[]>();
    private List<List<int[]>> collectionList = new ArrayList<List<int[]>>();

    private int breaker = 0;

    Separator (int cC, List cL){
        coordCount=cC;
        coordList=cL;
    }

    public void setLines(){
        tempCoordList.clear();
        tempCoordList.addAll(coordList);
        while(coordList.size()!=0 && breaker<10){
            calculateLine();
            System.out.println("1 lineCalculated");//---------------------------------------------------------
            appendLine();
            //breaker++;
        }
    }

    public void appendLine(){
        lineCount++;
        if(orientation==0){
            lineList.add("h "+ Integer.toString(line) +" \n");
        }else{
            lineList.add("v "+ Integer.toString(line) +" \n");
        }
    }

    public void calculateLine(){
        int segmentablePoints=0;
        int currentDistance=0;
        List<int[]> subCollection1 = new ArrayList<int[]>();
        List<int[]> subCollection2 = new ArrayList<int[]>();

        line=0;
        if(orientation==1){ //NOOOOOTTTTTGGGGGOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOODDDD
            orientation=0;
        }else{
            orientation=1;
        }

        while(segmentablePoints<(tempCoordList.size()/2)){
            currentDistance++;
            for(int i=0; i<tempCoordList.size();i++){
                if(currentDistance==tempCoordList.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1.add(tempCoordList.get(i)); //Will work on all points equidistant from axis.
                }
            }
        }
        subCollection2.addAll(tempCoordList);
        subCollection2.removeAll(subCollection1);
        collectionList.add(subCollection1);
        collectionList.add(subCollection2);

        segregateCollections();

        line=currentDistance;
    }

    public void segregateCollections() {
        tempCoordList.clear();
        tempCoordList.addAll(collectionList.get(0));

        for (int i = 0; i < collectionList.size(); i++) {       //
            for (int j = 0; j < collectionList.size(); j++) {
                if (i != j) {
                    collectionList.get(i).removeAll(collectionList.get(j));
                    if (tempCoordList.size() < collectionList.get(j).size()) {
                        tempCoordList.clear();
                        tempCoordList.addAll(collectionList.get(j));
                    }
                }
            }

            if (collectionList.get(i).size() == 1) {        //remove fully segregated points from cumulative list
                coordList.remove(collectionList.get(i).get(0));
            }

        }

        for(int i=0; i<collectionList.size();i++) {
            if (collectionList.get(i).isEmpty()) {
                collectionList.remove(i);
            }
        }
        for(int i=0; i<collectionList.size();i++) {
            System.out.println(collectionList.get(i));
        }
    }

    public int getLineCount(){
        return this.lineCount;
    }

    public List<String> getLines(){
        return lineList;
    }
}
