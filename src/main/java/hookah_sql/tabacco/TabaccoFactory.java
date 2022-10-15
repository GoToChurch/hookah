package hookah_sql.tabacco;

import hookah_sql.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TabaccoFactory {
    private static final Logger logger = LoggerFactory.getLogger(TabaccoFactory.class);

    public static Tabacco tabaccoFactory(TabaccoEnum tabaccoEnum) {
        logger.info("Getting tabacco entity from tabaccoEnum: {}", tabaccoEnum);

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
            }
            case DUFT -> {
                return new Duft();
            }
            default -> {
                logger.error("There is no {} tabacco in data base", tabaccoEnum);

                return null;
            }
        }
    }
}
