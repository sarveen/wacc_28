package waccBackEnd.ARM11Instructions;

public class BLVSInstruction extends BInstruction{
    public BLVSInstruction(String label) {
        super(label);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "BLVS " + getLabel();
    }
}
