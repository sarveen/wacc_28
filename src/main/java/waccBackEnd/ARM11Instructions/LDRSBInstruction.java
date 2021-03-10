package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class LDRSBInstruction extends LDRFromMemoryInstruction{


    public LDRSBInstruction(ARMRegister register, ARMRegister srcRegister, Object value) {
        super(register, srcRegister, value);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "LDRSB " + getRegister() + ", [" + getSrcRegister() +((Integer)getValue()==0?"":", #"+getValue().toString()) + "]";
    }
}
