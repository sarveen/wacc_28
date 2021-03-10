package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class LDRLTInstruction extends LDRInstruction{

    public LDRLTInstruction(ARMRegister register, Object value) {
        super(register, value);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "LDRLT " + getRegister() + " , =" + getValue();
    }
}
