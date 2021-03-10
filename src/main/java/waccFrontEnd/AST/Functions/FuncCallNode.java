package waccFrontEnd.AST.Functions;

import waccFrontEnd.AST.Node;
import java.util.List;

public class FuncCallNode implements Node {

    private String funcName;
    private List<Node> args;
    List<Node> typeNodes;
    private Node funcNode;


  public FuncCallNode(String funcName, List<Node> args,List<Node> typeNodes) {
    this.funcName = funcName;
    this.args = args;
    this.typeNodes = typeNodes;
  }

  public FuncCallNode(String funcName, List<Node> args, Node funcNode,List<Node> typeNodes){
      this.funcName = funcName;
      this.args = args;
      this.funcNode = funcNode;
      this.typeNodes = typeNodes;
  }

    public List<Node> getTypeNodes() {
        return typeNodes;
    }
    public Node getFuncNode(){
      return funcNode;
  }

    @Override
    public String getValue() {
            return funcName;
        }

    public List<Node> getArgs() {
        return args;
    }

    @Override
    public String getInfoType() {
        return null;
    }

}


