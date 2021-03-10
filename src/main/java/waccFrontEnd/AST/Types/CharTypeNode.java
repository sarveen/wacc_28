package waccFrontEnd.AST.Types;

import waccFrontEnd.AST.Node;

public class CharTypeNode implements Node {

    @Override
    public String getInfoType() {
        return "CHAR";
    }

    @Override
    public String getValue() {
        return "char";
    }
}
