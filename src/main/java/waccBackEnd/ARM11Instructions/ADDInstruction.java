package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class ADDInstruction extends Instruction{

    private final ARMRegister dstRegister;
    private final ARMRegister operand1Register;
    private ARMRegister operand2Register;
    private Object operand2Value;

    public ADDInstruction(ARMRegister destRegister, ARMRegister operand1Register, ARMRegister operand2Register) {
        this.setIndentation(2);
        this.dstRegister =destRegister;
        this.operand1Register = operand1Register;
        this.operand2Register = operand2Register;
    }

    public ADDInstruction(ARMRegister destRegister, ARMRegister operand1Register, Object operand2Value) {
        this.setIndentation(2);
        this.dstRegister =destRegister;
        this.operand1Register = operand1Register;
        this.operand2Value = operand2Value;
    }

    public ARMRegister getDstRegister(){
        return dstRegister;
    }

    public ARMRegister getOperand1Register() {
        return operand1Register;
    }

    public ARMRegister getOperand2Register() {
        return operand2Register;
    }

    @Override
    public String generateCode() {
        if(operand2Value!=null){
            return representIndentation() + "ADD " + dstRegister + ", " + operand1Register + ", #" + operand2Value;
        }
        return representIndentation() + "ADD " + dstRegister + ", " + operand1Register + ", " + operand2Register;
    }
}
