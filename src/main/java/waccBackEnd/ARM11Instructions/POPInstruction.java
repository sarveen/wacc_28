package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class POPInstruction extends Instruction{

    private final ARMRegister register;

    public POPInstruction(ARMRegister register) {
        this.setIndentation(2);
        this.register = register;
    }

    @Override
    public String generateCode() {
        return representIndentation() + "POP {" + register + "}";
    }
}
