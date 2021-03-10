package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class LDRNEInstruction extends LDRInstruction {

    public LDRNEInstruction(ARMRegister register, Object value) {
        super(register, value);
    }

    @Override
    public String generateCode(){
        return representIndentation()+ "LDRNE " + getRegister() + ", =" + getValue();
    }
}