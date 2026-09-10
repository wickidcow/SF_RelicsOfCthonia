package ne.fnfal113.relicsofcthonia.listeners;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.PiglinBarterDrop;
import ne.fnfal113.relicsofcthonia.RelicsOfCthonia;
import ne.fnfal113.relicsofcthonia.RelicsRegistry;
import ne.fnfal113.relicsofcthonia.slimefun.relics.AbstractRelic;
import ne.fnfal113.relicsofcthonia.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static ne.fnfal113.relicsofcthonia.core.Keys.CURRENTLY_TRADING_MATERIAL;
import static ne.fnfal113.relicsofcthonia.core.Keys.CURRENTLY_TRADING_PLAYER;

public class PiglinMainListener implements Listener {

    private static final Map<UUID, ItemStack> DROPPING_ITEM = new ConcurrentHashMap<>();

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void cancelInteractingWhileTrading(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Piglin piglin && isCurrentlyTradingRelic(piglin)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPiglinRightClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        if (!(event.getRightClicked() instanceof Piglin piglin) || !piglin.isAdult() || piglin.hasMetadata("NPC")
                || event.getHand() == EquipmentSlot.OFF_HAND || !(SlimefunItem.getByItem(mainHandItem) instanceof AbstractRelic)) {
            return;
        }

        clearTradingState(piglin);

        // Heads cannot be traded by default so we temporarily add the relic's material as a barter material.
        Material type = mainHandItem.getType();
        piglin.addBarterMaterial(type);
        setCurrentlyTradingPlayer(piglin, player, type);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBarter(PiglinBarterEvent event) {
        Piglin piglin = event.getEntity();
        UUID uuid = clearTradingState(piglin);
        if (uuid == null) {
            return;
        }

        Optional<Player> player = Optional.ofNullable(Bukkit.getPlayer(uuid));
        if (event.isCancelled()) {
            player.ifPresent(p -> Utils.sendRelicMessage("&cThe trade was cancelled by an external source!", p));
            return;
        }
        if (!(SlimefunItem.getByItem(event.getInput()) instanceof AbstractRelic relic)) {
            RelicsOfCthonia.getInstance().getLogger().severe("Something modified the piglin's input item while a RelicsOfCthonia trade was in progress");
            player.ifPresent(p -> Utils.sendRelicMessage("&cThe trade was cancelled by an external source!", p));
            return;
        }

        if (ThreadLocalRandom.current().nextInt(100) > AbstractRelic.getRelicCondition(event.getInput())) {
            piglin.getWorld().spawnParticle(Particle.ANGRY_VILLAGER, piglin.getLocation().add(0, 2.2, 0), 0);
            piglin.getWorld().playSound(piglin.getLocation(), Sound.ENTITY_PIGLIN_ANGRY, 1.0F, 1.0F);
            return;
        }

        List<ItemStack> rewards = RelicsRegistry.RELIC_OUTPUTS.get(relic);
        if (rewards == null || rewards.isEmpty()) {
            RelicsOfCthonia.getInstance().getLogger().severe("The relic " + relic.getId() + " does not have a corresponding reward list");
            player.ifPresent(p -> Utils.sendRelicMessage("&cThe relic has no configured rewards, notify your server!", p));
            return;
        }

        int rewardIndex = ThreadLocalRandom.current().nextInt(0, rewards.size());
        ItemStack reward = rewards.get(rewardIndex).clone();
        reward.setAmount(relic.getPiglinRewardAmount());
        event.getOutcome().clear();
        event.getOutcome().add(reward);
        DROPPING_ITEM.put(piglin.getUniqueId(), reward);

        piglin.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, piglin.getLocation().add(0, 2.2, 0), 0);
        piglin.getWorld().playSound(piglin.getLocation(), Sound.ENTITY_PIGLIN_ADMIRING_ITEM, 1.0F, 1.0F);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Piglin piglin) {
            clearTradingState(piglin);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDrop(EntityDropItemEvent event) {
        ItemStack droppingItem = DROPPING_ITEM.remove(event.getEntity().getUniqueId());
        if (droppingItem != null && SlimefunItem.getByItem(event.getItemDrop().getItemStack()) instanceof PiglinBarterDrop) {
            event.getItemDrop().setItemStack(droppingItem);
        }
    }

    private void setCurrentlyTradingPlayer(Piglin piglin, Player player, Material material) {
        PersistentDataContainer data = piglin.getPersistentDataContainer();
        data.set(CURRENTLY_TRADING_PLAYER, PersistentDataType.STRING, player.getUniqueId().toString());
        data.set(CURRENTLY_TRADING_MATERIAL, PersistentDataType.STRING, material.getKey().toString());
    }

    private UUID clearTradingState(Piglin piglin) {
        UUID uuid = getCurrentlyTradingPlayer(piglin);
        PersistentDataContainer data = piglin.getPersistentDataContainer();

        String materialKey = data.get(CURRENTLY_TRADING_MATERIAL, PersistentDataType.STRING);
        if (materialKey != null) {
            Material material = Material.matchMaterial(materialKey);
            if (material != null) {
                piglin.removeBarterMaterial(material);
            }
        }

        data.remove(CURRENTLY_TRADING_PLAYER);
        data.remove(CURRENTLY_TRADING_MATERIAL);
        return uuid;
    }

    private UUID getCurrentlyTradingPlayer(Piglin piglin) {
        String idString = piglin.getPersistentDataContainer().get(CURRENTLY_TRADING_PLAYER, PersistentDataType.STRING);
        if (idString == null) {
            return null;
        }
        try {
            return UUID.fromString(idString);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private boolean isCurrentlyTradingRelic(Piglin piglin) {
        return getCurrentlyTradingPlayer(piglin) != null
                && SlimefunItem.getByItem(piglin.getEquipment().getItemInOffHand()) instanceof AbstractRelic;
    }
}
