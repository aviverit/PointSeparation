import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Separator {

    private int lineCount=0;
    private int coordCount=0;
    private int line;
    private int orientation=0; //0 = h, 1=v
    private List<int[]> coordList = new ArrayList<int[]>();
    private List<String> lineList = new ArrayList<String>();
    private List<int[]> tempCoordList = new ArrayList<int[]>();
    private List<int[]> tempCoordListH = new ArrayList<int[]>();
    private List<int[]> tempCoordListV = new ArrayList<int[]>();
    private List<List<int[]>> collectionList = new ArrayList<List<int[]>>();
    private List<List<int[]>> collectionListH = new ArrayList<List<int[]>>();
    private List<List<int[]>> collectionListV = new ArrayList<List<int[]>>();

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
            lineList.add("h "+ Integer.toString(line) +".5 \n");
        }else{
            lineList.add("v "+ Integer.toString(line) +".5 \n");
        }
    }

    public void calculateLine(){
        int segmentablePoints=0;
        int currentDistance=0;
        int currentDistanceH=0;
        int currentDistanceV=0;
        List<int[]> subCollection1 = new ArrayList<int[]>();//May need to be deprecated. Instead create enough lists  to double tempCoordList
        List<int[]> subCollection2 = new ArrayList<int[]>();
        List<List<int[]>> collectionList2 = new ArrayList<List<int[]>>();

        tempCoordListH.clear();
        tempCoordListH.addAll();
        line=0;
        orientation=0;

        while(segmentablePoints<(tempCoordListH.size()/2)){
            currentDistance++;
            for(int i=0; i<tempCoordListH.size();i++){
                if(currentDistance==tempCoordListH.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1.add(tempCoordListH.get(i)); //Will work on all points equidistant from axis.
                }
            }
        }
        subCollection2.addAll(tempCoordListH);
        subCollection2.removeAll(subCollection1);
        collectionList.add(subCollection1);
        collectionList.add(subCollection2);


        segregateCollections();
        collectionListH.addAll(collectionList);


        tempCoordListH.clear();
        tempCoordListH.addAll();
        line=0;
        orientation=1;
        segmentablePoints=0;

        while(segmentablePoints<(tempCoordListV.size()/2)){
            currentDistance++;
            for(int i=0; i<tempCoordListV.size();i++){
                if(currentDistance==tempCoordListV.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1.add(tempCoordListV.get(i)); //Will work on all points equidistant from axis.
                }
            }
        }
        subCollection2.addAll(tempCoordListV);
        subCollection2.removeAll(subCollection1);
        collectionList.add(subCollection1);
        collectionList.add(subCollection2);

        segregateCollections();
        collectionListV.addAll(collectionList);

        if(collectionListH.size()>collectionListV.size()) {
            line = currentDistanceH;
            orientation = 0;
        }else{
            line=currentDistanceV;
            orientation=1;
        }
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
            if (collectionList.get(i).isEmpty()||collectionList.get(i).size()==1) {
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


