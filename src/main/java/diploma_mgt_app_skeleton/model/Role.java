package diploma_mgt_app_skeleton.model;
public enum Role {
	STUDENT("STUDENT"),
    PROFESSOR("PROFESSOR");

    private final String value;

    private Role(String value) {
    	this.value = value;
    }

    public String getValue() {
        return value;
    }
}
