package waccBackEnd.ARM11Instructions;

public class BEQInstruction extends BInstruction{

    public BEQInstruction(String label) {
        super(label);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "BEQ " + getLabel();
    }
}
