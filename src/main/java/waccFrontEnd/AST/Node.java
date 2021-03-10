package waccFrontEnd.AST;

public interface Node {

  public Object getValue();

  public String getInfoType();

  default Node getChild(int i){
    return null;
  }

}
