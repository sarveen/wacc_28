package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class AssignmentNode implements Node {

    private final Node lhs;
    private final Node rhs;

    public AssignmentNode(Node lhsNode, Node rhsNode) {
        this.lhs = lhsNode;
        this.rhs = rhsNode;
    }

    @Override
    public String getValue() {
        return "ASSIGNMENT";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public Node getLhs() {
        return lhs;
    }

    public Node getRhs() {
        return rhs;
    }

}
