package waccBackEnd.ARM11Instructions;

public class BLInstruction extends Instruction{

    private final String label;

    public BLInstruction(String label) {
        this.setIndentation(2);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String generateCode() {
        return representIndentation() + "BL " + label;
    }
}
