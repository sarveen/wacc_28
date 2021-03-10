package waccFrontEnd.AST.Assignments;

import waccFrontEnd.AST.Node;

public class PairElemNode implements Node {

    Node identNode;

    String name;

    boolean isFst;

    private Node typeNode;


    public PairElemNode(String name, boolean isFst, Node identNode){
        this.identNode = identNode;
        this.isFst = isFst;
        this.name = name;
    }

    @Override
    public String getValue() {
        if (isFst) {
            return "fst " + name;
        } else {
            return "snd " + name;
        }
    }

    public void setTypeNode(Node typeNode) {
        this.typeNode = typeNode;
    }

    public Node getTypeNode() {
        return typeNode;
    }

    public Node getIdentNode() {
        return identNode;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getInfoType() {
        return "PAIR";
    }

    public boolean isFst() {
        return isFst;
    }
}
