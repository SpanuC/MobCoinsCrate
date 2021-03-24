package me.peNNNk.MobCoinsCrate.Events;

import com.chup.mobcoinsplus.extras.Extras;
import me.peNNNk.MobCoinsCrate.Main;
import me.peNNNk.MobCoinsCrate.Utils.ColorUtil;
import me.badbones69.crazycrates.api.CrazyCrates;
import me.badbones69.crazycrates.api.enums.KeyType;
import me.badbones69.crazycrates.api.objects.Crate;
import me.badbones69.crazycrates.controllers.Preview;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClickEvent implements Listener {
    @EventHandler
    public void clickr(final NPCRightClickEvent e) {
        if (Main.getInstance().getConfig().isSet("commands") && Main.getInstance().getConfig().isConfigurationSection("commands")) {
            final Player p = e.getClicker();
            final NPC npc = e.getNPC();
            for (final String s : Main.getInstance().getConfig().getConfigurationSection("commands").getKeys(false)) {
                if (npc.getId() == Main.getInstance().getConfig().getInt("commands." + s + ".npc")) {
                    if (Extras.getCoins(p.getUniqueId()).intValue() < Main.getInstance().getConfig().getInt("commands." + s + ".coins")) {
                        if (Main.getInstance().getConfig().getBoolean("commands." + s + ".no-coins-message"))
                            for (final String s2 : Main.getInstance().getConfig().getStringList("commands." + s + ".no-coins-messages"))
                                p.sendMessage(ColorUtil.get(s2.replace("%coins%", String.valueOf(Extras.getCoins(p.getUniqueId())))));
                        return;
                    }
                    final Crate crate = CrazyCrates.getInstance().getCrateFromName(Main.getInstance().getConfig().getString("commands." + s + ".crate"));
                    if (CrazyCrates.getInstance().isInOpeningList(p)) {
                        return;
                    }
                    CrazyCrates.getInstance().openCrate(p, crate, KeyType.FREE_KEY, p.getLocation(), true, false);
                    Extras.removeCoins(e.getClicker(), Main.getInstance().getConfig().getInt("commands." + s + ".coins"));
                    if (!Main.getInstance().getConfig().getBoolean("commands." + s + ".enable-messages"))
                        continue;
                    for (final String s3 : Main.getInstance().getConfig().getStringList("commands." + s + ".messages"))
                        p.sendMessage(ColorUtil.get(s3));
                }
            }
        }
    }

    @EventHandler
    public void clickl(final NPCLeftClickEvent e) {
        if (Main.getInstance().getConfig().isSet("commands") && Main.getInstance().getConfig().isConfigurationSection("commands")) {
            final Player p = e.getClicker();
            final NPC npc = e.getNPC();
            for (final String s : Main.getInstance().getConfig().getConfigurationSection("commands").getKeys(false)) {
                if (npc.getId() == Main.getInstance().getConfig().getInt("commands." + s + ".npc")) {
                    final Crate crate = CrazyCrates.getInstance().getCrateFromName(Main.getInstance().getConfig().getString("commands." + s + ".crate"));
                    Preview.setPlayerInMenu(p, true);
                    Preview.openPreview(p, crate);
                    break;
                }
            }
        }
    }
}
