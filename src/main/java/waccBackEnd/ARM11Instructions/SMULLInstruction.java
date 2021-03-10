package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class SMULLInstruction extends Instruction{

    private final ARMRegister operand1Register;
    private final ARMRegister operand2Register;

    public SMULLInstruction(ARMRegister operand1Register, ARMRegister operand2Register) {
        this.setIndentation(2);
        this.operand1Register = operand1Register;
        this.operand2Register = operand2Register;
    }
    @Override
    public String generateCode() {
      return representIndentation() + "SMULL " + operand1Register + ", " + operand2Register + ", " + operand1Register + ", " + operand2Register;
    }
}

