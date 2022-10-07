# Developer Notes for Adding Charm

## Things to consider before adding new charm:
1. What kind of buff will player receive?
1. How long will buff last?
1. What strength will buff be?
1. Will there be a cooldown?
1. How will charm be charged?
1. How will charm be activated?
1. What will the charm look like?
1. What will the recipe be?
1. Name?

## Actually adding new charm in code
1. Create texture for new charm in files/artwork/items.xcf
  1. Export texture to Common assets with naming convention: charged\_{charmname}\_charm.png
1. Update en\_us.json in Common assets with default translation text (Add to other lang files also)
1. Add model generation to Fabric::data/FabricItemModelProvider.java
1. Add charm item to Common::common/item/ChargedCharmsItems
1. Add settings for charm to: Common::config/ConfigHandler
1. If charging via recipe:
  1. Create charging recipe in Common::common/crafting/ with naming convention: {CharmName}ChargeRecipe
  1. Add registration for charging recipe serializer to ::common/crafting/ChargedCharmsCrafting
1. If charging via world interaction, add relevant code to mixin event
1. Add charm base crafting recipe to Common::common/data/recipe/RecipeProviderBase
  1. Add wrapped crafting recipe and wrapped charging recipes to:
    1. Fabric::data/recipe/FabricModRecipeProvider
    1. Forge::data/recipe/ForgeRecipeProvider
1. Add charging recipes for JEI/REI to Common::client/integration/CharmChargingRecipeMaker
  1. Add any show/hide logic for JEI to Common::client/integration/jei/JEIPlugin
  1. Add any show/hide logic for REI to Fabric::client/integration/rei/REIPlugin
1. Create effect for charm in Common::common/effect/ with naming convention: {CharmName}EffectProvider
1. Register charm event in Common::common/CharmEffectProviders
1. ./scripts/datagen
1. Add relevant trigger code to either an existing, or new mixin event, depends ... dunno
