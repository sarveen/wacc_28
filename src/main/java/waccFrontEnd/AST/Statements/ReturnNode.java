package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class ReturnNode implements Node {

    private Node expr;

    public ReturnNode(Node exprNode){
        this.expr = exprNode;
    }


    @Override
    public String getValue() {
        return "RETURN";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public Node getExpr() {
        return expr;
    }

}
