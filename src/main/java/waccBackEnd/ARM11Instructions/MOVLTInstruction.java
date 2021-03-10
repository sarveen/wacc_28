package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class MOVLTInstruction extends MOVInstruction{
    public MOVLTInstruction(ARMRegister destRegister, Object value) {
        super(destRegister, value);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "MOVLT " + getDestRegister() + ", #" + getValue().toString();
    }
}
