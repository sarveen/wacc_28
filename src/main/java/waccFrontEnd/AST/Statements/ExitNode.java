package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class ExitNode implements Node {

    private final Node expr;

    public ExitNode(Node exprNode) {
        this.expr = exprNode;
    }

    @Override
    public String getValue() {
        return "EXIT";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public Node getExpr() {
        return expr;
    }



}