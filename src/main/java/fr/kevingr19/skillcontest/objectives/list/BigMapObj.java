package fr.kevingr19.skillcontest.objectives.list;

import fr.kevingr19.skillcontest.Main;
import fr.kevingr19.skillcontest.event.ResultItemTakenEvent;
import fr.kevingr19.skillcontest.game.GameTeam;
import fr.kevingr19.skillcontest.objectives.Objective;
import fr.kevingr19.skillcontest.objectives.ObjectiveType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class BigMapObj extends Objective {

    private static BufferedImage image = null;
    public static void loadImage(String url){
        try{
            image = ImageIO.read(new URL(url));
            image = MapPalette.resizeImage(image);
            Main.print("Map Image loaded");
        }
        catch (Exception ex){
            Main.print("Could not load MapImage.");
            ex.printStackTrace();
        }
    }

    private static ItemStack replaceByImageMap(){

        ItemStack item = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) item.getItemMeta();

        MapView view = Bukkit.createMap(Bukkit.getWorlds().get(0));
        view.setScale(MapView.Scale.FARTHEST);
        view.setLocked(true);
        view.getRenderers().clear();

        view.addRenderer(new MapRenderer() {
            @Override
            public void render(MapView map, MapCanvas canvas, Player player) {
                canvas.drawImage(0, 0, image);
            }
        });

        meta.setMapView(view);
        item.setItemMeta(meta);
        return item;
    }

    public BigMapObj(GameTeam gameTeam) {
        super(ObjectiveType.BIG_MAP, Rarity.EASY, "Dézoom parfait...",
                "Fabriquer une carte de taille maximale.",
                "Vous devriez utiliser une carte\nsans trop d'importance...",
                new ItemStack(Material.FILLED_MAP), false, gameTeam);
    }

    @EventHandler
    public void onMapCraft(final ResultItemTakenEvent e){
        if(!isInTeam(e.getPlayer())) return;
        if(e.getInventory().getType() != InventoryType.CARTOGRAPHY) return;

        if(e.getResult().getType() == Material.FILLED_MAP)
        {
            final MapMeta meta = (MapMeta) e.getResult().getItemMeta();
            if(!meta.hasMapView() || meta.getMapView().getScale() != MapView.Scale.FAR) return;

            final ItemStack item = e.getInventory().getItem(1);
            if(item != null && item.getType() == Material.PAPER){
                complete(e.getPlayer());
                if(image != null) e.setResult(replaceByImageMap());
            }
        }
    }

}
