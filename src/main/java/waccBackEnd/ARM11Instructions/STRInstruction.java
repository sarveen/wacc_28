package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class STRInstruction extends Instruction{

    private final ARMRegister srcRegister;
    private final ARMRegister dstRegister;
    private final int offset;
    private final boolean exclamation;
    private final boolean isByte;

    public STRInstruction(ARMRegister srcRegister, ARMRegister dstRegister, int offset, boolean exclamation,boolean isByte) {
        this.setIndentation(2);
        this.srcRegister = srcRegister;
        this.dstRegister = dstRegister;
        this.offset = offset;
        this.exclamation = exclamation;
        this.isByte = isByte;
    }

    public STRInstruction(ARMRegister srcRegister, ARMRegister dstRegister, int offset, boolean exclamation) {
        this(srcRegister,dstRegister,offset,exclamation,false);
    }

    public STRInstruction(ARMRegister srcRegister, ARMRegister dstRegister, int offset) {
        this(srcRegister, dstRegister, offset, false,false);
    }

    public String generateCode(){
        return representIndentation() + "STR" + (isByte?"B":"") + " " + srcRegister + ", [" + dstRegister + (offset != 0 ? ", #" + offset : "") + "]" + (exclamation ? "!" : "");
    }
}
