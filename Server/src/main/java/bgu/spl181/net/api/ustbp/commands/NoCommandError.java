package bgu.spl181.net.api.ustbp.commands;

public class NoCommandError extends ERRORCommand {
    public NoCommandError() {
        super("No such command");
        name = "No such command";
    }
}
