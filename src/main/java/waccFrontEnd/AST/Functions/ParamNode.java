package waccFrontEnd.AST.Functions;

import waccFrontEnd.AST.Node;

public class ParamNode implements Node {

    private final String name;
    private final Node type;

    public ParamNode(String name, Node type) {
        this.name = name;
        this.type = type;
    }

    public String getValue() {
        return type.getValue().toString() + " " + name;
    }

    public Node getType() {
        return type;
    }



    @Override
    public String getInfoType() {
        return type.getInfoType();
    }

    public String getName() {
        return name;
    }
}
