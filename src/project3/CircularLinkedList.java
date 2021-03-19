package project3;

import java.util.ArrayList;

/**
 *
 * @author Elijah Hand
 */
public class CircularLinkedList implements Cloneable {

    private Node head;
    private Node tail;
    int size;

    /**
     *
     * @return Will return a cloned object @throws CloneNotSupportedException
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     *
     * @param o Any Object in java to initialize the list with
     */
    public CircularLinkedList(Object o) {
        head = new Node(o);
        tail = new Node(o);
        this.size++;
    }

    /**
     * toArrayList: converts circular linked list to arraylist 
     * @return will return an arraylist 
     */
    public ArrayList<SuspicionCharacter> toArrayList() {
        ArrayList<SuspicionCharacter> returnArrayList = new ArrayList<SuspicionCharacter>();
        Node temp = this.head;
        do {
            returnArrayList.add((SuspicionCharacter) temp.get());
            temp = temp.nextNode;
        } while (temp != this.head);
        return returnArrayList;
    }

    /**
     * createFromArrayList: will convert the array list to a circularlinked list
     * @param a an arraylist 
     * @return Will return a circularlinked list
     */
    public static CircularLinkedList createFromArrayList(ArrayList a) {
        CircularLinkedList temp = new CircularLinkedList();
        for (int i = 0; i < a.size(); i++) {
            temp.insert(a.get(i));
        }
        return temp;
    }

    /**
     * Initialize the list
     */
    public CircularLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * length
     * @return Returns an integer corresponding to the size of the list.
     */
    public int length() {
        return this.size;
    }

    /**
     * insert: inserts object inside circular linked list
     * @param o Insert a given object into the List
     */
    public void insert(Object o) {
        Node tempNode = new Node(o);
        if (this.head == null) {
            this.head = tempNode;
            this.tail = tempNode;
        } else {
            tempNode.prevNode = tail;
            tempNode.nextNode = head;
            tail.nextNode = tempNode;
            head.prevNode = tempNode;
            tail = tempNode;
        }
        this.size++;
    }

    /**
     * remove: will remove from circularlinked list
     * @param o Remove a given object from the list
     * @return Returns the removed object
     */
    public Node remove(Object o) {
        Node tempNode = this.get(o);
        Node beforeTemp;
        Node afterTemp;
        if (tempNode != null) {
            if (tempNode == this.head) {
                this.head = tempNode.nextNode;
            }
            beforeTemp = tempNode.prevNode;
            afterTemp = tempNode.nextNode;
            beforeTemp.nextNode = afterTemp;
            afterTemp.prevNode = beforeTemp;
            this.size--;
        }

        return tempNode;
    }

    /**
     * getHead
     * @return Returns the Node which head is currently pointing to.
     */
    public Node getHead() {
        return this.head;
    }

    /**
     * getTail
     * @return will get the last item in the linked list
     */
    public Node getTail() {
        return this.tail;
    }

    /**
     * deleteList: will delete the entire list
     */
    public void deleteList() {
        this.head = null;
        this.head = null;
        this.size = 0;
    }

    /**
     * Advances the Head Node 1 jump forward.
     */
    public void next() {
        this.head = this.head.nextNode;
    }

    /**
     * Decrements the head 1 jump backward
     */
    public void prev() {
        this.head = this.head.prevNode;
    }

    /**
     *
     * @param o Object you are searching for in the list.
     * @return Returns a Node object that contains the same object as o. If o is
     * is not found, returns null.
     *
     * Note: Once you have the node object, the links are still intact so you
     * can advance both forward and backward using the Node methods getNext and
     * getPrev.
     */
    public Node get(Object o) {
        Node tempNode = this.head;
        for (int i = 0; i < this.length(); i++) {
            if (tempNode.get() != o) {
                tempNode = tempNode.nextNode;
            } else {
                return tempNode;
            }
        }
        // Object not found
        tempNode = null;
        return tempNode;
    }

}
