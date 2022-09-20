package hookah_sql.commands.mix_commands;

import hookah_sql.ConsoleHelper;
import hookah_sql.commands.Command;
import hookah_sql.config.Context;
import hookah_sql.mix.Mix;
import hookah_sql.mix.Mixer;
import hookah_sql.tabacco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;


public class MixCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(MixCommand.class);

    @Override
    public void execute() {
        logger.info("User chose mix operation");

        Mixer mixer = Context.getInstance().getContext().getBean("mixer", Mixer.class);

        int tabaccoCount = ConsoleHelper.askTabaccoCount();
        int hardness = ConsoleHelper.askHardness();
        String[] taste = ConsoleHelper.askTaste();
        String[] tabaccoString = ConsoleHelper.askTabaccosInMix();
        Tabacco[] tabaccos = new Tabacco[tabaccoString.length];

        Mix mix = null;

        if (tabaccoString.length != 0) {
            for (int i = 0; i < tabaccoString.length; i++) {
                tabaccos[i] = convertStringInTobacco(tabaccoString[i]);
            }
        }

        if (hardness == 0 && taste.length == 0 && tabaccoString.length == 0) {
            mix = mixer.mix(tabaccoCount);
        }
        else if (taste.length == 0 && tabaccoString.length == 0 && hardness > 0) {
            mix = mixer.mix(tabaccoCount, hardness);
        }
        else if (tabaccoString.length == 0 && hardness == 0 && taste.length > 0) {
            mix = mixer.mix(tabaccoCount, taste);
        }
        else if (tabaccoString.length == 0 && hardness > 0 && taste.length > 0) {
            mix = mixer.mix(tabaccoCount, hardness, taste);
        }
        else if (tabaccos.length > 0 && taste.length == 0 && hardness == 0) {
            mix = mixer.mix(tabaccoCount, tabaccos);
        }
        else if (taste.length > 0 && tabaccos.length > 0 && hardness == 0) {
            mix = mixer.mix(tabaccoCount, taste, tabaccos);
        }

        mix.finilizeMix();

        System.out.println(mix);

        saveMix(mix);
    }

    private void saveMix(Mix mix) {
        logger.info("Mix is ready:\n{}", mix.toString());

        ConsoleHelper.writeMessage("Хотите сохранить этот микс? Да/Нет");
        String response = ConsoleHelper.readString();

        switch (response.toLowerCase(Locale.ROOT)) {
            case "да" -> {
                logger.info("User wants to add new mix: {}", mix.getNames());
                mix.save();
            }
            case "нет" -> {
                logger.info("User doesn't want to save mix: {}", mix.getNames());
            }
        }
    }

    private Tabacco convertStringInTobacco(String s) {
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
}
