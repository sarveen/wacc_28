package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class MOVGEInstruction extends MOVInstruction{
    public MOVGEInstruction(ARMRegister destRegister, Object value) {
        super(destRegister, value);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "MOVGE " + getDestRegister() + ", #" + getValue().toString();
    }
}
