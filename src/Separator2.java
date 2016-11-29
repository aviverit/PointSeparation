import java.util.ArrayList;
import java.util.List;

public class Separator2 {

    private int lineCount=0;
    private int line;
    private int orientation=0; //0 = h, 1=v
    private List<int[]> allUnsegregatedCoords = new ArrayList<int[]>();
    private List<int[]> allUnsegregatedCoordsH = new ArrayList<int[]>();
    private List<int[]> allUnsegregatedCoordsV = new ArrayList<int[]>();
    private List<String> lineList = new ArrayList<String>();
    private List<int[]> largestCoordCollection = new ArrayList<int[]>();
    private List<int[]> largestCoordCollectionH = new ArrayList<int[]>();
    private List<int[]> largestCoordCollectionV = new ArrayList<int[]>();
    private List<List<int[]>> officialCollectionList = new ArrayList<List<int[]>>();
    private List<List<int[]>> horCollectionList = new ArrayList<List<int[]>>();
    private List<List<int[]>> verCollectionList = new ArrayList<List<int[]>>();

    private int breaker = 0;

    Separator2 (List cL){
        allUnsegregatedCoords=cL;
    }

    public void setLines(){
        largestCoordCollection.clear();
        largestCoordCollection.addAll(allUnsegregatedCoords);
        while(allUnsegregatedCoords.size()!=0 && breaker<10){
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
        int currentDistanceH=0;
        int currentDistanceV=0;
        List<int[]> subCollection1 = new ArrayList<int[]>();
        List<int[]> subCollection2 = new ArrayList<int[]>();
        orientation=1;
        largestCoordCollectionH.clear();
        largestCoordCollectionV.clear();
        largestCoordCollectionH.addAll(largestCoordCollection);
        largestCoordCollectionV.addAll(largestCoordCollection);

        while(segmentablePoints<(largestCoordCollectionH.size()/2)){
            currentDistanceH++;
            for(int i=0; i<largestCoordCollectionH.size();i++){
                if(currentDistanceH==largestCoordCollectionH.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1.add(largestCoordCollectionH.get(i)); //Will work on all points equidistant from axis.
                }
                if(currentDistanceH==allUnsegregatedCoordsH.get(i)[orientation]) {
                    if (!subCollection1.contains(largestCoordCollectionH.get(i))) {
                        subCollection1.add(largestCoordCollectionH.get(i)); //Will work on all points equidistant from axis.
                    }
                }
            }
        }
        subCollection2.addAll(allUnsegregatedCoordsH);
        subCollection2.removeAll(subCollection1);
        officialCollectionList.add(subCollection1);
        officialCollectionList.add(subCollection2);

        horCollectionList.addAll(officialCollectionList);
        segregateCollections();

        orientation=0;
        segmentablePoints=0;
        subCollection1.clear();
        subCollection2.clear();

        while(segmentablePoints<(largestCoordCollectionV.size()/2)){
            currentDistanceV++;
            for(int i=0; i<largestCoordCollectionV.size();i++){
                if(currentDistanceV==largestCoordCollectionV.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1.add(largestCoordCollectionV.get(i)); //Will work on all points equidistant from axis.
                }
                if(currentDistanceV==allUnsegregatedCoordsV.get(i)[orientation]) {
                    if (!subCollection1.contains(largestCoordCollectionV.get(i))) {
                        subCollection1.add(largestCoordCollectionV.get(i)); //Will work on all points equidistant from axis.
                    }
                }
            }
        }
        officialCollectionList.clear();
        subCollection2.addAll(allUnsegregatedCoordsV);
        subCollection2.removeAll(subCollection1);
        officialCollectionList.add(subCollection1);
        officialCollectionList.add(subCollection2);

        verCollectionList.addAll(officialCollectionList);
        segregateCollections();

        if(allUnsegregatedCoordsH.size()<allUnsegregatedCoordsV.size()){//if(collectionListH.size()>collectionListV.size()) {//<= //OOOOOOOOOORRRRRRRRRRRRRRRRRRRRR MAYBE THE LINE THAT PRODUCES THE SMALLEST LARGESTCOLLECTION IS BEST
            mergeLists(horCollectionList, largestCoordCollectionH, allUnsegregatedCoordsH);
            line = currentDistanceH;
            orientation = 0;
        }else{
            mergeLists(verCollectionList, largestCoordCollectionV, allUnsegregatedCoordsV);
            line=currentDistanceV;
            orientation=1;
        }
    }

    public void segregateCollections() {
        largestCoordCollection.clear();
        largestCoordCollection.addAll(officialCollectionList.get(0));

        //Collections.sort(officialCollectionList,sizeOrder);

        for (int i = 0; i < officialCollectionList.size(); i++) {       //
            for (int j = 0; j < officialCollectionList.size(); j++) {
                if (i != j) {
                    officialCollectionList.get(i).removeAll(officialCollectionList.get(j));
                    if (largestCoordCollection.size() < officialCollectionList.get(j).size()) {
                        largestCoordCollection.clear();
                        largestCoordCollection.addAll(officialCollectionList.get(j));
                    }
                }
            }

            if (officialCollectionList.get(i).size() == 1) {        //remove fully segregated points from cumulative list
                allUnsegregatedCoords.remove(officialCollectionList.get(i).get(0));
            }
        }

        for(int i = 0; i< officialCollectionList.size(); i++) {
            if (officialCollectionList.get(i).isEmpty()|| officialCollectionList.get(i).size()==1) {
                officialCollectionList.remove(i);
            }
        }
        for(int i = 0; i< officialCollectionList.size(); i++) {
            System.out.println(officialCollectionList.get(i));
        }
    }

    public void mergeLists(List<List<int[]>> oCL, List<int[]> lCC, List<int[]> aUC){//After orientation is decided, set real lists their H or V counterparts
        officialCollectionList.clear();
        officialCollectionList.addAll(oCL);

        largestCoordCollection.clear();
        largestCoordCollection.addAll(lCC);

        allUnsegregatedCoords.clear();
        allUnsegregatedCoords.addAll(aUC);
    }

    public int getLineCount(){
        return this.lineCount;
    }

    public List<String> getLines(){
        return lineList;
    }
}
