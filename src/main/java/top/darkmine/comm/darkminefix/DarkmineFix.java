package top.darkmine.comm.darkminefix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class DarkmineFix extends JavaPlugin implements @NotNull Listener {

    @Override
    public void onEnable() {
        this.getLogger().info("DMFIX loaded");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().startsWith("/hd") || event.getMessage().startsWith("/hologram") || event.getMessage().startsWith("/holograms") || event.getMessage().startsWith("/holographicdisplays:hd") || event.getMessage().startsWith("/holographicdisplays:hologram") || event.getMessage().startsWith("/holographicdisplays:holograms") || event.getMessage().startsWith("/holographicdisplays:holo") || event.getMessage().startsWith("/holo")) {
            event.setMessage(event.getMessage().replace(".", " "));
        }

        String s =event.getMessage();
        String[] stp= s.split(" ");
        String eventPlayerName = event.getPlayer().getName();
        String playerName = stp[2];
        if (event.getMessage().startsWith("/enchant") || event.getMessage().startsWith("/cmi enchant") || event.getMessage().startsWith("/minecraft:enchant")) {
            CheckEnchantCommand(stp, playerName, event);
        }else if (event.getMessage().startsWith("/effect ") || event.getMessage().startsWith("/cmi effect ") || event.getMessage().startsWith("/minecraft:effect ")) {
            CheckEffectCommand(stp, playerName, event);
        }else if (event.getMessage().startsWith("/tppos") || event.getMessage().startsWith("/cmi tppos")) {
            CheckTpposCommand(stp, playerName, event);
        }
    }
    private void CheckEffectCommand(String[] stp, String PlayerName, PlayerCommandPreprocessEvent event){
        try{
            String eventPlayerName = event.getPlayer().getName();
            int lvl = 10;
            System.out.println("l = " + stp.length);
            if (PlayerName.equalsIgnoreCase(eventPlayerName)) {
                if (stp.length > 5) {
                    System.out.println("Сила эффекта ограничена " + lvl + ". Игрок " + eventPlayerName + ", power = " + Integer.parseInt(stp[5]));
                    if (Integer.parseInt(stp[5]) > lvl) {
                        event.getPlayer().sendMessage(ChatColor.GRAY + "Сила эффекта ограничена " + lvl + " !");
                        event.setMessage("/NULL");
                    }
                }
            } else {
                if(!Arrays.asList(stp).contains("clear")){
                    System.out.println("Сила эффекта ограничена " + lvl + ". Игрок " + eventPlayerName + ", power = " + Integer.parseInt(stp[4]));
                    if (stp.length == 5 && Integer.parseInt(stp[4]) > lvl) {
                        event.getPlayer().sendMessage(ChatColor.GRAY + "Сила эффекта ограничена " + lvl + " !");
                        event.setMessage("/NULL");
                    }

                    if (stp.length >= 6 && Integer.parseInt(stp[5]) > lvl) {
                        event.getPlayer().sendMessage(ChatColor.GRAY + "Сила эффекта ограничена " + lvl + " !");
                        event.setMessage("/NULL");
                    }
                }

            }
        }catch (Exception e){
            Bukkit.getServer().getLogger().info(e.getMessage());
        }

    }
    private void CheckTpposCommand(String[] stp, String PlayerName, PlayerCommandPreprocessEvent event){
        try{
            System.out.println(event.getMessage() + " команда!");
            String eventPlayerName = event.getPlayer().getName();
            double h;
            double playerHeight;
            String height;
            if (PlayerName.equalsIgnoreCase(eventPlayerName)) {
                if (stp[4].startsWith("~")) {
                    playerHeight = event.getPlayer().getLocation().getY();
                    height = stp[4];
                    h = Double.parseDouble(height.replace('~', ' ')) + playerHeight;
                } else {
                    h = Double.parseDouble(stp[4]);
                }
            } else if (stp[3].startsWith("~")) {
                playerHeight = event.getPlayer().getLocation().getY();
                height = stp[3];
                h = Double.parseDouble(height.replace('~', ' ')) + playerHeight;
            } else {
                h = Double.parseDouble(stp[3]);
            }

            String w = event.getPlayer().getWorld().getName();
            if (w.equals("world")) {
                if (h < -64.0) {
                    event.getPlayer().sendMessage(ChatColor.GRAY + " СЛИШКОМ НИЗКО!!! Введите координаты в пределах высоты мира!");
                    event.setMessage("/tppos ~ ~ ~");
                }

                if (h > 320.0) {
                    event.getPlayer().sendMessage(ChatColor.GRAY + " СЛИШКОМ ВЫСОКО!!! Введите координаты в пределах высоты мира!");
                    event.setMessage("/tppos ~ ~ ~");
                }
            } else {
                if (h < 0.0) {
                    event.getPlayer().sendMessage(ChatColor.GRAY + " СЛИШКОМ НИЗКО!!! Введите координаты в пределах высоты мира!");
                    event.setMessage("/tppos ~ ~ ~");
                }

                if (h > 256.0) {
                    event.getPlayer().sendMessage(ChatColor.GRAY + " СЛИШКОМ ВЫСОКО!!! Введите координаты в пределах высоты мира!");
                    event.setMessage("/tppos ~ ~ ~");
                }
            }
        }catch (Exception e){
            Bukkit.getServer().getLogger().info(e.getMessage());
        }

    }
    private void CheckEnchantCommand(String[] stp, String PlayerName, PlayerCommandPreprocessEvent event){
        try{
            String eventPlayerName = event.getPlayer().getName();
            System.out.println("l = " + stp.length);
            ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
            if (heldItem.getType() == Material.BOOK) {
                if (heldItem.getAmount() != 1) {
                    event.getPlayer().sendMessage(ChatColor.GRAY + "Можно зачаровать только одну книгу!");
                    event.setMessage("/NULL");
                }
            } else {
                if (eventPlayerName.equalsIgnoreCase(PlayerName)) {
                    if (stp.length > 4) {
                        System.out.println("power = " + Integer.parseInt(stp[4]));
                        if (Integer.parseInt(stp[4]) < 0) {
                            event.getPlayer().sendMessage(ChatColor.GRAY + "Отрицательные зачарования запрещены!");
                            event.setMessage("/NULL");
                        }
                    }
                } else {
                    System.out.println("power = " + Integer.parseInt(stp[3]));
                    if (stp.length > 3 && Integer.parseInt(stp[3]) < 0) {
                        event.getPlayer().sendMessage(ChatColor.GRAY + "Отрицательные зачарования запрещены!");
                        event.setMessage("/NULL");
                    }
                }
            }
        }catch (Exception e){
            Bukkit.getServer().getLogger().info(e.getMessage());
        }

    }
}
