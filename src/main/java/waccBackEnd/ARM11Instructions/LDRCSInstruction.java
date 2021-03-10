package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class LDRCSInstruction extends LDRInstruction{
    public LDRCSInstruction(ARMRegister register, Object value) {
        super(register, value);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "LDRCS " + getRegister() + ", =" + getValue().toString();
    }
}
