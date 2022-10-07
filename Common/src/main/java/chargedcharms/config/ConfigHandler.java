package chargedcharms.config;

import java.util.HashMap;
import java.util.Map;

import com.illusivesoulworks.spectrelib.config.SpectreConfigSpec;

import org.apache.commons.lang3.tuple.Pair;

public class ConfigHandler {

    public static final SpectreConfigSpec CLIENT_SPEC;
    public static final SpectreConfigSpec COMMON_SPEC;
    public static final Map<String, Boolean> conditionsMap = new HashMap<>();

    private static final Client CLIENT;
    private static final Common COMMON;

    static {
        final Pair<Client, SpectreConfigSpec> specPairClient = new SpectreConfigSpec.Builder().configure(Client::new);
        final Pair<Common, SpectreConfigSpec> specPairCommon = new SpectreConfigSpec.Builder().configure(Common::new);

        CLIENT_SPEC = specPairClient.getRight();
        CLIENT = specPairClient.getLeft();
        COMMON_SPEC = specPairCommon.getRight();
        COMMON = specPairCommon.getLeft();

    }

    public static void init() {
        conditionsMap.clear();
        conditionsMap.put("disableRegenCharm", Common.disableRegenCharm());
        conditionsMap.put("disableAbsorptionCharm", Common.disableAbsorptionCharm());
        conditionsMap.put("disableGlowupCharm", Common.disableGlowupCharm());
        conditionsMap.put("disableTotemCharm", Common.disableTotemCharm());
        conditionsMap.put("disableEnchTotemCharm", Common.disableEnchTotemCharm());
        conditionsMap.put("disableSpeedCharm", Common.disableSpeedCharm());
    }

    public static class Client {

        private final SpectreConfigSpec.BooleanValue showCharms;

        public Client(SpectreConfigSpec.Builder builder) {
            builder.push("rendering");

            showCharms = builder.comment("Show Charged Charm on player chest.")
                    .define("showCharms", true);

            builder.pop();
        }

        public static boolean showCharms() {
            return CLIENT.showCharms.get();
        }

    }

    public static class Common {

        private final SpectreConfigSpec.BooleanValue disableRegenCharm;
        private final SpectreConfigSpec.BooleanValue disableAbsorptionCharm;
        private final SpectreConfigSpec.BooleanValue disableGlowupCharm;
        private final SpectreConfigSpec.BooleanValue disableTotemCharm;
        private final SpectreConfigSpec.BooleanValue disableEnchTotemCharm;
        private final SpectreConfigSpec.BooleanValue disableSpeedCharm;
        private final SpectreConfigSpec.IntValue absorptionCooldown;
        private final SpectreConfigSpec.IntValue absorptionDuration;
        private final SpectreConfigSpec.IntValue absorptionAmplifier;
        private final SpectreConfigSpec.DoubleValue regenPercentage;
        private final SpectreConfigSpec.IntValue regenDuration;
        private final SpectreConfigSpec.IntValue regenAmplifier;
        private final SpectreConfigSpec.IntValue glowUpDuration;
        private final SpectreConfigSpec.IntValue speedDuration;
        private final SpectreConfigSpec.IntValue speedCooldown;

        public Common(SpectreConfigSpec.Builder builder) {
            builder.push("charms");

            disableRegenCharm = builder.comment("Disable Charged Regeneration Charm")
                    .define("disableRegenCharm", false);

            disableAbsorptionCharm = builder.comment("Disable Charged Absorption Charm")
                    .define("disableAbsorptionCharm", false);

            disableGlowupCharm = builder.comment("Disable Charged Glow Up Charm")
                    .define("disableGlowupCharm", false);

            disableTotemCharm = builder.comment("Disable Charged Totem Charm")
                    .define("disableTotemCharm", false);

            disableEnchTotemCharm = builder.comment("Disable Charged Enchanted Totem Charm")
                    .define("disableEnchTotemCharm", false);

            disableSpeedCharm = builder.comment("Disable Charged Speed Charm")
                    .define("disableSpeedCharm", false);

            builder.pop();

            builder.push("tweaks");

            absorptionCooldown = builder.comment("Cooldown in seconds for the Charged Absorption Charm.")
                    .defineInRange("absorptionCooldown", 20, 0, 300);

            absorptionDuration = builder.comment("Duration in seconds for the Charged Absorption Charm effect.")
                    .defineInRange("absorptionDuration", 5, 1, 300);

            absorptionAmplifier = builder.comment("Charged Absorption Charm effect amplifier.")
                    .defineInRange("absorptionAmplifier", 0, 0, 255);

            regenPercentage = builder.comment("Low health percentage to trigger Charged Regeneration Charm.")
                    .defineInRange("regenPercentage", 0.35, 0.2, 0.8);

            regenDuration = builder.comment("Duration in seconds for the Charged Regeneration Charm effect.")
                    .defineInRange("regenDuration", 15, 1, 300);

            regenAmplifier = builder.comment("Charged Regeneration Charm effect amplifier.")
                    .defineInRange("regenAmplifier", 0, 0, 255);

            glowUpDuration = builder.comment("Duration in seconds for the Charged Glow Up Charm effect.")
                    .defineInRange("glowUpDuration", 30, 1, 300);

            speedDuration = builder.comment("Duration in seconds for the Charged Speed Charm effect.")
                    .defineInRange("speedDuration", 180, 1, 360);

            speedCooldown = builder.comment("Cooldown in seconds for the Charged Absorption Charm.")
                    .defineInRange("absorptionCooldown", 120, 0, 360);

            builder.pop();
        }

        public static boolean disableRegenCharm() {
            return COMMON.disableRegenCharm.get();
        }

        public static boolean disableAbsorptionCharm() {
            return COMMON.disableAbsorptionCharm.get();
        }

        public static boolean disableGlowupCharm() {
            return COMMON.disableGlowupCharm.get();
        }

        public static boolean disableTotemCharm() {
            return COMMON.disableTotemCharm.get();
        }

        public static boolean disableEnchTotemCharm() {
            return COMMON.disableEnchTotemCharm.get();
        }

        public static boolean disableSpeedCharm() {
            return COMMON.disableSpeedCharm.get();
        }

        public static long absorptionCooldown() {
            return (COMMON.absorptionCooldown.get() + COMMON.absorptionDuration.get()) * 1000L;
        }

        public static int absorptionDuration() {
            return COMMON.absorptionDuration.get();
        }

        public static int absorptionAmplifier() {
            return COMMON.absorptionAmplifier.get();
        }

        public static Float regenPercentage() {
            double regenPercentage = COMMON.regenPercentage.get();

            return (float) regenPercentage;
        }

        public static int regenDuration() {
            return COMMON.regenDuration.get();
        }

        public static int regenAmplifier() {
            return COMMON.regenAmplifier.get();
        }

        public static int glowUpDuration() {
            return COMMON.glowUpDuration.get();
        }

        public static int speedDuration() {
            return COMMON.speedDuration.get();
        }

        public static long speedCooldown() {
            return (COMMON.speedCooldown.get() + COMMON.speedDuration.get()) * 1000L;
        }

    }

}