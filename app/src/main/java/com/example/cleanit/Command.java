package com.example.cleanit;

public class Command {
    private int idCommand;
    private int total;

    public Command(int idCommand){
        this.idCommand= idCommand;
    }
    public int getIdCommand() {
        return idCommand;
    }

    public void setIdCommand(int idCommand) {
        this.idCommand = idCommand;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
