package fr.kevingr19.skillcontest.gui.inventory;

import fr.kevingr19.skillcontest.constants.Texts;
import fr.kevingr19.skillcontest.game.GamePlayer;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveFlag;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import fr.kevingr19.skillcontest.utils.InventoryUtil;
import fr.kevingr19.skillcontest.utils.ItemUtil;
import net.minecraft.util.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

/**
 * {@link ClickGUI} that shows the progression of a player's objectives and team objectives.
 */

final public class ObjectiveGUI extends ClickGUI{

    /**
     * Represents the ways of displaying objective logos.
     */

    private enum DisplayMode {
        ALL("§eTous"), DONE("§aFaits"), UNDONE("§cNon-faits");

        private String title;

        DisplayMode(String title){
            this.title = title;
        }

        private static boolean isCompatible(DisplayMode mode, boolean done){
            return mode == ALL || (mode == DONE && done) || (mode == UNDONE && !done);
        }

        private static DisplayMode next(DisplayMode mode){
            switch(mode){
                case ALL -> {return DONE;}
                case DONE -> {return UNDONE;}
                default -> {return ALL;}
            }
        }
    }

    private static final int PAGE_BACK_SLOT = 47;
    private static final int PAGE_NEXT_SLOT = 48;
    private static final int OBJ_FILTER_SLOT = 51;

    private static final Map<UUID, ObjectiveGUI> inventories = new HashMap<>();

    public static void openPlayerInventory(GamePlayer gamePlayer, Player viewer) {
        if(!gamePlayer.hasGameTeam() || !gamePlayer.getGameTeam().isPlaying()) return;

        if(!inventories.containsKey(viewer.getUniqueId()) || !inventories.get(viewer.getUniqueId()).uuid.equals(gamePlayer.getUuid())){
            ObjectiveGUI gui = new ObjectiveGUI(gamePlayer);
            inventories.put(viewer.getUniqueId(), gui);
        }

        inventories.get(viewer.getUniqueId()).openInventory(viewer);
    }

    @Override
    protected Inventory createInventory(String title) {
        return Bukkit.createInventory(null, 54, title);
    }

    private GameTeam team;
    private UUID uuid;

    private final Map<ObjectiveType, Tuple<ItemStack, Boolean>> logos = new HashMap<>();
    private final List<ItemStack> itemList = new ArrayList<>();

    private final List<Tuple<ItemStack, ClickAction>> sortButtonList = new ArrayList<>();
    private int sortButtonStart = 0;

    private ObjectiveFlag currentCategory = ObjectiveFlag.MISC; // Everything except null
    private DisplayMode currentDisplay = DisplayMode.ALL;

    private int page;
    private int maxPage;

    public ObjectiveGUI(GamePlayer gamePlayer){
        super("Objectifs : " + gamePlayer.getGameTeam().team().getColor() + gamePlayer.getName());
        this.team = gamePlayer.getGameTeam();
        this.uuid = gamePlayer.getUuid();

        activeActionMap = new HashMap<>();

        InventoryUtil.fill(inv, ItemUtil.create(Material.BLACK_STAINED_GLASS_PANE, 1, " "));

        activeActionMap.put(PAGE_BACK_SLOT, e -> {
            if(page > 1) setPage(page-1);
        });
        activeActionMap.put(PAGE_NEXT_SLOT, e -> {
            if(page < maxPage) setPage(page+1);
        });

        createSortButtons();
    }

    public void openInventory(Player player){
        updateLogos();
        setSortButtons(0);
        super.openInventory(player);
    }

    private void createSortButtons(){

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.GRASS_BLOCK, 1, String.format(Texts.FILTER_TITLE, "§2Overworld")),
                        e -> changeCategory(ObjectiveFlag.OVERWORLD, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.TUBE_CORAL, 1, String.format(Texts.FILTER_TITLE, "§3Océan")),
                e -> changeCategory(ObjectiveFlag.OCEAN, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.TORCH, 1, String.format(Texts.FILTER_TITLE, "§7Caves")),
                e -> changeCategory(ObjectiveFlag.CAVES, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.NETHERRACK, 1, String.format(Texts.FILTER_TITLE, "§4Nether")),
                e -> changeCategory(ObjectiveFlag.NETHER, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.END_STONE, 1, String.format(Texts.FILTER_TITLE, "§5L'End")),
                e -> changeCategory(ObjectiveFlag.END, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.SPYGLASS, 1, String.format(Texts.FILTER_TITLE, "§bExploration")),
                e -> changeCategory(ObjectiveFlag.EXPLORE, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.MAP, 1, String.format(Texts.FILTER_TITLE, "§bBiomes")),
                e -> changeCategory(ObjectiveFlag.BIOME, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.SPIDER_SPAWN_EGG, 1, String.format(Texts.FILTER_TITLE, "§cMobs")),
                e -> changeCategory(ObjectiveFlag.MOBS, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.TOTEM_OF_UNDYING, 1, String.format(Texts.FILTER_TITLE, "§6Item")),
                e -> changeCategory(ObjectiveFlag.ITEM, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.DEEPSLATE_DIAMOND_ORE, 1, String.format(Texts.FILTER_TITLE, "§eMinerais")),
                e -> changeCategory(ObjectiveFlag.MINERALS, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.COOKED_BEEF, 1, String.format(Texts.FILTER_TITLE, "§aNourriture")),
                e -> changeCategory(ObjectiveFlag.FOOD, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.BREWING_STAND, 1, String.format(Texts.FILTER_TITLE, "§dPotions")),
                e -> changeCategory(ObjectiveFlag.POTION, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.EMERALD, 1, String.format(Texts.FILTER_TITLE, "§2Village")),
                e -> changeCategory(ObjectiveFlag.VILLAGE, currentDisplay)));

        sortButtonList.add(new Tuple<>(ItemUtil.create(Material.PAPER, 1, String.format(Texts.FILTER_TITLE, "Autres")),
                e -> changeCategory(ObjectiveFlag.MISC, currentDisplay)));

        inv.setItem(0, ItemUtil.create(Material.RED_STAINED_GLASS_PANE, 1, "§cFiltres précédents"));
        activeActionMap.put(0, e -> setSortButtons(sortButtonStart-7));

        inv.setItem(8, ItemUtil.create(Material.GREEN_STAINED_GLASS_PANE, 1, "§aFiltres suivants"));
        activeActionMap.put(8, e -> setSortButtons(sortButtonStart+7));

        inv.setItem(50, ItemUtil.create(Material.WHITE_WOOL, 1, "§fListe complète"));
        activeActionMap.put(50, e -> changeCategory(null, currentDisplay));

        activeActionMap.put(OBJ_FILTER_SLOT, e -> {
            if(e != null) changeCategory(currentCategory, DisplayMode.next(currentDisplay));
            inv.setItem(OBJ_FILTER_SLOT, ItemUtil.create(Material.ENDER_EYE, 1,
                    "§fCatégorie : " + currentDisplay.title));
        });
    }

    private void setSortButtons(int start){
        sortButtonStart = Math.max(Math.min(start, sortButtonList.size() - 7), 0);
        for(int i = 0; i < 7; i++){
            inv.setItem(i+1, sortButtonList.get(sortButtonStart+i).getA());
            activeActionMap.put(i+1, sortButtonList.get(sortButtonStart+i).getB());
        }
    }

    private void createLogos(){
        for(ObjectiveType type : ObjectiveType.values()){
            Objective obj = team.getObjective(type);
            ItemStack logo = ItemUtil.clone(obj.logo, obj.titleName);

            updateLogo(obj, logo);
            logos.put(type, new Tuple<>(logo, obj.isDone()));
        }
    }

    private void updateLogos(){
        if(logos.size() != ObjectiveType.values().length) createLogos();
        else{
            for(ObjectiveType type : logos.keySet()) {
                Objective obj = team.getObjective(type);
                logos.get(type).setB(obj.isDone());
                updateLogo(obj, logos.get(type).getA());
            }
        }

        changeCategory(null, DisplayMode.ALL);
    }

    private void updateLogo(Objective obj, final ItemStack logo){
        ItemUtil.setLore(logo, Arrays.asList(obj.getLore(uuid).split("\\R")));
        ItemUtil.setEnchantSkin(logo, obj.isDone());
    }

    private void changeCategory(@Nullable ObjectiveFlag flag, DisplayMode mode){
        if(flag == currentCategory && mode == currentDisplay) {
            setPage(1);
            return;
        }
        currentCategory = flag;
        currentDisplay = mode;
        activeActionMap.get(OBJ_FILTER_SLOT).invoke(null);

        itemList.clear();

        for(ObjectiveType type : ObjectiveType.sortedObjectives){
            if((flag == null || type.flags.contains(flag)) && DisplayMode.isCompatible(mode, logos.get(type).getB()))
                itemList.add(logos.get(type).getA());
        }

        maxPage = (int) Math.ceil(itemList.size() / 18f);
        setPage(1);
    }

    private void setPage(int page){
        if(maxPage == 0){
            this.page = 1;
            for(int i = 0; i < 18; i++) inv.setItem(18 + i, null);
        }
        else {
            this.page = Math.max(Math.min(page, maxPage), 1);
            for (int i = 0; i < 18; i++) {
                final int list_i = (this.page-1)*18 + i;
                inv.setItem(18 + i, list_i < itemList.size() ? itemList.get(list_i) : null);
            }
        }

        inv.setItem(PAGE_BACK_SLOT, ItemUtil.create(Material.GREEN_WOOL, 1,
                page > 1 ? "§cAller à la page " + (page-1) : "§7Page minimum"));

        inv.setItem(PAGE_NEXT_SLOT, ItemUtil.create(Material.RED_WOOL, 1,
                page < maxPage ? "§aAller à la page " + (page+1) : "§7Page maximum"));
    }

}
