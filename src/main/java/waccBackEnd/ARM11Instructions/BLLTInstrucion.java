package waccBackEnd.ARM11Instructions;

public class BLLTInstrucion extends BLInstruction{
    public BLLTInstrucion(String label) {
        super(label);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "BLLT " + getLabel();
    }
}
