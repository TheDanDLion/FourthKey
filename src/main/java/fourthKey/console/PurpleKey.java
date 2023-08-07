package fourthKey.console;

import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;

public class PurpleKey extends ConsoleCommand {

    public static final String COMMAND_VALUE = "purpleKey";

    public PurpleKey() {
        this.requiresPlayer = true;
        this.followup.put("add", PurpleKeyAdd.class);
        this.followup.put("lose", PurpleKeyLose.class);
    }

    @Override
    protected void execute(String[] arg0, int arg1) {
        cmdDrawHelp();
    }

    private static void cmdDrawHelp() {
        DevConsole.couldNotParse();
        DevConsole.log("options are:");
        DevConsole.log("* add lose");
    }
}
