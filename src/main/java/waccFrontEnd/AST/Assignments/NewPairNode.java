package waccFrontEnd.AST.Assignments;

import waccFrontEnd.AST.Node;

public class NewPairNode implements Node {

    private Node fstExpr;
    private Node sndExpr;
    private Node fstType;
    private Node sndType;

    public NewPairNode(Node fstExpr, Node sndExpr){
        this.fstExpr = fstExpr;
        this.sndExpr = sndExpr;
    }

    public void setSndType(Node sndType) {
        this.sndType = sndType;
    }

    public Node getFstType() {
        return fstType;
    }

    public Node getSndType() {
        return sndType;
    }

    public void setFstType(Node fstType) {
        this.fstType = fstType;
    }

    public Node getFstExpr() {
        return fstExpr;
    }

    public Node getSndExpr() {
        return sndExpr;
    }

    @Override
    public Node getChild(int i){
        if(i ==0){
            return fstExpr;
        }else{
            return sndExpr;
        }
    }

    @Override
    public String getValue() {
        return "NEW_PAIR";
    }

    @Override
    public String getInfoType() {
        return "PAIR" + '(' + fstExpr.getInfoType() + ',' + sndExpr.getInfoType() + ')';
    }
}
