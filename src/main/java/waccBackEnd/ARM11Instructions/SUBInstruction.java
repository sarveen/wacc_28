package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class SUBInstruction extends Instruction{

    private final ARMRegister destRegister;
    private final ARMRegister operand1Register;
    private ARMRegister operand2Register;
    private Object value;

    public SUBInstruction(ARMRegister destRegister, ARMRegister operand1Register, ARMRegister operand2Register) {
        this.setIndentation(2);
        this.destRegister =destRegister;
        this.operand1Register = operand1Register;
        this.operand2Register = operand2Register;
    }
    public SUBInstruction(ARMRegister destRegister, ARMRegister operand1Register, Object value) {
        this.setIndentation(2);
        this.destRegister =destRegister;
        this.operand1Register = operand1Register;
        this.value = value;
    }


    @Override
    public String generateCode() {
        if(value!=null){
            return representIndentation() + "SUB " + destRegister + ", " + operand1Register + ", #" + value.toString();
        }
        return representIndentation() + "SUB " + destRegister + ", " + operand1Register + ", " + operand2Register;
    }
}
