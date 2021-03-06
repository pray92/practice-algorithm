package Utils;

public class DoubleLinkedList {

    private int value;

    public DoubleLinkedList getParentNode() {
        return parentNode;
    }

    public void setParentNode(DoubleLinkedList parentNode) {
        this.parentNode = parentNode;
    }

    private DoubleLinkedList parentNode;
    private DoubleLinkedList leftNode;
    private DoubleLinkedList rightNode;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public DoubleLinkedList getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(DoubleLinkedList leftNode) {
        this.leftNode = leftNode;
        if(null != this.leftNode) {
            this.leftNode.setParentNode(this);
        }
    }

    public DoubleLinkedList getRightNode() {
        return rightNode;
    }

    public void setRightNode(DoubleLinkedList rightNode) {
        this.rightNode = rightNode;
        if(null != this.rightNode){
            this.rightNode.setParentNode(this);
        }
    }


    public DoubleLinkedList(){
        value = 0;
        leftNode = null;
        rightNode = null;
    }
}
