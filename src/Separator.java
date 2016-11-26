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

    public void setLines(){
        while(coordList!=null){
            calculateLine();
            appendLine();
        }
    }

    public void appendLine(){
        if(true){
            lineList.add("h "+new Integer(line).toString()+" \n");
        }else{
            lineList.add("v "+new Integer(line).toString()+" \n");
        }
    }

    public void calculateLine(){
        line=0;
        lineCount++;
    }

    public int getLineCount(){
        return this.lineCount;
    }

    public List<String> getLines(){
        return lineList;
    }

}
