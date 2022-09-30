package hookah_sql.utils;

import hookah_sql.config.Context;
import hookah_sql.tabacco.*;

public class Utils {

    public static Tabacco convertStringInTobacco(String s) {
        switch (s) {
            case "Black Burn" -> {
                return Context.getInstance().getContext().getBean("Blackburn", Blackburn.class);
            }
            case "Darkside" -> {
                return Context.getInstance().getContext().getBean("Darkside", Darkside.class);
            }
            case "Must Have" -> {
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
                return Context.getInstance().getContext().getBean("Duft", Element.class);
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }

    public static TabaccoEnum getTobaccoEnumByName(String name) {
        switch (name.toLowerCase().replace(" ", "")) {
            case "all" -> {
                return TabaccoEnum.ALL;
            }
            case "blackburn" -> {
                return TabaccoEnum.BLACKBURN;
            }
            case "darkside" -> {
                return TabaccoEnum.DARKSIDE;
            }
            case "musthave" -> {
                return TabaccoEnum.MUSTHAVE;
            }
            case "satyr" -> {
                return TabaccoEnum.SATYR;
            }
            case "tangiers" -> {
                return TabaccoEnum.TANGIERS;
            }
            case "element" -> {
                return TabaccoEnum.ELEMENT;
            }
            case "duft" -> {
                return TabaccoEnum.DUFT;
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }

    public static Tabacco castTabaccoInSpecificClass(String clazz) {
        Tabacco tabacco = null;

        switch (clazz.toLowerCase().replace(" ", "")) {
            case "blackburn" -> {
                tabacco = new Blackburn();
            }
            case "darkside" -> {
                tabacco = new Darkside();
            }
            case "musthave" -> {
                tabacco = new Musthave();
            }
            case "satyr" -> {
                tabacco = new Satyr();
            }
            case "tangiers" -> {
                tabacco = new Tangiers();
            }
            case "element" -> {
                tabacco = new Element();
            }
            case "duft" -> {
                tabacco = new Duft();
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }

        return tabacco;
    }
}
