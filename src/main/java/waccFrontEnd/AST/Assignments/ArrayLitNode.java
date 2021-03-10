package waccFrontEnd.AST.Assignments;

import waccFrontEnd.AST.Node;

import java.util.List;


public class ArrayLitNode implements Node {

    private final List<Node> exprNodes;

    private Node typeNode;

    public ArrayLitNode(List<Node> exprNodes){
        this.exprNodes = exprNodes;
    }

    public String getValue() {
        return "ARRAY LITERAL";
    }

    @Override
    public String getInfoType() {
        return exprNodes.get(0).getInfoType() + "[]";
    }

    public List<Node> getExprNodes() {
        return exprNodes;
    }

    public Node getInnerType(){
        if(exprNodes.size()>0){
            return exprNodes.get(0);
        }
        return null;
    }

    public Node getTypeNode(){
        return typeNode;
    }

    public void setTypeNode(Node typeNode){
        this.typeNode = typeNode;
    }

    @Override
    public Node getChild(int i) {
       return exprNodes.get(i);
    }
}
