package hookah_sql.mix;

import hookah_sql.ConsoleHelper;
import hookah_sql.tabacco.Tabacco;
import hookah_sql.tabacco.TabaccoEnum;
import hookah_sql.tabacco.Tabaccos;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Mixer {
    private static final Logger logger = LoggerFactory.getLogger(Mixer.class);

    private final Random random = new Random();
    private int mixIter = 0;

    @Autowired
    private Mix mix;

    @Autowired
    private Tabaccos tabaccos;

    private List<Tabacco> tabaccoList;

    public Mix mix(int tabaccoCount) {
        logger.info("Making new mix from {} tabacco", tabaccoCount);

        tabaccoList = prepare();

        for (int i = 0; i < tabaccoCount;) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco, mix)) {
                mix.addToMix(tabacco);
                i++;
            }
        }

        mix.countHardness();

        return mix;
    }

    public Mix mix(int tabaccoCount, int hardness) {
        logger.info("Making new mix from {} tabacco and with {} hardness", tabaccoCount, hardness);

        tabaccoList = prepare();

        while (true) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco, mix)) {
                mix.addToMix(tabacco);
                mixIter++;
            }
            if (mixIter == tabaccoCount) {
                mix.countHardness();
                if (mix.getHardness() == hardness) {
                    break;
                }
                else {
                    mixIter = 0;
                    mix.setHardness(0);
                    mix.clearMix();
                }
            }
        }

        return mix;
    }

    public Mix mix(int tabaccoCount, Tabacco[] wantedTabaccos) {
        logger.info("Making new mix from {} and {} flavors", Arrays.toString(wantedTabaccos), tabaccoCount);

        tabaccoList = prepare();

        if (wantedTabaccos.length > tabaccoCount) {
            return null;
        }

        for (int i = 0; i < tabaccoCount;) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco, mix) && isProperTabacco(tabacco, wantedTabaccos, mix)) {
                mix.addToMix(tabacco);
                i++;
            }
        }

        mix.countHardness();

        return mix;
    }

    public Mix mix(int tabaccoCount, String[] tastes) {
        logger.info("Making new mix from {} tobaccos and with {} tastes", tabaccoCount, Arrays.toString(tastes));

        tabaccoList = prepare();

        for (int i = 0; i < tabaccoCount;) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco, mix) && isProperTaste(tastes, tabacco, mix)) {
                mix.addToMix(tabacco);
                i++;
            }
        }

        mix.countHardness();

        return mix;
    }

    public Mix mix(int tabaccoCount, int hardness, String[] tastes) {
        logger.info("Making new mix from {} tobaccos with {} tastes and with {} hardness",
                tabaccoCount, Arrays.toString(tastes), hardness);

        tabaccoList = prepare();

        while (true) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco, mix) && isProperTaste(tastes, tabacco, mix)) {
                mix.addToMix(tabacco);
                mixIter++;
            }
            if (mixIter == tabaccoCount) {
                mix.countHardness();

                if (mix.getHardness() == hardness) {
                    break;
                }
                else {
                    mixIter = 0;
                    mix.setHardness(0);
                    mix.clearMix();
                }
            }
        }

        return mix;
    }

    public Mix mix(int tabaccoCount, String[] tastes, Tabacco[] wantedTabaccos) {
        logger.info("Making new mix from {} with {} tastes",
                Arrays.toString(wantedTabaccos), Arrays.toString(tastes));

        tabaccoList = prepare();

        if (wantedTabaccos.length > tabaccoCount) {
            return null;
        }

        for (int i = 0; i < tabaccoCount;) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco, mix) && isProperTabacco(tabacco, wantedTabaccos, mix)
                    && isProperTaste(tastes, tabacco, mix)) {
                mix.addToMix(tabacco);
                i++;
            }
        }

        mix.countHardness();

        return mix;
    }

    private boolean isProperTabacco(Tabacco tabacco, Tabacco[] tabaccos, Mix mix) {
        if (!isMixGotAllWantedTabaccos(mix, tabaccos)) {
            for (Tabacco tabacco1 : tabaccos) {
                if (tabacco.getClass().getSimpleName().equals(tabacco1.getClass().getSimpleName())) {
                    for (Tabacco tabacco2 : mix.getTabaccoList()) {
                        if (tabacco.getClass().getSimpleName().equals(tabacco2.getClass().getSimpleName())) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        else {
            for (Tabacco tabacco1 : tabaccos) {
                if (tabacco.getClass().getSimpleName().equals(tabacco1.getClass().getSimpleName())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isProperTaste(String[] tastes, Tabacco tabacco, Mix mix) {
        if (!isMixGotAllWantedTastes(mix, tastes)) {
            for (String taste : tastes) {
                if (tabacco.getTaste().contains(taste) && !mix.getTaste().contains(taste)) {
                    return true;
                }
            }
        }
        else {
            for (String taste : tastes) {
                if (tabacco.getTaste().contains(taste)) {
                    return true;
                }
            }
        }

        return false;
    }

    private List<Tabacco> prepare() {
        mix.clearMix();
        return tabaccos.getTabaccoList(TabaccoEnum.ALL);
    }

    private boolean isSameTabacco(Tabacco tabacco, Mix mix) {
        return mix.getTabaccoList().contains(tabacco);
    }

    private boolean isMixGotAllWantedTabaccos(Mix mix, Tabacco[] tabaccos) {

        if (mix.getTabaccoList().size() == 0) {
            return false;
        }

        Set<String> brands = new HashSet<>();

        for (Tabacco tabaccoInMix : mix.getTabaccoList()) {
            brands.add(tabaccoInMix.getClass().getSimpleName());
        }

        for (Tabacco tabacco : tabaccos) {
            if (!brands.contains(tabacco.getClass().getSimpleName())) {
                return false;
            }
        }

        return true;
    }

    private boolean isMixGotAllWantedTastes(Mix mix, String[] tastes) {
        return CollectionUtils.containsAll(Arrays.asList(mix.getTaste().split(", ")), Arrays.asList(tastes));
    }


}