package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class LDREQInstruction extends LDRInstruction {

    public LDREQInstruction(ARMRegister register, Object value) {
        super(register, value);
    }

    @Override
    public String generateCode(){
        return representIndentation()+ "LDREQ " + getRegister() + ", =" + getValue().toString();
    }
}
