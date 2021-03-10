package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class FreeNode implements Node{

    private final Node expr;
    private final Node typeNode;

    public FreeNode(Node exprNode, Node typeNode){
        this.expr = exprNode;
        this.typeNode = typeNode;
    }

    public Node getTypeNode() {
        return typeNode;
    }

    @Override
    public String getValue() {
        return "FREE";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public Node getExpr() {
        return expr;
    }




}
