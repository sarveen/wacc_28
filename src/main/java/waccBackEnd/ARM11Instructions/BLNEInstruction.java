package waccBackEnd.ARM11Instructions;

public class BLNEInstruction extends BLInstruction{

    public BLNEInstruction(String label) {
        super(label);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "BLNE " + getLabel();
    }
}
