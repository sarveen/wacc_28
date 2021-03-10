package waccFrontEnd.AST;

import waccFrontEnd.AST.Functions.FuncNode;

import java.util.List;

public class ProgNode implements Node {

    private Node statementNode;
    private List<FuncNode> functions;
    private int stmtScopeSize;

    public ProgNode(Node statementNode, List<FuncNode> functions, int stmtScopeSize) {
        this.statementNode = statementNode;
        this.functions = functions;
        this.stmtScopeSize = stmtScopeSize;
    }

    public int getStmtScopeSize(){
        return stmtScopeSize;
    }

    public Node getStatementNode() {
        return statementNode;
    }


    @Override
    public String getInfoType() {
        return null;
    }

    @Override
    public String getValue() {
    return "Program";
    }

    public List<FuncNode> getFunctionNodes() {
        return functions;
    }



}
