package model;

public class Diario {
    private int id;
    private boolean status;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public Diario (){}
    public Diario(int id, boolean status) {
        this.id = id;
        this.status = status;
    }
    

}
