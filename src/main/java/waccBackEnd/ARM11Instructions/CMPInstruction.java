package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class CMPInstruction extends Instruction{ ARMRegister destRegister;

    private final ARMRegister operand1Register;
    private ARMRegister operand2Register;
    private Object operand2Value;


    public CMPInstruction(ARMRegister operand1Register, ARMRegister operand2Register) {
        this.setIndentation(2);
        this.operand1Register = operand1Register;
        this.operand2Register = operand2Register;
    }

    public CMPInstruction(ARMRegister operand1Register, Object operand2Value) {
        this.setIndentation(2);
        this.operand1Register = operand1Register;
        this.operand2Value = operand2Value;
    }

    public ARMRegister getOperand1Register() {
        return operand1Register;
    }

    public ARMRegister getOperand2Register() {
        return operand2Register;
    }

    @Override
    public String generateCode() {
        String operand2String = "";
        if(operand2Value == null){
            operand2String = operand2Register.toString();
        }else{
            operand2String = "#" + operand2Value.toString();
        }
        return representIndentation()+ "CMP " + operand1Register + ", " + operand2String;
    }
}
