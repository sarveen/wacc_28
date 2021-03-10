package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class PrintLnNode extends PrintNode {

    private Node expr;

    public PrintLnNode(Node exprNode) {
        super(exprNode);
    }

    @Override
    public String getValue() {
        return "PRINTLN";
    }

}
