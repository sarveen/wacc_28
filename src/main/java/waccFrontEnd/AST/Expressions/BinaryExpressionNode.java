package waccFrontEnd.AST.Expressions;

import waccFrontEnd.AST.Node;

public class BinaryExpressionNode implements Node {

  private final Node leftOperand;
  private final Node rightOperand;
  private final String operator; // binary operator
  private Node typeNode;

  public BinaryExpressionNode(String operator, Node leftOperandNode, Node rightOperandNode) {
    this.leftOperand = leftOperandNode;
    this.rightOperand = rightOperandNode;
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

  @Override
  public String getInfoType() {
    return leftOperand.getInfoType();
  }

  public Node getLeftOperand() {
    return leftOperand;
  }

  public Node getRightOperand() {
    return rightOperand;
  }

  public String getOperator() {
    return operator;
  }
}