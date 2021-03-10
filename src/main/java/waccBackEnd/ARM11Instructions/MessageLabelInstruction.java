package waccBackEnd.ARM11Instructions;

public class MessageLabelInstruction extends LabelInstruction {

  private final String msg;
  private final int length;


  public MessageLabelInstruction(String msg, int index) {
    super("msg_" + index);
    this.msg = msg;
    length = fixLength(msg);
    setIndentation(1);
  }

  private int fixLength(String msg){
    int length = 0;
    for(int i=0;i<msg.length();i++){
      if(msg.charAt(i)!='\\'){
        length ++;
      }
    }
    return length;
  }

  @Override
  public String generateCode() {
    return representIndentation() + getLabel() + ":\n" + "\t\t.word " + length + '\n' + "\t\t.ascii \"" + msg + "\"\n";
  }
}
