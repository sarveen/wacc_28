package waccFrontEnd.AST.Statements;

import waccFrontEnd.AST.Node;

import java.util.List;

public class SeqNode implements Node{

    List<Node> stats;

    public SeqNode(List<Node> statsNodes){
        this.stats = statsNodes;
    }

    @Override
    public String getValue() {   // getValue() will never be called on this node.
        return "";
    }

    @Override
    public String getInfoType() {
        return null;
    }

    public List<Node> getStats() {
        return stats;
    }

}
