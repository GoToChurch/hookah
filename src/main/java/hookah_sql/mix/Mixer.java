package hookah_sql.mix;

import hookah_sql.config.Context;
import hookah_sql.dao.TabaccoDAO;
import hookah_sql.tabacco.Tabacco;
import hookah_sql.tabacco.TabaccoEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Mixer {
    private static final Logger logger = LoggerFactory.getLogger(Mixer.class);

    private final Random random = new Random();
    private int mixIter;

    private Mix mix;

    private List<Tabacco> tabaccoList;

    public Mixer() {
        this.tabaccoList = TabaccoDAO.getTabaccoList(TabaccoEnum.ALL);
    }

    public Mix mix(int tabaccoCount) {
        logger.info("Making new mix from {} tabacco", tabaccoCount);

        mix = Context.getInstance().getContext().getBean("mix", Mix.class);

        for (int i = 0; i < tabaccoCount;) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco) && !isAlreadyInMix(tabacco)) {
                mix.addToMix(tabacco);
                i++;
            }
        }

        mix.countHardness();

        return mix;
    }

    public Mix mix(int tabaccoCount, int hardness) {
        logger.info("Making new mix from {} tabacco and with {} hardness", tabaccoCount, hardness);

        mix = Context.getInstance().getContext().getBean("mix", Mix.class);
        mixIter = 0;

        while (true) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco) && !isAlreadyInMix(tabacco)) {
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

        mix = Context.getInstance().getContext().getBean("mix", Mix.class);

        if (wantedTabaccos.length > tabaccoCount) {
            return null;
        }

        for (int i = 0; i < tabaccoCount;) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco) && isProperTabacco(tabacco, wantedTabaccos) && !isAlreadyInMix(tabacco)) {
                mix.addToMix(tabacco);
                i++;
            }
        }

        mix.countHardness();

        return mix;
    }

    public Mix mix(int tabaccoCount, List<String> tastes) {
        logger.info("Making new mix from {} tobaccos and with {} tastes", tabaccoCount, tastes.toString());

        mix = Context.getInstance().getContext().getBean("mix", Mix.class);

        for (int i = 0; i < tabaccoCount;) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco) && isProperTaste(tastes, tabacco) && !isAlreadyInMix(tabacco)) {
                mix.addToMix(tabacco);
                i++;
            }
        }

        mix.countHardness();

        return mix;
    }

    public Mix mix(int tabaccoCount, int hardness, List<String> tastes) {
        logger.info("Making new mix from {} tobaccos with {} tastes and with {} hardness",
                tabaccoCount, tastes.toString(), hardness);

        mix = Context.getInstance().getContext().getBean("mix", Mix.class);
        mixIter = 0;

        while (true) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco) && isProperTaste(tastes, tabacco) && !isAlreadyInMix(tabacco)) {
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

    public Mix mix(int tabaccoCount, List<String> tastes, Tabacco[] wantedTabaccos) {
        logger.info("Making new mix from {} with {} tastes",
                Arrays.toString(wantedTabaccos), tastes.toString());

        mix = Context.getInstance().getContext().getBean("mix", Mix.class);

        if (wantedTabaccos.length > tabaccoCount) {
            return null;
        }

        for (int i = 0; i < tabaccoCount;) {
            Tabacco tabacco = tabaccoList.get(random.nextInt(tabaccoList.size()));
            if (!isSameTabacco(tabacco) && isProperTabacco(tabacco, wantedTabaccos)
                    && isProperTaste(tastes, tabacco) && !isAlreadyInMix(tabacco)) {
                mix.addToMix(tabacco);
                i++;
            }
        }

        mix.countHardness();

        return mix;
    }

    private boolean isProperTabacco(Tabacco tabacco, Tabacco[] tabaccos) {
        if (!isMixGotAllWantedTabaccos(tabaccos)) {
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

    private boolean isProperTaste(List<String> tastes, Tabacco tabacco) {
        if (!isMixGotAllWantedTastes(tastes)) {
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

    private boolean isAlreadyInMix(Tabacco tabacco) {
        return mix.getFlavors().toLowerCase().contains(tabacco.getFlavor().toLowerCase());
    }

    private boolean isSameTabacco(Tabacco tabacco) {
        return mix.getTabaccoList().contains(tabacco);
    }

    private boolean isMixGotAllWantedTabaccos(Tabacco[] tabaccos) {

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

    private boolean isMixGotAllWantedTastes(List<String> tastes) {
        return CollectionUtils.containsAll(Arrays.asList(mix.getTaste().split(", ")), tastes);
    }


}