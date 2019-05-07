import java.util.Arrays;

public class ArrayIntList {

    /*
        Tassadaq Hussain
        CP 6200
        PA8
     */
    protected static final int capacity = 20;

    //Variable Declarations
    private int size;
    private int[] array;

    //Initialize default constructor
    public ArrayIntList() {
        this(capacity);   // call the (int) constructor
    }

    //Initializes a constructor for capacity as the parameter
    public ArrayIntList(int capacity) {
        size = 0;
        array = new int[capacity];
    }

    //Adds an element to the end of the array
    public void add(int value) {
        add(size, value);
    }

    //Adds the given value to the given index
    public void add(int index, int value) {
        //adds to the end of the list
        resize();

        for (int i = size; i > index; i--)
            array[i] = array[i - 1];

        array[index] = value;
        size++;
    }

    //Remove an element at the given index
    public void remove(int index) {
        for (int i = index; i <= size - 1; i++)
            array[i] = array[i + 1];

        size--;
    }

    //Returns an element of the given index
    public int get(int index) {
        //makes sure the geven index is less then size
        return array[index];
    }

    //Return the index of the given element if the not found then returns -1
    public int indexOf(int value) {
        for (int i = 0; i < size; i++)
            if (array[i] == value)
                return i;

        return -1;
    }

    //Resizes the array to its double the size
    private void resize() {
        if (size == array.length)
            array = Arrays.copyOf(array, 2 * size);
    }

    //Sets and element at the given index to a given value
    public void set(int index, int value) {
        array[index] = value;
    }

    //Return the number of elements in the array
    public int size() {
        return size;
    }

    //Return true if the given value exists in the list
    public boolean contains(int value) {
        return indexOf(value) >= 0;
    }


    //Returns a String representation of the elements in the list
    public String toString() {
        if (size > 0) {
            String result = "[" + array[0];
            for (int i = 1; i < size; i++)
                result = result + ", " + array[i];
            result += "]";
            return result;
        } else {
            return "[]";   // empty list
        }
    }

    //Empties the arrayList
    public void clear() {
        for (int i = 0; i < size; i++)
            array[i] = Integer.parseInt(null);

        size = 0;
    }
}