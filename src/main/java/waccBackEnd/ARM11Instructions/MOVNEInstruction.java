package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class MOVNEInstruction extends MOVInstruction{
    public MOVNEInstruction(ARMRegister destRegister, Object value) {
        super(destRegister, value);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "MOVNE " + getDestRegister() + ", #" + getValue().toString();
    }
}
