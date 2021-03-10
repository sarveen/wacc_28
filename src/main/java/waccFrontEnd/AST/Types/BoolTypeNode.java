package waccFrontEnd.AST.Types;

import waccFrontEnd.AST.Node;

public class BoolTypeNode implements Node {
    @Override
    public String getValue() {
        return "bool";
    }

    @Override
    public String getInfoType() {
        return "BOOL";
    }
}
