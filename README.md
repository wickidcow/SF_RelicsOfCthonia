<div align="center">

# 🏰 Relics of Cthonia — Slimefun Legacy

**Unearth ancient Cthonian relics, discover their powers, and barter them with Piglins for configurable rewards.**

![Slimefun Legacy](https://img.shields.io/badge/Slimefun-Legacy-6bd425?style=for-the-badge)
![Paper 26.2](https://img.shields.io/badge/Paper-26.2-blue?style=for-the-badge)
![Java 21+](https://img.shields.io/badge/Java-21%2B-orange?style=for-the-badge)
![License: GPLv3](https://img.shields.io/badge/License-GPLv3-blue?style=for-the-badge)
![Maintained by wickidcow](https://img.shields.io/badge/Maintained%20by-wickidcow-7b68ee?style=for-the-badge)

</div>

> [!IMPORTANT]
> This repository is an **unofficial Slimefun Legacy maintenance fork** of **Relics of Cthonia**, originally created by **FN_FAL113** for the **Slimefun Addon Jam 2022**. The original concept, gameplay design, relics, artwork choices, and authorship remain credited to the original project. This fork focuses on keeping that work usable on current Slimefun Legacy and Paper servers.

## 📥 Download

Download the latest maintained release from the repository's **GitHub Releases** page.

Release JAR naming follows the Slimefun Legacy addon convention:

```text
SF_RelicsOfCthonia<version>.jar
```

Current maintained release:

```text
SF_RelicsOfCthonia2.0.1.jar
```

Relics of Cthonia is also included in the maintained **Slimefun Legacy Addons & Dependencies** bundle published with Slimefun Legacy releases.

## ✅ Requirements

- **Paper 26.2** production baseline
- **Java 21+** runtime; current CI builds with Java 25 while emitting Java 21 bytecode
- **Slimefun Legacy**

Purpur builds based on Paper 26.2 should generally work as well. Folia should be treated as experimental unless every installed addon in the server stack is Folia-safe.

## 💎 What is Relics of Cthonia?

Relics of Cthonia adds ancient Nether-themed relics that can be discovered while playing and traded with Piglins for rewards based on relic rarity.

Relics are divided across several rarity tiers, including:

- Common
- Uncommon
- Rare
- Epic
- Legendary

Some relics are more than barter items and have their own special functions or effects to discover in-game.

Server owners can configure where relics drop, their drop rates, and the rewards available from successful Piglin bartering.

## 🧪 Slimefun Legacy maintenance

The maintained fork keeps the original gameplay intact while modernizing compatibility and stability for current servers.

Current maintenance includes:

- Paper 26.2 API compatibility;
- Java 25 build tooling with Java 21 bytecode output;
- native Bukkit Persistent Data Container storage for maintained relic metadata;
- persistent spawner-origin mob tracking to preserve intended anti-farming behavior across reloads;
- safer random relic selection without mutating shared registry lists;
- corrected off-hand relic interaction handling;
- hardened Piglin barter cleanup for cancelled and interrupted trades;
- modern max-health attribute API usage;
- corrected Eye of Sauron detected-player reporting;
- embedded Slimefun Legacy compatibility metadata so current Legacy builds report the addon as **Compatible**;
- CI that boots the built addon on current Paper 26.2 together with the latest Slimefun Legacy release.

### Compatibility promise

The maintenance pass intentionally preserves the established Relics of Cthonia experience. Existing Slimefun item IDs, recipes, relic items/textures, drop rates, Piglin reward configuration, configuration keys, and stored relic condition/voider data are kept compatible.

## ✨ Original lore

In Middle Earth, the lands of Cthonia were home to a rich civilization that had existed since the dawn of the aether. Centuries of war against the gods of oblivion eventually brought Cthonia to its knees.

The land fell into darkness, slowly transforming into the dire biome now known as the Nether and leaving behind scattered wastelands filled with entities damned during the ancient war. As centuries passed, Cthonian relics became scattered throughout these wastes, waiting to be unearthed from the ground or recovered from dark creatures.

As an adventurer, you take part in the discovery of these ancient relics and pursue the fortune that lies beyond.

## 💫 Relic categories

<div align="center">
  <div style="display: flex;">
    <img src="https://user-images.githubusercontent.com/88238718/173013545-486b5a78-c571-4c15-996f-79d52df1b31c.png" style="vertical-align: top;">
    <img src="https://user-images.githubusercontent.com/88238718/173013559-d7cb01aa-48f9-413b-a0d7-499543892795.png" style="vertical-align: top;">
    <img src="https://user-images.githubusercontent.com/88238718/173013556-7d33196c-e1d8-4dd6-ab4b-92f1c0afdc44.png" style="vertical-align: top;">
    <img src="https://user-images.githubusercontent.com/88238718/173013550-77537837-420c-49c6-ae1a-5b6ce5479299.png" style="vertical-align: top;">
    <img src="https://user-images.githubusercontent.com/88238718/173013553-20129439-717b-42bb-b9c6-8dec00c2b7d4.png" style="vertical-align: top;">
  </div>
</div>

The original addon provides seven relics in each of the Common, Uncommon, and Rare groups, with six relics in the Epic and Legendary groups.

## 💠 Relic samples

<div align="center">
  <div style="display: flex;">
    <img src="https://user-images.githubusercontent.com/88238718/173015529-fd101d6b-2e82-4b6d-94f2-97a6249dae22.png" width="350" height="450" style="vertical-align: top;">
    <img src="https://user-images.githubusercontent.com/88238718/173015551-f29b539f-57ee-42fc-8da3-5d1b798b3164.png" width="350" height="450" style="vertical-align: top;">
    <img src="https://user-images.githubusercontent.com/88238718/173015548-c5003f1f-779b-4a55-acb1-394bb95f56d1.png" width="350" height="450" style="vertical-align: top;">
    <img src="https://user-images.githubusercontent.com/88238718/173015536-001e8435-d1d4-439c-a586-9644f40f2580.png" width="350" height="450" style="vertical-align: top;">
    <img src="https://user-images.githubusercontent.com/88238718/173015545-76ad48e3-263f-4450-83d3-776178a2b8f6.png" width="350" height="450" style="vertical-align: top;">
  </div>
</div>

## ⚙️ Configuration

<div align="center">
  <img src="https://user-images.githubusercontent.com/88238718/173017725-59cd0967-e558-4f87-91d8-5b8bf6ae4a72.png" width="350" height="450" style="vertical-align: top;">
  <img src="https://user-images.githubusercontent.com/88238718/173021026-ef43ff23-eee9-434c-a3d5-0e874bd32919.png" width="350" height="450" style="vertical-align: top;">
</div>

The addon is highly configurable. Server owners can control:

- relic drop rates;
- which blocks can produce relics;
- which mobs can drop relics;
- the amount and type of Piglin barter rewards;
- additional Vanilla or Slimefun reward items using the appropriate item IDs.

Deleting `relic-settings.yml` allows the addon to regenerate its configuration template.

## ❤️ Credits & project lineage

- **FN_FAL113** — original creator of Relics of Cthonia and its Slimefun Addon Jam 2022 entry.
- **Original Relics of Cthonia contributors and community** — testing, feedback, and continued use of the addon.
- **Minecraft-Heads** — head textures used by the original addon.
- **Slimefun developers and contributors** — the Slimefun platform and API.
- **wickidcow / Slimefun Legacy** — current compatibility and preservation maintenance for modern Paper/Slimefun Legacy servers.

Original project attribution is intentionally preserved. This maintenance fork is a continuation of the project, not a claim to have created the original addon.

## 📜 License

Relics of Cthonia is distributed under the **GNU General Public License v3.0**. See [`LICENSE`](LICENSE) for the complete license text.

## ⚖️ Independence & trademark notice

**NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG OR MICROSOFT.**

Relics of Cthonia, Slimefun Legacy, and this maintenance fork are independent community projects. Minecraft-related names, brands, and assets remain the property of their respective rights holders.

---

<div align="center">

**🏰 Unearth relics → 🔥 survive Cthonia → 🐷 barter with Piglins → 💎 claim the reward.**

</div>
