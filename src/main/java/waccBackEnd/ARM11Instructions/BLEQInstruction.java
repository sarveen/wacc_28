package waccBackEnd.ARM11Instructions;

public class BLEQInstruction extends BLInstruction {
    public BLEQInstruction(String label) {
        super(label);
    }

    public String generateCode(){
        return representIndentation() + "BLEQ " + getLabel();
    }
}
