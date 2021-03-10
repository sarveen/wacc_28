package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class PUSHInstruction extends Instruction{

    private final ARMRegister register;

    public PUSHInstruction(ARMRegister register) {
        this.setIndentation(2);
        this.register = register;
    }

    @Override
    public String generateCode() {
        return representIndentation() + "PUSH {" + register + "}";
    }

}
