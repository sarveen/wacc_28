package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class ADDWithShiftInstruction extends ADDInstruction{

    private final int shift;

    public ADDWithShiftInstruction(ARMRegister destRegister, ARMRegister operand1Register, ARMRegister operand2Register, int shift) {
        super(destRegister, operand1Register, operand2Register);
        this.shift = shift;
    }

  public String generateCode() {
    return representIndentation()
        + "ADD "
        + getDstRegister()
        + ", "
        + getOperand1Register()
        + ", "
        + getOperand2Register()
        + ", LSL #"
        + shift;
    }
}
