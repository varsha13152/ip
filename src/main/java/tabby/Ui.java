package tabby;

public class Ui {
    private static final String EMOTICON = "(^-.-^)";
    private static final String SEPARATOR = "------------------------------------------------------------";

    public  void display(String message) {
        System.out.println(String.format("%s\n %s: %s\n %s", SEPARATOR,EMOTICON, message,SEPARATOR));
    }

    public  void error(String message) {
        System.err.println(String.format("%s\n ⚠ Error!: \n %s", SEPARATOR, message,SEPARATOR));
    }
}

