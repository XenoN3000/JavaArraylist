package ArrayList;
import java.util.Collection;
import java.util.Iterator;



public class ArrayList<T extends Comparable<T>> implements HoseinList<T> {



    private static final int Next = 0, Prev = 1, Empty = -1;
    private int first, last, size, freeSpace, cap;

    private T firstElement, tailElement;

    private T[] elements;
    private int[][] indexes;



    public ArrayList() {
        cap = 8;
        init();
    }

    public ArrayList(int cap) {
        this.cap = cap;
        init();
    }


    @SuppressWarnings("unchecked")
    private void init() {

        this.indexes = new int[cap][2];
        this.elements = ((T[]) new Object[cap]);


        indexes[0][Next] = -1;
        indexes[cap - 1][Prev] = -1;

        for (int i = 0; i < cap; i++)
            indexes[i][Next] = i + 1;

        size = 0;
        first = -1;
        freeSpace = 0;
        last = cap - 1;

    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull(){
        return freeSpace == -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        T t = (T) o;
        int i = realElemntIndex(t);
        if (i == -1 )
            return false;
        return o.equals(elements[i]);
    }

    @Override
    public void insertFirst(T t) {
        if (isFull())
            updateSize(true);
        if (this.first == -1)
            this.last = this.freeSpace;

        this.elements[freeSpace] = t;
        this.firstElement = t;
        int nF = this.indexes[freeSpace][Next];
        this.indexes[freeSpace][Next] = this.first;
        this.indexes[freeSpace][Prev] = -1;
        this.first = this.freeSpace;
        this.freeSpace = nF;
        this.size++;

    }

    @Override
    public void insertAfter(T t, T e) {
        if (isFull())
            updateSize(true);

        int i = this.first;
        int k, l;
        if (i == -1) {
            insertFirst(e);
            return;
        }
        while (i != -1 && !t.equals(this.elements[i])) {
            i = this.indexes[i][Next];
        }
        k = indexes[i][Next];
        l = indexes[freeSpace][Next];

        if (indexOf(t) == (indexOf(this.tailElement))) {
            this.indexes[freeSpace][Prev] = i;
            this.indexes[freeSpace][Next] = -1;
            this.indexes[i][Next] = this.freeSpace;
            this.tailElement = e;
        } else {

            this.indexes[this.freeSpace][Prev] = i;
            this.indexes[this.freeSpace][Next] = k;
            this.indexes[k][Prev] = this.freeSpace;
            this.indexes[i][Next] = this.freeSpace;

        }

        this.elements[this.freeSpace] = e;
        this.freeSpace = l;
        this.size++;
    }

    @Override
    public void append(T t) {
        if (t != null) {
            if (this.first == -1)
                insertFirst(t);
            else
                insertAfter(this.tailElement, t);
        }
    }

    @Override
    public T getFirst() {
        return this.elements[first];
    }

    @Override
    public T deleteFirst() {
        T t = getFirst();
        if (size == 1) {
            this.indexes[first][Next] = freeSpace;
            this.freeSpace = first;
            this.first = Empty;
            this.last = Empty;
            this.size--;
            return t;
        }

        int tempfirst = this.first;
        this.first = this.indexes[this.first][Next];

        this.indexes[tempfirst][Next] = freeSpace;
        this.indexes[tempfirst][Prev] = -1;
        this.indexes[this.first][Prev] = -1;
        this.freeSpace = tempfirst;
        this.size--;

        if (this.sizeChecker())
            updateSize(false);

        return t;
    }

    @Override
    public T deleteAfter(T e) {
        if (this.size < 2)
            return null;
        else if (indexOf(e) == this.size - 1)
            return null;
        else if (indexOf(e) == this.size - 2) {
            T t =  deletelast();
            return t;
        }

        int i = realElemntIndex(e);
        i = this.indexes[i][Next];
        T t = deleteIndexOf(indexOf(this.elements[i]));
        return t;
    }

    @Override
    public T deletelast() {
        T t = this.elements[last];

        if (this.size == 1)
            return deleteFirst();

        int i = realElemntIndex(t);
        this.last = this.indexes[i][Prev];

        indexUpdateforDelete(i);

        this.freeSpace = i;
        this.tailElement = this.elements[last];

        this.size--;

        if (this.sizeChecker())
            updateSize(false);

        return t;
    }

    @Override
    public T deleteIndexOf(Integer i) {
        T e;
        if (i == 0) {
            e = deleteFirst();
            return e;
        } else if (i == this.size - 1) {
            e = deletelast();
            return e;
        }
        e = getElement(i);
        int j = realElemntIndex(e);

        indexUpdateforDelete(j);

        this.freeSpace = j;

        this.size--;


        if (this.sizeChecker())
            updateSize(false);

        return e;
    }

    @Override
    public T getElement(Integer i) {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {

        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        init();
    }

    private int realElemntIndex(T t) {

        int i = this.first;
        while (i != -1 && !t.equals(this.elements[i])) {
            i = this.indexes[i][Next];
        }
        return i;
    }

    public int indexOf(T t) {
        int i = first, j = 1;

        while (i != -1 && t != this.elements[i]) {
            i = this.indexes[i][Next];
            j++;
        }

        if (i == -1)
            return i;
        return j;
    }


    private void indexUpdateforDelete(int j) {
        int k = this.indexes[j][Prev];
        int l = this.indexes[j][Next];
        this.indexes[j][Next] = this.freeSpace;
        this.indexes[j][Prev] = -1;
        this.indexes[this.freeSpace][Prev] = j;
        this.indexes[k][Next] = l;
        if (l != -1)
            this.indexes[l][Prev] = k;

    }


    private void updateSize(boolean full) {
        int newLength;
        if (!full) {
            newLength = this.elements.length / 2;
        } else newLength = this.elements.length * 2;

        int[][] tempIndex = new int[newLength][2];
        T[] tempElement = (T[]) new Object[newLength];

        int i = 0;
        int j = this.first;

        while (i < this.size && j != -1 && this.indexes[j][Next] != -1) {

            tempElement[i] = this.elements[j];
            tempIndex[i][Next] = i + 1;
            tempIndex[i][Prev] = i - 1;

            j = this.indexes[j][Next];
            i++;
        }

        tempElement[i] = this.elements[j];
        tempIndex[i][Next] = -1;
        tempIndex[i][Prev] = i - 1;

        for (int k = i + 1; k < tempElement.length - 1; k++) {
            tempIndex[k][Next] = k + 1;
        }
        tempIndex[tempElement.length - 1][Next] = -1;


        this.first = 0;
        this.freeSpace = i + 1;

        this.elements = tempElement;
        this.indexes = tempIndex;
    }


    private boolean sizeChecker() {
        return (this.size * 4) == elements.length && this.elements.length > 8;
    }

}
