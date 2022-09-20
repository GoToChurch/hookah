package hookah_sql.tabacco;

public enum TabaccoEnum {
    ALL,
    BLACKBURN,
    DARKSIDE,
    MUSTHAVE,
    SATYR,
    TANGIERS,
    ELEMENT,
    DUFT;

    public static TabaccoEnum getTobaccosByOrdinal(Integer i) {
        switch (i) {
            case 0 -> {
                return ALL;
            }
            case 1 -> {
                return BLACKBURN;
            }
            case 2 -> {
                return DARKSIDE;
            }
            case 3 -> {
                return MUSTHAVE;
            }
            case 4 -> {
                return SATYR;
            }
            case 5 -> {
                return TANGIERS;
            }
            case 6 -> {
                return ELEMENT;
            }
            case 7 -> {
                return DUFT;
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }
}
