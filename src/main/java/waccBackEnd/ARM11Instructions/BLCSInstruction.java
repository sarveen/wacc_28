package waccBackEnd.ARM11Instructions;

public class BLCSInstruction extends BLInstruction{
    public BLCSInstruction(String label) {
        super(label);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "BLCS " + getLabel();
    }
}
