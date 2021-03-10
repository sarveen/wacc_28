package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

public class ScopeNode implements Node{

    private Node stmt;
    private final int scopeSize;

    public ScopeNode(Node stmtNode, int scopeSize){
        this.stmt = stmtNode;
        this.scopeSize = scopeSize;
    }

    public int getScopeSize(){
        return scopeSize;
    }

    @Override
    public String getValue() {
        return "SCOPE";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public Node getStmt() {
        return stmt;
    }
}
