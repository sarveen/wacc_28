package waccFrontEnd.AST.Expressions;

import waccFrontEnd.AST.Node;

public class IntLitNode implements Node {

    private final int value;

    public IntLitNode(int value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String getInfoType() {
        return "INT";
    }
}
