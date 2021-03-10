package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class MOVGTInstruction extends MOVInstruction{
    public MOVGTInstruction(ARMRegister destRegister, Object value) {
        super(destRegister, value);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "MOVGT " + getDestRegister() + ", #" + getValue().toString();
    }
}
