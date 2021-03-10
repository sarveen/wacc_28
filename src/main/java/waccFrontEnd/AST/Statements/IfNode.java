package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class IfNode implements Node {

    private final Node cond;
    private final Node thenStat;
    private final Node elseStat;
    private final int thenScopeSize;
    private final int elseScopeSize;

    public IfNode(Node condNode, Node thenStatNode, Node elseStatNode, int thenScopeSize, int elseScopeSize) {
        this.cond = condNode;
        this.thenStat = thenStatNode;
        this.elseStat = elseStatNode;
        this.thenScopeSize = thenScopeSize;
        this.elseScopeSize = elseScopeSize;
    }

    public int getThenScopeSize(){
        return thenScopeSize;
    }

    public int getElseScopeSize(){
        return elseScopeSize;
    }

    @Override
    public String getValue() {
        return "IF";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public Node getCond() {
        return cond;
    }

    public Node getThenStat() {
        return thenStat;
    }

    public Node getElseStat() {
        return elseStat;
    }
}
