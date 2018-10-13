/*
 * Copyright (c) 2018, Ron Young <https://github.com/raiyni>
 * All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.client.game;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import static net.runelite.api.ItemID.*;

public enum ChargedItem
{
	CHARGED_ANCHOVY_PIZZA(ANCHOVY_PIZZA, _12_ANCHOVY_PIZZA),
	CHARGED_MEAT_PIZZA(MEAT_PIZZA, _12_MEAT_PIZZA),
	CHARGED_PINEAPPLE_PIZZA(PINEAPPLE_PIZZA, _12_PINEAPPLE_PIZZA),
	CHARGED_PLAIN_PIZZA(PLAIN_PIZZA, _12_PLAIN_PIZZA),
	CHARGED_CAKE(CAKE, _23_CAKE),
	CHARGED_CHOCOLATE_CAKE(CHOCOLATE_CAKE, _23_CHOCOLATE_CAKE),
	CHARGED_ABSORPTION_4(ABSORPTION_4, ABSORPTION_1, ABSORPTION_2, ABSORPTION_3, ABSORPTION_4),
	CHARGED_ABYSSAL_BRACELET5(ABYSSAL_BRACELET5, ABYSSAL_BRACELET1, ABYSSAL_BRACELET2, ABYSSAL_BRACELET3, ABYSSAL_BRACELET4, ABYSSAL_BRACELET5),
	CHARGED_ACHIEVEMENT_DIARY_CAPE(ACHIEVEMENT_DIARY_CAPE, ACHIEVEMENT_DIARY_CAPE_T),
	CHARGED_ADAMANT_FULL_HELM(ADAMANT_FULL_HELM, ADAMANT_FULL_HELM_T),
	CHARGED_ADAMANT_KITESHIELD(ADAMANT_KITESHIELD, ADAMANT_KITESHIELD_T),
	CHARGED_ADAMANT_PLATEBODY(ADAMANT_PLATEBODY, ADAMANT_PLATEBODY_T),
	CHARGED_ADAMANT_PLATELEGS(ADAMANT_PLATELEGS, ADAMANT_PLATELEGS_T),
	CHARGED_ADAMANT_PLATESKIRT(ADAMANT_PLATESKIRT, ADAMANT_PLATESKIRT_T),
	CHARGED_AGILITY_CAPE(AGILITY_CAPE, AGILITY_CAPET),
	CHARGED_AGILITY_MIX2(AGILITY_MIX2, AGILITY_MIX1, AGILITY_MIX2),
	CHARGED_AGILITY_POTION4(AGILITY_POTION4, AGILITY_POTION1, AGILITY_POTION2, AGILITY_POTION3, AGILITY_POTION4),
	CHARGED_AHRIMS_HOOD(AHRIMS_HOOD, AHRIMS_HOOD_0, AHRIMS_HOOD_100, AHRIMS_HOOD_25, AHRIMS_HOOD_50, AHRIMS_HOOD_75),
	CHARGED_AHRIMS_ROBESKIRT(AHRIMS_ROBESKIRT, AHRIMS_ROBESKIRT_0, AHRIMS_ROBESKIRT_100, AHRIMS_ROBESKIRT_25, AHRIMS_ROBESKIRT_50, AHRIMS_ROBESKIRT_75),
	CHARGED_AHRIMS_ROBETOP(AHRIMS_ROBETOP, AHRIMS_ROBETOP_0, AHRIMS_ROBETOP_100, AHRIMS_ROBETOP_25, AHRIMS_ROBETOP_50, AHRIMS_ROBETOP_75),
	CHARGED_AHRIMS_STAFF(AHRIMS_STAFF, AHRIMS_STAFF_0, AHRIMS_STAFF_100, AHRIMS_STAFF_25, AHRIMS_STAFF_50, AHRIMS_STAFF_75),
	CHARGED_AMULET_OF_GLORY(AMULET_OF_GLORY, AMULET_OF_GLORY_T, AMULET_OF_GLORY_T1, AMULET_OF_GLORY_T2, AMULET_OF_GLORY_T3, AMULET_OF_GLORY_T4, AMULET_OF_GLORY_T5, AMULET_OF_GLORY_T6, AMULET_OF_GLORY1, AMULET_OF_GLORY2, AMULET_OF_GLORY3, AMULET_OF_GLORY4, AMULET_OF_GLORY5, AMULET_OF_GLORY6),
	CHARGED_AMULET_OF_MAGIC(AMULET_OF_MAGIC, AMULET_OF_MAGIC_T),
	CHARGED_ANCIENT_PAGE(ANCIENT_PAGE, ANCIENT_PAGE_1, ANCIENT_PAGE_2, ANCIENT_PAGE_3, ANCIENT_PAGE_4),
	CHARGED_ANTIPOISON_SUPERMIX2(ANTIPOISON_SUPERMIX2, ANTIPOISON_SUPERMIX1, ANTIPOISON_SUPERMIX2),
	CHARGED_ANTIVENOM4(ANTIVENOM4, ANTIVENOM1, ANTIVENOM2, ANTIVENOM3, ANTIVENOM4),
	CHARGED_ANTIVENOM4_12913(ANTIVENOM4_12913, ANTIVENOM1_12919, ANTIVENOM2_12917, ANTIVENOM3_12915, ANTIVENOM4_12913),
	CHARGED_ANTIDOTE_MIX2(ANTIDOTE_MIX2, ANTIDOTE_MIX1, ANTIDOTE_MIX2),
	CHARGED_ANTIDOTE_UNF(ANTIDOTE_UNF, ANTIDOTE1, ANTIDOTE2, ANTIDOTE3, ANTIDOTE4),
	CHARGED_ANTIDOTE_UNF_5951(ANTIDOTE_UNF_5951, ANTIDOTE1_5958, ANTIDOTE2_5956, ANTIDOTE3_5954, ANTIDOTE4_5952),
	CHARGED_ANTIFIRE_MIX2(ANTIFIRE_MIX2, ANTIFIRE_MIX1, ANTIFIRE_MIX2),
	CHARGED_ANTIFIRE_POTION4(ANTIFIRE_POTION4, ANTIFIRE_POTION1, ANTIFIRE_POTION2, ANTIFIRE_POTION3, ANTIFIRE_POTION4),
	CHARGED_ANTIPOISON_MIX2(ANTIPOISON_MIX2, ANTIPOISON_MIX1, ANTIPOISON_MIX2),
	CHARGED_ANTIPOISON3(ANTIPOISON3, ANTIPOISON1, ANTIPOISON2, ANTIPOISON3, ANTIPOISON4),
	CHARGED_APPLES1(APPLES1, APPLES1, APPLES2, APPLES3, APPLES4, APPLES5),
	CHARGED_ARCHERS_RING(ARCHERS_RING, ARCHERS_RING_I),
	CHARGED_ARDOUGNE_CLOAK(ARDOUGNE_CLOAK, ARDOUGNE_CLOAK_1, ARDOUGNE_CLOAK_2, ARDOUGNE_CLOAK_3, ARDOUGNE_CLOAK_4),
	CHARGED_ARMADYL_PAGE_1(ARMADYL_PAGE_1, ARMADYL_PAGE_1, ARMADYL_PAGE_2, ARMADYL_PAGE_3, ARMADYL_PAGE_4),
	CHARGED_ASGARNIAN_ALE(ASGARNIAN_ALE, ASGARNIAN_ALE1, ASGARNIAN_ALE2, ASGARNIAN_ALE3, ASGARNIAN_ALE4, ASGARNIAN_ALEM1, ASGARNIAN_ALEM2, ASGARNIAN_ALEM3, ASGARNIAN_ALEM4),
	CHARGED_ATTACK_CAPE(ATTACK_CAPE, ATTACK_CAPET),
	CHARGED_ATTACK_MIX2(ATTACK_MIX2, ATTACK_MIX1, ATTACK_MIX2),
	CHARGED_ATTACK_POTION3(ATTACK_POTION3, ATTACK_POTION1, ATTACK_POTION2, ATTACK_POTION3, ATTACK_POTION4),
	CHARGED_AXEMANS_FOLLY(AXEMANS_FOLLY, AXEMANS_FOLLY1, AXEMANS_FOLLY2, AXEMANS_FOLLY3, AXEMANS_FOLLY4, AXEMANS_FOLLYM1, AXEMANS_FOLLYM2, AXEMANS_FOLLYM3, AXEMANS_FOLLYM4),
	CHARGED_BAGGED_PLANT_1(BAGGED_PLANT_1, BAGGED_PLANT_1, BAGGED_PLANT_2, BAGGED_PLANT_3),
	CHARGED_BANANAS1(BANANAS1, BANANAS1, BANANAS2, BANANAS3, BANANAS4, BANANAS5),
	CHARGED_BANDOS_PAGE_1(BANDOS_PAGE_1, BANDOS_PAGE_1, BANDOS_PAGE_2, BANDOS_PAGE_3, BANDOS_PAGE_4),
	CHARGED_BASTION_POTION4(BASTION_POTION4, BASTION_POTION1, BASTION_POTION2, BASTION_POTION3, BASTION_POTION4),
	CHARGED_BATTLEMAGE_POTION4(BATTLEMAGE_POTION4, BATTLEMAGE_POTION1, BATTLEMAGE_POTION2, BATTLEMAGE_POTION3, BATTLEMAGE_POTION4),
	CHARGED_BERSERKER_RING(BERSERKER_RING, BERSERKER_RING_I),
	CHARGED_BLACK_DHIDE_BODY(BLACK_DHIDE_BODY, BLACK_DHIDE_BODY_T),
	CHARGED_BLACK_DHIDE_CHAPS(BLACK_DHIDE_CHAPS, BLACK_DHIDE_CHAPS_T),
	CHARGED_BLACK_FULL_HELM(BLACK_FULL_HELM, BLACK_FULL_HELM_T),
	CHARGED_BLACK_KITESHIELD(BLACK_KITESHIELD, BLACK_KITESHIELD_T),
	CHARGED_BLACK_MASK(BLACK_MASK, BLACK_MASK_1, BLACK_MASK_2, BLACK_MASK_3, BLACK_MASK_4, BLACK_MASK_5, BLACK_MASK_6, BLACK_MASK_7, BLACK_MASK_8, BLACK_MASK_9, BLACK_MASK_I),
	CHARGED_BLACK_PLATEBODY(BLACK_PLATEBODY, BLACK_PLATEBODY_T),
	CHARGED_BLACK_PLATELEGS(BLACK_PLATELEGS, BLACK_PLATELEGS_T),
	CHARGED_BLACK_PLATESKIRT(BLACK_PLATESKIRT, BLACK_PLATESKIRT_T),
	CHARGED_BLACK_SKIRT(BLACK_SKIRT, BLACK_SKIRT_T),
	CHARGED_BLACK_SLAYER_HELMET(BLACK_SLAYER_HELMET, BLACK_SLAYER_HELMET_I),
	CHARGED_BLACK_WIZARD_HAT_G(BLACK_WIZARD_HAT_G, BLACK_WIZARD_HAT_T),
	CHARGED_BLACK_WIZARD_ROBE_G(BLACK_WIZARD_ROBE_G, BLACK_WIZARD_ROBE_T),
	CHARGED_BLUE_DHIDE_BODY(BLUE_DHIDE_BODY, BLUE_DHIDE_BODY_T),
	CHARGED_BLUE_DHIDE_CHAPS(BLUE_DHIDE_CHAPS, BLUE_DHIDE_CHAPS_T),
	CHARGED_BLUE_SKIRT(BLUE_SKIRT, BLUE_SKIRT_T),
	CHARGED_BLUE_WIZARD_HAT(BLUE_WIZARD_HAT, BLUE_WIZARD_HAT_T),
	CHARGED_BLUE_WIZARD_ROBE(BLUE_WIZARD_ROBE, BLUE_WIZARD_ROBE_T),
	CHARGED_BOOK_PAGE_1(BOOK_PAGE_1, BOOK_PAGE_1, BOOK_PAGE_2, BOOK_PAGE_3),
	CHARGED_BRAWK_FISH_3(BRAWK_FISH_3, BRAWK_FISH_3),
	CHARGED_BRONZE_FULL_HELM(BRONZE_FULL_HELM, BRONZE_FULL_HELM_T),
	CHARGED_BRONZE_KITESHIELD(BRONZE_KITESHIELD, BRONZE_KITESHIELD_T),
	CHARGED_BRONZE_PLATEBODY(BRONZE_PLATEBODY, BRONZE_PLATEBODY_T),
	CHARGED_BRONZE_PLATELEGS(BRONZE_PLATELEGS, BRONZE_PLATELEGS_T),
	CHARGED_BRONZE_PLATESKIRT(BRONZE_PLATESKIRT, BRONZE_PLATESKIRT_T),
	CHARGED_BROODOO_SHIELD(BROODOO_SHIELD, BROODOO_SHIELD_1, BROODOO_SHIELD_2, BROODOO_SHIELD_3, BROODOO_SHIELD_4, BROODOO_SHIELD_5, BROODOO_SHIELD_6, BROODOO_SHIELD_7, BROODOO_SHIELD_8, BROODOO_SHIELD_9),
	CHARGED_BROWN_SPICE_4(BROWN_SPICE_4, BROWN_SPICE_1, BROWN_SPICE_2, BROWN_SPICE_3, BROWN_SPICE_4),
	CHARGED_BURNING_AMULET5(BURNING_AMULET5, BURNING_AMULET1, BURNING_AMULET2, BURNING_AMULET3, BURNING_AMULET4, BURNING_AMULET5),
	CHARGED_CABBAGES1(CABBAGES1, CABBAGES1, CABBAGES2, CABBAGES3, CABBAGES4, CABBAGES5, CABBAGES6, CABBAGES7, CABBAGES8, CABBAGES9),
	CHARGED_CASKET(CASKET, CASKET_EASY, CASKET_ELITE, CASKET_HARD, CASKET_MEDIUM),
	CHARGED_CASTLE_SKETCH_1(CASTLE_SKETCH_1, CASTLE_SKETCH_1, CASTLE_SKETCH_2, CASTLE_SKETCH_3),
	CHARGED_CASTLE_WARS_BRACELET3(CASTLE_WARS_BRACELET3, CASTLE_WARS_BRACELET1, CASTLE_WARS_BRACELET2, CASTLE_WARS_BRACELET3),
	CHARGED_CATSPEAK_AMULET(CATSPEAK_AMULET, CATSPEAK_AMULETE),
	CHARGED_CELL_KEY_1(CELL_KEY_1, CELL_KEY_1, CELL_KEY_2),
	CHARGED_CHALLENGE_SCROLL_MEDIUM(CHALLENGE_SCROLL_MEDIUM, CHALLENGE_SCROLL_ELITE, CHALLENGE_SCROLL_HARD, CHALLENGE_SCROLL_MEDIUM),
	CHARGED_CHEFS_DELIGHT(CHEFS_DELIGHT, CHEFS_DELIGHT1, CHEFS_DELIGHT2, CHEFS_DELIGHT3, CHEFS_DELIGHT4, CHEFS_DELIGHTM1, CHEFS_DELIGHTM2, CHEFS_DELIGHTM3, CHEFS_DELIGHTM4),
	CHARGED_CIDER(CIDER, CIDER1, CIDER2, CIDER3, CIDER4, CIDERM1, CIDERM2, CIDERM3, CIDERM4),
	CHARGED_CLUE_BOTTLE_EASY(CLUE_BOTTLE_EASY, CLUE_BOTTLE_EASY, CLUE_BOTTLE_ELITE, CLUE_BOTTLE_HARD, CLUE_BOTTLE_MEDIUM),
	CHARGED_CLUE_GEODE_EASY(CLUE_GEODE_EASY, CLUE_GEODE_EASY, CLUE_GEODE_ELITE, CLUE_GEODE_HARD, CLUE_GEODE_MEDIUM),
	CHARGED_CLUE_NEST_EASY(CLUE_NEST_EASY, CLUE_NEST_EASY, CLUE_NEST_ELITE, CLUE_NEST_HARD, CLUE_NEST_MEDIUM),
	CHARGED_CLUE_SCROLL(CLUE_SCROLL, CLUE_SCROLL_EASY, CLUE_SCROLL_ELITE, CLUE_SCROLL_HARD, CLUE_SCROLL_MASTER, CLUE_SCROLL_MEDIUM),
	CHARGED_COMBAT_BRACELET(COMBAT_BRACELET, COMBAT_BRACELET1, COMBAT_BRACELET2, COMBAT_BRACELET3, COMBAT_BRACELET4, COMBAT_BRACELET5, COMBAT_BRACELET6),
	CHARGED_COMBAT_MIX2(COMBAT_MIX2, COMBAT_MIX1, COMBAT_MIX2),
	CHARGED_COMBAT_POTION4(COMBAT_POTION4, COMBAT_POTION1, COMBAT_POTION2, COMBAT_POTION3, COMBAT_POTION4),
	CHARGED_COMMORB_V2(COMMORB_V2, COMMORB_V2),
	CHARGED_COMPOST_POTION4(COMPOST_POTION4, COMPOST_POTION1, COMPOST_POTION2, COMPOST_POTION3, COMPOST_POTION4),
	CHARGED_CONSTRUCT_CAPE(CONSTRUCT_CAPE, CONSTRUCT_CAPET),
	CHARGED_COOKING_CAPE(COOKING_CAPE, COOKING_CAPET),
	CHARGED_CRAFTING_CAPE(CRAFTING_CAPE, CRAFTING_CAPET),
	CHARGED_CRAFTING_TABLE_1(CRAFTING_TABLE_1, CRAFTING_TABLE_1, CRAFTING_TABLE_2, CRAFTING_TABLE_3, CRAFTING_TABLE_4),
	CHARGED_CRYSTAL_BOW_FULL(CRYSTAL_BOW_FULL, CRYSTAL_BOW_110_I, CRYSTAL_BOW_110, CRYSTAL_BOW_210_I, CRYSTAL_BOW_210, CRYSTAL_BOW_310_I, CRYSTAL_BOW_310, CRYSTAL_BOW_410_I, CRYSTAL_BOW_410, CRYSTAL_BOW_510_I, CRYSTAL_BOW_510, CRYSTAL_BOW_610_I, CRYSTAL_BOW_610, CRYSTAL_BOW_710_I, CRYSTAL_BOW_710, CRYSTAL_BOW_810_I, CRYSTAL_BOW_810, CRYSTAL_BOW_910_I, CRYSTAL_BOW_910, CRYSTAL_BOW_FULL_I, CRYSTAL_BOW_FULL),
	CHARGED_CRYSTAL_HALBERD_FULL_I(CRYSTAL_HALBERD_FULL_I, CRYSTAL_HALBERD_110_I, CRYSTAL_HALBERD_110, CRYSTAL_HALBERD_210_I, CRYSTAL_HALBERD_210, CRYSTAL_HALBERD_310_I, CRYSTAL_HALBERD_310, CRYSTAL_HALBERD_410_I, CRYSTAL_HALBERD_410, CRYSTAL_HALBERD_510_I, CRYSTAL_HALBERD_510, CRYSTAL_HALBERD_610_I, CRYSTAL_HALBERD_610, CRYSTAL_HALBERD_710_I, CRYSTAL_HALBERD_710, CRYSTAL_HALBERD_810_I, CRYSTAL_HALBERD_810, CRYSTAL_HALBERD_910_I, CRYSTAL_HALBERD_910, CRYSTAL_HALBERD_FULL_I, CRYSTAL_HALBERD_FULL),
	CHARGED_CRYSTAL_SHIELD_FULL(CRYSTAL_SHIELD_FULL, CRYSTAL_SHIELD_110_I, CRYSTAL_SHIELD_110, CRYSTAL_SHIELD_210_I, CRYSTAL_SHIELD_210, CRYSTAL_SHIELD_310_I, CRYSTAL_SHIELD_310, CRYSTAL_SHIELD_410_I, CRYSTAL_SHIELD_410, CRYSTAL_SHIELD_510_I, CRYSTAL_SHIELD_510, CRYSTAL_SHIELD_610_I, CRYSTAL_SHIELD_610, CRYSTAL_SHIELD_710_I, CRYSTAL_SHIELD_710, CRYSTAL_SHIELD_810_I, CRYSTAL_SHIELD_810, CRYSTAL_SHIELD_910_I, CRYSTAL_SHIELD_910, CRYSTAL_SHIELD_FULL_I, CRYSTAL_SHIELD_FULL),
	CHARGED_CW_ARMOUR_1(CW_ARMOUR_1, CW_ARMOUR_1, CW_ARMOUR_2, CW_ARMOUR_3),
	CHARGED_DHIDE_BODY_G(DHIDE_BODY_G, DHIDE_BODY_T),
	CHARGED_DEFENCE_CAPE(DEFENCE_CAPE, DEFENCE_CAPET),
	CHARGED_DEFENCE_MIX2(DEFENCE_MIX2, DEFENCE_MIX1, DEFENCE_MIX2),
	CHARGED_DEFENCE_POTION3(DEFENCE_POTION3, DEFENCE_POTION1, DEFENCE_POTION2, DEFENCE_POTION3, DEFENCE_POTION4),
	CHARGED_DESERT_AMULET(DESERT_AMULET, DESERT_AMULET_1, DESERT_AMULET_2, DESERT_AMULET_3, DESERT_AMULET_4),
	CHARGED_DHAROKS_GREATAXE(DHAROKS_GREATAXE, DHAROKS_GREATAXE_0, DHAROKS_GREATAXE_100, DHAROKS_GREATAXE_25, DHAROKS_GREATAXE_50, DHAROKS_GREATAXE_75),
	CHARGED_DHAROKS_HELM(DHAROKS_HELM, DHAROKS_HELM_0, DHAROKS_HELM_100, DHAROKS_HELM_25, DHAROKS_HELM_50, DHAROKS_HELM_75),
	CHARGED_DHAROKS_PLATEBODY(DHAROKS_PLATEBODY, DHAROKS_PLATEBODY_0, DHAROKS_PLATEBODY_100, DHAROKS_PLATEBODY_25, DHAROKS_PLATEBODY_50, DHAROKS_PLATEBODY_75),
	CHARGED_DHAROKS_PLATELEGS(DHAROKS_PLATELEGS, DHAROKS_PLATELEGS_0, DHAROKS_PLATELEGS_100, DHAROKS_PLATELEGS_25, DHAROKS_PLATELEGS_50, DHAROKS_PLATELEGS_75),
	CHARGED_DIAMOND_BOLTS(DIAMOND_BOLTS, DIAMOND_BOLTS_E),
	CHARGED_DIAMOND_DRAGON_BOLTS(DIAMOND_DRAGON_BOLTS, DIAMOND_DRAGON_BOLTS_E),
	CHARGED_DIGSITE_PENDANT_1(DIGSITE_PENDANT_1, DIGSITE_PENDANT_1, DIGSITE_PENDANT_2, DIGSITE_PENDANT_3, DIGSITE_PENDANT_4, DIGSITE_PENDANT_5),
	CHARGED_DRAGON_BITTER(DRAGON_BITTER, DRAGON_BITTER1, DRAGON_BITTER2, DRAGON_BITTER3, DRAGON_BITTER4, DRAGON_BITTERM1, DRAGON_BITTERM2, DRAGON_BITTERM3, DRAGON_BITTERM4),
	CHARGED_DRAGON_DEFENDER(DRAGON_DEFENDER, DRAGON_DEFENDER_T),
	CHARGED_DRAGONSTONE_BOLTS(DRAGONSTONE_BOLTS, DRAGONSTONE_BOLTS_E),
	CHARGED_DRAGONSTONE_DRAGON_BOLTS(DRAGONSTONE_DRAGON_BOLTS, DRAGONSTONE_DRAGON_BOLTS_E),
	CHARGED_DWARVEN_STOUT(DWARVEN_STOUT, DWARVEN_STOUT1, DWARVEN_STOUT2, DWARVEN_STOUT3, DWARVEN_STOUT4, DWARVEN_STOUTM1, DWARVEN_STOUTM2, DWARVEN_STOUTM3, DWARVEN_STOUTM4),
	CHARGED_ELDER_POTION_1(ELDER_POTION_1, ELDER_POTION_1, ELDER_POTION_2, ELDER_POTION_3, ELDER_POTION_4),
	CHARGED_EMERALD_BOLTS(EMERALD_BOLTS, EMERALD_BOLTS_E),
	CHARGED_EMERALD_DRAGON_BOLTS(EMERALD_DRAGON_BOLTS, EMERALD_DRAGON_BOLTS_E),
	CHARGED_ENCHANTED_LYRE(ENCHANTED_LYRE, ENCHANTED_LYRE1, ENCHANTED_LYRE2, ENCHANTED_LYRE3, ENCHANTED_LYRE4, ENCHANTED_LYRE5),
	CHARGED_ENERGY_MIX2(ENERGY_MIX2, ENERGY_MIX1, ENERGY_MIX2),
	CHARGED_ENERGY_POTION4(ENERGY_POTION4, ENERGY_POTION1, ENERGY_POTION2, ENERGY_POTION3, ENERGY_POTION4),
	CHARGED_EXPLORERS_RING(EXPLORERS_RING, EXPLORERS_RING_1, EXPLORERS_RING_2, EXPLORERS_RING_3, EXPLORERS_RING_4),
	CHARGED_EXTENDED_ANTIFIRE_MIX2(EXTENDED_ANTIFIRE_MIX2, EXTENDED_ANTIFIRE_MIX1, EXTENDED_ANTIFIRE_MIX2),
	CHARGED_EXTENDED_ANTIFIRE4(EXTENDED_ANTIFIRE4, EXTENDED_ANTIFIRE1, EXTENDED_ANTIFIRE2, EXTENDED_ANTIFIRE3, EXTENDED_ANTIFIRE4),
	CHARGED_EXTENDED_SUPER_ANTIFIRE_MIX2(EXTENDED_SUPER_ANTIFIRE_MIX2, EXTENDED_SUPER_ANTIFIRE_MIX1, EXTENDED_SUPER_ANTIFIRE_MIX2),
	CHARGED_EXTENDED_SUPER_ANTIFIRE4(EXTENDED_SUPER_ANTIFIRE4, EXTENDED_SUPER_ANTIFIRE1, EXTENDED_SUPER_ANTIFIRE2, EXTENDED_SUPER_ANTIFIRE3, EXTENDED_SUPER_ANTIFIRE4),
	CHARGED_FALADOR_SHIELD(FALADOR_SHIELD, FALADOR_SHIELD_1, FALADOR_SHIELD_2, FALADOR_SHIELD_3, FALADOR_SHIELD_4),
	CHARGED_FARMING_CAPE(FARMING_CAPE, FARMING_CAPET),
	CHARGED_FIREMAKING_CAPE(FIREMAKING_CAPE, FIREMAKING_CAPET),
	CHARGED_FISHING_CAPE(FISHING_CAPE, FISHING_CAPET),
	CHARGED_FISHING_MIX2(FISHING_MIX2, FISHING_MIX1, FISHING_MIX2),
	CHARGED_FISHING_POTION3(FISHING_POTION3, FISHING_POTION1, FISHING_POTION2, FISHING_POTION3, FISHING_POTION4),
	CHARGED_FLETCHING_CAPE(FLETCHING_CAPE, FLETCHING_CAPET),
	CHARGED_FRAGMENT_1(FRAGMENT_1, FRAGMENT_1, FRAGMENT_2, FRAGMENT_3),
	CHARGED_FREMENNIK_SEA_BOOTS(FREMENNIK_SEA_BOOTS, FREMENNIK_SEA_BOOTS_1, FREMENNIK_SEA_BOOTS_2, FREMENNIK_SEA_BOOTS_3, FREMENNIK_SEA_BOOTS_4),
	CHARGED_FUNGICIDE_SPRAY_10(FUNGICIDE_SPRAY_10, FUNGICIDE_SPRAY_0, FUNGICIDE_SPRAY_1, FUNGICIDE_SPRAY_10, FUNGICIDE_SPRAY_2, FUNGICIDE_SPRAY_3, FUNGICIDE_SPRAY_4, FUNGICIDE_SPRAY_5, FUNGICIDE_SPRAY_6, FUNGICIDE_SPRAY_7, FUNGICIDE_SPRAY_8, FUNGICIDE_SPRAY_9),
	CHARGED_GAMES_NECKLACE8(GAMES_NECKLACE8, GAMES_NECKLACE1, GAMES_NECKLACE2, GAMES_NECKLACE3, GAMES_NECKLACE4, GAMES_NECKLACE5, GAMES_NECKLACE6, GAMES_NECKLACE7, GAMES_NECKLACE8),
	CHARGED_GHOST_BUSTER_500(GHOST_BUSTER_500, GHOST_BUSTER_500),
	CHARGED_GIRAL_BAT_2(GIRAL_BAT_2, GIRAL_BAT_2),
	CHARGED_GODSWORD_SHARDS_1__2(GODSWORD_SHARDS_1__2, GODSWORD_SHARD_1, GODSWORD_SHARD_2, GODSWORD_SHARD_3),
	CHARGED_GRANITE_SHIELD(GRANITE_SHIELD, GRANITE_2KG, GRANITE_5KG),
	CHARGED_GRANITE_RING(GRANITE_RING, GRANITE_RING_I),
	CHARGED_GREEN_DHIDE_BODY(GREEN_DHIDE_BODY, GREEN_DHIDE_BODY_T),
	CHARGED_GREEN_DHIDE_CHAPS(GREEN_DHIDE_CHAPS, GREEN_DHIDE_CHAPS_T),
	CHARGED_GREEN_SLAYER_HELMET(GREEN_SLAYER_HELMET, GREEN_SLAYER_HELMET_I),
	CHARGED_GREENMANS_ALE1(GREENMANS_ALE1, GREENMANS_ALE1, GREENMANS_ALE2, GREENMANS_ALE3, GREENMANS_ALE4, GREENMANS_ALEM1, GREENMANS_ALEM2, GREENMANS_ALEM3, GREENMANS_ALEM4),
	CHARGED_GUANIC_BAT_0(GUANIC_BAT_0, GUANIC_BAT_0),
	CHARGED_GUTHANS_CHAINSKIRT(GUTHANS_CHAINSKIRT, GUTHANS_CHAINSKIRT_0, GUTHANS_CHAINSKIRT_100, GUTHANS_CHAINSKIRT_25, GUTHANS_CHAINSKIRT_50, GUTHANS_CHAINSKIRT_75),
	CHARGED_GUTHANS_HELM(GUTHANS_HELM, GUTHANS_HELM_0, GUTHANS_HELM_100, GUTHANS_HELM_25, GUTHANS_HELM_50, GUTHANS_HELM_75),
	CHARGED_GUTHANS_PLATEBODY(GUTHANS_PLATEBODY, GUTHANS_PLATEBODY_0, GUTHANS_PLATEBODY_100, GUTHANS_PLATEBODY_25, GUTHANS_PLATEBODY_50, GUTHANS_PLATEBODY_75),
	CHARGED_GUTHANS_WARSPEAR(GUTHANS_WARSPEAR, GUTHANS_WARSPEAR_0, GUTHANS_WARSPEAR_100, GUTHANS_WARSPEAR_25, GUTHANS_WARSPEAR_50, GUTHANS_WARSPEAR_75),
	CHARGED_GUTHIX_BALANCE_UNF(GUTHIX_BALANCE_UNF, GUTHIX_BALANCE1, GUTHIX_BALANCE2, GUTHIX_BALANCE3, GUTHIX_BALANCE4),
	CHARGED_GUTHIX_PAGE_1(GUTHIX_PAGE_1, GUTHIX_PAGE_1, GUTHIX_PAGE_2, GUTHIX_PAGE_3, GUTHIX_PAGE_4),
	CHARGED_GUTHIX_REST4(GUTHIX_REST4, GUTHIX_REST1, GUTHIX_REST2, GUTHIX_REST3, GUTHIX_REST4),
	CHARGED_BOTANICAL_PIE(BOTANICAL_PIE, HALF_A_BOTANICAL_PIE),
	CHARGED_FISH_PIE(FISH_PIE, HALF_A_FISH_PIE, PART_FISH_PIE),
	CHARGED_GARDEN_PIE(GARDEN_PIE, HALF_A_GARDEN_PIE, PART_GARDEN_PIE),
	CHARGED_MEAT_PIE(MEAT_PIE, HALF_A_MEAT_PIE),
	CHARGED_MUSHROOM_PIE(MUSHROOM_PIE, HALF_A_MUSHROOM_PIE),
	CHARGED_REDBERRY_PIE(REDBERRY_PIE, HALF_A_REDBERRY_PIE),
	CHARGED_ROCK(ROCK, HALF_A_ROCK),
	CHARGED_SUMMER_PIE(SUMMER_PIE, HALF_A_SUMMER_PIE, PART_SUMMER_PIE),
	CHARGED_WILD_PIE(WILD_PIE, HALF_A_WILD_PIE, PART_WILD_PIE),
	CHARGED_ADMIRAL_PIE(ADMIRAL_PIE, HALF_AN_ADMIRAL_PIE, PART_ADMIRAL_PIE),
	CHARGED_APPLE_PIE(APPLE_PIE, HALF_AN_APPLE_PIE),
	CHARGED_HEALING_VIAL(HEALING_VIAL, HEALING_VIAL1, HEALING_VIAL2, HEALING_VIAL3, HEALING_VIAL4),
	CHARGED_HERBLORE_CAPE(HERBLORE_CAPE, HERBLORE_CAPET),
	CHARGED_HITPOINTS_CAPE(HITPOINTS_CAPE, HITPOINTS_CAPET),
	CHARGED_HUNTER_CAPE(HUNTER_CAPE, HUNTER_CAPET),
	CHARGED_HUNTER_POTION4(HUNTER_POTION4, HUNTER_POTION1, HUNTER_POTION2, HUNTER_POTION3, HUNTER_POTION4),
	CHARGED_HUNTING_MIX2(HUNTING_MIX2, HUNTING_MIX1, HUNTING_MIX2),
	CHARGED_IMPINABOX2(IMPINABOX2, IMPINABOX1, IMPINABOX2),
	CHARGED_IRON_FULL_HELM(IRON_FULL_HELM, IRON_FULL_HELM_T),
	CHARGED_IRON_KITESHIELD(IRON_KITESHIELD, IRON_KITESHIELD_T),
	CHARGED_IRON_PLATEBODY(IRON_PLATEBODY, IRON_PLATEBODY_T),
	CHARGED_IRON_PLATELEGS(IRON_PLATELEGS, IRON_PLATELEGS_T),
	CHARGED_IRON_PLATESKIRT(IRON_PLATESKIRT, IRON_PLATESKIRT_T),
	CHARGED_JADE_BOLTS(JADE_BOLTS, JADE_BOLTS_E),
	CHARGED_JADE_DRAGON_BOLTS(JADE_DRAGON_BOLTS, JADE_DRAGON_BOLTS_E),
	CHARGED_KANDARIN_HEADGEAR(KANDARIN_HEADGEAR, KANDARIN_HEADGEAR_1, KANDARIN_HEADGEAR_2, KANDARIN_HEADGEAR_3, KANDARIN_HEADGEAR_4),
	CHARGED_KARAMJA_GLOVES(KARAMJA_GLOVES, KARAMJA_GLOVES_1, KARAMJA_GLOVES_2, KARAMJA_GLOVES_3, KARAMJA_GLOVES_4),
	CHARGED_KARILS_COIF(KARILS_COIF, KARILS_COIF_0, KARILS_COIF_100, KARILS_COIF_25, KARILS_COIF_50, KARILS_COIF_75),
	CHARGED_KARILS_CROSSBOW(KARILS_CROSSBOW, KARILS_CROSSBOW_0, KARILS_CROSSBOW_100, KARILS_CROSSBOW_25, KARILS_CROSSBOW_50, KARILS_CROSSBOW_75),
	CHARGED_KARILS_LEATHERSKIRT(KARILS_LEATHERSKIRT, KARILS_LEATHERSKIRT_0, KARILS_LEATHERSKIRT_100, KARILS_LEATHERSKIRT_25, KARILS_LEATHERSKIRT_50, KARILS_LEATHERSKIRT_75),
	CHARGED_KARILS_LEATHERTOP(KARILS_LEATHERTOP, KARILS_LEATHERTOP_0, KARILS_LEATHERTOP_100, KARILS_LEATHERTOP_25, KARILS_LEATHERTOP_50, KARILS_LEATHERTOP_75),
	CHARGED_KEY(KEY, KEY_ELITE, KEY_MEDIUM),
	CHARGED_KODAI_POTION_1(KODAI_POTION_1, KODAI_POTION_1, KODAI_POTION_2, KODAI_POTION_3, KODAI_POTION_4),
	CHARGED_KRYKET_BAT_4(KRYKET_BAT_4, KRYKET_BAT_4),
	CHARGED_KYREN_FISH_6(KYREN_FISH_6, KYREN_FISH_6),
	CHARGED_LECKISH_FISH_2(LECKISH_FISH_2, LECKISH_FISH_2),
	CHARGED_LUNAR_STAFF__PT1(LUNAR_STAFF__PT1, LUNAR_STAFF__PT1, LUNAR_STAFF__PT2, LUNAR_STAFF__PT3),
	CHARGED_MAGIC_CAPE(MAGIC_CAPE, MAGIC_CAPET),
	CHARGED_MAGIC_ESSENCE_MIX2(MAGIC_ESSENCE_MIX2, MAGIC_ESSENCE_MIX1, MAGIC_ESSENCE_MIX2),
	CHARGED_MAGIC_ESSENCE_UNF(MAGIC_ESSENCE_UNF, MAGIC_ESSENCE1, MAGIC_ESSENCE2, MAGIC_ESSENCE3, MAGIC_ESSENCE4),
	CHARGED_MAGIC_MIX2(MAGIC_MIX2, MAGIC_MIX1, MAGIC_MIX2),
	CHARGED_MAGIC_POTION4(MAGIC_POTION4, MAGIC_POTION1, MAGIC_POTION2, MAGIC_POTION3, MAGIC_POTION4),
	CHARGED_MAGIC_SHORTBOW(MAGIC_SHORTBOW, MAGIC_SHORTBOW_I),
	CHARGED_MAGICAL_BALANCE_1(MAGICAL_BALANCE_1, MAGICAL_BALANCE_1, MAGICAL_BALANCE_2, MAGICAL_BALANCE_3),
	CHARGED_MALEDICTION_SHARD_1(MALEDICTION_SHARD_1, MALEDICTION_SHARD_1, MALEDICTION_SHARD_2, MALEDICTION_SHARD_3),
	CHARGED_MIND_BOMB1(MIND_BOMB1, MIND_BOMB1, MIND_BOMB2, MIND_BOMB3, MIND_BOMB4, MIND_BOMBM1, MIND_BOMBM2, MIND_BOMBM3, MIND_BOMBM4),
	CHARGED_MINING_CAPE(MINING_CAPE, MINING_CAPET),
	CHARGED_MITHRIL_FULL_HELM(MITHRIL_FULL_HELM, MITHRIL_FULL_HELM_T),
	CHARGED_MITHRIL_KITESHIELD(MITHRIL_KITESHIELD, MITHRIL_KITESHIELD_T),
	CHARGED_MITHRIL_PLATEBODY(MITHRIL_PLATEBODY, MITHRIL_PLATEBODY_T),
	CHARGED_MITHRIL_PLATELEGS(MITHRIL_PLATELEGS, MITHRIL_PLATELEGS_T),
	CHARGED_MITHRIL_PLATESKIRT(MITHRIL_PLATESKIRT, MITHRIL_PLATESKIRT_T),
	CHARGED_MOONLIGHT_MEAD(MOONLIGHT_MEAD, MOONLIGHT_MEAD1, MOONLIGHT_MEAD2, MOONLIGHT_MEAD3, MOONLIGHT_MEAD4, MOONLIGHT_MEADM1, MOONLIGHT_MEADM2, MOONLIGHT_MEADM3, MOONLIGHT_MEADM4),
	CHARGED_MORYTANIA_LEGS(MORYTANIA_LEGS, MORYTANIA_LEGS_1, MORYTANIA_LEGS_2, MORYTANIA_LEGS_3, MORYTANIA_LEGS_4),
	CHARGED_MURNG_BAT_5(MURNG_BAT_5, MURNG_BAT_5),
	CHARGED_MUSIC_CAPE(MUSIC_CAPE, MUSIC_CAPET),
	CHARGED_MYCIL_FISH_4(MYCIL_FISH_4, MYCIL_FISH_4),
	CHARGED_NECKLACE_OF_PASSAGE5(NECKLACE_OF_PASSAGE5, NECKLACE_OF_PASSAGE1, NECKLACE_OF_PASSAGE2, NECKLACE_OF_PASSAGE3, NECKLACE_OF_PASSAGE4, NECKLACE_OF_PASSAGE5),
	CHARGED_NEW_CRYSTAL_BOW(NEW_CRYSTAL_BOW, NEW_CRYSTAL_BOW_I),
	CHARGED_NEW_CRYSTAL_HALBERD_FULL_I(NEW_CRYSTAL_HALBERD_FULL_I, NEW_CRYSTAL_HALBERD_FULL_I, NEW_CRYSTAL_HALBERD_FULL),
	CHARGED_NEW_CRYSTAL_SHIELD(NEW_CRYSTAL_SHIELD, NEW_CRYSTAL_SHIELD_I),
	CHARGED_OAK_SHELVES_1(OAK_SHELVES_1, OAK_SHELVES_1, OAK_SHELVES_2),
	CHARGED_ODIUM_SHARD_1(ODIUM_SHARD_1, ODIUM_SHARD_1, ODIUM_SHARD_2, ODIUM_SHARD_3),
	CHARGED_OGRE_BELLOWS(OGRE_BELLOWS, OGRE_BELLOWS_1, OGRE_BELLOWS_2, OGRE_BELLOWS_3),
	CHARGED_OLIVE_OIL4(OLIVE_OIL4, OLIVE_OIL1, OLIVE_OIL2, OLIVE_OIL3, OLIVE_OIL4),
	CHARGED_ONIONS1(ONIONS1, ONIONS1, ONIONS2, ONIONS3, ONIONS4, ONIONS5, ONIONS6, ONIONS7, ONIONS8, ONIONS9),
	CHARGED_ONYX_BOLTS(ONYX_BOLTS, ONYX_BOLTS_E),
	CHARGED_ONYX_DRAGON_BOLTS(ONYX_DRAGON_BOLTS, ONYX_DRAGON_BOLTS_E),
	CHARGED_OPAL_BOLTS(OPAL_BOLTS, OPAL_BOLTS_E),
	CHARGED_OPAL_DRAGON_BOLTS(OPAL_DRAGON_BOLTS, OPAL_DRAGON_BOLTS_E),
	CHARGED_ORANGE_SPICE_4(ORANGE_SPICE_4, ORANGE_SPICE_1, ORANGE_SPICE_2, ORANGE_SPICE_3, ORANGE_SPICE_4),
	CHARGED_ORANGES1(ORANGES1, ORANGES1, ORANGES2, ORANGES3, ORANGES4, ORANGES5),
	CHARGED_OVERLOAD_4(OVERLOAD_4, OVERLOAD_1, OVERLOAD_2, OVERLOAD_3, OVERLOAD_4),
	CHARGED_PAGES(PAGES, PAGE_1, PAGE_2, PAGE_3),
	CHARGED_MUD_PIE(MUD_PIE, PART_MUD_PIE),
	CHARGED_PEARL_BOLTS(PEARL_BOLTS, PEARL_BOLTS_E),
	CHARGED_PEARL_DRAGON_BOLTS(PEARL_DRAGON_BOLTS, PEARL_DRAGON_BOLTS_E),
	CHARGED_PHARAOHS_SCEPTRE(PHARAOHS_SCEPTRE, PHARAOHS_SCEPTRE_1, PHARAOHS_SCEPTRE_2, PHARAOHS_SCEPTRE_3, PHARAOHS_SCEPTRE_4, PHARAOHS_SCEPTRE_5, PHARAOHS_SCEPTRE_6, PHARAOHS_SCEPTRE_7, PHARAOHS_SCEPTRE_8),
	CHARGED_PHLUXIA_BAT_3(PHLUXIA_BAT_3, PHLUXIA_BAT_3),
	CHARGED_POT_OF_TEA_4(POT_OF_TEA_4, POT_OF_TEA_1, POT_OF_TEA_2, POT_OF_TEA_3, POT_OF_TEA_4),
	CHARGED_POTATOES1(POTATOES1, POTATOES1, POTATOES2, POTATOES3, POTATOES4, POTATOES5, POTATOES6, POTATOES7, POTATOES8, POTATOES9),
	CHARGED_PRAEL_BAT_1(PRAEL_BAT_1, PRAEL_BAT_1),
	CHARGED_PRAYER_CAPE(PRAYER_CAPE, PRAYER_CAPET),
	CHARGED_PRAYER_ENHANCE_1(PRAYER_ENHANCE_1, PRAYER_ENHANCE_1_20965, PRAYER_ENHANCE_2_20966, PRAYER_ENHANCE_3_20967, PRAYER_ENHANCE_4_20968),
	CHARGED_PRAYER_MIX2(PRAYER_MIX2, PRAYER_MIX1, PRAYER_MIX2),
	CHARGED_PRAYER_POTION3(PRAYER_POTION3, PRAYER_POTION1, PRAYER_POTION2, PRAYER_POTION3, PRAYER_POTION4),
	CHARGED_PSYKK_BAT_6(PSYKK_BAT_6, PSYKK_BAT_6),
	CHARGED_PURPLE_SLAYER_HELMET(PURPLE_SLAYER_HELMET, PURPLE_SLAYER_HELMET_I),
	CHARGED_PUZZLE_BOX_HARD(PUZZLE_BOX_HARD, PUZZLE_BOX_ELITE, PUZZLE_BOX_HARD, PUZZLE_BOX_MASTER),
	CHARGED_PYSK_FISH_0(PYSK_FISH_0, PYSK_FISH_0),
	CHARGED_QUEST_POINT_CAPE(QUEST_POINT_CAPE, QUEST_POINT_CAPE_T),
	CHARGED_RANGING_CAPE(RANGING_CAPE, RANGING_CAPET),
	CHARGED_RANGING_MIX2(RANGING_MIX2, RANGING_MIX1, RANGING_MIX2),
	CHARGED_RANGING_POTION3(RANGING_POTION3, RANGING_POTION1, RANGING_POTION2, RANGING_POTION3, RANGING_POTION4),
	CHARGED_RAW_BRAWK_FISH_3(RAW_BRAWK_FISH_3, RAW_BRAWK_FISH_3),
	CHARGED_RAW_GIRAL_BAT_2(RAW_GIRAL_BAT_2, RAW_GIRAL_BAT_2),
	CHARGED_RAW_GUANIC_BAT_0(RAW_GUANIC_BAT_0, RAW_GUANIC_BAT_0),
	CHARGED_RAW_KRYKET_BAT_4(RAW_KRYKET_BAT_4, RAW_KRYKET_BAT_4),
	CHARGED_RAW_KYREN_FISH_6(RAW_KYREN_FISH_6, RAW_KYREN_FISH_6),
	CHARGED_RAW_LECKISH_FISH_2(RAW_LECKISH_FISH_2, RAW_LECKISH_FISH_2),
	CHARGED_RAW_MURNG_BAT_5(RAW_MURNG_BAT_5, RAW_MURNG_BAT_5),
	CHARGED_RAW_MYCIL_FISH_4(RAW_MYCIL_FISH_4, RAW_MYCIL_FISH_4),
	CHARGED_RAW_PHLUXIA_BAT_3(RAW_PHLUXIA_BAT_3, RAW_PHLUXIA_BAT_3),
	CHARGED_RAW_PRAEL_BAT_1(RAW_PRAEL_BAT_1, RAW_PRAEL_BAT_1),
	CHARGED_RAW_PSYKK_BAT_6(RAW_PSYKK_BAT_6, RAW_PSYKK_BAT_6),
	CHARGED_RAW_PYSK_FISH_0(RAW_PYSK_FISH_0, RAW_PYSK_FISH_0),
	CHARGED_RAW_ROQED_FISH_5(RAW_ROQED_FISH_5, RAW_ROQED_FISH_5),
	CHARGED_RAW_SUPHI_FISH_1(RAW_SUPHI_FISH_1, RAW_SUPHI_FISH_1),
	CHARGED_RED_DHIDE_BODY(RED_DHIDE_BODY, RED_DHIDE_BODY_T),
	CHARGED_RED_DHIDE_CHAPS(RED_DHIDE_CHAPS, RED_DHIDE_CHAPS_T),
	CHARGED_RED_SLAYER_HELMET(RED_SLAYER_HELMET, RED_SLAYER_HELMET_I),
	CHARGED_RED_SPICE_4(RED_SPICE_4, RED_SPICE_1, RED_SPICE_2, RED_SPICE_3, RED_SPICE_4),
	CHARGED_REJUVENATION_POTION_UNF(REJUVENATION_POTION_UNF, REJUVENATION_POTION_1, REJUVENATION_POTION_2, REJUVENATION_POTION_3, REJUVENATION_POTION_4),
	CHARGED_RELIC_PART_1(RELIC_PART_1, RELIC_PART_1, RELIC_PART_2, RELIC_PART_3),
	CHARGED_RELICYMS_BALM4(RELICYMS_BALM4, RELICYMS_BALM1, RELICYMS_BALM2, RELICYMS_BALM3, RELICYMS_BALM4),
	CHARGED_RELICYMS_MIX2(RELICYMS_MIX2, RELICYMS_MIX1, RELICYMS_MIX2),
	CHARGED_RESTORE_MIX2(RESTORE_MIX2, RESTORE_MIX1, RESTORE_MIX2),
	CHARGED_RESTORE_POTION3(RESTORE_POTION3, RESTORE_POTION1, RESTORE_POTION2, RESTORE_POTION3, RESTORE_POTION4),
	CHARGED_REVITALISATION_POTION(REVITALISATION_POTION, REVITALISATION_POTION_1, REVITALISATION_POTION_2, REVITALISATION_POTION_3, REVITALISATION_POTION_4),
	CHARGED_REWARD_CASKET_MASTER(REWARD_CASKET_MASTER, REWARD_CASKET_EASY, REWARD_CASKET_ELITE, REWARD_CASKET_HARD, REWARD_CASKET_MASTER, REWARD_CASKET_MEDIUM),
	CHARGED_RICKTORS_DIARY_7(RICKTORS_DIARY_7, RICKTORS_DIARY_7),
	CHARGED_RING_OF_DUELING8(RING_OF_DUELING8, RING_OF_DUELING1, RING_OF_DUELING2, RING_OF_DUELING3, RING_OF_DUELING4, RING_OF_DUELING5, RING_OF_DUELING6, RING_OF_DUELING7, RING_OF_DUELING8),
	CHARGED_RING_OF_RETURNING5(RING_OF_RETURNING5, RING_OF_RETURNING1, RING_OF_RETURNING2, RING_OF_RETURNING3, RING_OF_RETURNING4, RING_OF_RETURNING5),
	CHARGED_RING_OF_SUFFERING(RING_OF_SUFFERING, RING_OF_SUFFERING_I),
	CHARGED_RING_OF_THE_GODS(RING_OF_THE_GODS, RING_OF_THE_GODS_I),
	CHARGED_RING_OF_WEALTH(RING_OF_WEALTH, RING_OF_WEALTH_1, RING_OF_WEALTH_2, RING_OF_WEALTH_3, RING_OF_WEALTH_4, RING_OF_WEALTH_5, RING_OF_WEALTH_I),
	CHARGED_ROD_OF_IVANDIS_10(ROD_OF_IVANDIS_10, ROD_OF_IVANDIS_1, ROD_OF_IVANDIS_2, ROD_OF_IVANDIS_3, ROD_OF_IVANDIS_4, ROD_OF_IVANDIS_5, ROD_OF_IVANDIS_6, ROD_OF_IVANDIS_7, ROD_OF_IVANDIS_8, ROD_OF_IVANDIS_9),
	CHARGED_ROQED_FISH_5(ROQED_FISH_5, ROQED_FISH_5),
	CHARGED_RUBY_BOLTS(RUBY_BOLTS, RUBY_BOLTS_E),
	CHARGED_RUBY_DRAGON_BOLTS(RUBY_DRAGON_BOLTS, RUBY_DRAGON_BOLTS_E),
	CHARGED_RUNE_CASE_1(RUNE_CASE_1, RUNE_CASE_1, RUNE_CASE_2, RUNE_CASE_3),
	CHARGED_RUNE_FULL_HELM(RUNE_FULL_HELM, RUNE_FULL_HELM_T),
	CHARGED_RUNE_KITESHIELD(RUNE_KITESHIELD, RUNE_KITESHIELD_T),
	CHARGED_RUNE_PLATEBODY(RUNE_PLATEBODY, RUNE_PLATEBODY_T),
	CHARGED_RUNE_PLATELEGS(RUNE_PLATELEGS, RUNE_PLATELEGS_T),
	CHARGED_RUNE_PLATESKIRT(RUNE_PLATESKIRT, RUNE_PLATESKIRT_T),
	CHARGED_RUNECRAFT_CAPE(RUNECRAFT_CAPE, RUNECRAFT_CAPET),
	CHARGED_SACRED_OIL4(SACRED_OIL4, SACRED_OIL1, SACRED_OIL2, SACRED_OIL3, SACRED_OIL4),
	CHARGED_SALVE_AMULET(SALVE_AMULET, SALVE_AMULET_E, SALVE_AMULETI),
	CHARGED_SANDSTONE_1KG(SANDSTONE_1KG, SANDSTONE_1KG, SANDSTONE_2KG, SANDSTONE_5KG),
	CHARGED_SANFEW_SERUM4(SANFEW_SERUM4, SANFEW_SERUM1, SANFEW_SERUM2, SANFEW_SERUM3, SANFEW_SERUM4),
	CHARGED_SAPPHIRE_BOLTS(SAPPHIRE_BOLTS, SAPPHIRE_BOLTS_E),
	CHARGED_SAPPHIRE_DRAGON_BOLTS(SAPPHIRE_DRAGON_BOLTS, SAPPHIRE_DRAGON_BOLTS_E),
	CHARGED_SARADOMIN_BREW4(SARADOMIN_BREW4, SARADOMIN_BREW1, SARADOMIN_BREW2, SARADOMIN_BREW3, SARADOMIN_BREW4),
	CHARGED_SARADOMIN_PAGE_1(SARADOMIN_PAGE_1, SARADOMIN_PAGE_1, SARADOMIN_PAGE_2, SARADOMIN_PAGE_3, SARADOMIN_PAGE_4),
	CHARGED_SEERS_RING(SEERS_RING, SEERS_RING_I),
	CHARGED_SHAYZIEN_BOOTS_1(SHAYZIEN_BOOTS_1, SHAYZIEN_BOOTS_1, SHAYZIEN_BOOTS_2, SHAYZIEN_BOOTS_3, SHAYZIEN_BOOTS_4, SHAYZIEN_BOOTS_5),
	CHARGED_SHAYZIEN_GLOVES_1(SHAYZIEN_GLOVES_1, SHAYZIEN_GLOVES_1, SHAYZIEN_GLOVES_2, SHAYZIEN_GLOVES_3, SHAYZIEN_GLOVES_4, SHAYZIEN_GLOVES_5),
	CHARGED_SHAYZIEN_GREAVES_1(SHAYZIEN_GREAVES_1, SHAYZIEN_GREAVES_1, SHAYZIEN_GREAVES_2, SHAYZIEN_GREAVES_3, SHAYZIEN_GREAVES_4, SHAYZIEN_GREAVES_5),
	CHARGED_SHAYZIEN_HELM_1(SHAYZIEN_HELM_1, SHAYZIEN_HELM_1, SHAYZIEN_HELM_2, SHAYZIEN_HELM_3, SHAYZIEN_HELM_4, SHAYZIEN_HELM_5),
	CHARGED_SHAYZIEN_PLATEBODY_1(SHAYZIEN_PLATEBODY_1, SHAYZIEN_PLATEBODY_1, SHAYZIEN_PLATEBODY_2, SHAYZIEN_PLATEBODY_3, SHAYZIEN_PLATEBODY_4, SHAYZIEN_PLATEBODY_5),
	CHARGED_SHAYZIEN_SUPPLY_BOOTS_1(SHAYZIEN_SUPPLY_BOOTS_1, SHAYZIEN_SUPPLY_BOOTS_1, SHAYZIEN_SUPPLY_BOOTS_2, SHAYZIEN_SUPPLY_BOOTS_3, SHAYZIEN_SUPPLY_BOOTS_4, SHAYZIEN_SUPPLY_BOOTS_5),
	CHARGED_SHAYZIEN_SUPPLY_GLOVES_1(SHAYZIEN_SUPPLY_GLOVES_1, SHAYZIEN_SUPPLY_GLOVES_1, SHAYZIEN_SUPPLY_GLOVES_2, SHAYZIEN_SUPPLY_GLOVES_3, SHAYZIEN_SUPPLY_GLOVES_4, SHAYZIEN_SUPPLY_GLOVES_5),
	CHARGED_SHAYZIEN_SUPPLY_GREAVES_1(SHAYZIEN_SUPPLY_GREAVES_1, SHAYZIEN_SUPPLY_GREAVES_1, SHAYZIEN_SUPPLY_GREAVES_2, SHAYZIEN_SUPPLY_GREAVES_3, SHAYZIEN_SUPPLY_GREAVES_4, SHAYZIEN_SUPPLY_GREAVES_5),
	CHARGED_SHAYZIEN_SUPPLY_HELM_1(SHAYZIEN_SUPPLY_HELM_1, SHAYZIEN_SUPPLY_HELM_1, SHAYZIEN_SUPPLY_HELM_2, SHAYZIEN_SUPPLY_HELM_3, SHAYZIEN_SUPPLY_HELM_4, SHAYZIEN_SUPPLY_HELM_5),
	CHARGED_SHAYZIEN_SUPPLY_PLATEBODY_1(SHAYZIEN_SUPPLY_PLATEBODY_1, SHAYZIEN_SUPPLY_PLATEBODY_1, SHAYZIEN_SUPPLY_PLATEBODY_2, SHAYZIEN_SUPPLY_PLATEBODY_3, SHAYZIEN_SUPPLY_PLATEBODY_4, SHAYZIEN_SUPPLY_PLATEBODY_5),
	CHARGED_SHAYZIEN_SUPPLY_SET_1(SHAYZIEN_SUPPLY_SET_1, SHAYZIEN_SUPPLY_SET_1, SHAYZIEN_SUPPLY_SET_2, SHAYZIEN_SUPPLY_SET_3, SHAYZIEN_SUPPLY_SET_4, SHAYZIEN_SUPPLY_SET_5),
	CHARGED_SHEEP_BONES_1(SHEEP_BONES_1, SHEEP_BONES_1, SHEEP_BONES_2, SHEEP_BONES_3, SHEEP_BONES_4),
	CHARGED_SINHAZA_SHROUD_TIER_1(SINHAZA_SHROUD_TIER_1, SINHAZA_SHROUD_TIER_1, SINHAZA_SHROUD_TIER_2, SINHAZA_SHROUD_TIER_3, SINHAZA_SHROUD_TIER_4, SINHAZA_SHROUD_TIER_5),
	CHARGED_SKILLS_NECKLACE(SKILLS_NECKLACE, SKILLS_NECKLACE1, SKILLS_NECKLACE2, SKILLS_NECKLACE3, SKILLS_NECKLACE4, SKILLS_NECKLACE5, SKILLS_NECKLACE6),
	CHARGED_SKULL_SCEPTRE(SKULL_SCEPTRE, SKULL_SCEPTRE_I),
	CHARGED_SLAYER_CAPE(SLAYER_CAPE, SLAYER_CAPET),
	CHARGED_SLAYER_HELMET(SLAYER_HELMET, SLAYER_HELMET_I),
	CHARGED_SLAYER_RING_8(SLAYER_RING_8, SLAYER_RING_1, SLAYER_RING_2, SLAYER_RING_3, SLAYER_RING_4, SLAYER_RING_5, SLAYER_RING_6, SLAYER_RING_7, SLAYER_RING_8, SLAYER_RING_ETERNAL),
	CHARGED_SLAYERS_RESPITE(SLAYERS_RESPITE, SLAYERS_RESPITE1, SLAYERS_RESPITE2, SLAYERS_RESPITE3, SLAYERS_RESPITE4, SLAYERS_RESPITEM1, SLAYERS_RESPITEM2, SLAYERS_RESPITEM3, SLAYERS_RESPITEM4),
	CHARGED_SLAYERS_STAFF(SLAYERS_STAFF, SLAYERS_STAFF_E),
	CHARGED_SMITHING_CAPE(SMITHING_CAPE, SMITHING_CAPET),
	CHARGED_SNAKE_BASKET(SNAKE_BASKET, SNAKE_BASKET_FULL),
	CHARGED_STAMINA_MIX2(STAMINA_MIX2, STAMINA_MIX1, STAMINA_MIX2),
	CHARGED_STAMINA_POTION4(STAMINA_POTION4, STAMINA_POTION1, STAMINA_POTION2, STAMINA_POTION3, STAMINA_POTION4),
	CHARGED_STASH_UNITS_EASY(STASH_UNITS_EASY, STASH_UNITS_EASY, STASH_UNITS_ELITE, STASH_UNITS_HARD, STASH_UNITS_MASTER, STASH_UNITS_MEDIUM),
	CHARGED_STEEL_FULL_HELM(STEEL_FULL_HELM, STEEL_FULL_HELM_T),
	CHARGED_STEEL_KITESHIELD(STEEL_KITESHIELD, STEEL_KITESHIELD_T),
	CHARGED_STEEL_PLATEBODY(STEEL_PLATEBODY, STEEL_PLATEBODY_T),
	CHARGED_STEEL_PLATELEGS(STEEL_PLATELEGS, STEEL_PLATELEGS_T),
	CHARGED_STEEL_PLATESKIRT(STEEL_PLATESKIRT, STEEL_PLATESKIRT_T),
	CHARGED_STRAWBERRIES1(STRAWBERRIES1, STRAWBERRIES1, STRAWBERRIES2, STRAWBERRIES3, STRAWBERRIES4, STRAWBERRIES5),
	CHARGED_STRENGTH_AMULET_T(STRENGTH_AMULET_T, STRENGTH_AMULET_T),
	CHARGED_STRENGTH_CAPE(STRENGTH_CAPE, STRENGTH_CAPET),
	CHARGED_STRENGTH_MIX1(STRENGTH_MIX1, STRENGTH_MIX1, STRENGTH_MIX2),
	CHARGED_STRENGTH_POTION4(STRENGTH_POTION4, STRENGTH_POTION1, STRENGTH_POTION2, STRENGTH_POTION3, STRENGTH_POTION4),
	CHARGED_STUDDED_BODY(STUDDED_BODY, STUDDED_BODY_T),
	CHARGED_STUDDED_CHAPS(STUDDED_CHAPS, STUDDED_CHAPS_T),
	CHARGED_SUPER_ANTIFIRE_MIX2(SUPER_ANTIFIRE_MIX2, SUPER_ANTIFIRE_MIX1, SUPER_ANTIFIRE_MIX2),
	CHARGED_SUPER_ANTIFIRE_POTION4(SUPER_ANTIFIRE_POTION4, SUPER_ANTIFIRE_POTION1, SUPER_ANTIFIRE_POTION2, SUPER_ANTIFIRE_POTION3, SUPER_ANTIFIRE_POTION4),
	CHARGED_SUPER_ATTACK3(SUPER_ATTACK3, SUPER_ATTACK1, SUPER_ATTACK2, SUPER_ATTACK3, SUPER_ATTACK4),
	CHARGED_SUPER_COMBAT_POTION4(SUPER_COMBAT_POTION4, SUPER_COMBAT_POTION1, SUPER_COMBAT_POTION2, SUPER_COMBAT_POTION3, SUPER_COMBAT_POTION4),
	CHARGED_SUPER_DEF_MIX2(SUPER_DEF_MIX2, SUPER_DEF_MIX1, SUPER_DEF_MIX2),
	CHARGED_SUPER_DEFENCE3(SUPER_DEFENCE3, SUPER_DEFENCE1, SUPER_DEFENCE2, SUPER_DEFENCE3, SUPER_DEFENCE4),
	CHARGED_SUPER_ENERGY_MIX2(SUPER_ENERGY_MIX2, SUPER_ENERGY_MIX1, SUPER_ENERGY_MIX2),
	CHARGED_SUPER_ENERGY4(SUPER_ENERGY4, SUPER_ENERGY1, SUPER_ENERGY2, SUPER_ENERGY3, SUPER_ENERGY4),
	CHARGED_SUPER_MAGIC_POTION_4(SUPER_MAGIC_POTION_4, SUPER_MAGIC_POTION_1, SUPER_MAGIC_POTION_2, SUPER_MAGIC_POTION_3, SUPER_MAGIC_POTION_4),
	CHARGED_SUPER_RANGING_4(SUPER_RANGING_4, SUPER_RANGING_1, SUPER_RANGING_2, SUPER_RANGING_3, SUPER_RANGING_4),
	CHARGED_SUPER_RESTORE_MIX2(SUPER_RESTORE_MIX2, SUPER_RESTORE_MIX1, SUPER_RESTORE_MIX2),
	CHARGED_SUPER_RESTORE4(SUPER_RESTORE4, SUPER_RESTORE1, SUPER_RESTORE2, SUPER_RESTORE3, SUPER_RESTORE4),
	CHARGED_SUPER_STR_MIX2(SUPER_STR_MIX2, SUPER_STR_MIX1, SUPER_STR_MIX2),
	CHARGED_SUPER_STRENGTH3(SUPER_STRENGTH3, SUPER_STRENGTH1, SUPER_STRENGTH2, SUPER_STRENGTH3, SUPER_STRENGTH4),
	CHARGED_SUPERANTIPOISON3(SUPERANTIPOISON3, SUPERANTIPOISON1, SUPERANTIPOISON2, SUPERANTIPOISON3, SUPERANTIPOISON4),
	CHARGED_SUPERATTACK_MIX2(SUPERATTACK_MIX2, SUPERATTACK_MIX1, SUPERATTACK_MIX2),
	CHARGED_SUPHI_FISH_1(SUPHI_FISH_1, SUPHI_FISH_1),
	CHARGED_TEAK_SHELVES_1(TEAK_SHELVES_1, TEAK_SHELVES_1, TEAK_SHELVES_2),
	CHARGED_TELEPORT_CRYSTAL_4(TELEPORT_CRYSTAL_4, TELEPORT_CRYSTAL_1, TELEPORT_CRYSTAL_2, TELEPORT_CRYSTAL_3, TELEPORT_CRYSTAL_4, TELEPORT_CRYSTAL_5),
	CHARGED_THIEVING_CAPE(THIEVING_CAPE, THIEVING_CAPET),
	CHARGED_TOMATOES1(TOMATOES1, TOMATOES1, TOMATOES2, TOMATOES3, TOMATOES4, TOMATOES5),
	CHARGED_TOME_OF_EXPERIENCE(TOME_OF_EXPERIENCE, TOME_OF_EXPERIENCE_1, TOME_OF_EXPERIENCE_2, TOME_OF_EXPERIENCE_3),
	CHARGED_TOOL_STORE_1(TOOL_STORE_1, TOOL_STORE_1, TOOL_STORE_2, TOOL_STORE_3, TOOL_STORE_4, TOOL_STORE_5),
	CHARGED_TOPAZ_BOLTS(TOPAZ_BOLTS, TOPAZ_BOLTS_E),
	CHARGED_TOPAZ_DRAGON_BOLTS(TOPAZ_DRAGON_BOLTS, TOPAZ_DRAGON_BOLTS_E),
	CHARGED_TORAGS_HAMMERS(TORAGS_HAMMERS, TORAGS_HAMMERS_0, TORAGS_HAMMERS_100, TORAGS_HAMMERS_25, TORAGS_HAMMERS_50, TORAGS_HAMMERS_75),
	CHARGED_TORAGS_HELM(TORAGS_HELM, TORAGS_HELM_0, TORAGS_HELM_100, TORAGS_HELM_25, TORAGS_HELM_50, TORAGS_HELM_75),
	CHARGED_TORAGS_PLATEBODY(TORAGS_PLATEBODY, TORAGS_PLATEBODY_0, TORAGS_PLATEBODY_100, TORAGS_PLATEBODY_25, TORAGS_PLATEBODY_50, TORAGS_PLATEBODY_75),
	CHARGED_TORAGS_PLATELEGS(TORAGS_PLATELEGS, TORAGS_PLATELEGS_0, TORAGS_PLATELEGS_100, TORAGS_PLATELEGS_25, TORAGS_PLATELEGS_50, TORAGS_PLATELEGS_75),
	CHARGED_TREASONOUS_RING(TREASONOUS_RING, TREASONOUS_RING_I),
	CHARGED_TRIDENT_OF_THE_SEAS(TRIDENT_OF_THE_SEAS, TRIDENT_OF_THE_SEAS_E),
	CHARGED_TRIDENT_OF_THE_SWAMP(TRIDENT_OF_THE_SWAMP, TRIDENT_OF_THE_SWAMP_E),
	CHARGED_TURQUOISE_SLAYER_HELMET(TURQUOISE_SLAYER_HELMET, TURQUOISE_SLAYER_HELMET_I),
	CHARGED_TWISTED_POTION_1(TWISTED_POTION_1, TWISTED_POTION_1, TWISTED_POTION_2, TWISTED_POTION_3, TWISTED_POTION_4),
	CHARGED_TYRANNICAL_RING(TYRANNICAL_RING, TYRANNICAL_RING_I),
	CHARGED_UNCHARGED_TOXIC_TRIDENT(UNCHARGED_TOXIC_TRIDENT, UNCHARGED_TOXIC_TRIDENT_E),
	CHARGED_UNCHARGED_TRIDENT(UNCHARGED_TRIDENT, UNCHARGED_TRIDENT_E),
	CHARGED_VARROCK_ARMOUR(VARROCK_ARMOUR, VARROCK_ARMOUR_1, VARROCK_ARMOUR_2, VARROCK_ARMOUR_3, VARROCK_ARMOUR_4),
	CHARGED_VERACS_BRASSARD(VERACS_BRASSARD, VERACS_BRASSARD_0, VERACS_BRASSARD_100, VERACS_BRASSARD_25, VERACS_BRASSARD_50, VERACS_BRASSARD_75),
	CHARGED_VERACS_FLAIL(VERACS_FLAIL, VERACS_FLAIL_0, VERACS_FLAIL_100, VERACS_FLAIL_25, VERACS_FLAIL_50, VERACS_FLAIL_75),
	CHARGED_VERACS_HELM(VERACS_HELM, VERACS_HELM_0, VERACS_HELM_100, VERACS_HELM_25, VERACS_HELM_50, VERACS_HELM_75),
	CHARGED_VERACS_PLATESKIRT(VERACS_PLATESKIRT, VERACS_PLATESKIRT_0, VERACS_PLATESKIRT_100, VERACS_PLATESKIRT_25, VERACS_PLATESKIRT_50, VERACS_PLATESKIRT_75),
	CHARGED_VIAL_OF_TEARS_EMPTY(VIAL_OF_TEARS_EMPTY, VIAL_OF_TEARS_1, VIAL_OF_TEARS_2, VIAL_OF_TEARS_3),
	CHARGED_VOID_SEAL8(VOID_SEAL8, VOID_SEAL1, VOID_SEAL2, VOID_SEAL3, VOID_SEAL4, VOID_SEAL5, VOID_SEAL6, VOID_SEAL7, VOID_SEAL8),
	CHARGED_WARRIOR_RING(WARRIOR_RING, WARRIOR_RING_I),
	CHARGED_WATERING_CAN(WATERING_CAN, WATERING_CAN1, WATERING_CAN2, WATERING_CAN3, WATERING_CAN4, WATERING_CAN5, WATERING_CAN6, WATERING_CAN7, WATERING_CAN8),
	CHARGED_WATERSKIN4(WATERSKIN4, WATERSKIN0, WATERSKIN1, WATERSKIN2, WATERSKIN3, WATERSKIN4),
	CHARGED_WESTERN_BANNER(WESTERN_BANNER, WESTERN_BANNER_1, WESTERN_BANNER_2, WESTERN_BANNER_3, WESTERN_BANNER_4),
	CHARGED_WILDERNESS_SWORD(WILDERNESS_SWORD, WILDERNESS_SWORD_1, WILDERNESS_SWORD_2, WILDERNESS_SWORD_3, WILDERNESS_SWORD_4),
	CHARGED_WIZARD_ROBE_G(WIZARD_ROBE_G, WIZARD_ROBE_T),
	CHARGED_WOODCUT_CAPET(WOODCUT_CAPET, WOODCUT_CAPET),
	CHARGED_WOODEN_SHELVES_1(WOODEN_SHELVES_1, WOODEN_SHELVES_1, WOODEN_SHELVES_2, WOODEN_SHELVES_3),
	CHARGED_XERICS_AID_1(XERICS_AID_1, XERICS_AID_1_20977, XERICS_AID_2_20978, XERICS_AID_3_20979, XERICS_AID_4_20980),
	CHARGED_YELLOW_SPICE_4(YELLOW_SPICE_4, YELLOW_SPICE_1, YELLOW_SPICE_2, YELLOW_SPICE_3, YELLOW_SPICE_4),
	CHARGED_ZAMORAK_BREW3(ZAMORAK_BREW3, ZAMORAK_BREW1, ZAMORAK_BREW2, ZAMORAK_BREW3, ZAMORAK_BREW4),
	CHARGED_ZAMORAK_MIX2(ZAMORAK_MIX2, ZAMORAK_MIX1, ZAMORAK_MIX2),
	CHARGED_ZAMORAK_PAGE_1(ZAMORAK_PAGE_1, ZAMORAK_PAGE_1, ZAMORAK_PAGE_2, ZAMORAK_PAGE_3, ZAMORAK_PAGE_4);

	private static final Map<Integer, Integer> map;

	static
	{
		Map<Integer, Integer> m = new HashMap<>();
		for (ChargedItem item : values())
		{
			for (int i : item.items)
			{
				m.put(i, item.id);
			}
		}

		map = ImmutableMap.copyOf(m);
	}

	private final int id;
	private final int[] items;

	ChargedItem(int itemId, int... itemIds)
	{
		this.id = itemId;
		this.items = itemIds;
	}

	public static int get(int id)
	{
		return map.getOrDefault(id, id);
	}
}
