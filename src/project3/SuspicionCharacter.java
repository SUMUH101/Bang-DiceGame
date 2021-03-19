package project3;
/* The goal of this class is to hold the character name and suspicion value associated with that character
inside the AI class */

class SuspicionCharacter
{
    private String name;
    private int suspicionVal;
    private int range;
    private int index; // Index in the Game's PlayerList

    public SuspicionCharacter(String name, int suspicionVal,int index) {
        this.name = name;
        this.suspicionVal = suspicionVal;
        this.index = index;
    }

    public SuspicionCharacter(Character a,int suspicionVal,int index)
    {
    	this.name = a.getName();
    	this.suspicionVal = suspicionVal;
    	this.index = index;
    }
    
    
    public int getIndex()
    {
        return this.index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getRange()
    {
        return this.range;
    }

    public void setRange(int range)
    {
        this.range = range;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuspicionVal() {
        return this.suspicionVal;
    }

    public void setSuspicionVal(int suspicionVal) {
        this.suspicionVal = suspicionVal;
    }

    public void subSuspicionVal(int sub)
    {
        this.suspicionVal -= sub;
    }

    public void addSuspicionVal(int add)
    {
        this.suspicionVal += add;
    }

}