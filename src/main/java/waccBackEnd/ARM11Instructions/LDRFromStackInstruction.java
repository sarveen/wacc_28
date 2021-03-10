package waccBackEnd.ARM11Instructions;

import waccBackEnd.ARMRegister;

public class LDRFromStackInstruction extends LDRInstruction{
    public LDRFromStackInstruction(ARMRegister register, Object value) {
        super(register, value);
    }


    @Override
    public String generateCode(){
        return representIndentation()+ "LDR " + getRegister() + ", [sp" +((Integer) getValue() ==0?"":", #"+ getValue().toString()) + "]";
    }

}
