package com.thucnh96.jpa.baseOCT.ntree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private  T data;
    private List<Node<T>> chilrens;
    private Node<T> parent;
    private int depth;

    // new node with a given parent
    public Node (T data){
        this.data = data;
        this.chilrens = new ArrayList<>();
        this.depth = 0;
    }

    // new node with a given parent
    public Node (T data,Node<T> parent){
        this.data = data;
        this.setParent(parent);
        this.chilrens = new ArrayList<>();
        this.depth = parent.getDepth() + 1;
        parent.addChildNode(this);
    }

    /**
    * Khởi tạo một nút với dữ liệu của nút khác.
    * Điều này không sao chép các nút con của nút.
    *
    * Nút @param Là nút có dữ liệu sẽ được sao chép.
    */
    public Node (Node<T> node){
        this.data = node.getData();
        this.chilrens = node.getChilrens();
    }

    /**
    *
    * Thêm một phần tử con vào nút này.
    *
    * Nút con @param con
     */
    public void addChildNode(Node<T> child){
        child.setParent(this);
        chilrens.add(child);
    }

    /**
     *
     * Thêm một phần tử con vào nút này.
     *
     * Nút con @param con
     */
    public void addChildNodeAt(int index,Node<T> child){
        child.setParent(this);
        chilrens.add(index,child);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Node<T>> getChilrens() {
        return chilrens;
    }

    public void setChilrens(List<Node<T>> chilrens) {
        this.chilrens = chilrens;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
