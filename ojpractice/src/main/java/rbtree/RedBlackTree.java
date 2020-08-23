package rbtree;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.CONCURRENT;

public class RedBlackTree<Key extends Comparable<Key>,Value> {
    /**
     * 根节点
     */
    private Node root;
    /**
     * 元素个数
     */
    private int n;
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * 当前节点的父指向链接是否为红色
     * @param x 节点
     * @return 是/否
     */
    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }

        return x.color == RED;
    }

    /**
     * 右旋转调整
     * @param h 当前节点
     * @return 旋转后的
     */
    private Node rotateRight(Node h) {
        //h的左边节点旋转至右边
        Node x = h.left;
        //x右旋 变为h的父节点
        //对于h
        //x的右子节点，变为h左子节点 介于 h与x之间
        h.left = x.right;

        //对于x
        //h成为x的右子节点
        x.right = h;

        //color
        //让x的color变为h的color
        //让h的color变为Red
        x.color = h.color;
        h.color = RED;
        return x;
    }

    /**
     * 左旋转调整
     * @param h 当前节点
     * @return
     */
    private Node rotateLeft(Node h) {
        //h右边节点旋转至左边
        Node x = h.right;
        //x左旋 变为h的父节点
        //对于h
        //x左子节点，变为h右子节点 介于 h与x之间
        h.right = x.left;

        //对于x
        //h成为x的左子节点
        x.left = h;

        //color
        //让h的color变为x的color
        //让h的color变为RED
        x.color = h.color;
        h.color = RED;

        return x;
    }

    /**
     * 颜色反转
     * @param h 当前节点
     */
    private void flipColors(Node h) {
        //将当前节点左子、右子节点变为黑色
        //当前节点变为红色
        h.color = RED;

        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    /**
     * 在整个树上完成插入操作,并返回添加元素后的树
     * @param key key
     * @param value value
     */
    public void put(Key key,Value value) {
        root = put(root, key, value);
        //防止颜色反转，根节点颜色总为黑色
        root.color = BLACK;
    }

    /**
     * 向指定节点插入K-V
     * @param h 指定节点
     * @param key key
     * @param value value
     */
    public Node put(Node h,Key key,Value value) {
        //h是否空，如果为空，则返回一个红色的新节点
        if (h == null) {
            n++;
            return new Node(key, value, null, null, RED);
        }
        //比较h与k的大小
        int cmp = key.compareTo(h.key);
        //h>K
        if (cmp > 0) {
            //继续往右
            //可能生成新节点，可能旋转，需要重新赋值右链接
            h.right = put(h.right, key, value);
        }
        //H<K
        else if (cmp < 0) {
            //往左
            h.left = put(h.left, key, value);
        } else {
            //发生值的替换
            h.value = value;
        }

        //后续遍历，从下到上，反向路径，逐层处理，插入后的平衡逻辑
        //进行左旋
        //当h的左子节点为黑色，右子节点为红色，需要左旋
        if (isRed(h.right) && !isRed(h.left)) {
            //旋转后，当前节点位置已改变，父节点重新对接
           h = rotateLeft(h);
        }

        //进行右旋,当h的左子节点和左子节点的左子节点都为红色，需要右旋
        if (isRed(h.left) && isRed(h.left.left)) {
            //旋转后，当前节点位置已改变，父节点重新对接
           h = rotateRight(h);
        }

        //颜色反转,h的左子节点和右子节点都为红色，需要进行颜色反转
        if (isRed(h.left) && isRed(h.right)) {
            //重置颜色，不会改变当前节点位置
            flipColors(h);
        }

        //当前节点位置，未改变
        return h;
    }

    /**
     * 根据Key，从树中找出对应的值
     * @param key key
     * @return value
     */
    public Value get(Key key) {
        return get(root,key);
    }

    /**
     * 根据Key，从树种找出对应的值
     * @param x 指定节点
     * @param key key
     * @return 值
     */
    public Value get(Node x,Key key) {
        if (x == null) {
            return null;
        }

        //比较x和key大小
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        }

        return x.value;
    }

    /**
     * 前序遍历Node
     * @return Streams
     */
    public Stream<Node> toStream() {
        Queue<Node> nodes = new ArrayDeque<>();
        forEach(root, nodes::add);

        return StreamSupport.stream(Spliterators.spliterator(nodes.iterator(), n, CONCURRENT), false);
    }


    private void forEach(Node node, Consumer<Node> action) {
        if (node == null) {
            return;
        }

        action.accept(node);
        forEach(node.left, action);
        forEach(node.right, action);
    }

    /**
     * 获取树种元素个数
     * @return 元素个数
     */
    public int size(){
        return n;
    }

    public class Node{
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private boolean color;

        public Node(Key key,Value value,Node left,Node right,boolean color) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.color = color;
        }

        @Override
        public String toString() {
            return String.format("%s->l:%s,r:%s,color:%s",key, getValueStr(left), getValueStr(right),color);
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        public String getValueStr(Node n) {
            if (n == null) {
                return "Null";
            } else {
                return n.key.toString();
            }
        }
    }
}
