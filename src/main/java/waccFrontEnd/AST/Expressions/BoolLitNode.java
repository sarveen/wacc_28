package waccFrontEnd.AST.Expressions;

import waccFrontEnd.AST.Node;

public class BoolLitNode implements Node {

    private final boolean value;

    public BoolLitNode(boolean value){
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String getInfoType() {
        return "BOOL";
    }
}
