package me.alvsch.techjet.listener;

import lombok.RequiredArgsConstructor;
import me.alvsch.techjet.TechJetRegistry;
import me.alvsch.techjet.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

@RequiredArgsConstructor
public class SilentContainerListener implements Listener {

    private final TechJetRegistry registry;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        Block block = event.getClickedBlock();
        if(block == null || block.getType().isAir() || !Utils.isValidContainer(block)) {
            return;
        }

        Player player = event.getPlayer();
        if(registry.getSilentContainer().contains(player.getUniqueId())) {
            // Silently open container
            Container container = (Container) event.getClickedBlock().getState();
            Inventory inv = Bukkit.createInventory(null, container.getInventory().getSize(), "Silent Container");
            inv.setContents(container.getInventory().getContents());
            player.openInventory(inv);

            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.ENTITY_WARDEN_NEARBY_CLOSER, 1, 1);
            player.sendMessage(Utils.color("&7Opened container silently"));
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getView().getTitle().equals("Silent Container")) {
            event.setCancelled(true);
        }
    }

}
