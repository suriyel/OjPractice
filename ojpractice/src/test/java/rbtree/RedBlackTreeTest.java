package rbtree;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RedBlackTreeTest {
    @Test
    public void t1() {
        RedBlackTree<Integer,String> redBlackTree = new RedBlackTree();
        BiFunction<Integer,String, Stream<RedBlackTree<Integer, String>.Node>> fun = (n, str)-> {
            redBlackTree.put(n, str);
            return redBlackTree.toStream();
        };

        fun.andThen(getAssertFunc(Arrays.asList(1))).apply(1,"a");
        fun.andThen(getAssertFunc(Arrays.asList(2,1))).apply(2,"b");
        fun.andThen(getAssertFunc(Arrays.asList(2,1,3))).apply(3,"c");
        fun.andThen(getAssertFunc(Arrays.asList(2,1,4,3))).apply(4,"d");
        fun.andThen(getAssertFunc(Arrays.asList(4,2,1,3,5))).apply(5,"e");
        fun.andThen(getAssertFunc(Arrays.asList(4,2,1,3,6,5))).apply(6,"f");
    }

    private Function<Stream<RedBlackTree<Integer, String>.Node>,Boolean> getAssertFunc(List<Integer> targets) {
        return nodeStream -> {
            List<RedBlackTree<Integer, String>.Node> lists = nodeStream.collect(Collectors.toList());
            assert lists.size() == targets.size();

            for (int i = 0; i < lists.size(); i++) {
                assert lists.get(i).getKey() == targets.get(i);
            }

            return true;
        };
    }
}