package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class DeclarationNode implements Node {

    private final Node type;
    private final Node lhs;
    private final Node rhs;

    public DeclarationNode(Node typeNode, Node lhsNode, Node rhsNode){
        this.type = typeNode;
        this.lhs = lhsNode;
        this.rhs = rhsNode;
    }

    @Override
    public String getValue() {
        return "DECLARE";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public Node getType() {
        return type;
    }

    public Node getLhs() {
        return lhs;
    }

    public Node getRhs() {
        return rhs;
    }

}