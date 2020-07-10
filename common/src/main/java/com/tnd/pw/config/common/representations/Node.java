package com.tnd.pw.config.common.representations;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private T data;

    private List<Node<T>> children = new ArrayList<>();

    public Node(T data) {
        this.data = data;
    }

    public Node<T> addChild(Node<T> child) {
        this.children.add(child);
        return child;
    }

    public Node<T> addChild(T data) {
        Node<T> child = new Node<>(data);
        this.children.add(child);
        return child;
    }

    public void addChildren(List<Node<T>> children) {
        this.children.addAll(children);
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
