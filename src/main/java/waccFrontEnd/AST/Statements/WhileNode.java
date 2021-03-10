package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class WhileNode implements Node {

    private Node cond;
    private Node stat;
    private final int scopeSize;

    public WhileNode(Node condNode, Node statNode, int scopeSize){
        this.cond = condNode;
        this.stat = statNode;
        this.scopeSize = scopeSize;
    }

    public int getScopeSize(){
       return scopeSize;
    }

    @Override
    public String getValue() {
        return "LOOP";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public Node getCond() {
        return cond;
    }

    public Node getStat() {
        return stat;
    }

}
