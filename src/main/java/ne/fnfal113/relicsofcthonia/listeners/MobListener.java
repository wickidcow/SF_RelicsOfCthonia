package ne.fnfal113.relicsofcthonia.listeners;

import ne.fnfal113.relicsofcthonia.RelicsRegistry;
import ne.fnfal113.relicsofcthonia.core.Keys;
import ne.fnfal113.relicsofcthonia.slimefun.relics.AbstractRelic;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MobListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMobSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER
                && event.getEntity().getWorld().getEnvironment() == World.Environment.NETHER) {
            event.getEntity().getPersistentDataContainer().set(Keys.SPAWNER_MOB, PersistentDataType.BYTE, (byte) 1);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMobKill(EntityDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        World world = livingEntity.getWorld();
        if (livingEntity.getKiller() == null || world.getEnvironment() != World.Environment.NETHER
                || livingEntity.getPersistentDataContainer().has(Keys.SPAWNER_MOB, PersistentDataType.BYTE)) {
            return;
        }

        List<AbstractRelic> registeredRelics = RelicsRegistry.ENTITY_SOURCES.get(livingEntity.getType());
        if (registeredRelics == null || registeredRelics.isEmpty()) {
            return;
        }

        int dropped = 0;
        List<AbstractRelic> relics = new ArrayList<>(registeredRelics);
        Collections.shuffle(relics);
        for (AbstractRelic relic : relics) {
            if (relic.isDisabledIn(world) || relic.isDisabled()) {
                continue;
            }

            if (ThreadLocalRandom.current().nextDouble() < relic.getDropChance()) {
                ItemStack drop = relic.randomRelic();
                world.dropItemNaturally(livingEntity.getLocation(), drop);
                if (++dropped >= 2) {
                    break;
                }
            }
        }
    }
}
