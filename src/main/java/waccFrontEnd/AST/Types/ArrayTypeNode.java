package waccFrontEnd.AST.Types;

import waccFrontEnd.AST.Node;

public class ArrayTypeNode implements Node {

    private Node type;

    public ArrayTypeNode(Node type){
        this.type = type;
    }

    public String getValue() {
        return type.getValue().toString() + "[]";
    }

    @Override
    public String getInfoType() {
        return type.getInfoType() + "[]";
    }

    public Node getType() {
        return type;
    }

    @Override
    public Node getChild(int i) {
        return type;
    }
}
