package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author FF_Crazy, ChatGPT.
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                checkInitExist();
                checkLength(2, args);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                checkInitExist();
                checkLength(2, args);

                break;
            case "rm":
                checkInitExist();
                checkLength(2, args);

                break;
            case "log":
                checkInitExist();
                checkLength(1, args);
                break;
            case "global-log":
                checkInitExist();
                checkLength(1, args);
                break;
            case "find":
                checkInitExist();
                checkLength(2, args);

                break;
            case "status":
                checkInitExist();
                checkLength(1, args);

                break;
            case "checkout":

                break;
            case "branch":
                checkInitExist();
                checkLength(2, args);

                break;
            case "rm-branch":
                checkInitExist();
                checkLength(2, args);

                break;
            case "reset":
                checkInitExist();
                checkLength(2, args);

                break;
            case "merge":
                checkInitExist();
                checkLength(2, args);

                break;
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
        }
    }

    public static void checkLength(int i, String[] args) {
        if (i != args.length) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }
    public static void checkInitExist() {
        //TODO:
        if () {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }
}
