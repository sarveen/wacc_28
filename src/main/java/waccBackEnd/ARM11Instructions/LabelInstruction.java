package waccBackEnd.ARM11Instructions;

public class LabelInstruction extends Instruction{

    private final String label;

    public LabelInstruction(String label) {
        this.setIndentation(1);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }


    @Override
    public String generateCode() {
        return representIndentation() + label + ":";
    }
}
