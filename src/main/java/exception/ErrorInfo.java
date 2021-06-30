package exception;

public enum ErrorInfo {

    CAPACITY_LESS_THAN_ZERO("capacity less than 0"),
    NULL_ARGUMENT("NULL ARGUMENT");

    private String key;

    ErrorInfo(String key){
        this.key=key;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
