package heap;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeapTest {
    @Test
    public void createTest(){
        Heap<Integer> heaps = new Heap(4);
        heaps.add(4);
        heaps.add(1);
        heaps.add(3);

        List<Integer> results = new ArrayList<>();
        results.add(heaps.deleteTop());
        results.add(heaps.deleteTop());
        results.add(heaps.deleteTop());

        Arrays.toString(results.toArray());
    }
}