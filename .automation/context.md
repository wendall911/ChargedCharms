# ChargedCharms — Project Context

## What This Is
A mod that adds charged charms — items with a fixed number of charges for a given effect. Designed for balanced server play when utilizing charm abilities. Available since 1.18.2 (backported from 1.20.1).

License: LGPL v3

## Project Structure
Multi-loader: `Common/` + `NeoForge/` (+ `Forge/` on older branches) + `Fabric/`

## Branch Convention
| Branch | Modloaders        |
|--------|-------------------|
| 1.18.2 | Forge + Fabric    |
| 1.20.1 | Forge + Fabric    |
| 1.21.1 | NeoForge + Fabric |
| 26.1   | NeoForge + Fabric |

Maintained: 1.20.1, 1.21.1, 26.1

## Dependencies
- WhiteNoise (jarJar/include)
- Curios API (NeoForge/Forge) — charm slot integration
- Trinkets (Fabric) — charm slot integration

## Charm Slot Dependency History
This mod uses Curios (NeoForge/Forge) and Trinkets (Fabric). At one point during
development, the mod was migrated to Accessories — a cross-modloader charm slot API whose creator promised unified cross-loader support and ongoing maintenance. That promise was not kept; Accessories is now abandoned. Meanwhile, Curios resumed active maintenance and Trinkets has a stable, updated fork. The mod has moved back to Curios/Trinkets. This history matters when reading the git log — Accessories-related commits reflect a deliberate migration that was subsequently reversed, not experimental work.

## Distribution
Side: both (clientRequired = true, serverRequired = true)

## Release Process
Follow the standard wendall911 release process in `../docs/minecraft/MINECRAFT_DEVELOPMENT_NOTES.md`.
