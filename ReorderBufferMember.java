public class ReorderBufferMember {

    Integer cycleNumber;
    Integer statusBit;
    Integer rbAllocated;
    String archDestReg;
    Instruction instruction;
    PhysicalRegister source1;
    PhysicalRegister source2;
    PhysicalRegister destination;
    String operationType;

    public Integer getCycleNumber() {
        return cycleNumber;
    }

    public void setCycleNumber(Integer cycleNumber) {
        this.cycleNumber = cycleNumber;
    }

    public Integer getStatusBit() {
        return statusBit;
    }

    public void setStatusBit(Integer statusBit) {
        this.statusBit = statusBit;
    }

    public Integer getRbAllocated() {
        return rbAllocated;
    }

    public void setRbAllocated(Integer rbAllocated) {
        this.rbAllocated = rbAllocated;
    }

    public String getArchDestReg() {
        return archDestReg;
    }

    public void setArchDestReg(String archDestReg) {
        this.archDestReg = archDestReg;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public PhysicalRegister getSource1() {
        return source1;
    }

    public void setSource1(PhysicalRegister source1) {
        this.source1 = source1;
    }

    public PhysicalRegister getSource2() {
        return source2;
    }

    public void setSource2(PhysicalRegister source2) {
        this.source2 = source2;
    }

    public PhysicalRegister getDestination() {
        return destination;
    }

    public void setDestination(PhysicalRegister destination) {
        this.destination = destination;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
