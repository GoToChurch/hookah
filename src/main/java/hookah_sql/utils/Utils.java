package hookah_sql.utils;

import hookah_sql.config.Context;
import hookah_sql.tabacco.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static Tabacco convertStringInTobacco(String s) {
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
                throw new IllegalArgumentException();
            }
        }
    }

    public static String getStringByTabaccoEnum(TabaccoEnum tabaccoEnum) {
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
                throw new IllegalArgumentException();
            }
        }
    }

    public static String capitalize(String s) {
        String result = "";

        if (!s.equals("")) {
            result = s.toLowerCase().replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(s.charAt(0)).toUpperCase());
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
