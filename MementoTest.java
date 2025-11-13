class EditorMemento {
    private final String state;

    public EditorMemento(String state) {
        this.state = state;
    }

    public String getSavedState() {
        return state;
    }
}

class TextEditor {
    private String text = "";

    public void type(String words) {
        text += words;
        System.out.println("Current Text: " + text);
    }

    public EditorMemento save() {
        return new EditorMemento(text);
    }

    public void restore(EditorMemento memento) {
        this.text = memento.getSavedState();
    }

    public String getText() {
        return text;
    }
}

class History {
    private EditorMemento lastMemento;

    public void save(EditorMemento memento) {
        this.lastMemento = memento;
    }

    public EditorMemento undo() {
        return lastMemento;
    }
}

public class MementoTest {
    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        History history = new History();

        editor.type("Hello ");
        history.save(editor.save());

        editor.type("World! ");
        history.save(editor.save());

        editor.type("This is new text.");

        System.out.println("\n--- UNDO ---");
        editor.restore(history.undo());
        System.out.println("Text after undo: " + editor.getText());
    }
}
