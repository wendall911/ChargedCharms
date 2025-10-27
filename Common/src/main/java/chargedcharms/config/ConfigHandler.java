package chargedcharms.config;

import org.apache.commons.lang3.tuple.Pair;

import technology.roughness.whitenoise.config.WhiteNoiseConfigSpec;

public class ConfigHandler {

    public static final WhiteNoiseConfigSpec CLIENT_SPEC;
    public static final WhiteNoiseConfigSpec COMMON_SPEC;

    private static final Client CLIENT;
    private static final Common COMMON;
    private static boolean loaded = false;

    static {
        final Pair<Client, WhiteNoiseConfigSpec> specPairClient = new WhiteNoiseConfigSpec.Builder().configure(Client::new);
        final Pair<Common, WhiteNoiseConfigSpec> specPairCommon = new WhiteNoiseConfigSpec.Builder().configure(Common::new);

        CLIENT_SPEC = specPairClient.getRight();
        CLIENT = specPairClient.getLeft();
        COMMON_SPEC = specPairCommon.getRight();
        COMMON = specPairCommon.getLeft();
    }

    public static void init() {
        loaded = true;
    }

    public static class Client {

        private final WhiteNoiseConfigSpec.BooleanValue showCharms;

        public Client(WhiteNoiseConfigSpec.Builder builder) {
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

        private final WhiteNoiseConfigSpec.BooleanValue disableRegenCharm;
        private final WhiteNoiseConfigSpec.BooleanValue disableAbsorptionCharm;
        private final WhiteNoiseConfigSpec.BooleanValue disableGlowupCharm;
        private final WhiteNoiseConfigSpec.BooleanValue disableTotemCharm;
        private final WhiteNoiseConfigSpec.BooleanValue disableEnchTotemCharm;
        private final WhiteNoiseConfigSpec.BooleanValue disableSpeedCharm;
        private final WhiteNoiseConfigSpec.BooleanValue disableWaterBreathingCharm;
        private final WhiteNoiseConfigSpec.IntValue absorptionCooldown;
        private final WhiteNoiseConfigSpec.IntValue absorptionDuration;
        private final WhiteNoiseConfigSpec.IntValue absorptionAmplifier;
        private final WhiteNoiseConfigSpec.IntValue absorptionCharges;
        private final WhiteNoiseConfigSpec.DoubleValue regenPercentage;
        private final WhiteNoiseConfigSpec.IntValue regenDuration;
        private final WhiteNoiseConfigSpec.IntValue regenAmplifier;
        private final WhiteNoiseConfigSpec.IntValue regenCharges;
        private final WhiteNoiseConfigSpec.IntValue glowUpDuration;
        private final WhiteNoiseConfigSpec.IntValue glowUpCharges;
        private final WhiteNoiseConfigSpec.IntValue speedDuration;
        private final WhiteNoiseConfigSpec.IntValue speedCooldown;
        private final WhiteNoiseConfigSpec.IntValue speedCharges;
        private final WhiteNoiseConfigSpec.IntValue totemCharges;
        private final WhiteNoiseConfigSpec.IntValue airRemaining;
        private final WhiteNoiseConfigSpec.IntValue waterBreathingCharges;

        public Common(WhiteNoiseConfigSpec.Builder builder) {
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

            disableWaterBreathingCharm = builder.comment("Disable Charged Water Breathing Charm")
                    .define("disableWaterBreathingCharm", false);

            builder.pop();

            builder.push("tweaks");

            absorptionCooldown = builder.comment("Cooldown in seconds for the Charged Absorption Charm.")
                    .defineInRange("absorptionCooldown", 20, 0, 300);

            absorptionDuration = builder.comment("Duration in seconds for the Charged Absorption Charm effect.")
                    .defineInRange("absorptionDuration", 5, 1, 300);

            absorptionAmplifier = builder.comment("Charged Absorption Charm effect amplifier.")
                    .defineInRange("absorptionAmplifier", 0, 0, 255);

            absorptionCharges = builder.comment("Number of charges for the Charged Absorption Charm.")
                    .defineInRange("absorptionCharges", 15, 1, 100);

            regenPercentage = builder.comment("Low health percentage to trigger Charged Regeneration Charm.")
                    .defineInRange("regenPercentage", 0.35, 0.2, 0.8);

            regenDuration = builder.comment("Duration in seconds for the Charged Regeneration Charm effect.")
                    .defineInRange("regenDuration", 15, 1, 300);

            regenAmplifier = builder.comment("Charged Regeneration Charm effect amplifier.")
                    .defineInRange("regenAmplifier", 0, 0, 255);

            regenCharges = builder.comment("Number of charges for the Charged Regeneration Charm.")
                    .defineInRange("regenCharges", 15, 1, 100);

            glowUpDuration = builder.comment("Duration in seconds for the Charged Glow Up Charm effect.")
                    .defineInRange("glowUpDuration", 30, 1, 300);

            glowUpCharges = builder.comment("Number of charges for the Charged Glow Up Charm.")
                    .defineInRange("glowUpCharges", 20, 1, 100);

            speedDuration = builder.comment("Duration in seconds for the Charged Speed Charm effect.")
                    .defineInRange("speedDuration", 180, 1, 360);

            speedCooldown = builder.comment("Cooldown in seconds for the Charged Speed Charm.")
                    .defineInRange("speedCooldown", 120, 0, 360);

            speedCharges = builder.comment("Number of charges for the Charged Speed Charm.")
                    .defineInRange("speedCharges", 20, 1, 100);

            totemCharges = builder.comment("Number of charges for the Charged Totem Charm and Charged Enchanted Totem Charm.")
                    .defineInRange("totemCharges", 5, 1, 100);

            airRemaining = builder.comment("Amount of air remaining when Charged Water Breathing Charm is triggered.")
                    .defineInRange("airRemaining", 0, 0, 10);
            waterBreathingCharges = builder.comment("Number of charges for the Charged Water Breathing Charm.")
                    .defineInRange("waterBreathingCharges", 5, 1, 100);

            builder.pop();
        }

        public static boolean disableRegenCharm() {
            if (loaded) {
                return COMMON.disableRegenCharm.get();
            }

            return false;
        }

        public static boolean disableAbsorptionCharm() {
            if (loaded) {
                return COMMON.disableAbsorptionCharm.get();
            }

            return false;
        }

        public static boolean disableGlowupCharm() {
            if (loaded) {
                return COMMON.disableGlowupCharm.get();
            }

            return false;
        }

        public static boolean disableTotemCharm() {
            if (loaded) {
                return COMMON.disableTotemCharm.get();
            }

            return false;
        }

        public static boolean disableEnchTotemCharm() {
            if (loaded) {
                return COMMON.disableEnchTotemCharm.get();
            }

            return false;
        }

        public static boolean disableSpeedCharm() {
            if (loaded) {
                return COMMON.disableSpeedCharm.get();
            }

            return false;
        }

        public static boolean disableWaterBreathingCharm() {
            if (loaded) {
                return COMMON.disableWaterBreathingCharm.get();
            }

            return false;
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

        public static int absorptionCharges() {
            return COMMON.absorptionCharges.get();
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

        public static int regenCharges() {
            return COMMON.regenCharges.get();
        }

        public static int glowUpDuration() {
            return COMMON.glowUpDuration.get();
        }

        public static int glowUpCharges() {
            return COMMON.glowUpCharges.get();
        }

        public static int speedDuration() {
            return COMMON.speedDuration.get();
        }

        public static long speedCooldown() {
            return (COMMON.speedCooldown.get() + COMMON.speedDuration.get()) * 1000L;
        }

        public static int speedCharges() {
            return COMMON.speedCharges.get();
        }

        public static int totemCharges() {
            return COMMON.totemCharges.get();
        }

        public static int airRemaining() {
            return COMMON.airRemaining.get();
        }

        public static int waterBreathingCharges() {
            return COMMON.waterBreathingCharges.get();
        }

        public static boolean getConfigValue(String key) {
            return switch (key) {
                case "disableRegenCharm" -> disableRegenCharm();
                case "disableAbsorptionCharm" -> disableAbsorptionCharm();
                case "disableGlowupCharm" -> disableGlowupCharm();
                case "disableTotemCharm" -> disableTotemCharm();
                case "disableEnchTotemCharm" -> disableEnchTotemCharm();
                case "disableSpeedCharm" -> disableSpeedCharm();
                case "disableWaterBreathingCharm" -> disableWaterBreathingCharm();
                default -> false;
            };
        }

    }

}
