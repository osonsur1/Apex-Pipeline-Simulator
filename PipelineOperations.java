import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class PipelineOperations {
    static Integer currentCycleValue;
    static Map<Integer, Instruction> instructionMap = new LinkedHashMap<>();
    static Integer currentPC = 4000;
    static Integer lastInstrctionAddress;
    static PipelineStage fetchStage;
    static PipelineStage decodeStage;
    static PipelineStage intFUStage;
    static PipelineStage mulFUStage1;
    static PipelineStage mulFUStage2;
    static PipelineStage divFUStage1;
    static PipelineStage divFUStage2;
    static PipelineStage divFUStage3;
    static PipelineStage divFUStage4;
    static PipelineStage memStage;
    static IQ iq;
    static LSQ lsq;
    static ReorderBuffer rob;
    static Map<String, String> renameTable;
    static PhysicalRegisterFile phyRegFile;
    static RegisterFile archRegisterFile;
    static Integer programAddress = 4000;



    public static void main(String args[]){
        readCSVfile();
        initializeStageandElements();
        executeStages();
    }


    static void initializeStageandElements(){
        currentCycleValue = 1;
        Instruction nopInstruction = new Instruction();
        nopInstruction.setInstructionName("NOP");
        fetchStage = new PipelineStage(nopInstruction, nopInstruction, 0);
        decodeStage = new PipelineStage(nopInstruction, nopInstruction, 0);
        intFUStage = new PipelineStage(nopInstruction, nopInstruction, 0);
        mulFUStage1 = new PipelineStage(nopInstruction, nopInstruction, 0);
        mulFUStage2 = new PipelineStage(nopInstruction, nopInstruction, 0);
        divFUStage1= new PipelineStage(nopInstruction, nopInstruction, 0);
        divFUStage2 = new PipelineStage(nopInstruction, nopInstruction, 0);
        divFUStage3 = new PipelineStage(nopInstruction, nopInstruction, 0);
        divFUStage4 = new PipelineStage(nopInstruction, nopInstruction, 0);
        memStage = new PipelineStage(nopInstruction, nopInstruction, 0);
        iq = new IQ();
        lsq = new LSQ();
        rob = new ReorderBuffer();
        renameTable = new HashMap<String, String>();
        phyRegFile = new PhysicalRegisterFile();
        archRegisterFile = new RegisterFile();
    }

    static void executeStages(){
        while (programAddress <= lastInstrctionAddress
                && (fetchStage.getOutgoingInstruction().getInstructionName().equalsIgnoreCase("NOP"))
                && (decodeStage.getOutgoingInstruction().getInstructionName().equalsIgnoreCase("NOP"))
                && (intFUStage.getOutgoingInstruction().getInstructionName().equalsIgnoreCase("NOP"))
                && (mulFUStage1.getOutgoingInstruction().getInstructionName().equalsIgnoreCase("NOP"))
                && (mulFUStage2.getOutgoingInstruction().getInstructionName().equalsIgnoreCase("NOP"))
                && (divFUStage1.getOutgoingInstruction().getInstructionName().equalsIgnoreCase("NOP"))
                && (divFUStage2.getOutgoingInstruction().getInstructionName().equalsIgnoreCase("NOP"))
                && (divFUStage3.getOutgoingInstruction().getInstructionName().equalsIgnoreCase("NOP"))
                && (divFUStage4.getOutgoingInstruction().getInstructionName().equalsIgnoreCase("NOP"))
                && (memStage.getOutgoingInstruction().getInstructionName().equalsIgnoreCase("NOP"))
                && !rob.isROBEmpty() && !lsq.isLSQEmpty() && !iq.isIQEmpty()){
            commitReorderBuffer();
            executeMemStage();
            executeDiv4();
            executeDiv3();
            executeDiv2();
            executeDiv1();
            executeMul2();
            executeMul1();
            executeIntFu();
            executeDecode();
            executeFetch(programAddress);
            programAddress += 4;
            currentCycleValue++;
        }
    }

    static  Boolean executeDecode() {

        Instruction instruction = decodeStage.getIncomingInstruction();
        Map<String, PhysicalRegister> physicalRegisterMap = phyRegFile.physicalRegisterMap;
        if (decodeStage.stall == 1) {
            if (!instruction.getInstructionName().equalsIgnoreCase("NOP")) {

                if ((instruction.getOperationType().equalsIgnoreCase("STORE")
                        || instruction.getOperationType().equalsIgnoreCase("LOAD"))
                        && lsq.isLSQFull()) {
                    return false;
                }

                if (!(instruction.getOperationType().equalsIgnoreCase("STORE")
                        || instruction.getOperationType().equalsIgnoreCase("JUMP")
                        || instruction.getOperationType().equalsIgnoreCase("HALT")
                        || instruction.getOperationType().equalsIgnoreCase("BZ")
                        || instruction.getOperationType().equalsIgnoreCase("BNZ"))
                        && !checkPhysicalRegister()) {

                } else
                    return false;
            }
        }


        if (!instruction.getInstructionName().equalsIgnoreCase("NOP")) {
            String sourceReg1 = "";
            PhysicalRegister physicalRegister1 = null;
            String sourceReg2 = "";
            PhysicalRegister physicalRegister2 = null;
            Integer literal = null;
            String destReg = "";
            PhysicalRegister physicalRegisterDest = null;
            //PhysicalRegister robPhyDestRegName = null;
            Boolean decodeStageStall = false;

            String[] instructionString = instruction.getInstructionName().split(",");

            if(instruction.getOperationType().equalsIgnoreCase("BZ")
                    || (instruction.getOperationType().equalsIgnoreCase("BNZ"))){
                if(!iq.isIQFull() && !rob.isROBFull()) {
                    instruction.setLiteral(instructionString[3].replaceAll("#", ""));
                    Map<String, PhysicalRegister> temp1 = new LinkedHashMap<String, PhysicalRegister>();
                    temp1.putAll(physicalRegisterMap);
                    instruction.phyRegMapBranch = temp1;

                    Map<String, String> temp2 = new LinkedHashMap<String, String>();
                    temp2.putAll(renameTable);
                    instruction.renameTableMapBranch = temp2;


                } else {
                    decodeStageStall = true;
                }
            }

            else if(instruction.getOperationType().equalsIgnoreCase("JUMP")){
                if(!iq.isIQFull() && !rob.isROBFull()) {
                    instruction.setLiteral(instructionString[3].replaceAll("#", ""));
                    Map<String, PhysicalRegister> temp1 = new LinkedHashMap<String, PhysicalRegister>();
                    temp1.putAll(physicalRegisterMap);
                    instruction.phyRegMapBranch = temp1;

                    Map<String, String> temp2 = new LinkedHashMap<String, String>();
                    temp2.putAll(renameTable);
                    instruction.renameTableMapBranch = temp2;


                } else {
                    decodeStageStall = true;
                }
            }

            else if(instruction.getOperationType().equalsIgnoreCase("STORE")){
                if(!iq.isIQFull() && !rob.isROBFull() && !lsq.isLSQFull()){

                    if(renameTable.containsKey(instructionString[1])){
                        PhysicalRegister physicalRegisterTemp = physicalRegisterMap.get(renameTable.get(instructionString[1]));
                        physicalRegister1 = new PhysicalRegister();
                        physicalRegister1.setRegName(physicalRegisterTemp.getRegName());
                        physicalRegister1.setRegValid(physicalRegisterTemp.getRegValid());
                        sourceReg1 = physicalRegisterTemp.getRegName();

                        if(physicalRegisterTemp.getRegData() != null)
                            physicalRegister1.setRegData(physicalRegisterTemp.getRegData());

                        instruction.setOperand1(physicalRegister1);
                    }

                    if (renameTable.containsKey(instructionString[2])){
                        PhysicalRegister physicalRegisterTemp = physicalRegisterMap.get(renameTable.get(instructionString[2]));
                        physicalRegister2 = new PhysicalRegister();
                        physicalRegister2.setRegName(physicalRegisterTemp.getRegName());
                        physicalRegister2.setRegValid(physicalRegisterTemp.getRegValid());
                        sourceReg2 = physicalRegisterTemp.getRegName();

                        if(physicalRegisterTemp.getRegData() != null)
                            physicalRegister2.setRegData(physicalRegisterTemp.getRegData());

                        instruction.setOperand2(physicalRegister2);
                    }

                    instruction.setLiteral(instructionString[3].replaceAll("#", ""));
                    instruction.setInstructionName(instruction.getInstructionNumber() + instruction.getOperationType() + ","
                            + sourceReg1 + "," + sourceReg2 + "," + instructionString[3]);
                }
                else{
                    decodeStageStall = true;
                }
            }
        }
            else if (instruction.getOperationType().equalsIgnoreCase("HALT")){
                decodeStage.setIncomingInstruction(instruction);
                decodeStage.setOutgoingInstruction(instruction);
                programAddress += 4;
                return true;
            }
        }
    }

    static  void executeFetch(Integer pcAddress){
        if (instructionMap.containsKey(pcAddress) && (decodeStage.stall == 0)){
            Instruction fetchInstruction = new Instruction();
            Instruction instruction = instructionMap.get(pcAddress);
            fetchInstruction.setInstructionName(instruction.getInstructionName());
            fetchInstruction.setPc(instruction.getPc());
            fetchInstruction.setInstructionName(instruction.getInstructionName());
            fetchInstruction.setCycleNumber(currentCycleValue);
            fetchInstruction.setDependentInstruction(instruction.getDependentInstruction());
            fetchInstruction.setOperationType(instruction.getOperationType());
            fetchInstruction.setInstructionNumber(instruction.getInstructionNumber());
            fetchStage.setIncomingInstruction(fetchInstruction);
            fetchStage.setOutgoingInstruction(fetchInstruction);
            decodeStage.setIncomingInstruction(fetchInstruction);

        }
    }


    static void readCSVfile(){
        String csvFile = "C:/Users/omkar/Desktop/test.txt";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            int i = 0;
            String dependentInstrcutionAddress = "";
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                Instruction instruction = new Instruction();

                // use comma as separator
                String[] instructionString = line.split(cvsSplitBy);
                instruction.setInstructionName(instructionString.toString());
                instruction.setOperationType(instructionString[0]);
                instruction.setInstructionNumber("(I" + i + ")");
                instruction.setPc(currentPC);
                lastInstrctionAddress = currentPC;

                if(instruction.getOperationType().equalsIgnoreCase("Add") ||
                        instruction.getOperationType().equalsIgnoreCase("Sub") ||
                        instruction.getOperationType().equalsIgnoreCase("Mul") ||
                        instruction.getOperationType().equalsIgnoreCase("Div")){
                    dependentInstrcutionAddress = instruction.getInstructionNumber();
                }
                else if(instruction.getOperationType().equalsIgnoreCase("BZ") ||
                        instruction.getOperationType().equalsIgnoreCase("BNZ")){
                    instruction.setDependentInstruction(dependentInstrcutionAddress);
                }
                else if(instruction.getOperationType().equalsIgnoreCase("JAL")) {
                    instruction.nextInstructionAddress = currentPC + 4;
                }

                instructionMap.put(currentPC, instruction);
                System.out.println(instructionString[0]);
                i++;

                currentPC += 4;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static Boolean checkPhysicalRegister(){
        for (Map.Entry<String, PhysicalRegister> keyValuePair : phyRegFile.physicalRegisterMap.entrySet()){
            if(keyValuePair.getValue().getPhyRegAllocated() == 0){
                return true;            }
        }
        return false;
    }

    static PhysicalRegister fetchPhysicalRegister(){
        for (Map.Entry<String, PhysicalRegister> keyValuePair : phyRegFile.physicalRegisterMap.entrySet()){
            if(keyValuePair.getValue().getPhyRegAllocated() == 0){
                return keyValuePair.getValue();
            }
        }
        return null;
    }

}
