package hookah_sql;

import hookah_sql.commands.CommandExecutor;
import hookah_sql.hibernate.HibernateUtils;

public class Main {
    public static void main(String[] args) {
        Operation operation = null;
        CommandExecutor.execute(Operation.LOGIN);
        do {
            try {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            } catch (Exception e) {
                e.printStackTrace();
                ConsoleHelper.writeMessage("Good bye! I will miss you :(");
                operation = Operation.EXIT;
            }
        }
        while (operation != Operation.EXIT);
        HibernateUtils.shutdown();
    }
}
