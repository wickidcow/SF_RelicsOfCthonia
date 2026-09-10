package ne.fnfal113.relicsofcthonia.core;

import ne.fnfal113.relicsofcthonia.RelicsOfCthonia;
import org.bukkit.NamespacedKey;

public class Keys {
    public static final String PLACED_BLOCK = "placed_block";

    public static final NamespacedKey SPAWNER_MOB = createKey("relic_spawned_mob");
    public static final NamespacedKey CURRENTLY_TRADING_PLAYER = createKey("currently_trading_player");
    public static final NamespacedKey RELIC_CONDITION = createKey("relic_condition");
    public static final NamespacedKey RELIC_CONDITION_QUOTA = createKey("condition_quota");

    private static NamespacedKey createKey(String id) {
        return new NamespacedKey(RelicsOfCthonia.getInstance(), id.toLowerCase());
    }
}
