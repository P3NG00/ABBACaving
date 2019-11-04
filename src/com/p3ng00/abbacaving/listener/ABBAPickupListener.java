package com.p3ng00.abbacaving.listener;

import com.p3ng00.abbacaving.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ABBAPickupListener implements Listener {
    @EventHandler
    public void onPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Main.party.contains(event.getEntity())) {
                if (Main.isValidItem(event.getItem().getItemStack().getType())) {
                    // IMPLEMENT DIFFERENT SOUNDS FOR DIFFERENT ITEMS
                }
            }
        }
    }
}
