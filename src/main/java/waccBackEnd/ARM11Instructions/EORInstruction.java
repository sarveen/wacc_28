package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class EORInstruction extends Instruction{
    private final ARMRegister dstRegister;
    private final ARMRegister srcRegister;
    private final Object value;



    public EORInstruction(ARMRegister dstRegister, ARMRegister srcRegister, Object value) {
        this.setIndentation(2);
        this.dstRegister = dstRegister;
        this.srcRegister = srcRegister;
        this.value = value;
    }


    @Override
    public String generateCode() {
        return representIndentation() + "EOR " + dstRegister + ", " + srcRegister + ", #" + value;
    }
}
