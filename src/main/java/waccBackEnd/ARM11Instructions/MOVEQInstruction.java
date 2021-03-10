package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class MOVEQInstruction extends MOVInstruction{
    public MOVEQInstruction(ARMRegister destRegister, Object value) {
        super(destRegister, value);
    }

    @Override
    public String generateCode() {
        return representIndentation() + "MOVEQ " + getDestRegister() + ", #" + getValue().toString();
    }
}
