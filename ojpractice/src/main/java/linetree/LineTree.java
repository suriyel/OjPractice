package linetree;

public class LineTree {
    private Node[] tree;
    private int[] input;

    public LineTree(int[] input) {
        this.tree = new Node[2 * input.length];
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new Node();
        }
        this.input = input;

        build(1, 1, input.length);
    }

    void build(int i,int l,int r) {
        tree[i].left = l;
        tree[i].right = r;

        if (l == r) {
            tree[i].sum = input[l - 1];
            return;
        }

        int mid = (l + r) >> 1;
        build(i * 2, l, mid);
        build(i * 2 + 1, mid + 1, r);

        tree[i].sum = tree[i * 2].sum + tree[i * 2 + 1].sum;
    }

    int search(int i,int l,int r) {
        if (tree[i].left >= l && tree[i].right <= r) {
            return tree[i].sum;
        }

        if (tree[i].right < l || tree[i].left > r) return 0;
        int s = 0;

        if (tree[i * 2].right >= l) s += search(i * 2, l, r);
        if (tree[i * 2 + 1].left <= r) s += search(i * 2 + 1, l, r);
        return s;
    }

    void add(int i,int dis,int k) {
        if (tree[i].left == tree[i].right) {
            tree[i].sum += k;
            return;
        }

        if (dis <= tree[i * 2].right)
            add(i * 2, dis, k);
        else
            add(i * 2 + 1, dis, k);
        tree[i].sum = tree[i * 2].sum + tree[i * 2 + 1].sum;
    }

    void add(int i,int l,int r,int k){
        if (tree[i].left >= l && tree[i].right <= r){
            tree[i].sum +=k;
            return;
        }

        if(tree[i*2].right >=l) {
            add(i * 2, l, r, k);
        }
        if (tree[i*2+1].left <= r) {
            add(i * 2 + 1, l, r, k);
        }
    }

    public static class Node {
        public int left;
        public int right;
        public int sum;
    }
}
