import java.util.ArrayList;
import java.util.List;

public class Separator {

    private int lineCount=0;
    private int coordCount=0;
    private int line;
    private List<int[]> coordList = new ArrayList<int[]>();
    private List<String> lineList = new ArrayList<String>();

    Separator (int cC, List cL){
        coordCount=cC;
        coordList=cL;
    }

    void setLines(){
        while(coordList!=null){
            calculateLine();
            appendLine();
        }
    }

    void appendLine(){
        if(true){
            lineList.add("h "+new Integer(line).toString()+" \n");
        }else{
            lineList.add("v "+new Integer(line).toString()+" \n");
        }
    }

    void calculateLine(){
        line=0;
        lineCount++;
    }

    int getLineCount(){
        return this.lineCount;
    }

    List<String> getLines(){
        return lineList;
    }

}
