package Model;

public class BaseModel {
    private static final String ADMIN = "admin";
    private long createAt;
    private String createBy;
    private long modifyAt;
    private String modifyBy;

    public BaseModel(){
        createAt = System.currentTimeMillis();
        modifyAt = System.currentTimeMillis();
        createBy = ADMIN;
        createBy = ADMIN;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(long modifyAt) {
        this.modifyAt = modifyAt;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
}
