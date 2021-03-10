package waccBackEnd;

import waccBackEnd.ARM11Instructions.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;

public class ARM11ProgramRepresentation {

  // -------------------------------------------------------------------- //
  // Internal representation of the arm1176jzf-s program.                 //
  // -------------------------------------------------------------------- //

  // Map of ARM11 labels (one will be created for each visited
  // user defined function, conditional, loop or program function, as well as
  // one for main.
  // A string of the label's name maps to a list of instructions used
  // in that function.
  Map<String, List<Instruction>> labelMap;

  // Stack of the lists of instructions representing functions, with the top
  // one being the current function label we are working on.
  Stack<List<Instruction>> scope;

  // Current label's instruction body.
  List<Instruction> currLabel;

  // List of message instructions for printing (outputted before instructions
  // added to represent program code).
  List<Instruction> msgs;

  // Booleans to represent whether runtime error or IO labels or labels
  // for freeing heap space need to be created.
  private boolean divideByZeroLabelCreated;
  private boolean runtimeErrorLabelCreated;
  private boolean overflowLabelCreated;
  private boolean printStringLabelCreated;
  private boolean arrayOutOfBoundsLabelCreated;
  private boolean freeArrayLabelCreated;
  private boolean freePairLabelCreated;
  private boolean nullPointerLabelCreated;
  private boolean printBoolLabelCreated;
  private boolean printIntLabelCreated;
  private boolean printRefLabelCreated;
  private boolean printLnLabelCreated;
  private boolean readIntLabelCreated;
  private boolean readCharLabelCreated;

  // Number of condition or loop labels generated.
  private int labelNumber;

  // Number of messages created.
  private int messageIndex;

  public ARM11ProgramRepresentation() {
    labelMap = new LinkedHashMap<>();
    scope = new Stack<>();
    currLabel = new LinkedList<>();
    msgs = new LinkedList<>();
    messageIndex = 0;
    labelNumber = 0;
    divideByZeroLabelCreated = false;
    runtimeErrorLabelCreated = false;
    overflowLabelCreated = false;
    printStringLabelCreated = false;
    arrayOutOfBoundsLabelCreated = false;
    freeArrayLabelCreated = false;
    freePairLabelCreated = false;
    nullPointerLabelCreated = false;
    printBoolLabelCreated = false;
    printIntLabelCreated = false;
    printRefLabelCreated = false;
    printLnLabelCreated = false;
    readIntLabelCreated = false;
    readCharLabelCreated = false;
  }

  // Sets one of the booleans to represent if a program label for runtime
  // errors, IO or freeing heap space to true.
  public void setProgramLabel(String labelName){
    try {
      Field label = this.getClass().getDeclaredField(labelName);
      label.setBoolean(this ,true);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  // Methods to create internal program labels for runtime errors or IO or
  // freeing heap space.

  public void createDivideByZeroLabelIfNeeded() {
    if (divideByZeroLabelCreated) {
      openLabel("p_check_divide_by_zero");
      addMessage("DivideByZeroError: divide or modulo by zero\\n\\0");
      addInstruction(new PUSHInstruction(ARMRegister.lr));
      addInstruction(new CMPInstruction(ARMRegister.r1, 0));
      addInstruction(new LDREQInstruction(ARMRegister.r0, "msg_" + (messageIndex - 1)));
      addInstruction(new BLEQInstruction("p_throw_runtime_error"));
      addInstruction(new POPInstruction(ARMRegister.pc));
      setProgramLabel("runtimeErrorLabelCreated");
      closeLabel();
    }
  }

  public void createArrayOutOfBoundsLabelIfNeeded() {
    if (arrayOutOfBoundsLabelCreated) {
      openLabel("p_check_array_bounds");
      addInstruction(new PUSHInstruction(ARMRegister.lr));
      addMessage("ArrayIndexOutOfBoundsError: negative index\\n\\0");
      addMessage("ArrayIndexOutOfBoundsError: index too large\\n\\0");
      addInstruction(new CMPInstruction(ARMRegister.r0, 0));
      addInstruction(new LDRLTInstruction(ARMRegister.r0, "msg_" + (messageIndex - 2)));
      addInstruction(new BLLTInstruction("p_throw_runtime_error"));
      addInstruction(new LDRFromMemoryInstruction(ARMRegister.r1,ARMRegister.r1,0));
      addInstruction(new CMPInstruction(ARMRegister.r0,ARMRegister.r1));
      addInstruction(new LDRCSInstruction(ARMRegister.r0,"msg_" + (messageIndex - 1)));
      addInstruction(new BLCSInstruction("p_throw_runtime_error"));
      setProgramLabel("runtimeErrorLabelCreated");
      addInstruction(new POPInstruction(ARMRegister.pc));
      closeLabel();
    }
  }

  private void createRunTimeErrorLabelIfNeeded() {
    if (runtimeErrorLabelCreated) {
      openLabel("p_throw_runtime_error");
      addInstruction(new BLInstruction("p_print_string"));
      setProgramLabel("printStringLabelCreated");
      addInstruction(new MOVInstruction(ARMRegister.r0, -1));
      addInstruction(new BLInstruction("exit"));
      closeLabel();
    }
  }

  public void createOverflowLabelIfNeeded() {
    if (overflowLabelCreated) {
      openLabel("p_throw_overflow_error");
      addMessage(
          "OverflowError: the result is too small/large to store in a 4-byte signed-integer.\\n");
      addInstruction(new LDRInstruction(ARMRegister.r0, "msg_" + (messageIndex - 1)));
      addInstruction(new BLInstruction("p_throw_runtime_error"));
      setProgramLabel("runtimeErrorLabelCreated");
      closeLabel();
    }
  }

  public void createPrintStringLabelIfNeeded() {
    if (printStringLabelCreated) {
      openLabel("p_print_string");
      addMessage("%.*s\\0");
      addInstruction(new PUSHInstruction(ARMRegister.lr));
      addInstruction(new LDRFromMemoryInstruction(ARMRegister.r1, ARMRegister.r0,0));
      addInstruction(new ADDInstruction(ARMRegister.r2, ARMRegister.r0, 4));
      addInstruction(new LDRInstruction(ARMRegister.r0, "msg_" + (messageIndex - 1)));
      endPrintLabel("printf");
    }
  }

  public void createPrintBoolLabelIfNeeded() {
    if (printBoolLabelCreated) {
      openLabel("p_print_bool");
      addMessage("true\\0");
      addMessage("false\\0");
      addInstruction(new PUSHInstruction(ARMRegister.lr));
      addInstruction(new CMPInstruction(ARMRegister.r0, 0));
      addInstruction(new LDRNEInstruction(ARMRegister.r0, "msg_" + (messageIndex - 2)));
      addInstruction(new LDREQInstruction(ARMRegister.r0, "msg_" + (messageIndex - 1)));
      endPrintLabel("printf");
    }
  }

  public void createPrintIntLabelIfNeeded() {
    if (printIntLabelCreated) {
      openLabel("p_print_int");
      addMessage("%d\\0");
      addInstruction(new PUSHInstruction(ARMRegister.lr));
      addInstruction(new MOVInstruction(ARMRegister.r1, ARMRegister.r0));
      addInstruction(new LDRInstruction(ARMRegister.r0, "msg_" + (messageIndex - 1)));
      endPrintLabel("printf");
    }
  }

  public void createPrintRefLabelIfNeeded() {
    if (printRefLabelCreated) {
      openLabel("p_print_reference");
      addMessage("%p\\0");
      addInstruction(new PUSHInstruction(ARMRegister.lr));
      addInstruction(new MOVInstruction(ARMRegister.r1, ARMRegister.r0));
      addInstruction(new LDRInstruction(ARMRegister.r0, "msg_" + (messageIndex - 1)));
      endPrintLabel("printf");
    }
  }

  public void createPrintLnLabelIfNeeded() {
    if (printLnLabelCreated) {
      openLabel("p_print_ln");
      addMessage("\\0");
      addInstruction(new PUSHInstruction(ARMRegister.lr));
      addInstruction(new LDRInstruction(ARMRegister.r0, "msg_" + (messageIndex-1)));
      endPrintLabel("puts");
    }
  }

  public void endPrintLabel(String branchTo) {
    addInstruction(new ADDInstruction(ARMRegister.r0, ARMRegister.r0, 4));
    addInstruction(new BLInstruction(branchTo));
    addInstruction(new MOVInstruction(ARMRegister.r0, 0));
    addInstruction(new BLInstruction("fflush"));
    addInstruction(new POPInstruction(ARMRegister.pc));
    closeLabel();
  }

  public void endReadLabel() {
    addInstruction(new PUSHInstruction(ARMRegister.lr));
    addInstruction(new MOVInstruction(ARMRegister.r1, ARMRegister.r0));
    addInstruction(new LDRInstruction(ARMRegister.r0, "msg_" + (messageIndex - 1)));
    addInstruction(new ADDInstruction(ARMRegister.r0, ARMRegister.r0, 4));
    addInstruction(new BLInstruction("scanf"));
    addInstruction(new POPInstruction(ARMRegister.pc));
    closeLabel();
  }

  public void createReadIntLabelIfNeeded() {
    if (readIntLabelCreated) {
      openLabel("p_read_int");
      addMessage("%d\\0");
      endReadLabel();
    }
  }

  public void createReadCharLabelIfNeeded() {
    if (readCharLabelCreated) {
      openLabel("p_read_char");
      addMessage(" %c\\0");
      endReadLabel();
    }
  }

  public void createFreeArrayLabelIfNeeded() {
    if (freeArrayLabelCreated) {
      openLabel("p_free_array");
      addMessage("NullReferenceError: dereference a null reference\\n\\0");
      addInstruction(new PUSHInstruction(ARMRegister.lr));
      addInstruction(new CMPInstruction(ARMRegister.r0, 0));
      addInstruction(new LDREQInstruction(ARMRegister.r0, "msg_" + (messageIndex - 1)));
      addInstruction(new BEQInstruction("p_throw_runtime_error"));
      addInstruction(new BLInstruction("free"));
      addInstruction(new POPInstruction(ARMRegister.pc));
      setProgramLabel("runtimeErrorLabelCreated");
      closeLabel();
    }
  }

  public void createFreePairLabelIfNeeded(){
    if(freePairLabelCreated){
      openLabel("p_free_pair");
      addMessage("NullReferenceError: dereference a null reference\\n\\0");
      addInstruction(new PUSHInstruction(ARMRegister.lr));
      addInstruction(new CMPInstruction(ARMRegister.r0,0));
      addInstruction(new LDREQInstruction(ARMRegister.r0,"msg_" + (messageIndex - 1)));
      addInstruction(new BEQInstruction("p_throw_runtime_error"));
      setProgramLabel("runtimeErrorLabelCreated");
      addInstruction(new PUSHInstruction(ARMRegister.r0));
      addInstruction(new LDRFromMemoryInstruction(ARMRegister.r0,ARMRegister.r0,0));
      addInstruction(new BLInstruction("free"));
      addInstruction(new LDRFromMemoryInstruction(ARMRegister.r0,ARMRegister.sp,0));
      addInstruction(new LDRFromMemoryInstruction(ARMRegister.r0,ARMRegister.r0,4));
      addInstruction(new BLInstruction("free"));
      addInstruction(new POPInstruction(ARMRegister.r0));
      addInstruction(new BLInstruction("free"));
      addInstruction(new POPInstruction(ARMRegister.pc));
      closeLabel();
    }
  }

  public void createNullPointerLabelIfNeeded(){
    if(nullPointerLabelCreated){
      openLabel("p_check_null_pointer");
      addMessage("NullReferenceError: dereference a null reference\\n\\0");
      addInstruction(new PUSHInstruction(ARMRegister.lr));
      addInstruction(new CMPInstruction(ARMRegister.r0,0));
      addInstruction(new LDREQInstruction(ARMRegister.r0,"msg_" + (messageIndex - 1)));
      addInstruction(new BEQInstruction("p_throw_runtime_error"));
      setProgramLabel("runtimeErrorLabelCreated");
      addInstruction(new POPInstruction(ARMRegister.pc));
      closeLabel();
    }
  }

  // Method to add instructions to the current function label.
  public void addInstruction(Instruction i) {
    if (i != null) {
      currLabel.add(i);
    }
  }

  public void printInstructionsToFile(String fileName) throws IOException {
    FileWriter writer = new FileWriter(fileName);
    makeProgramLabels();
    if (msgs.size() != 0) {
      writer.write("\t.data\n\n");
      for (Instruction msg : msgs) {
        writer.write(msg.generateCode());
      }
      writer.write("\n");
    }
    writer.write("\t.text\n");
    writer.write("\n");
    writer.write("\t.global main\n");
    for (List<Instruction> labelInstructions : labelMap.values()) {
      for (Instruction instruction : labelInstructions) {
        writer.write(instruction.generateCode() + "\n");
      }
      writer.write("\n");
    }
    writer.close();
  }

  public void makeProgramLabels() {
    createDivideByZeroLabelIfNeeded();
    createOverflowLabelIfNeeded();
    createArrayOutOfBoundsLabelIfNeeded();
    createFreeArrayLabelIfNeeded();
    createFreePairLabelIfNeeded();
    createNullPointerLabelIfNeeded();
    createRunTimeErrorLabelIfNeeded();
    createPrintStringLabelIfNeeded();
    createPrintIntLabelIfNeeded();
    createPrintBoolLabelIfNeeded();
    createPrintRefLabelIfNeeded();
    createPrintLnLabelIfNeeded();
    createReadIntLabelIfNeeded();
    createReadCharLabelIfNeeded();
  }

  // Methods used to create a new program function or user-defined function
  // label.
  public void openLabel(String labelName) {
      currLabel = new LinkedList<>();
      labelMap.put(labelName, currLabel);
      scope.push(currLabel);
      currLabel.add(new LabelInstruction(labelName));
  }

  // Closes a function label, removing its' instructions (List<Instruction>)
  // from the top of the scope stack and setting the currLabel to the previous
  // instruction body from the scope.
  public void closeLabel() {
    scope.pop();
    if (scope.size() > 0) {
      currLabel = scope.peek();
    }
  }

  // Creates a new message for IO usage.
  public String addMessage(String msg) {
    LabelInstruction i = new MessageLabelInstruction(msg, messageIndex);
    msgs.add(i);
    messageIndex++;
    return i.getLabel();
  }

  public String generateLabel() {
    return "L" + labelNumber++;
  }
}
