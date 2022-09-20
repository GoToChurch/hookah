package hookah_sql;

import hookah_sql.tabacco.TabaccoEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    private static final Logger logger = LoggerFactory.getLogger(ConsoleHelper.class);

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static Operation askOperation() {
        try {
            writeMessage("""
                    Что вы хотите сделать?
                    1 - Получить микс
                    2 - Получить список табаков
                    3 - Получить информацию о вкусе
                    4 - Добавить новый вкус
                    5 - Обновить существующий вкус
                    6 - Удалить существующий вкус
                    7 - Получить список миксов
                    8 - Получить определенный микс
                    9 - Добавить новый микс
                    10 - Обновить существующий микс
                    11 - Удалить существующий микс
                    12 - Выйти""");
            int operationCode = Integer.parseInt(readString());

            return Operation.getAllowableOperationByOrdinal(operationCode);
        } catch (Exception e) {
            writeMessage("Что-то пошло не так. Попробуйте снова.");
            logger.error("User entered wrong operation code; Exception: " + e.getMessage());
            askOperation();
        }
        return null;
    }

    public static TabaccoEnum askTabacco() {
        try {
            writeMessage("""
                    Какой табак вы хотите посмотреть?
                    0 - Все табаки
                    1 - Black Burn
                    2 - Darkside
                    3 - Must Have
                    4 - Satyr
                    5 - Tangiers
                    6 - Element
                    7 - Duft""");
            int tobaccoCode = Integer.parseInt(readString());

            return TabaccoEnum.getTobaccosByOrdinal(tobaccoCode);
        } catch (Exception e) {
            writeMessage("Что-то пошло не так. Попробуйте снова.");
            logger.error("User entered wrong tabacco code; Exception: " + e.getMessage());
            askOperation();
        }
        return null;
    }

    public static TabaccoEnum askTabaccoDetail() {
        try {
            writeMessage("""
                    О вкусе какого табака вы хотите узнать?
                    1 - Black Burn
                    2 - Darkside
                    3 - Must Have
                    4 - Satyr
                    5 - Tangiers
                    6 - Element
                    7 - Duft""");
            int tobaccoCode = Integer.parseInt(readString());

            return TabaccoEnum.getTobaccosByOrdinal(tobaccoCode);
        } catch (Exception e) {
            writeMessage("Что-то пошло не так. Попробуйте снова.");
            logger.error("User entered wrong tabacco code; Exception: " + e.getMessage());
            askOperation();
        }
        return null;
    }
    public static TabaccoEnum askTabaccoDelete() {
        try {
            writeMessage("""
                    Вкус какого табака вы хотите удалить?
                    1 - Black Burn
                    2 - Darkside
                    3 - Must Have
                    4 - Satyr
                    5 - Tangiers
                    6 - Element
                    7 - Duft""");
            int tobaccoCode = Integer.parseInt(readString());

            return TabaccoEnum.getTobaccosByOrdinal(tobaccoCode);
        } catch (Exception e) {
            writeMessage("Что-то пошло не так. Попробуйте снова.");
            logger.error("User entered wrong tabacco code; Exception: " + e.getMessage());
            askOperation();
        }
        return null;
    }
    public static int askTabaccoCount() {
        try {
            writeMessage("Сколько табаков будет в миксе?");
            int tabaccoCount = Integer.parseInt(readString());
            if (tabaccoCount < 1) {
                writeMessage("Количество табаков должно быть больше 0. Повторите попытку");
                askTabaccoCount();
            }
            return tabaccoCount;
        } catch (Exception e) {
            logger.error("Something wrong with asking tabacco count; Exception: " + e.getMessage());
        }

        return 1;
    }

    public static int askHardness() {
        try {
            writeMessage("Какую крепость желаете? Введите 0, если все равно");
            return Integer.parseInt(readString());
        } catch (Exception e) {
            logger.error("Something wrong with asking hardness; Exception: " + e.getMessage());
        }

        return 0;
    }

    public static String[] askTabaccosInMix() {
        try {
            writeMessage("Какие табаки желаете? Введите пустую строку, если все равно");
            String tabaccos = readString();
            if (!tabaccos.equals("")) {
                return tabaccos.split(", ");
            }
            else {
                return new String[0];
            }
        } catch (Exception e) {
            logger.error("Something wrong with asking tabaccos in mix; Exception: " + e.getMessage());
        }

        return null;
    }

    public static String[] askTaste() {
        try {
            writeMessage("Каким должен быть микс? Введите пустую строку, если все равно");
            String taste = readString();
            if (!taste.equals("")) {
                return taste.split(", ");
            }
            else {
                return new String[0];
            }
        } catch (Exception e) {
            logger.error("Something wrong with asking tastes; Exception: " + e.getMessage());
        }

        return null;
    }

    public static String askFlavor() {
        writeMessage("Введите вкус:");
        return readString();
    }

    public static String askMix() {
        writeMessage("Введите названия табаков в миксе:");
        return readString();
    }
    public static String readString() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            writeMessage("Что-то пошло не так. Попробуйте снова.");
            logger.error("Problem with reading user input string; Exception: " + e.getMessage());
        }
        return null;
    }

}
