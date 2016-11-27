import java.util.ArrayList;
import java.util.List;

public class Separator {

    private int lineCount=0;
    private int coordCount=0;
    private int line;
    private int orientation=0; //0 = h, 1=v
    private List<int[]> coordList = new ArrayList<int[]>();
    private List<String> lineList = new ArrayList<String>();
    List<int[]> tempCoordList = coordList;
    private List<List<int[]>> collectionList = new ArrayList<List<int[]>>();


    Separator (int cC, List cL){
        coordCount=cC;
        coordList=cL;
    }

    public void setLines(){
        while(coordList.size()!=0){
            calculateLine();
            appendLine();
        }
    }

    public void appendLine(){
        lineCount++;
        if(orientation==1){
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
        if(orientation==1){
            orientation=0;
        }else{
            orientation=1;
        }

        while(segmentablePoints<(tempCoordList.size()/2)){
            for(int i=0; i<tempCoordList.size();i++){
                if(currentDistance==tempCoordList.get(i)[orientation]){
                    segmentablePoints++;
                    subCollection1.add(tempCoordList.get(i)); //Will work on all points equidistant from axis.
                }
            }
            currentDistance++;
        }
        for(int j=0;j<collectionList.size();j++){
            collectionList.get(j).removeAll(subCollection1);
            collectionList.get(j).removeAll(subCollection2);
            if(collectionList.get(j).size()==1){
                coordList.remove(collectionList.get(j).get(0));
            }
        }

        subCollection2.addAll(tempCoordList);
        subCollection2.removeAll(subCollection1);
        collectionList.add(subCollection1);
        collectionList.add(subCollection2);

        line=currentDistance;
    }

    public int getLineCount(){
        return this.lineCount;
    }

    public List<String> getLines(){
        return lineList;
    }
}
