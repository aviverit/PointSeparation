import java.util.ArrayList;
import java.util.List;

public class Separator {

    private int lineCount=0;
    private int line;
    private int orientation=0; //0 = h, 1=v
    private List<int[]> coordList = new ArrayList<int[]>();
    private List<int[]> coordListH = new ArrayList<int[]>();
    private List<int[]> coordListV = new ArrayList<int[]>();
    private List<String> lineList = new ArrayList<String>();
    private List<int[]> tempCoordList = new ArrayList<int[]>();
    private List<int[]> tempCoordListH = new ArrayList<int[]>();
    private List<int[]> tempCoordListV = new ArrayList<int[]>();
    private List<List<int[]>> collectionList = new ArrayList<List<int[]>>();
    private List<List<int[]>> collectionListH = new ArrayList<List<int[]>>();
    private List<List<int[]>> collectionListV = new ArrayList<List<int[]>>();

    private int breaker = 0;

    Separator (List cL){
        coordList=cL;
    }

    public void setLines(){
        tempCoordList.clear();
        tempCoordList.addAll(coordList);
        while(coordList.size()!=0 && breaker<10){
            calculateLine();
            System.out.println("1 lineCalculated");//---------------------------------------------------------
            appendLine();
            breaker++;
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
        int currentDistanceH=0;
        int currentDistanceV=0;
        List<int[]> subCollection1 = new ArrayList<int[]>();//May need to be deprecated. Instead create enough lists  to double tempCoordList
        List<int[]> subCollection2 = new ArrayList<int[]>();
        List<List<int[]>> collectionList2 = new ArrayList<List<int[]>>();

        tempCoordListH.clear();
        tempCoordListH.addAll(tempCoordList);
        tempCoordListV.clear();
        tempCoordListV.addAll(tempCoordList);
        line=0;
        orientation=0;

        while(segmentablePoints<(tempCoordListH.size()/2)){
            currentDistanceH++;
            for(int i=0; i<tempCoordListH.size();i++){
                if(currentDistanceH==tempCoordListH.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1.add(tempCoordListH.get(i)); //Will work on all points equidistant from axis.
                }
            }
        }
        subCollection2.addAll(tempCoordListH);
        subCollection2.removeAll(subCollection1);
        collectionList.add(subCollection1);
        collectionList.add(subCollection2);

        collectionListH.addAll(collectionList);
        segregateCollectionsH();

        line=0;
        orientation=1;
        segmentablePoints=0;

        subCollection1.clear();
        subCollection2.clear();

        while(segmentablePoints<(tempCoordListV.size()/2)){
            currentDistanceV++;
            for(int i=0; i<tempCoordListV.size();i++){
                if(currentDistanceV==tempCoordListV.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1.add(tempCoordListV.get(i)); //Will work on all points equidistant from axis.
                }
            }
        }
        collectionList.clear();
        subCollection2.addAll(tempCoordListV);
        subCollection2.removeAll(subCollection1);
        collectionList.add(subCollection1);
        collectionList.add(subCollection2);

        collectionListV.addAll(collectionList);
        segregateCollectionsV();

        if(collectionListH.size()>collectionListV.size()) {
            mergeLists(collectionListH, tempCoordListH, coordListH);
            line = currentDistanceH;
            orientation = 0;
        }else{
            mergeLists(collectionListV, tempCoordListV, coordListV);
            line=currentDistanceV;
            orientation=1;
        }
    }

    public void segregateCollectionsH() {
        tempCoordListH.clear();
        tempCoordListH.addAll(collectionListH.get(0));
        coordListH.clear();
        coordListH.addAll(coordList);

        for (int i = 0; i < collectionListH.size(); i++) {       //
            for (int j = 0; j < collectionListH.size(); j++) {
                if (i != j) {
                    if(collectionListH.get(i).size()>collectionListH.get(j).size()) {
                        collectionListH.get(i).removeAll(collectionListH.get(j));
                    }
                    if (tempCoordListH.size() < collectionListH.get(j).size()) {
                        tempCoordListH.clear();
                        tempCoordListH.addAll(collectionListH.get(j));
                    }
                }
            }

            if (collectionListH.get(i).size() == 1) {        //remove fully segregated points from cumulative list
                coordListH.remove(collectionListH.get(i).get(0));
            }
        }

        for(int i=0; i<collectionListH.size();i++) {
            if (collectionListH.get(i).isEmpty()||collectionListH.get(i).size()==1) {
                collectionListH.remove(i);
            }
        }
        for(int i=0; i<collectionListH.size();i++) {
            System.out.println(collectionListH.get(i));
        }
    }

    public void segregateCollectionsV() {
        tempCoordListV.clear();
        tempCoordListV.addAll(collectionListV.get(0));
        coordListV.clear();
        coordListV.addAll(coordList);

        for (int i = 0; i < collectionListV.size(); i++) {       //
            for (int j = 0; j < collectionListV.size(); j++) {
                if (i != j) {
                    if(collectionListV.get(i).size()>collectionListV.get(j).size()) {
                        collectionListV.get(i).removeAll(collectionListV.get(j));
                    }
                    if (tempCoordListV.size() < collectionListV.get(j).size()) {
                        tempCoordListV.clear();
                        tempCoordListV.addAll(collectionListV.get(j));
                    }
                }
            }

            if (collectionListV.get(i).size() == 1) {        //remove fully segregated points from cumulative list
                coordListV.remove(collectionListV.get(i).get(0));
            }
        }

        for(int i=0; i<collectionListV.size();i++) {
            if (collectionListV.get(i).isEmpty()||collectionListV.get(i).size()==1) {
                collectionListV.remove(i);
            }
        }
        for(int i=0; i<collectionListV.size();i++) {
            System.out.println(collectionListV.get(i));
        }
    }

    public void mergeLists(List<List<int[]>> colL, List<int[]> tCL, List<int[]> coordL){//After orientation is decided, set real lists their H or V counterparts
        collectionList.clear();
        collectionList.addAll(colL);

        tempCoordList.clear();
        tempCoordList.addAll(tCL);

        coordList.clear();
        coordList.addAll(coordL);
    }

    public int getLineCount(){
        return this.lineCount;
    }

    public List<String> getLines(){
        return lineList;
    }
}


