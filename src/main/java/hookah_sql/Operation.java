package hookah_sql;

public enum Operation {
    LOGIN,
    MIX,
    SHOW_TABACCO,
    DETAIL_TABACCO,
    ADD_TABACCO,
    UPDATE_TABACCO,
    DELETE_TABACCO,
    SHOW_MIX,
    DETAIL_MIX,
    ADD_MIX,
    UPDATE_MIX,
    DELETE_MIX,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        switch (i) {
            case 1 -> {
                return MIX;
            }
            case 2 -> {
                return SHOW_TABACCO;
            }
            case 3 -> {
                return DETAIL_TABACCO;
            }
            case 4 -> {
                return ADD_TABACCO;
            }
            case 5 -> {
                return UPDATE_TABACCO;
            }
            case 6 -> {
                return DELETE_TABACCO;
            }
            case 7 -> {
                return SHOW_MIX;
            }
            case 8 -> {
                return DETAIL_MIX;
            }
            case 9 -> {
                return ADD_MIX;
            }
            case 10 -> {
                return UPDATE_MIX;
            }
            case 11 -> {
                return DELETE_MIX;
            }
            case 12 -> {
                return EXIT;
            }
            default -> {
                throw new IllegalArgumentException();
            }
        }
    }
}
