package hookah_sql.utils;

import hookah_sql.config.Context;
import hookah_sql.tabacco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Tabacco convertStringInTobacco(String s) {
        logger.info("Converting string {} in tabacco entity", s);

        switch (capitalize(s)) {
            case "Blackburn" -> {
                return Context.getInstance().getContext().getBean("Blackburn", Blackburn.class);
            }
            case "Darkside" -> {
                return Context.getInstance().getContext().getBean("Darkside", Darkside.class);
            }
            case "MustHave" -> {
                return Context.getInstance().getContext().getBean("Musthave", Musthave.class);
            }
            case "Satyr" -> {
                return Context.getInstance().getContext().getBean("Satyr", Satyr.class);
            }
            case "Tangiers" -> {
                return Context.getInstance().getContext().getBean("Tangiers", Tangiers.class);
            }
            case "Element" -> {
                return Context.getInstance().getContext().getBean("Element", Element.class);
            }
            case "Duft" -> {
                return Context.getInstance().getContext().getBean("Duft", Duft.class);
            }
            default -> {
                logger.error("Couldn't convert string {} in tabacco entity", s);

                return null;
            }
        }
    }

    public static String getStringByTabaccoEnum(TabaccoEnum tabaccoEnum) {
        logger.info("Getting string from tabaccoEnum: {}", tabaccoEnum);

        switch (tabaccoEnum) {
            case BLACKBURN -> {
                return "Blackburn";
            }
            case DARKSIDE -> {
                return "Darkside";
            }
            case DUFT -> {
                return "Duft";
            }
            case ELEMENT -> {
                return "Element";
            }
            case MUSTHAVE -> {
                return "Musthave";
            }
            case SATYR -> {
                return "Satyr";
            }
            case TANGIERS -> {
                return "Tangiers";
            }
            default -> {
                logger.error("There is no {} tabacco in data base", tabaccoEnum);

                return null;
            }
        }
    }

    public static String capitalize(String s) {
        String result = "";
        if (!s.equals("")) {
            String lowerString = s.toLowerCase();
            result = lowerString.replaceFirst(String.valueOf(lowerString.charAt(0)), String.valueOf(lowerString.charAt(0)).toUpperCase());
        }

        return result;
    }

    public static List<String> getAllTastes() {
        return new ArrayList<>() {{
            add("Алкогольный");
            add("Десертный");
            add("Жареный");
            add("Кислый");
            add("Мясной");
            add("Овощной");
            add("Ореховый");
            add("Пряный");
            add("Свежий");
            add("Сладкий");
            add("Сливочный");
            add("Табачный");
            add("Терпкий");
            add("Травянистый");
            add("Тропический");
            add("Фруктовый");
            add("Цветочный");
            add("Цитрусовый");
            add("Чайный");
            add("Экзотический");
            add("Ягодный");
        }};
    }

    public static List<String> getAllTabaccos() {
        return new ArrayList<>() {{
            add("Blackburn");
            add("Darkside");
            add("Duft");
            add("Element");
            add("Musthave");
            add("Satyr");
            add("Tangiers");
        }};
    }
}
