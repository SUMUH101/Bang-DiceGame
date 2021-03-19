package project3;

/**
 *
 * @author Elijah Hand
 */
public class Node{
    Object o;
    Node nextNode;
    Node prevNode;
    
    /**
     *
     * @param o Node must be initialized holding some object
     */
    public Node(Object o)
    {
        this.o = o;
    }
    
    /**
     *
     * @return returns the object held by the node
     */
    public Object get()
    {
        return this.o;
    }
    
    /**
     *
     * @return returns the next Node in line
     */
    public Node getNext()
    {
        return this.nextNode;
    }
    
    /**
     *
     * @return returns the previous Node in line.
     */
    public Node getPrev()
    {
        return this.prevNode;
    }
}