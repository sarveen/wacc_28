package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class CMPWithConditionInstruction extends CMPInstruction{

    private final String condition;

    public CMPWithConditionInstruction(ARMRegister operand1Register, ARMRegister operand2Register, String condition) {
        super(operand1Register, operand2Register);
        this.condition = condition;
    }

    public String generateCode(){
        return representIndentation()+ "CMP " + getOperand1Register() + ", " + getOperand2Register() + ", " + condition;
    }
}
