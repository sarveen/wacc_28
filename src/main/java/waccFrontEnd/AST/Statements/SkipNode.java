package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class SkipNode implements Node {

    @Override
    public String getValue() {
        return "SKIP";
    }

    @Override
    public String getInfoType() {
        return null;
    }

}
