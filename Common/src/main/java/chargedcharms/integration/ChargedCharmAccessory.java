package chargedcharms.integration;

import io.wispforest.accessories.api.core.Accessory;

import chargedcharms.common.item.ChargedCharmBase;

public record ChargedCharmAccessory(ChargedCharmBase item) implements Accessory {}
