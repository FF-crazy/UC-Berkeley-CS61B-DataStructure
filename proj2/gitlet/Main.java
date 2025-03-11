package gitlet;

import static gitlet.Utils.*;
import static gitlet.Utils.join;

import java.io.File;
import java.io.IOException;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author FF_Crazy, ChatGPT.
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("Please enter a command.");
                System.exit(0);
            }
            Repository repository = new Repository();
            String firstArg = args[0];
            switch (firstArg) {
                case "init":
                    repository.init();
                    break;
                case "add":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(2, args);
                    repository.add(args[1]);
                    break;
                case "commit":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(2, args);
                    repository.commit(args[1]);
                    break;
                case "rm":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(2, args);
                    repository.rm(args[1]);
                    break;
                case "log":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(1, args);
                    repository.log();
                    break;
                case "global-log":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(1, args);
                    repository.globalLog();
                    break;
                case "find":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(2, args);
                    repository.find(args[1]);
                    break;
                case "status":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(1, args);
                    repository.status();
                    break;
                case "checkout":
                    checkInitExist(Utils.checkInitExist());
                    if (args.length == 2) {
                        repository.checkoutBranch(args[1]);
                    } else if (args.length == 3) {
                        if (!args[1].equals("--")) {
                            checkLength(-1, args);
                        }
                        repository.checkoutFile(args[2]);
                    } else if (args.length == 4){
                        if (!args[2].equals("--")) {
                            checkLength(-1, args);
                        }
                        repository.checkoutCommit(args[1], args[3]);
                    } else {
                        checkLength(-1, args);
                    }
                    break;
                case "branch":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(2, args);
                    repository.branch(args[1]);
                    break;
                case "rm-branch":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(2, args);
                    repository.rmBranch(args[1]);
                    break;
                case "reset":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(2, args);

                    break;
                case "merge":
                    checkInitExist(Utils.checkInitExist());
                    checkLength(2, args);

                    break;
                //TODO:
                case "test":
                    repository.init();
                    repository.add("123");
                    repository.commit("123");
                    break;
                default:
                    System.out.println("No command with that name exists.");
                    System.exit(0);
            }
        } catch (IOException e) {

        }
    }

    public static void checkLength(int i, String[] args) {
        if (i != args.length) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }

    public static void checkInitExist(boolean bool) {
        if (!bool) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }

}
