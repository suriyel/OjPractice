package linetree;


import org.junit.Test;

public class LineTreeTest {
    @Test
    public void buildTest() {
        int[] array = new int[] {
            1, 2, 3, 4
        };

        LineTree lineTree = new LineTree(array);

        int value = lineTree.search(1,1,3);

        lineTree.add(1,3,2);

        value = lineTree.search(1,1,3);
    }
}