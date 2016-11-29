import java.util.ArrayList;
import java.util.List;

public class Separator2 {

    private int lineCount=0;
    private int line;
    private int orientation=0; //0 = h, 1=v
    private List<int[]> allUnsegregatedCoords = new ArrayList<int[]>();
    private List<int[]> allUnsegregatedCoordsH = new ArrayList<int[]>();
    private List<int[]> allUnsegregatedCoordsV = new ArrayList<int[]>();
    private List<int[]> allCoords = new ArrayList<int[]>();
    private List<String> lineList = new ArrayList<String>();
    private List<int[]> largestCoordCollection = new ArrayList<int[]>();
    private List<int[]> largestCoordCollectionH = new ArrayList<int[]>();
    private List<int[]> largestCoordCollectionV = new ArrayList<int[]>();
    private List<List<int[]>> officialCollectionList = new ArrayList<List<int[]>>();
    private List<List<int[]>> officialCollectionListH = new ArrayList<List<int[]>>();
    private List<List<int[]>> officialCollectionListV = new ArrayList<List<int[]>>();

    private int breaker = 0;
    private int baseCase = 0;

    Separator2 (List<int[]> aC){
        allCoords.addAll(aC);
        allUnsegregatedCoords.addAll(allCoords);
    }

    public void setLines(){
        largestCoordCollection.clear();
        largestCoordCollection.addAll(allUnsegregatedCoords);
        while(allUnsegregatedCoords.size()!=0 && breaker<10){
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
        List<int[]> subCollection1 = new ArrayList<int[]>();
        List<int[]> subCollection2 = new ArrayList<int[]>();
        orientation=1;
        largestCoordCollectionH.clear();
        largestCoordCollectionV.clear();
        largestCoordCollectionH.addAll(largestCoordCollection);
        largestCoordCollectionV.addAll(largestCoordCollection);
        allUnsegregatedCoordsH.addAll(allUnsegregatedCoords);
        allUnsegregatedCoordsV.addAll(allUnsegregatedCoords);

        while(segmentablePoints<(largestCoordCollectionH.size()/2)){
            currentDistanceH++;
            for(int i=0; i<largestCoordCollectionH.size();i++){
                if(currentDistanceH==largestCoordCollectionH.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1.add(largestCoordCollectionH.get(i)); //Will work on all points equidistant from axis.
                }
                if(currentDistanceH==allCoords.get(i)[orientation]) {
                    if (!subCollection1.contains(largestCoordCollectionH.get(i))) {
                        subCollection1.add(largestCoordCollectionH.get(i)); //Will work on all points equidistant from axis.
                    }
                }
            }
        }
        subCollection2.addAll(allCoords);
        subCollection2.removeAll(subCollection1);
        officialCollectionList.add(subCollection1);
        officialCollectionList.add(subCollection2);

        officialCollectionListH.addAll(officialCollectionList);
        segregateCollectionsH();

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
                if(currentDistanceV==allCoords.get(i)[orientation]) {
                    if (!subCollection1.contains(largestCoordCollectionV.get(i))) {
                        subCollection1.add(largestCoordCollectionV.get(i)); //Will work on all points equidistant from axis.
                    }
                }
            }
        }
        officialCollectionList.clear();
        subCollection2.addAll(allCoords);
        subCollection2.removeAll(subCollection1);
        officialCollectionList.add(subCollection1);
        officialCollectionList.add(subCollection2);

        officialCollectionListV.addAll(officialCollectionList);
        segregateCollectionsV();

        if(largestCoordCollectionH.size()<largestCoordCollectionV.size()){//(allUnsegregatedCoordsH.size()<allUnsegregatedCoordsV.size()){//if(collectionListH.size()>collectionListV.size()) {//<= //OOOOOOOOOORRRRRRRRRRRRRRRRRRRRR MAYBE THE LINE THAT PRODUCES THE SMALLEST LARGESTCOLLECTION IS BEST
            mergeLists(officialCollectionListH, largestCoordCollectionH, allUnsegregatedCoordsH);
            line = currentDistanceH;
            orientation = 0;
        }else{
            mergeLists(officialCollectionListV, largestCoordCollectionV, allUnsegregatedCoordsV);
            line=currentDistanceV;
            orientation=1;
        }
    }

    public void segregateCollectionsH() {
        largestCoordCollectionH.clear();
        largestCoordCollectionH.addAll(officialCollectionListH.get(0));

        System.out.println("before segregationH");
        System.out.println(officialCollectionListH.size());

        List<List<int[]>> tempOfficialCollectionList = new ArrayList<List<int[]>>();

        for(int i=0; i<officialCollectionListH.size()-2;i++){//n-1 and n-2 should not add themselves to the list
            if(officialCollectionListH.get(officialCollectionListH.size()-1).containsAll(officialCollectionListH.get(i))||officialCollectionListH.get(officialCollectionListH.size()-2).containsAll(officialCollectionListH.get(i))){
                tempOfficialCollectionList.add(officialCollectionListH.get(i)); //add all collections that don't intersect the new line.
            }
        }

        officialCollectionListH.removeAll(tempOfficialCollectionList);
        System.out.println("Mid segregationH");
        System.out.println(officialCollectionListH.size());

        for(int i=0; i<officialCollectionListH.size()-2; i++){
            List<int[]> subCollection1 = new ArrayList<int[]>();
            List<int[]> subCollection2 = new ArrayList<int[]>();

            subCollection1.addAll(officialCollectionListH.get(i));
            subCollection2.addAll(officialCollectionListH.get(i));
            subCollection1.retainAll(officialCollectionListH.get(officialCollectionListH.size()-1));
            subCollection2.retainAll(officialCollectionListH.get(officialCollectionListH.size()-2));

            tempOfficialCollectionList.add(subCollection1);
            tempOfficialCollectionList.add(subCollection2);
        }


        if(baseCase<2) {
            officialCollectionListH.clear();
            officialCollectionListH.addAll(tempOfficialCollectionList);
        }
        baseCase++;
        System.out.println("after segregationH");
        System.out.println(officialCollectionListH.size());


        for (int i = 0; i < officialCollectionListH.size(); i++) {
            if (largestCoordCollectionH.size() < officialCollectionListH.get(i).size()) { //assign largestCoordCollection to the largest collection of coords.
                largestCoordCollectionH.clear();
                largestCoordCollectionH.addAll(officialCollectionListH.get(i));
            }
            if (officialCollectionListH.get(i).size() == 1) {        //remove fully segregated points from cumulative list
                allUnsegregatedCoordsH.remove(officialCollectionListH.get(i).get(0));
            }
        }

        for(int i = 0; i< officialCollectionListH.size(); i++) {
            if (officialCollectionListH.get(i).isEmpty()) {
                officialCollectionListH.remove(i);
                i--;
            }
        }
    }

    public void segregateCollectionsV() {
        largestCoordCollectionV.clear();
        largestCoordCollectionV.addAll(officialCollectionListV.get(0));

        System.out.println("before segregationV");
        System.out.println(officialCollectionListV.size());

        List<List<int[]>> tempOfficialCollectionList = new ArrayList<List<int[]>>();

        for(int i=0; i<officialCollectionListV.size()-2;i++){//n-1 and n-2 should not add themselves to the list
            if(officialCollectionListV.get(officialCollectionListV.size()-1).containsAll(officialCollectionListV.get(i))||officialCollectionListV.get(officialCollectionListV.size()-2).containsAll(officialCollectionListV.get(i))){
                tempOfficialCollectionList.add(officialCollectionListV.get(i));
            }
        }

        officialCollectionListV.removeAll(tempOfficialCollectionList);

        for(int i=0; i<officialCollectionListV.size()-2; i++){
            List<int[]> subCollection1 = new ArrayList<int[]>();
            List<int[]> subCollection2 = new ArrayList<int[]>();

            subCollection1.addAll(officialCollectionListV.get(i));
            subCollection2.addAll(officialCollectionListV.get(i));
            subCollection1.retainAll(officialCollectionListV.get(officialCollectionListV.size()-1));
            subCollection2.retainAll(officialCollectionListV.get(officialCollectionListV.size()-2));

            tempOfficialCollectionList.add(subCollection1);
            tempOfficialCollectionList.add(subCollection2);
        }

        if(baseCase<2) {
            officialCollectionListV.clear();
            officialCollectionListV.addAll(tempOfficialCollectionList);
        }
        baseCase++;
        System.out.println("after segregationV");
        System.out.println(officialCollectionListV.size());


        for (int i = 0; i < officialCollectionListV.size(); i++) {
            if (largestCoordCollectionV.size() < officialCollectionListV.get(i).size()) { //assign largestCoordCollection to the largest collection of coords.
                largestCoordCollectionV.clear();
                largestCoordCollectionV.addAll(officialCollectionListV.get(i));
            }
            if (officialCollectionListV.get(i).size() == 1) {        //remove fully segregated points from cumulative list
                allUnsegregatedCoordsV.remove(officialCollectionListV.get(i).get(0));
            }
        }

        for(int i = 0; i< officialCollectionListV.size(); i++) {
            if (officialCollectionListV.get(i).isEmpty()) {
                officialCollectionListV.remove(i);
                i--;
            }
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
