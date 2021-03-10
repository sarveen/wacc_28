package waccFrontEnd.AST;

import java.util.ArrayList;
import java.util.List;

public class ErrorNode implements Node{

    private final List<String> errors;

    public ErrorNode(String message){
        this.errors = new ArrayList<>();
        errors.add(message);
    }
    public ErrorNode(){
        this.errors = new ArrayList<>();
    }


    public List<String> getErrors(){
        return errors;
    }

    public void addErrorMessage(String message){
        errors.add(message);
    }

    public void merge(ErrorNode node) {
        errors.addAll(node.getErrors());
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String getInfoType() {
        return null;
    }
}
