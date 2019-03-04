import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PhysicalRegisterFile {

    //PhysicalRegister phyRegArray[];

    Map<String, PhysicalRegister> physicalRegisterMap;

    public PhysicalRegisterFile() {
        physicalRegisterMap = new LinkedHashMap<String, PhysicalRegister>();
        for (int i = 0; i < 32; i++) {
            PhysicalRegister physicalRegister = new PhysicalRegister();
            physicalRegister.setRegName("P" + i);
            physicalRegister.setRegValid(1);
            physicalRegister.setRegData(0);
            physicalRegister.setPhyRegAllocated(0);
            physicalRegister.setArchRegName("");
            physicalRegisterMap.put(physicalRegister.getRegName(), physicalRegister);
        }
    }
}
