package hookah_sql.commands;


import hookah_sql.Operation;
import hookah_sql.commands.mix_commands.MixCommand;
import hookah_sql.commands.tabacco_commands.*;

import java.util.HashMap;
import java.util.Map;


public class CommandExecutor {
    private static final Map<Operation, Command> allKnownCommandsMap = new HashMap<>() {{
        put(Operation.EXIT, new ExitCommand());
        put(Operation.MIX, new MixCommand());
        put(Operation.SHOW_TABACCO, new ShowCommand());
        put(Operation.DETAIL_TABACCO, new DetailCommand());
        put(Operation.ADD_TABACCO, new AddCommand());
        put(Operation.UPDATE_TABACCO, new UpdateCommand());
        put(Operation.DELETE_TABACCO, new DeleteCommand());
        put(Operation.SHOW_MIX, new hookah_sql.commands.mix_commands.ShowCommand());
        put(Operation.DETAIL_MIX, new hookah_sql.commands.mix_commands.DetailCommand());
        put(Operation.ADD_MIX, new hookah_sql.commands.mix_commands.AddCommand());
        put(Operation.UPDATE_MIX, new hookah_sql.commands.mix_commands.UpdateCommand());
        put(Operation.DELETE_MIX, new hookah_sql.commands.mix_commands.DeleteCommand());
        put(Operation.LOGIN, new LoginCommand());
    }};

    private CommandExecutor() {}

    public static void execute(Operation operation) {
        allKnownCommandsMap.get(operation).execute();
    }
}
