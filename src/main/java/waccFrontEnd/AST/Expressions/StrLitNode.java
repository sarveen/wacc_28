package waccFrontEnd.AST.Expressions;

import waccFrontEnd.AST.Node;

public class StrLitNode implements Node {

    private final String value;

    public StrLitNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getInfoType() {
        return "STRING";
    }
}
