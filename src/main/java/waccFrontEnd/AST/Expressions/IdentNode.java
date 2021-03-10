package waccFrontEnd.AST.Expressions;

import waccFrontEnd.AST.Node;

public class IdentNode implements Node {

    private final String name;

    private Node identType;

    private Node typeNode;

    public IdentNode(String name, Node identType, Node typeNode){
        this.identType = identType;
        this.name = name;
        this.typeNode = typeNode;
    }

    public Node getIdentType() {
        return identType;
    }


    public Node getTypeNode(){
        return typeNode;
    }

    public String getValue() {
        return name;
    }

    @Override
    public String getInfoType() {
        return null;
    }
}
