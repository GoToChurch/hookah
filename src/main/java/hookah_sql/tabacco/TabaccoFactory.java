package hookah_sql.tabacco;

public class TabaccoFactory {
    public static Tabacco tabaccoFactory(TabaccoEnum tabaccoEnum) {
        switch (tabaccoEnum) {
            case TANGIERS -> {
                return new Tangiers();
            }
            case ELEMENT -> {
                return new Element();
            }
            case DARKSIDE -> {
                return new Darkside();
            }
            case SATYR -> {
                return new Satyr();
            }
            case MUSTHAVE -> {
                return new Musthave();
            }
            case BLACKBURN -> {
                return new Blackburn();
                //return Context.getInstance().getContext().getBean("BlackBurn", BlackBurn.class);
            }
            case DUFT -> {
                return new Duft();
            }
            default -> {
                return null;
            }
        }
    }
}
