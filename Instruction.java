import java.util.Map;

public class Instruction {

    Integer cycleNumber;
    Integer pc;
    String instructionName;
    PhysicalRegister operand1;
    PhysicalRegister operand2;
    PhysicalRegister destOperand;
    String operationType;
    String instructionNumber;
    String dependentInstruction;
    Integer nextInstructionAddress;
    String literal;
    Map<String, PhysicalRegister> phyRegMapBranch;
    Map<String, String> renameTableMapBranch;

    public Integer getCycleNumber() {
        return cycleNumber;
    }

    public void setCycleNumber(Integer cycleNumber) {
        this.cycleNumber = cycleNumber;
    }

    public Integer getPc() {
        return pc;
    }

    public void setPc(Integer pc) {
        this.pc = pc;
    }

    public String getInstructionName() {
        return instructionName;
    }

    public void setInstructionName(String instructionName) {
        this.instructionName = instructionName;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getInstructionNumber() {
        return instructionNumber;
    }

    public void setInstructionNumber(String instructionNumber) {
        this.instructionNumber = instructionNumber;
    }

    public String getDependentInstruction() {
        return dependentInstruction;
    }

    public void setDependentInstruction(String dependentInstruction) {
        this.dependentInstruction = dependentInstruction;
    }

    public PhysicalRegister getOperand1() {
        return operand1;
    }

    public void setOperand1(PhysicalRegister operand1) {
        this.operand1 = operand1;
    }

    public PhysicalRegister getOperand2() {
        return operand2;
    }

    public void setOperand2(PhysicalRegister operand2) {
        this.operand2 = operand2;
    }

    public PhysicalRegister getDestOperand() {
        return destOperand;
    }

    public void setDestOperand(PhysicalRegister destOperand) {
        this.destOperand = destOperand;
    }

    public Integer getNextInstructionAddress() {
        return nextInstructionAddress;
    }

    public void setNextInstructionAddress(Integer nextInstructionAddress) {
        this.nextInstructionAddress = nextInstructionAddress;
    }

    public String getLiteral() {

        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }
}
