package structures;
import java.util.NoSuchElementException;

public class ArrayList<E> {

    private final E[] vector;
    private final int first; // always zero
    public int last;

    public ArrayList(int size) {
        this.vector = (E[]) new Object[size];
        this.first = this.last = 0;
    }

    public boolean isEmpty() {
        return this.first == this.last;
    }

    public boolean isFull() {
        return this.last == this.vector.length;
    }

    public E get(int index) {

        if(index < 0 || index >= this.last) {
            throw new IndexOutOfBoundsException();
        }

        return this.vector[index];

    }

    public void add(E element) {
        this.add(element, this.last);
    }

    public void add(E element, int index) {

        if(this.isFull()) {
            throw new IllegalStateException();
        }

        if(index < 0 || index > this.last) {
            throw new IndexOutOfBoundsException();
        }

        for(int i = this.last; i > index; i--) {
            this.vector[i] = this.vector[i - 1];
        }

        this.vector[index] = element;
        this.last++;

    }

    public E remove(int index) {

        E removed = this.get(index);
        this.last--;

        for(int i = index; i < this.last; i++) {
            this.vector[i] = this.vector[i + 1];
        }

        return removed;

    }

    public int indexOf(E element) {

        for(int i = this.first; i < this.last; i++) {

            if(this.vector[i].equals(element)) {
                return i;
            }

        }

        throw new NoSuchElementException();

    }

    public void moveToFirstIndex(E element) {

        int currentIndex = this.indexOf(element);
        E removed = this.remove(currentIndex);
        this.add(removed, 0);

    }

    public void print() {
        for(int i = this.first; i < this.last; i++) {
            System.out.println(this.vector[i]);
        }
    }

}
