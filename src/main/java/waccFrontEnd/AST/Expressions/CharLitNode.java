package waccFrontEnd.AST.Expressions;

import waccFrontEnd.AST.Node;

public class CharLitNode implements Node {

    private final char value;

    public CharLitNode(char value){
        this.value = value;
    }

    public Character getValue() {
        return value;
    }

    @Override
    public String getInfoType() {
        return "CHAR";
    }
}
