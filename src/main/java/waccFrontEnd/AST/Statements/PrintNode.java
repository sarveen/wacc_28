package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class PrintNode implements Node {

    private Node expr;

    public PrintNode(Node exprNode){
        this.expr = exprNode;
    }

    @Override
    public String getValue() {
        return "PRINT";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public Node getExpr() {
        return expr;
    }

}
