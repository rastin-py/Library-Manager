import java.util.Arrays;

import static java.lang.Math.abs;

public class HashMap<K, V> {
    public static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        //constructor
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<K, V>[] entries;
    private int capacity;//the initial capacity of array
    private int size;//the number of entry inside the hashMap

    //constructor
    public HashMap() {
        this.size = 0;
        this.capacity = 10;
        this.entries = new Entry[this.capacity];
    }

    private void resize() {
        //copy the previous array into a new one
        entries = Arrays.copyOf(entries, capacity * 2);
        capacity *= 2;//update capacity
    }

    public void put(K key, V value) {
        //if the initial capacity is equals to size, we have to double the array size
        if (size == capacity) resize();

        Entry<K, V> newEntry = new Entry<>(key, value);

        if (key == null) return;//if the given key is null, we can not add this into the hashMap

        int index = getIndex(key);//get the index of the specific key

        if (entries[index] == null)//if the cell is empty, we can inset the object
        {
            entries[index] = newEntry;
            size++;//increment size
        } else {
            Entry<K, V> current = entries[index];
            Entry<K, V> previous = null;

            //if the cell is not empty
            //we have to inset the value int the cell, and each cell has a pointer to another one
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = newEntry.value;
                    return;
                }
                previous = current;
                current = current.next;
                size++;//increment size

            }
            assert previous != null;
            previous.next = newEntry;
        }
    }

    public V getValue(K key) {
        if (key == null) return null;//if the given value is null, return null

        int Index = getIndex(key);//get the index of the key

        if (entries[Index] == null) return null;//check if the key is in the cell or not
        else {
            Entry<K, V> current = entries[Index];

            //search the entry to find the value within the cell
            while (current != null) {
                if (current.key.equals(key)) {
                    return current.value;
                }
                current = current.next;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        //check if the given is null or not
        if (key == null) return false;

        int index = getIndex(key);//get the index of the key that is stored in the hashmap

        //check if the cell is null or not
        if (entries[index] == null) return false;

        //if the key of the given value is equals to the value of the array cell, it means we have the same key inside the cell
        if (entries[index].key.equals(key)) {
            return true;
        } else {
            Entry<K, V> current = entries[index];
            //check the cell to find the key
            while (current != null) {
                if (current.key.equals(key)) {
                    return true;
                }
                current = current.next;

            }

        }
        return false;
    }

    private int getIndex(K key) {
        return abs(key.hashCode() % capacity);
    }
}
