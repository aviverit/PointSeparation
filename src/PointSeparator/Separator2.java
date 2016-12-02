package PointSeparator;

import java.util.ArrayList;
import java.util.Arrays;
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
            //breaker++;
        }
    }

    public void appendLine(){
        lineCount++;
        if(orientation==0){
            lineList.add("h "+ Integer.toString(line) +".5 \n");
            System.out.println("h "+ Integer.toString(line) +".5 \n");
        }else{
            lineList.add("v "+ Integer.toString(line) +".5 \n");
            System.out.println("v "+ Integer.toString(line) +".5 \n");
        }
    }

    public void calculateLine(){
        int segmentablePoints=0;
        int currentDistanceH=0;
        int currentDistanceV=0;
        List<int[]> subCollection1H = new ArrayList<int[]>();
        List<int[]> subCollection2H = new ArrayList<int[]>();
        List<int[]> subCollection1V = new ArrayList<int[]>();
        List<int[]> subCollection2V = new ArrayList<int[]>();
        orientation=1;
        largestCoordCollectionH.clear();
        largestCoordCollectionV.clear();
        allUnsegregatedCoordsH.clear();
        allUnsegregatedCoordsV.clear();
        largestCoordCollectionH.addAll(largestCoordCollection);
        largestCoordCollectionV.addAll(largestCoordCollection);
        allUnsegregatedCoordsH.addAll(allUnsegregatedCoords);
        allUnsegregatedCoordsV.addAll(allUnsegregatedCoords);
        System.out.println("largestcoordcollectionGen");
        for(int i=0;i<largestCoordCollection.size();i++){
            System.out.println(Arrays.toString(largestCoordCollection.get(i)));
        }

        while(segmentablePoints<((largestCoordCollectionH.size())/2)){
            currentDistanceH++;
            for(int i=0; i<largestCoordCollectionH.size();i++){
                if(currentDistanceH==largestCoordCollectionH.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1H.add(largestCoordCollectionH.get(i)); //Will work on all points equidistant from axis.
                }
            }
            for(int j=0;j<allCoords.size();j++) {
                if (currentDistanceH == allCoords.get(j)[orientation]) {
                    if (!largestCoordCollectionH.contains(allCoords.get(j))) {//(!subCollection1.contains(allCoords.get(i))) {
                        subCollection1H.add(allCoords.get(j)); //Will work on all points equidistant from axis.
                    }
                }
            }
        }
        subCollection2H.addAll(allCoords);
        subCollection2H.removeAll(subCollection1H);
        officialCollectionList.add(subCollection1H);
        officialCollectionList.add(subCollection2H);

        officialCollectionListH.clear();

        officialCollectionListH.addAll(officialCollectionList);
        segregateCollectionsH();

        orientation=0;
        segmentablePoints=0;

        System.out.println("largestcoordcollectionV");
        for(int i=0;i<largestCoordCollectionV.size();i++){
            System.out.println(Arrays.toString(largestCoordCollectionV.get(i)));
        }

        while(segmentablePoints<((largestCoordCollectionV.size())/2)){
            currentDistanceV++;
            for(int i=0; i<largestCoordCollectionV.size();i++){
                if(currentDistanceV==largestCoordCollectionV.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1V.add(largestCoordCollectionV.get(i)); //Will work on all points equidistant from axis.
                }
            }
            for(int j=0;j<allCoords.size();j++) {
                if (currentDistanceV == allCoords.get(j)[orientation]) {
                    if (!largestCoordCollectionV.contains(allCoords.get(j))) {//(!subCollection1.contains(largestCoordCollectionV.get(i))) {
                        subCollection1V.add(allCoords.get(j)); //Will work on all points equidistant from axis.
                    }
                }
            }
        }

        officialCollectionList.remove(officialCollectionList.size()-1);//remove the last two subcollections added for H
        officialCollectionList.remove(officialCollectionList.size()-1);

        subCollection2V.addAll(allCoords);
        subCollection2V.removeAll(subCollection1V);
        officialCollectionList.add(subCollection1V);
        officialCollectionList.add(subCollection2V);

        officialCollectionListV.clear();

        officialCollectionListV.addAll(officialCollectionList);
        segregateCollectionsV();

        if(allUnsegregatedCoordsH.size()<allUnsegregatedCoordsV.size()){//(officialCollectionListH.size()>=officialCollectionListV.size()){//(largestCoordCollectionH.size()<largestCoordCollectionV.size()){//(allUnsegregatedCoordsH.size()<allUnsegregatedCoordsV.size()){//
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

        for(int i=0; i<officialCollectionListH.size();i++){
            System.out.println("new collection at seg start");
            for(int j=0; j<officialCollectionListH.get(i).size();j++) {
                System.out.println(Arrays.toString(officialCollectionListH.get(i).get(j)));
            }
        }

        System.out.println("before segregationH");
        System.out.println(officialCollectionListH.size());

        List<List<int[]>> tempOfficialCollectionList = new ArrayList<List<int[]>>();

        for(int i=0; i<officialCollectionListH.size()-2;i++){//n-1 and n-2 should not add themselves to the list SUUUUUUUSSSSSPEECCCTTTTTTTTTT
            if(officialCollectionListH.get(officialCollectionListH.size()-1).containsAll(officialCollectionListH.get(i))||officialCollectionListH.get(officialCollectionListH.size()-2).containsAll(officialCollectionListH.get(i))){
                tempOfficialCollectionList.add(officialCollectionListH.get(i)); //add all collections that don't intersect the new line.
            }
        }

        System.out.println("Mid segregationH, temp size");
        System.out.println(tempOfficialCollectionList.size());

        officialCollectionListH.removeAll(tempOfficialCollectionList);          ////suuussssppeeeccttt. Does this maintain the last two collections?
        System.out.println("Mid segregation Post");
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

        if(baseCase>=2) {
            officialCollectionListH.clear();
            officialCollectionListH.addAll(tempOfficialCollectionList);
        }
        baseCase++;

        System.out.println("before removing emptysH");
        System.out.println(officialCollectionListH.size());

        for(int i = 0; i< officialCollectionListH.size(); i++) { /////////actually this appears to be not working.
            if (officialCollectionListH.get(i).isEmpty()) {
                officialCollectionListH.remove(i);
                i--;
            }
        }

        System.out.println("after segregationH");
        System.out.println(officialCollectionListH.size());

        for(int i=0; i<officialCollectionListH.size();i++){
            System.out.println("new collection after segregation");
            for(int j=0; j<officialCollectionListH.get(i).size();j++) {
                System.out.println(Arrays.toString(officialCollectionListH.get(i).get(j)));
            }
        }

        largestCoordCollectionH.clear();
        largestCoordCollectionH.addAll(officialCollectionListH.get(0));

        for (int i = 0; i < officialCollectionListH.size(); i++) {
            if (largestCoordCollectionH.size() < officialCollectionListH.get(i).size()) { //assign largestCoordCollection to the largest collection of coords.
                largestCoordCollectionH.clear();
                largestCoordCollectionH.addAll(officialCollectionListH.get(i));
            }
            if (officialCollectionListH.get(i).size() == 1) {        //remove fully segregated points from cumulative list
                allUnsegregatedCoordsH.remove(officialCollectionListH.get(i).get(0));
            }
        }
    }

    public void segregateCollectionsV() {
        System.out.println("before segregationV");
        System.out.println(officialCollectionListV.size());

        for(int i=0; i<officialCollectionListV.size();i++){
            System.out.println("new collection at seg startV");
            for(int j=0; j<officialCollectionListV.get(i).size();j++) {
                System.out.println(Arrays.toString(officialCollectionListV.get(i).get(j)));
            }
        }

        List<List<int[]>> tempOfficialCollectionList = new ArrayList<List<int[]>>();

        for(int i=0; i<officialCollectionListV.size()-2;i++){//n-1 and n-2 should not add themselves to the list
            if(officialCollectionListV.get(officialCollectionListV.size()-1).containsAll(officialCollectionListV.get(i))||officialCollectionListV.get(officialCollectionListV.size()-2).containsAll(officialCollectionListV.get(i))){
                tempOfficialCollectionList.add(officialCollectionListV.get(i));
            }
        }

        System.out.println("Mid segregationV, temp size");
        System.out.println(tempOfficialCollectionList.size());

        officialCollectionListV.removeAll(tempOfficialCollectionList);
        System.out.println("Mid segregationV");
        System.out.println(officialCollectionListV.size());

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

        if(baseCase>=2) {
            officialCollectionListV.clear();
            officialCollectionListV.addAll(tempOfficialCollectionList);
        }
        baseCase++;

        System.out.println("before removing emptysV");
        System.out.println(officialCollectionListV.size());

        for(int i = 0; i< officialCollectionListV.size(); i++) {
            if (officialCollectionListV.get(i).isEmpty()) {
                officialCollectionListV.remove(i);
                i--;
            }
        }

        System.out.println("after segregationV");
        System.out.println(officialCollectionListV.size());

        for(int i=0; i<officialCollectionListV.size();i++){
            System.out.println("new collection after segregationV");
            for(int j=0; j<officialCollectionListV.get(i).size();j++) {
                System.out.println(Arrays.toString(officialCollectionListV.get(i).get(j)));
            }
        }

        largestCoordCollectionV.clear();
        largestCoordCollectionV.addAll(officialCollectionListV.get(0));

        for (int i = 0; i < officialCollectionListV.size(); i++) {
            if (largestCoordCollectionV.size() < officialCollectionListV.get(i).size()) { //assign largestCoordCollection to the largest collection of coords.
                largestCoordCollectionV.clear();
                largestCoordCollectionV.addAll(officialCollectionListV.get(i));
            }
            if (officialCollectionListV.get(i).size() == 1) {        //remove fully segregated points from cumulative list
                allUnsegregatedCoordsV.remove(officialCollectionListV.get(i).get(0));
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
        System.out.println(Integer.toString(this.lineCount+1));
        System.out.println("unsegregated coords in merge");
        for(int i=0; i<allUnsegregatedCoords.size();i++) {
            System.out.println(Arrays.toString(allUnsegregatedCoords.get(i)));
        }
    }

    public int getLineCount(){
        return this.lineCount;
    }

    public List<String> getLines(){
        return lineList;
    }
}
