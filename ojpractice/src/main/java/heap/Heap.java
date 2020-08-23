package heap;

public class Heap<T extends Comparable<T>>  {
    private final Object[] datas;
    private int n = 0;
    /**
     * 初始化
     * @param capacity 大小
     */
    public Heap(int capacity) {
        datas = new Object[capacity+1];
    }

    private T getValue(int i){
        return (T)datas[i];
    }

    //判断大小
    private boolean lessThan(int i,int j) {
        return getValue(j).compareTo(getValue(i)) > 0;
    }

    //交换
    private void exc(int i,int j) {
        T temp = getValue(i);
        datas[i] = datas[j];
        datas[j] = temp;
    }

    //新加元素
    public void add(T item) {
        datas[++n] = item;
        floatUp(n);
    }

    //上浮
    private void floatUp(int i) {
        if (i/2 == 0){
            //已经到顶了
            return;
        }

        //查看它的父亲节点，是否比自己小
        if (lessThan(i/2,i)) {
            //小的话，上浮
            floatUp(i / 2);
            return;
        }
        //大的话，保持现状
    }

    //删除堆顶
    public T deleteTop() {
        T data = getValue(1);
        //堆顶和堆尾巴做交换
        exc(1, n);

        //堆尾巴置为null，并--
        datas[n--] = null;

        //对顶做下沉
        sink(1);

        return data;
    }

    //下沉
    private void sink(int i) {
        if (i*2>n) {
            return;
        }

        int index = i*2;
        //获取其左右节点中较大的索引
        if (i*2+1<=n &&lessThan(i*2,i*2+1)) {
            index = i * 2 + 1;
        }

        //和较大节点做比较
        if (lessThan(i,index)){
            //比较大的小
            //交换并继续下沉
            exc(i,index);
            sink(index);
        }
        //比较大的还大
        //保持现状
    }
}
