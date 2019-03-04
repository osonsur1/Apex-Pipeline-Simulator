public class IQ {

    IQMember issueQueue[];

    IQ(){
        issueQueue = new IQMember[16];

        for(int i = 0; i < 16; i++){
            IQMember iqMem = new IQMember();
            iqMem.setIqAllocated(0);
            issueQueue[i] = iqMem;
        }
    }

    Boolean isIQEmpty(){
        Boolean empty = true;

        for(int i = 0; i < 16; i++){
            if(issueQueue[i].getIqAllocated() != 0) {
                empty = false;
                break;
            }
        }

        return empty;
    }

    Boolean isIQFull(){
        Boolean full = true;

        for(int i = 0; i < 16; i++){
            if(issueQueue[i].getIqAllocated() == 0) {
                full = false;
                break;
            }
        }

        return full;
    }

}
