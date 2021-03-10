package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class ORRInstruction extends Instruction{
    private final ARMRegister destRegister;
    private final ARMRegister operand1Register;
    private final ARMRegister operand2Register;

    public ORRInstruction(ARMRegister destRegister, ARMRegister operand1Register, ARMRegister operand2Register) {
        this.setIndentation(2);
        this.destRegister =destRegister;
        this.operand1Register = operand1Register;
        this.operand2Register = operand2Register;
    }

    @Override
    public String generateCode() {
        return representIndentation() + "ORR " + destRegister + ", " + operand1Register + ", " + operand2Register;
    }
}
