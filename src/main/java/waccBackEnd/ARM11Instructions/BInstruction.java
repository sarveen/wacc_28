package waccBackEnd.ARM11Instructions;

public class BInstruction extends BLInstruction{
    public BInstruction(String label) {
        super(label);
    }

    public String generateCode(){
       return representIndentation() + "B " + getLabel();
    }
}
