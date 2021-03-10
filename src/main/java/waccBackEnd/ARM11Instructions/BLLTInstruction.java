package waccBackEnd.ARM11Instructions;

public class BLLTInstruction extends BLInstruction{
    public BLLTInstruction(String label) {
        super(label);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "BLLT " + getLabel();
    }
}
