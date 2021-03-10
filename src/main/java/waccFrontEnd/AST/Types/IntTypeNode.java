package waccFrontEnd.AST.Types;

import waccFrontEnd.AST.Node;

public class IntTypeNode implements Node {

    @Override
    public String getValue() {
        return "int";
    }

    @Override
    public String getInfoType() {
        return "INT";
    }
}
