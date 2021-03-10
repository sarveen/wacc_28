package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class MOVLEInstruction extends MOVInstruction{
    public MOVLEInstruction(ARMRegister destRegister, Object value) {
        super(destRegister, value);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "MOVLE " + getDestRegister() + ", #" + getValue().toString();
    }
}
