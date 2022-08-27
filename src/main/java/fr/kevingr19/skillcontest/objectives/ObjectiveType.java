package fr.kevingr19.skillcontest.objectives;

import fr.kevingr19.skillcontest.objectives.list.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
>>>>>>> bb6c13e8b9991e60589eac36b39ae084fa7ee097

/**
 * Enum of all objectives, with their constructor, flags, and points.
 */

public enum ObjectiveType {
    CRAFTING_TABLE(CraftingTableObj.class, 5, ObjectiveFlag.OVERWORLD, ObjectiveFlag.ITEM),

    BREAD_CRAFT(BreadCraftObj.class, 5, ObjectiveFlag.ITEM),

    ENTER_BOAT(EnterBoatObj.class, 5, ObjectiveFlag.OCEAN, ObjectiveFlag.ITEM, ObjectiveFlag.MISC),

    BREWING_STAND(BrewingStandObj.class, 5, ObjectiveFlag.ITEM, ObjectiveFlag.POTION),

    BREW_POTIONS(BrewPotionsObj.class, 75, ObjectiveFlag.POTION),

    INVISIBILITY(InvisibilityObj.class, 35, ObjectiveFlag.POTION),

    SIGN_BOOK(SignBookObj.class, 20, ObjectiveFlag.ITEM, ObjectiveFlag.MISC),

    BIG_MAP(BigMapObj.class, 30, ObjectiveFlag.OVERWORLD, ObjectiveFlag.ITEM, ObjectiveFlag.MISC),

    SHIELD_STYLE(ShieldStyleObj.class, 35, ObjectiveFlag.ITEM, ObjectiveFlag.MISC),

    ROTTEN_EATER(RottenFleshObj.class, 45, ObjectiveFlag.FOOD),

    ENCHANTED_APPLE(EnchantedAppleObj.class, 40, ObjectiveFlag.FOOD),

    SUS_SOUPS(SusSoupsObj.class, 60, ObjectiveFlag.FOOD, ObjectiveFlag.POTION),

    CAKE_CRAFT(CakeCraftObj.class, 55, ObjectiveFlag.FOOD, ObjectiveFlag.ITEM),

    ALL_FOODS(AllFoodsObj.class, 135, ObjectiveFlag.FOOD),

    JEB_KILL(JebKillObj.class, 15, ObjectiveFlag.MISC, ObjectiveFlag.MOBS),

    ARROW_KILL(ArrowMobKillObj.class, 65, ObjectiveFlag.MISC, ObjectiveFlag.MOBS),

    FISH(FishObj.class, 35, ObjectiveFlag.FOOD, ObjectiveFlag.MISC, ObjectiveFlag.OCEAN, ObjectiveFlag.OVERWORLD),

    FARM_MOBS_KILL(FarmMobsKillObj.class, 25, ObjectiveFlag.FOOD, ObjectiveFlag.MOBS, ObjectiveFlag.OVERWORLD),

    DONKEY_CHEST(DonkeyChestObj.class, 25, ObjectiveFlag.MISC, ObjectiveFlag.MOBS, ObjectiveFlag.OVERWORLD),

    BREED_MASTER(BreedMasterObj.class, 55, ObjectiveFlag.MOBS),

    BREED_COCKTAIL(BreedCocktailObj.class, 155, ObjectiveFlag.MOBS, ObjectiveFlag.BIOME),

    STAR_FIREWORK(StarFireworkObj.class, 30, ObjectiveFlag.MISC),

    ALL_LOGS(AllLogsObj.class, 60, ObjectiveFlag.OVERWORLD, ObjectiveFlag.NETHER, ObjectiveFlag.BIOME, ObjectiveFlag.ITEM),

    MINESHAFT(Mineshaftobj.class, 25, ObjectiveFlag.CAVES, ObjectiveFlag.EXPLORE),

    ALL_MINECARTS(AllMinecartsObj.class, 50, ObjectiveFlag.MINERALS, ObjectiveFlag.ITEM),

    MINECART_TRAVEL(MinecartTravelObj.class, 60, ObjectiveFlag.MISC),

    ALL_RIDEABLE(AllRideableObj.class, 60, ObjectiveFlag.ITEM, ObjectiveFlag.MOBS, ObjectiveFlag.MISC),

    IRON_PICKAXE(IronPickaxeObj.class, 20, ObjectiveFlag.MINERALS, ObjectiveFlag.ITEM),

    TORCH_CRAFT(TorchCraftObj.class, 20, ObjectiveFlag.MINERALS, ObjectiveFlag.CAVES, ObjectiveFlag.ITEM),

    DEEPSLATE_BREAK(DeepslateBreakObj.class, 20, ObjectiveFlag.MINERALS),

    DEEPSLATE_STACKS(DeepslateStacksObj.class, 60, ObjectiveFlag.MINERALS),

    DIAMOND(DiamondObj.class, 40, ObjectiveFlag.MINERALS),

    IRON_STAND(IronStandObj.class, 40, ObjectiveFlag.MINERALS, ObjectiveFlag.MISC, ObjectiveFlag.ITEM),

    FURNACE_ORES(FurnaceOresObj.class, 50, ObjectiveFlag.MINERALS, ObjectiveFlag.ITEM),

    ENCHANT_TABLE(EnchantTableObj.class, 25, ObjectiveFlag.MINERALS, ObjectiveFlag.ITEM),

    ANVIL_EXP(AnvilExpObj.class, 50, ObjectiveFlag.MINERALS, ObjectiveFlag.ITEM, ObjectiveFlag.MISC),

    MAX_ENCHANT(MaxEnchantObj.class, 75, ObjectiveFlag.MINERALS, ObjectiveFlag.ITEM, ObjectiveFlag.MISC),

    SMITH_ITEM(SmithItemObj.class, 75, ObjectiveFlag.MINERALS, ObjectiveFlag.ITEM),

    GOLD_TOOL_BREAK(GoldToolBreakObj.class, 50, ObjectiveFlag.MISC, ObjectiveFlag.ITEM, ObjectiveFlag.MINERALS),

    DIAMOND_ARMOR(DiamondArmorObj.class, 60, ObjectiveFlag.MINERALS, ObjectiveFlag.ITEM),

    SHIELD_BLOCK(ShieldBlockObj.class, 25, ObjectiveFlag.CAVES, ObjectiveFlag.MOBS, ObjectiveFlag.MISC),

    SCULK_SHRIEKER_TRIGGER(SculkShriekerTriggerObj.class, 30, ObjectiveFlag.CAVES, ObjectiveFlag.BIOME, ObjectiveFlag.MISC),

    SPAWNER_BREAK(CaveSpawnerBreakObj.class, 40, ObjectiveFlag.CAVES),

    AXOLOTL_BUCKET(AxolotlBucketObj.class, 50, ObjectiveFlag.CAVES, ObjectiveFlag.MOBS),

    ALL_CAVE_BIOMES(AllCaveBiomesObj.class, 65, ObjectiveFlag.CAVES, ObjectiveFlag.BIOME, ObjectiveFlag.EXPLORE),

    SCULK_SENSOR_BREAK(SculkSensorBreakObj.class, 65, ObjectiveFlag.CAVES, ObjectiveFlag.BIOME),

    TOTEM_USE(TotemUseObj.class, 65, ObjectiveFlag.ITEM),

    GREAT_FALL(GreatFallObj.class, 80, ObjectiveFlag.OVERWORLD, ObjectiveFlag.MISC),

    REDSTONE_EXPERT(RedstoneExpertObj.class, 100, ObjectiveFlag.ITEM, ObjectiveFlag.MINERALS),

    MANSION(MansionObj.class, 145, ObjectiveFlag.OVERWORLD, ObjectiveFlag.EXPLORE),

    POTATO_CHEST(PotatoChestObj.class, 160, ObjectiveFlag.OVERWORLD, ObjectiveFlag.FOOD, ObjectiveFlag.ITEM),

    ENTER_NETHER(EnterNetherObj.class, 20, ObjectiveFlag.OVERWORLD, ObjectiveFlag.NETHER),

    NETHER_ROOF(NetherRoofObj.class, 50, ObjectiveFlag.NETHER, ObjectiveFlag.MISC, ObjectiveFlag.EXPLORE),

    FORTRESS(FortressObj.class, 25, ObjectiveFlag.NETHER, ObjectiveFlag.EXPLORE),

    BASTION(BastionObj.class, 25, ObjectiveFlag.NETHER, ObjectiveFlag.EXPLORE),

    BASTION_CHEST(BastionChestObj.class, 25, ObjectiveFlag.NETHER, ObjectiveFlag.EXPLORE),

    PIGLIN_BRUTE_KILL(BruteKillObj.class, 55, ObjectiveFlag.NETHER, ObjectiveFlag.MOBS),

    PIGLIN_GOLD(PiglinGoldObj.class, 40, ObjectiveFlag.NETHER, ObjectiveFlag.MOBS, ObjectiveFlag.ITEM, ObjectiveFlag.MISC),

    BLAZE_KILL(BlazeKillObj.class, 50, ObjectiveFlag.NETHER, ObjectiveFlag.MOBS),

    WITHER_SKELETON_POTION(WitherSkeletonPotionObj.class, 50, ObjectiveFlag.NETHER, ObjectiveFlag.MOBS, ObjectiveFlag.POTION),

    ZOGLIN_KILL(ZoglinKillObj.class, 50, ObjectiveFlag.NETHER, ObjectiveFlag.MOBS, ObjectiveFlag.MISC),

    GHAST_RETURN_SENDER(GhastReturnSenderObj.class, 75, ObjectiveFlag.NETHER, ObjectiveFlag.MOBS),

    ANCHOR_CHARGE(AnchorChargeObj.class, 80, ObjectiveFlag.NETHER, ObjectiveFlag.ITEM, ObjectiveFlag.MISC),

    WITHER_SPAWN(WitherSpawnObj.class, 95, ObjectiveFlag.NETHER, ObjectiveFlag.MOBS, ObjectiveFlag.MISC),

    ZOMBIE_PIGLIN_KILL(ZombiePiglinObj.class, 170, ObjectiveFlag.NETHER, ObjectiveFlag.MOBS),

    WARDEN_KILL(WardenKillObj.class, 160, ObjectiveFlag.CAVES, ObjectiveFlag.MOBS),

    TNT_KILL(TntKillObj.class, 75, ObjectiveFlag.MOBS, ObjectiveFlag.MISC),

    DOLPHIN_GRACE(DolphinGraceObj.class, 20, ObjectiveFlag.OCEAN, ObjectiveFlag.MOBS, ObjectiveFlag.MISC),

    PUFFERFISH_EAT(PufferfishEatObj.class, 25, ObjectiveFlag.OCEAN, ObjectiveFlag.FOOD),

    DROWNED_KILL(DrownedKillObj.class, 55, ObjectiveFlag.OCEAN, ObjectiveFlag.MOBS),

    OCEAN_STRUCTURES(OceanStructuresObj.class, 80, ObjectiveFlag.OCEAN, ObjectiveFlag.EXPLORE),

    JUNGLE_TEMPLE(JungleTempleObj.class, 30, ObjectiveFlag.BIOME, ObjectiveFlag.OVERWORLD, ObjectiveFlag.EXPLORE),

    COOKIE(CookieObj.class, 25, ObjectiveFlag.BIOME, ObjectiveFlag.ITEM),

    PARROT_DANCE(ParrotDanceObj.class, 75, ObjectiveFlag.BIOME, ObjectiveFlag.MOBS, ObjectiveFlag.MISC),

    BELL_RING(RingBellObj.class, 10, ObjectiveFlag.OVERWORLD, ObjectiveFlag.VILLAGE, ObjectiveFlag.MISC),

    BERRY_STEP(BerryStepObj.class, 10, ObjectiveFlag.OVERWORLD, ObjectiveFlag.BIOME),

    WOLF_TAME(WolfTameObj.class, 25, ObjectiveFlag.OVERWORLD, ObjectiveFlag.BIOME, ObjectiveFlag.MOBS),

    FOX_KILL(FoxKillObj.class, 50, ObjectiveFlag.OVERWORLD, ObjectiveFlag.BIOME, ObjectiveFlag.MOBS),

    CAULDRON_SNOW(CauldronSnowObj.class, 20, ObjectiveFlag.BIOME, ObjectiveFlag.MISC),

    GOAT_HORN(GoatHornObj.class, 25, ObjectiveFlag.BIOME, ObjectiveFlag.ITEM),

    IGLOO(IglooObj.class, 30, ObjectiveFlag.OVERWORLD, ObjectiveFlag.BIOME, ObjectiveFlag.EXPLORE),

    BLUE_ICE_CRAFT(BlueIceCraftObj.class, 65, ObjectiveFlag.BIOME, ObjectiveFlag.ITEM),

    DESERT_TREE_GROW(DesertTreeGrowObj.class, 25, ObjectiveFlag.OVERWORLD, ObjectiveFlag.BIOME),

    DESERT_PYRAMID(DesertPyramidObj.class, 30, ObjectiveFlag.OVERWORLD, ObjectiveFlag.BIOME, ObjectiveFlag.EXPLORE),

    SAND_CHEST(SandChestObj.class, 75, ObjectiveFlag.BIOME, ObjectiveFlag.ITEM),

    BEE_BREED(BeeBreedObj.class, 20, ObjectiveFlag.OVERWORLD, ObjectiveFlag.BIOME, ObjectiveFlag.MOBS),

    HONEY_BOTTLE_POISON(HoneyBottlePoisonObj.class, 35, ObjectiveFlag.BIOME, ObjectiveFlag.MISC),

    WAXED_COPPER_BLOCK(WaxedCopperBlockObj.class, 65, ObjectiveFlag.BIOME, ObjectiveFlag.ITEM),

    TRADE(TradeObj.class, 15, ObjectiveFlag.VILLAGE),

    IRON_GOLEM_KILL(IronGolemKillObj.class, 15, ObjectiveFlag.VILLAGE, ObjectiveFlag.OVERWORLD, ObjectiveFlag.MOBS),

    VILLAGER_CURED(ZombieVillagerCuredObj.class, 60, ObjectiveFlag.VILLAGE, ObjectiveFlag.MISC, ObjectiveFlag.MOBS),

    PILLAGER_CHIEF_KILL(PillagerChiefKillObj.class, 50, ObjectiveFlag.VILLAGE, ObjectiveFlag.OVERWORLD, ObjectiveFlag.MOBS),

    PILLAGER_OUTPOST(PillagerOutpostObj.class, 30, ObjectiveFlag.VILLAGE, ObjectiveFlag.OVERWORLD, ObjectiveFlag.EXPLORE),

    TRADE_MASTER(TradeMasterObj.class, 85, ObjectiveFlag.VILLAGE),

    RAID_VICTORY(RaidVictoryObj.class, 105, ObjectiveFlag.VILLAGE, ObjectiveFlag.OVERWORLD, ObjectiveFlag.MOBS),

    ALL_VILLAGERS(AllVillagersObj.class, 110, ObjectiveFlag.VILLAGE),

    ALL_OVERWORLD_ORES(AllOverworldOresObj.class, 50, ObjectiveFlag.OVERWORLD, ObjectiveFlag.MINERALS),

    ALL_ARMORS(AllArmorsObj.class, 210, ObjectiveFlag.ITEM, ObjectiveFlag.MINERALS),

    ALL_TROPICAL_FISH(AllTropicalFishObj.class, 80, ObjectiveFlag.MOBS, ObjectiveFlag.OCEAN),

    EMERALDS(EmeraldsObj.class, 180, ObjectiveFlag.VILLAGE, ObjectiveFlag.ITEM),

    SWIM_TRAVEL(SwimTravelObj.class, 70, ObjectiveFlag.OCEAN, ObjectiveFlag.MISC),

    SUPER_HERO(SuperheroObj.class, 145, ObjectiveFlag.POTION),

    ELDER_GUARDIAN_KILL(ElderGuardianKillObj.class, 105, ObjectiveFlag.OCEAN, ObjectiveFlag.MOBS),

    ANCIENT_CITY(AncientCityObj.class, 50, ObjectiveFlag.CAVES, ObjectiveFlag.EXPLORE),

    CITY_PILLAGE(CityPillageObj.class, 85, ObjectiveFlag.CAVES, ObjectiveFlag.EXPLORE),

    STRONGHOLD(StrongholdObj.class, 30, ObjectiveFlag.OVERWORLD, ObjectiveFlag.END, ObjectiveFlag.EXPLORE),

    ENTER_THE_END(EnterTheEndObj.class, 30, ObjectiveFlag.OVERWORLD, ObjectiveFlag.END),

    END_CITY(EndCityObj.class, 40, ObjectiveFlag.END, ObjectiveFlag.EXPLORE),

    ELYTRA(ElytraObj.class, 55, ObjectiveFlag.END, ObjectiveFlag.ITEM),

    END_GATEWAY_TP(EndGatewayTpObj.class, 60, ObjectiveFlag.END, ObjectiveFlag.MISC),

    ENDERMAN_TARGET(EndermanTargetObj.class, 60, ObjectiveFlag.END, ObjectiveFlag.MOBS, ObjectiveFlag.MISC),

    ENDER_DRAGON_KILL(EnderDragonKillObj.class, 75, ObjectiveFlag.END, ObjectiveFlag.MOBS),

    ENDER_DRAGON_RESPAWN(EnderDragonRespawnObj.class, 75, ObjectiveFlag.END, ObjectiveFlag.MOBS),

    ELYTRA_TRAVEL(ElytraTravelObj.class, 85, ObjectiveFlag.END, ObjectiveFlag.MISC),

    BEACON_EFFECT(BeaconEffectObj.class, 120, ObjectiveFlag.MISC, ObjectiveFlag.MINERALS, ObjectiveFlag.POTION),

    CONDUIT_POWER(ConduitPowerObj.class, 220, ObjectiveFlag.OCEAN, ObjectiveFlag.ITEM, ObjectiveFlag.MISC),

    ALL_SHULKER_BOX_COLORS(AllShulkerBoxColorsObj.class, 100, ObjectiveFlag.END, ObjectiveFlag.ITEM),

    ALL_OCEAN_BIOMES(AllOceanBiomesObj.class, 90, ObjectiveFlag.OCEAN, ObjectiveFlag.BIOME, ObjectiveFlag.EXPLORE),

    ALL_NETHER_BIOMES(AllNetherBiomesObj.class, 90, ObjectiveFlag.NETHER, ObjectiveFlag.BIOME, ObjectiveFlag.EXPLORE),

    ALL_OVERWORLD_BIOMES(AllOverworldBiomesObj.class, 275, ObjectiveFlag.OVERWORLD, ObjectiveFlag.BIOME, ObjectiveFlag.EXPLORE);

    public static final ObjectiveType[] sortedObjectives;

    static{
        sortedObjectives = ObjectiveType.values();
        Arrays.sort(sortedObjectives, Comparator.comparingInt(o -> o.points));
    }

    public final Class<? extends Objective> objClass;
    public final int points;
    public final Set<ObjectiveFlag> flags;

    ObjectiveType(Class<? extends Objective> constructor, int points, ObjectiveFlag... flags){
        this.objClass = constructor;
        this.points = points;

        this.flags = new HashSet<>();
        this.flags.addAll(Arrays.asList(flags));
    }

}
