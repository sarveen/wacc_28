package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class ANDInstruction extends Instruction{

    private final ARMRegister dstRegister;
    private final ARMRegister operand1Register;
    private final ARMRegister operand2Register;

    public ANDInstruction(ARMRegister destRegister, ARMRegister operand1Register, ARMRegister operand2Register) {
        this.setIndentation(2);
        this.dstRegister =destRegister;
        this.operand1Register = operand1Register;
        this.operand2Register = operand2Register;
    }

    @Override
    public String generateCode() {
        return representIndentation() + "AND " + dstRegister + ", " + operand1Register + ", " + operand2Register;
    }
}
