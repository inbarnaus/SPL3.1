package bgu.spl181.net.api.ustbp.commands;

public abstract class ERRORCommand extends ServerCommand {
    private String message;

    public ERRORCommand(String message) {
        this.name="ERROR";
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ERROR {" +
                "message='" + message + '\'' +
                '}';
    }
}
