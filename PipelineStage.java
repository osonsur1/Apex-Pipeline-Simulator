public class PipelineStage {

    Instruction incomingInstruction;
    Instruction outgoingInstruction;
    Integer stall;

    public PipelineStage(Instruction incomingInstruction, Instruction outgoingInstruction, Integer stall) {
        this.incomingInstruction = incomingInstruction;
        this.outgoingInstruction = outgoingInstruction;
        this.stall = stall;
    }

    void flushStage(){
        Instruction nopInstruction = new Instruction();
        nopInstruction.setInstructionName("NOP");
        this.stall = 0;
        this.incomingInstruction = nopInstruction;
        this.outgoingInstruction = nopInstruction;
    }

    public Instruction getIncomingInstruction() {
        return incomingInstruction;
    }

    public void setIncomingInstruction(Instruction incomingInstruction) {
        this.incomingInstruction = incomingInstruction;
    }

    public Instruction getOutgoingInstruction() {
        return outgoingInstruction;
    }

    public void setOutgoingInstruction(Instruction outgoingInstruction) {
        this.outgoingInstruction = outgoingInstruction;
    }

    public Integer getStall() {
        return stall;
    }

    public void setStall(Integer stall) {
        this.stall = stall;
    }

}
