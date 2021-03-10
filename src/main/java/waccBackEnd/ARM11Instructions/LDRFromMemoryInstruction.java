package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class LDRFromMemoryInstruction extends LDRInstruction{

    private final ARMRegister srcRegister;

    public LDRFromMemoryInstruction(ARMRegister register, ARMRegister srcRegister, Object value) {
        super(register, value);
        this.srcRegister = srcRegister;
    }

    public ARMRegister getSrcRegister() {
        return srcRegister;
    }

    public String generateCode(){

        return representIndentation()+ "LDR " + getRegister() + ", [" + srcRegister +((Integer) getValue() == 0 ? "":", #"+ getValue().toString()) + "]";
    }

}
