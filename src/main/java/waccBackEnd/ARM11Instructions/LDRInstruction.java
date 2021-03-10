package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class LDRInstruction extends Instruction{

    private final ARMRegister register;
    private final Object value;

    public LDRInstruction(ARMRegister register, Object value) {
        this.setIndentation(2);
        this.register = register;
        this.value = value;
    }

    public ARMRegister getRegister() {
        return register;
    }

    public Object getValue(){
        return value;
    }

    @Override
    public String generateCode() {
        return representIndentation()+ "LDR " + register + ", =" + value.toString();
    }
}
