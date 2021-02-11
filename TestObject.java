import java.util.ArrayList;

public class TestObject {
    private ArrayList<String> xpaths;

    public TestObject(ArrayList<String> list)
    {
        this.xpaths = list;
    }

    public ArrayList<String> getXpaths()
    {
        return this.xpaths;
    }
}
