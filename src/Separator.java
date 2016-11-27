import java.util.ArrayList;
import java.util.List;

public class Separator {

    private int lineCount=0;
    private int coordCount=0;
    private int line;
    private List<int[]> coordList = new ArrayList<int[]>();
    private List<String> lineList = new ArrayList<String>();
    private int orientation=0; //0 = h, 1=v
    List<int[]> tempCoordList = coordList;

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
            lineList.add("h "+new Integer(line).toString()+" \n");
        }else{
            lineList.add("v "+new Integer(line).toString()+" \n");
        }
    }

    public void calculateLine(){
        line=0;
        if(orientation==1){
            orientation=0;
        }else{
            orientation=1;
        }

        int segmentablePoints=0;
        int currentDistance=0;

        while(segmentablePoints<(tempCoordList.size()/2)){
            for(int i=0; i<tempCoordList.size();i++){
                if(currentDistance==tempCoordList.get(i)[orientation]){
                    segmentablePoints++;
                }
            }
            currentDistance++;
        }
        line=currentDistance;
    }

    public int getLineCount(){
        return this.lineCount;
    }

    public List<String> getLines(){
        return lineList;
    }
}
