package waccBackEnd;

import waccBackEnd.ARM11Instructions.PUSHInstruction;

import java.util.*;

public class MemoryManagement {

  // -------------------------------------------------------------------- //
  // Internal representation of memory, including registers, and stack    //
  // sizes for the current function scope.                                //
  // -------------------------------------------------------------------- //

  private static final int INT_OFFSET = 4;
  private static final int CHAR_OFFSET = 1;
  private static final int BOOL_OFFSET = 1;
  private static final int STR_OFFSET = 4;
  private final ARM11ProgramRepresentation rep;

  // Simple list to represent whether each register is free (value 0 at it's
  // index) or in use (value 1 at it's index)

  private final int[] registers;

  private final List<Map<String, Integer>> stack; // Stack Representation
  private int currStackPointer;
  private final Stack<Integer> stackPointers;
  private final Stack<Integer> stackSizes;
  private int latestFuncStackSize;
  private int exclamationOffset;

  public void setLatestFuncStackSize(int latestFuncStackSize) {
    this.latestFuncStackSize = latestFuncStackSize;
  }

  public int getLatestFuncStackSize() {
    return latestFuncStackSize;
  }

  public MemoryManagement(ARM11ProgramRepresentation rep) {
    this.registers = new int[13];
    this.stack = new LinkedList<>();
    this.currStackPointer = 0;
    this.stackPointers = new Stack<>();
    this.rep = rep;
    this.currStackPointer = 0;
    this.stackSizes = new Stack<>();
    this.latestFuncStackSize = 0;
    this.exclamationOffset = 0;
  }

  public void setExclamationOffset(int exclamationOffset) {
    this.exclamationOffset = exclamationOffset;
  }

  public int getCurrentStackPointer() {
    return currStackPointer;
  }

  public void incrementCurrentStackPointer(int inc) {
    currStackPointer += inc;
  }

  public void decrementCurrentStackPointer(int dec){
    currStackPointer -= dec;
  }

  public void resetStackPointer(int size){
    stack.add(new HashMap<>());
    stackPointers.push(currStackPointer);
    stackSizes.push(size);
    currStackPointer = 0;
  }

  public void goBackToOldStackPointer(){
    stack.remove(stack.size()-1);
    stackSizes.pop();
    currStackPointer = stackPointers.pop();
  }

  public int getStackOffset(String ident) {
    int offset = 0;
    for(int i=stack.size()-1;i>=0;i--){
      if(stack.get(i).containsKey(ident)){
        offset += stack.get(i).get(ident) + exclamationOffset;
        return offset;
      }else{
        offset += stackSizes.get(i);
      }
    }
    return 0;
  }

  public int getExclamationOffset() {
    return exclamationOffset;
  }

  public void addToStack(String ident){
    stack.get(stack.size()-1).put(ident,currStackPointer);
  }

  public ARMRegister allocateFreeRegister() {
    int i = 4;
    while (registers[i] != 0 && i < 10) {
      i++;
    }
    if(registers[i] == 1){
      rep.addInstruction(new PUSHInstruction(ARMRegister.r10));
    }
    registers[i] = 1;
    return ARMRegister.values()[i];
  }

  public void freeRegister(ARMRegister register) {
    registers[register.ordinal()] = 0;
  }
}
