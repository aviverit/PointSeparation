import java.util.ArrayList;
import java.util.List;

public class Separator {

    private int lineCount=0;
    private int coordCount=0;
    private String line;
    private List<int[]> coordList = new ArrayList<int[]>();
    private List<String> lineList = new ArrayList<String>();

    Separator (int cC, List cL){
        coordCount=cC;
        coordList=cL;
    }

    void setLineCount(){
        this.lineCount++;
    }

    void setLines(){
        while(coordList!=null){
            calculateLine();
        }
    }

    void appendLines(){
        if(true){
            lineList.add("h"+line);
        }else{
            lineList.add("v"+line);
        }
    }

    void calculateLine(){

    }

    int getLineCount(){
        return this.lineCount;
    }

    List<String> getLines(){
        return lineList;
    }

}
