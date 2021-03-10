package waccBackEnd.ARM11Instructions;

public abstract class Instruction {

    public int indentation;// How much the instruction will be indented when it is
                          // generated into code

    public int getIndentation() {
        return indentation;
    }

    public abstract String generateCode();

    public String representIndentation(){
        return "\t".repeat(indentation);
    }

    public void setIndentation(int indentation) {
        this.indentation = indentation;
    }

}
