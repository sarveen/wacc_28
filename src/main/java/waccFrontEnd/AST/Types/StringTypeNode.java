package waccFrontEnd.AST.Types;

import waccFrontEnd.AST.Node;

public class StringTypeNode implements Node {


    @Override
    public String getValue() {
        return "string";
    }

    @Override
    public String getInfoType() {
        return "STRING";
    }
}
