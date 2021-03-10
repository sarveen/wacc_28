package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class RSBSInstruction extends Instruction{

    private final ARMRegister destRegister;
    private final ARMRegister operandRegister;
    private final Object value;

    public RSBSInstruction(ARMRegister destRegister, ARMRegister operandRegister, Object value) {
        this.destRegister = destRegister;
        this.operandRegister = operandRegister;
        this.value = value;
        this.setIndentation(2);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "RSBS " + destRegister + ", " + operandRegister + ", #" + value;
    }



}
