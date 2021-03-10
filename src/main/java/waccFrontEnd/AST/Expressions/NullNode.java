package waccFrontEnd.AST.Expressions;

import waccFrontEnd.AST.Node;

public class NullNode implements Node {

    private Node fstType;

    private Node sndType;

    public NullNode(){
    }

    public void setFstType(Node fstType) {
        this.fstType = fstType;
    }

    public void setSndType(Node sndType) {
        this.sndType = sndType;
    }

    public Node getSndType() {
        return sndType;
    }

    public Node getFstType() {
        return fstType;
    }

    public String getValue() {
        return "null";
    }

    @Override
    public String getInfoType() {
        return "PAIR";
    }

}