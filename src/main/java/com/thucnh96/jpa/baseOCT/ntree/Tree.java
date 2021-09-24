package com.thucnh96.jpa.baseOCT.ntree;

import java.util.ArrayList;

public class Tree<T> {

    private Node<T> root;

    public Tree(Node<T> root) {
        this.root = root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Node<T> getRoot() {
        return root;
    }
    public void setRoot(Node<T> root) {
        this.root = root;
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean exists(T key) {
        return find(root, key);
    }

    private boolean find(Node<T> node, T key){
        boolean result = false;
        if (node.getData().equals(key))
            result = true;
        else
            for(Node<T> child : node.getChilrens() )
               if(find(child,key))
                   result = true ;
        return result;
    }

    /**
     * Tìm node
     * @param node
     * @param key
     * @return
     */
    public Node<T> findNode(Node<T> node, T key){
        if (node.getData().equals(key)) {
            return node;
        }else {
            Node<T> exists = null;
            for (Node<T> child : node.getChilrens()){
               if( (exists = findNode(child,key)) != null ){
                    return  exists;
               }
            }
        }
        return null;
    }

    /**
      *
      * Tính số lượng nút con mà một nút nhất định có.
      * Nút @param Là nút cần có số lượng nút con.
      * @ quay lại số lượng con cháu
     */
    public int getNumberOfDescendants(Node<T> node) {
        if ( node == null ) return 0;
        int count = node.getChilrens().size();
        for (Node<T> child : node.getChilrens())
            count += getNumberOfDescendants(child);
        return count;
    }

    /**
      *
      * Lấy danh sách các nút được sắp xếp theo thứ tự sau của cây.
      *
      * @return Danh sách các nút trong cây, được sắp xếp theo thứ tự sau
    */
    public ArrayList<Node<T>> getPostOrderTraversal(){
        ArrayList<Node<T>> postOrder = new ArrayList<>();
        buildPostOrderTraversal(root,postOrder);
        return postOrder;
    }

    /**
      * ref getPostOrderTraversal
      * @param node
      * @param postOrder
    */
    private void buildPostOrderTraversal(Node<T> node, ArrayList<Node<T>> postOrder ){
        postOrder.add(node);
        for (Node<T> child : node.getChilrens()) {
            buildPostOrderTraversal(child, postOrder);
        }
    }

    /**
      *
      * Lấy chiều cao của cây (số nút trong con đường dài nhất từ gốc đến lá)
      *
      * @return Chiều cao của cây.
    */
    public int getHeight(){
        return getLongestPathFromRootToAnyLeaf().size();
    }

    /**
      * Nhận danh sách các nút trong đường dẫn dài nhất từ gốc đến bất kỳ lá nào trong cây.
      *
      * Ví dụ, đối với cây dưới đây
      * <pre>
      *      A
      *     / \
      *    B   C
      *   /     \
      *  D       E
      *           \
      *            F
      * </pre>
      *
         * Kết quả sẽ là [A, C, E, F]
         *
         * @return Danh sách các nút trong đường dẫn dài nhất.
       */
    public ArrayList<Node<T>> getLongestPathFromRootToAnyLeaf(){
        ArrayList<Node<T>> longestPath = null;
        int max = 0;
        for (ArrayList<Node<T>>  path : getPathsFromRootToAnyLeaf()){
            if (path.size() > max){
                max = path.size();
                longestPath = path;
            }
        }
        return longestPath;
    }

    /**
    *
    * Nhận danh sách tất cả các đường dẫn (lại là danh sách các nút dọc theo đường dẫn) từ nút gốc đến mọi lá.
    *
    * @return Danh sách các đường dẫn.
    */
    public ArrayList<ArrayList<Node<T>>> getPathsFromRootToAnyLeaf() {
        ArrayList<ArrayList<Node<T>>> paths = new ArrayList<ArrayList<Node<T>>>();
        ArrayList<Node<T>> currentPath = new ArrayList<Node<T>>();
        getPath(root, currentPath, paths);
        return paths;
    }

    /**
     *
     * @param node
     * @param currentPath
     * @param paths
     */
    private void getPath(Node<T> node,
                         ArrayList<Node<T>> currentPath,
                         ArrayList<ArrayList<Node<T>>> paths) {

        if(currentPath == null) return;
        currentPath.add(node);
        if (node.getChilrens().size() == 0){
            //đấy chính là node cuối cùng
            paths.add(clone(currentPath));
        }
        for (Node<T> child : node.getChilrens()){
            getPath(child,currentPath,paths);
        }
        int index = currentPath.indexOf(node);
        for (int i = index; i < currentPath.size(); i++)
            currentPath.remove(index);
    }


    private ArrayList<Node<T>> clone(ArrayList<Node<T>> list) {
        ArrayList<Node<T>> newList = new ArrayList<Node<T>>();
        for (Node<T> node : list)
            newList.add(new Node<T>(node));
        return newList;
    }

}

