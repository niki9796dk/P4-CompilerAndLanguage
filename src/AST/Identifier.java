package AST;

public class Identifier {
    private String id;
    private boolean isThis;

    public Identifier(String id, boolean isThis) {
        this.id = id;
        this.isThis = isThis;
    }

    public String getId() {
        return id;
    }

    public boolean isThis() {
        return isThis;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setThis(boolean aThis) {
        isThis = aThis;
    }

    @Override
    public String toString() {
        return ((isThis) ? "this." : "") + id;
    }
}
