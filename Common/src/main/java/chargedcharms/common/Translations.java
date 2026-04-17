package chargedcharms.common;

import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import org.slf4j.helpers.MessageFormatter;

public class Translations {

    private static final Joiner LINE_JOINER = Joiner.on("\n");
    private static final Map<String, String> translations = Maps.newHashMap();

    static {
        translations.put("rendering.title", "Rendering");
        translations.put("rendering", "Controls client rendering of Charged Charms.");
        translations.put("showcharms.title", "Show Charged Charm");
        translations.put("showcharms", "Show Charged Charm on player chest.");
        translations.put("charms.title", "Enable/Disable Charms");
        translations.put("charms", "Enable or disable individual Charged Charms.");
        translations.put("disableregencharm.title", "Charged Regeneration Charm");
        translations.put("disableregencharm", "Set to true/on to disable Charged Regeneration Charm");
        translations.put("disableabsorptioncharm.title", "Charged Absorption Charm");
        translations.put("disableabsorptioncharm", "Set to true/on to disable Charged Absorption Charm");
        translations.put("disableglowupcharm.title", "Charged Glow Up Charm");
        translations.put("disableglowupcharm", "Set to true/on to disable Charged Glow Up Charm");
        translations.put("disabletotemcharm.title", "Charged Totem Charm");
        translations.put("disabletotemcharm", "Set to true/on to disable Charged Totem Charm");
        translations.put("disableenchtotemcharm.title", "Charged Enchanted Totem Charm");
        translations.put("disableenchtotemcharm", "Set to true/on to disable Charged Enchanted Totem Charm");
        translations.put("disablespeedcharm.title", "Charged Speed Charm");
        translations.put("disablespeedcharm", "Set to true/on to disable Charged Speed Charm");
        translations.put("disablewaterbreathingcharm.title", "Charged Water Breathing Charm");
        translations.put("disablewaterbreathingcharm", "Set to true/on to disable Charged Water Breathing Charm");
        translations.put("tweaks.title", "Tweaks");
        translations.put("tweaks", "General tweaks for individual Charged Charms.");
        translations.put("absorptioncooldown.title", "Absorption Cooldown");
        translations.put("absorptioncooldown", "Cooldown in seconds for the Charged Absorption Charm.");
        translations.put("absorptionduration.title", "Absorption Duration");
        translations.put("absorptionduration", "Duration in seconds for the Charged Absorption Charm effect.");
        translations.put("absorptionamplifier.title", "Absorption Amplifier");
        translations.put("absorptionamplifier", "Charged Absorption Charm effect amplifier.");
        translations.put("absorptioncharges.title", "Absorption Charges");
        translations.put("absorptioncharges", "Number of charges for the Charged Absorption Charm.");
        translations.put("regenpercentage.title", "Regeneration Percentage");
        translations.put("regenpercentage", "Low health percentage to trigger Charged Regeneration Charm.");
        translations.put("regenduration.title", "Regeneration Duration");
        translations.put("regenduration", "Duration in seconds for the Charged Regeneration Charm effect.");
        translations.put("regenamplifier.title", "Regeneration Amplifier");
        translations.put("regenamplifier", "Charged Regeneration Charm effect amplifier.");
        translations.put("regencharges.title", "Regeneration Charges");
        translations.put("regencharges", "Number of charges for the Charged Regeneration Charm.");
        translations.put("glowupduration.title", "Glow Up Duration");
        translations.put("glowupduration", "Duration in seconds for the Charged Glow Up Charm effect.");
        translations.put("glowupcharges.title", "Glow Up Charges");
        translations.put("glowupcharges", "Number of charges for the Charged Glow Up Charm.");
        translations.put("speedduration.title", "Speed Duration");
        translations.put("speedduration", "Duration in seconds for the Charged Speed Charm effect.");
        translations.put("speedcooldown.title", "Speed Cooldown");
        translations.put("speedcooldown", "Cooldown in seconds for the Charged Speed Charm.");
        translations.put("speedcharges.title", "Speed Charges");
        translations.put("speedcharges", "Number of charges for the Charged Speed Charm.");
        translations.put("totemcharges.title", "Totem Charges");
        translations.put("totemcharges", "Number of charges for the Charged Totem Charm and Charged Enchanted Totem Charm.");
        translations.put("airremaining.title", "Air Remaining");
        translations.put("airremaining", "Amount of air remaining when Charged Water Breathing Charm is triggered.");
        translations.put("waterbreathingcharges.title", "Water Breathing Charges");
        translations.put("waterbreathingcharges", "Number of charges for the Charged Water Breathing Charm.");
    }

    public static String get(String key) {
        return translations.getOrDefault(key, key);
    }

    public static String get(String key, String... values) {
        return MessageFormatter.arrayFormat(translations.getOrDefault(key, key), values).getMessage();
    }

    private static String joiner(String... string) {
        return LINE_JOINER.join(string);
    }

}
