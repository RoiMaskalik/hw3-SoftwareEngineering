import java.io.File;
import java.util.List;

public class CaseInsensitiveIndex extends AbstractInvertedIndex {

    private static CaseInsensitiveIndex caseInsensitiveIndex;
    private CaseInsensitiveIndex(){
        super();
    }
    public static CaseInsensitiveIndex getInstance() {
        if(caseInsensitiveIndex == null) {
            caseInsensitiveIndex = new CaseInsensitiveIndex();
            System.out.println("New CaseInsensitive index is created");
        }
        else
            System.out.println("You already have a CaseInsensitive index");
        return caseInsensitiveIndex;
    }

    /**
     * Changes the text to lower case in order to ignore the sensitivity
     *
     * it is used on the method getTextFromFile that is in the abstract class
     * @param row
     * @return
     */
    @Override
    protected String sensitivity(String row) {
        return row.toLowerCase();
    }
}
