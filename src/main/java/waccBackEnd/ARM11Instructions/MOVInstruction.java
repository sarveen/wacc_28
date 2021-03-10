package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class MOVInstruction extends Instruction{

    ARMRegister destRegister;
    ARMRegister srcRegister;
    Object value;

    public MOVInstruction(ARMRegister destRegister, Object value) {
        this.setIndentation(2);
        this.destRegister = destRegister;
        this.value = value;
    }

    public MOVInstruction(ARMRegister destRegister, ARMRegister srcRegister) {
        this.setIndentation(2);
        this.destRegister = destRegister;
        this.srcRegister = srcRegister;
    }

    public Object getValue() {
        return value;
    }

    public ARMRegister getDestRegister() {
        return destRegister;
    }

    public ARMRegister getSrcRegister() {
        return srcRegister;
    }

    @Override
    public String generateCode() {
        String srcString = "";
        if(value == null){
            srcString = srcRegister.toString();
        }else{
            srcString = "#" + value.toString();
        }
        return representIndentation() + "MOV " + destRegister + ", " + srcString;
    }
}
