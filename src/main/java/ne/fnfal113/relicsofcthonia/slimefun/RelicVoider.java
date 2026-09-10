package ne.fnfal113.relicsofcthonia.slimefun;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import ne.fnfal113.relicsofcthonia.RelicsOfCthonia;
import ne.fnfal113.relicsofcthonia.api.Rarity;
import ne.fnfal113.relicsofcthonia.core.Keys;
import ne.fnfal113.relicsofcthonia.slimefun.relics.AbstractRelic;
import ne.fnfal113.relicsofcthonia.utils.Utils;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class RelicVoider extends UnplaceableBlock {

    private final boolean notifEnabled = RelicsOfCthonia.getInstance().getConfig().getBoolean("enable-relic-voider-notif", true);
    private final Rarity rarity;

    public RelicVoider(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, Rarity rarity) {
        super(itemGroup, item, recipeType, recipe);
        this.rarity = rarity;
    }

    public static void setConditionQuota(ItemStack itemStack, int quota) {
        itemStack.editMeta(meta -> meta.getPersistentDataContainer().set(Keys.RELIC_CONDITION_QUOTA, PersistentDataType.INTEGER, quota));
    }

    public static int getConditionQuota(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            return 1;
        }
        Integer quota = meta.getPersistentDataContainer().get(Keys.RELIC_CONDITION_QUOTA, PersistentDataType.INTEGER);
        return quota == null ? 1 : quota;
    }

    public boolean onRelicPickup(EntityPickupItemEvent event, ItemStack voider, AbstractRelic relic, Item relicItem) {
        if (relic.getRarity().ordinal() > rarity.ordinal()) {
            return false;
        }

        if (AbstractRelic.getRelicCondition(relicItem.getItemStack()) <= getConditionQuota(voider)) {
            if (notifEnabled) {
                Utils.sendRelicMessage("&6Voided " + "&r" + relic.getItemName(), event.getEntity());
            }
            relicItem.remove();
            event.setCancelled(true);
            return true;
        }
        return false;
    }
}
