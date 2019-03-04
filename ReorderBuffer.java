public class ReorderBuffer {

    Integer head, tail, maxSize;

    ReorderBufferMember reorderBuffer[];

    ReorderBuffer(){
        reorderBuffer = new ReorderBufferMember[32];
        head = 0;
        tail = 0;
        maxSize = 32;

        for(int i = 0; i < maxSize; i++){
            ReorderBufferMember robMem = new ReorderBufferMember();
            robMem.setRbAllocated(0);
            robMem.setStatusBit(1);
            reorderBuffer[i] = robMem;
        }
    }

    Boolean isROBEmpty(){
        Boolean empty = true;

        for(int i = 0; i < 32; i++){
            if(reorderBuffer[i].getRbAllocated() != 0) {
                empty = false;
                break;
            }
        }

        return empty;
    }


    Boolean isROBFull(){
        Boolean full = true;

        for(int i = 0; i < 32; i++){
            if(reorderBuffer[i].getRbAllocated() == 0) {
                full = false;
                break;
            }
        }

        return full;
    }

    void addROBMember(String archRegName, PhysicalRegister register, Instruction instruction){
        ReorderBufferMember robMember = new ReorderBufferMember();

        robMember.setInstruction(instruction);
        robMember.setStatusBit(0);
        robMember.setRbAllocated(1);
        robMember.setOperationType(instruction.getOperationType());

        if(archRegName != null && !archRegName.equalsIgnoreCase("")){
            robMember.setArchDestReg(archRegName);
        }

        if (register != null){
            robMember.setDestination(register);
        }

        reorderBuffer[tail] = robMember;
        tail = (tail + 1) % maxSize;

    }

    ReorderBufferMember removeROBMember(){
        ReorderBufferMember robMember = reorderBuffer[head];
        robMember.setRbAllocated(0);
        robMember.setStatusBit(1);
        head = (head + 1) % maxSize;

        return robMember;

    }
}
