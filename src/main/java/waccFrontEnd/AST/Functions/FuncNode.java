package waccFrontEnd.AST.Functions;

import waccFrontEnd.AST.Node;

import java.util.List;

public class FuncNode implements Node{

    String name;
    private Node returnType;
    private List<Node> params;
    private Node body;
    private int scopeSize;


    public FuncNode(String name, Node returnType, List<Node> params, Node body){
        this.returnType = returnType;
        this.params = params;
        this.name = name;
        this.body = body;
    }

    public void setScopeSize(int scopeSize){
        this.scopeSize = scopeSize;
    }

    public int getScopeSize(){
        return scopeSize;
    }

    public void setParams(List<Node> params){
        this.params = params;
    }

    public List<Node> getParams() {
        return params;
    }

    public Node getReturnType() {
        return returnType;
    }

    public void setBody(Node body) {
        this.body = body;
    }

    public Node getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        StringBuilder result = new StringBuilder();
        result.append(returnType.getValue().toString());
        result.append(" ");
        result.append(name);
        result.append("(");
        for (Node param : params) {
            result.append(param.getValue().toString());
            result.append(", ");
        }
        result.append(")");
        return result.toString();
    }

    @Override
    public String getInfoType() {
        return "FUNCTION";
    }
}
