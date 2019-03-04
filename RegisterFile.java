import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RegisterFile {

    //ArchitectureRegister archRegArray[];

    Map<String, ArchitectureRegister> architectureRegisterMap;

    public RegisterFile() {
        architectureRegisterMap = new LinkedHashMap<String, ArchitectureRegister>();
        for (int i = 0; i < 32; i++) {
            ArchitectureRegister architectureRegister  = new ArchitectureRegister();
            architectureRegister.setRegName("R" + i);
            architectureRegister.setRegValid(1);
            architectureRegister.setRegData(0);
            architectureRegisterMap.put(architectureRegister.getRegName(), architectureRegister);
        }
    }


}

