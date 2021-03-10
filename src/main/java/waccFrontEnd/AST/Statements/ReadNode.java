package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class ReadNode implements Node{

    private Node expr;

    public ReadNode(Node exprNode){
        this.expr = exprNode;
    }

    @Override
    public String getValue() {
        return "READ";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public Node getExpr() {
        return expr;
    }

}
