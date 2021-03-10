package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class ADDSInstruction extends Instruction{
    private final ARMRegister dstRegister;
    private final ARMRegister operand1Register;
    private ARMRegister operand2Register;
    private Object operand2Value;

    public ADDSInstruction(ARMRegister destRegister, ARMRegister operand1Register, ARMRegister operand2Register) {
        this.setIndentation(2);
        this.dstRegister =destRegister;
        this.operand1Register = operand1Register;
        this.operand2Register = operand2Register;
    }

    public ADDSInstruction(ARMRegister destRegister, ARMRegister operand1Register, Object operand2Value) {
        this.setIndentation(2);
        this.dstRegister =destRegister;
        this.operand1Register = operand1Register;
        this.operand2Value = operand2Value;
    }

    @Override
    public String generateCode() {
        if(operand2Value!=null){
            return representIndentation() + "ADDS " + dstRegister + ", " + operand1Register + ", #" + operand2Value;
        }
        return representIndentation() + "ADDS " + dstRegister + ", " + operand1Register + ", " + operand2Register;
    }
}
