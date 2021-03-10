package waccFrontEnd.AST.Expressions;

import waccFrontEnd.AST.Node;

public class UnaryExpressionNode implements Node {

    private final Node operand;
    private final String operator; // unary operator
    private Node typeNode;

    public UnaryExpressionNode(String operator, Node operandNode) {
        this.operand = operandNode;
        this.operator = operator;
    }

    public void setTypeNode(Node typeNode) {
        this.typeNode = typeNode;
    }

    public Node getTypeNode() {
        return typeNode;
    }

    public String getValue() {
        return operator;
    }

    public Node getOperand() {
        return operand;
    }

    @Override
    public String getInfoType() {
        return getOperand().getInfoType();
    }

    public String getOperator() {
        return operator;
    }
}
