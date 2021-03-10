package waccFrontEnd.AST.Types;

import waccFrontEnd.AST.Node;

public class PairTypeNode implements Node {

    private Node fstType;
    private Node sndType;

    public PairTypeNode(Node fstType, Node sndType) {
        this.fstType = fstType;
        this.sndType = sndType;
    }

    @Override
    public String getValue() {
        StringBuilder result = new StringBuilder();
        result.append("pair(");
        result.append(fstType.getValue().toString());
        result.append(", ");
        result.append(sndType.getValue().toString());
        result.append(")");
        return result.toString();
    }

    @Override
    public Node getChild(int i){
        if(i ==0){
            return fstType;
        }else{
            return sndType;
        }
    }


    @Override
    public String getInfoType() {
        return "PAIR";
    }

    public Node getFstType() {
        return fstType;
    }

    public Node getSndType() {
        return sndType;
    }
}
