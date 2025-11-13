import java.util.Stack;

interface Command {
    void execute();
    void undo();
}

class Light {
    public void turnOn() { System.out.println("Light is ON"); }
    public void turnOff() { System.out.println("Light is OFF"); }
}

class TurnOnLightCommand implements Command {
    private Light light;
    public TurnOnLightCommand(Light light) { this.light = light; }
    @Override
    public void execute() { light.turnOn(); }
    @Override
    public void undo() { light.turnOff(); }
}

class TurnOffLightCommand implements Command {
    private Light light;
    public TurnOffLightCommand(Light light) { this.light = light; }
    @Override
    public void execute() { light.turnOff(); }
    @Override
    public void undo() { light.turnOn(); }
}

class RemoteControl {
    private Command slot;
    private Stack<Command> history = new Stack<>();

    public void setCommand(Command command) { this.slot = command; }

    public void pressButton() {
        slot.execute();
        history.push(slot);
    }

    public void pressUndo() {
        if (!history.isEmpty()) {
            Command lastCommand = history.pop();
            System.out.print("UNDO: ");
            lastCommand.undo();
        } else {
            System.out.println("Nothing to undo.");
        }
    }
}

public class CommandTest {
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();
        Light livingRoomLight = new Light();

        Command onCommand = new TurnOnLightCommand(livingRoomLight);
        remote.setCommand(onCommand);
        remote.pressButton();

        Command offCommand = new TurnOffLightCommand(livingRoomLight);
        remote.setCommand(offCommand);
        remote.pressButton();

        remote.pressUndo();
        remote.pressUndo();
    }
}
