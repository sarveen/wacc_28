package waccFrontEnd.AST.Expressions;

import waccFrontEnd.AST.Node;

import java.util.List;

public class ArrayElemNode implements Node {

    private final String name;
    List<Node> indices;
    private Node identNode;
    private Node typeNode;


    public ArrayElemNode(String name, List<Node> indices, Node identNode){
        this.name = name;
        this.indices = indices;
        this.identNode = identNode;
    }

    public void setTypeNode(Node typeNode) {
        this.typeNode = typeNode;
    }

    public Node getIdentNode() {
        return identNode;
    }

    public Node getTypeNode() {
        return typeNode;
    }

    public String getValue() {
        return name;
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public List<Node> getIndices() {
        return indices;
    }
}
