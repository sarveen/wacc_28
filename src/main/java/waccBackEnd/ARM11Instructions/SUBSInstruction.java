package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class SUBSInstruction extends Instruction{
    private final ARMRegister destRegister;
    private final ARMRegister operand1Register;
    private ARMRegister operand2Register;
    private Object value;

    public SUBSInstruction(ARMRegister destRegister, ARMRegister operand1Register, ARMRegister operand2Register) {
        this.setIndentation(2);
        this.destRegister =destRegister;
        this.operand1Register = operand1Register;
        this.operand2Register = operand2Register;
    }
    public SUBSInstruction(ARMRegister destRegister, ARMRegister operand1Register, Object value) {
        this.setIndentation(2);
        this.destRegister =destRegister;
        this.operand1Register = operand1Register;
        this.value = value;
    }


    @Override
    public String generateCode() {
        if(value!=null){
            return representIndentation() + "SUBS " + destRegister + ", " + operand1Register + ", #" + value.toString();
        }
        return representIndentation() + "SUBS " + destRegister + ", " + operand1Register + ", " + operand2Register;
    }
}
