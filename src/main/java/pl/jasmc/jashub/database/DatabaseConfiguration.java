package pl.jasmc.jashub.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.jasmc.jashub.JasCollection;
import pl.jasmc.jashub.objects.CollectionItem;
import pl.jasmc.jashub.objects.CollectionStorage;
import pl.jasmc.jashub.objects.PlayerMeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseConfiguration {

    private static Statement stm;
    private static JasCollection ab = JasCollection.getInstance();

    public static void checkTable() throws SQLException {
        Connection connection = ab.getHikari().getConnection();
        stm = connection.createStatement();
        stm.executeUpdate("CREATE TABLE IF NOT EXISTS `JasCollectionUsers` (user_id int not null AUTO_INCREMENT, username varchar(16), uuid varchar(64), coins int(10), PRIMARY KEY(user_id));");
        stm.executeUpdate("CREATE TABLE IF NOT EXISTS `JasCollectionUnlocks` (unlock_id int, user_id int not null, FOREIGN KEY(user_id) REFERENCES JasCollectionUsers(user_id));");
    }

    public static void insertPlayerData(PlayerMeta meta) {

        String name = meta.getName();
        String query = "INSERT INTO `JasCollectionUsers` VALUES ('0', '" + name + "', '" + meta.getUuid().toString() + "', '0');";
        try {
            stm.execute(query);
            if(JasCollection.DEBUG) {
                System.out.println("Dodano gracza: " + meta.getName() + " do bazy danych.");
            }
            loadMetaAndCreateIfNotExist(meta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean playerExists(PlayerMeta meta) {
        String query = "SELECT * FROM JasCollectionUsers WHERE uuid='" + meta.getUuid().toString() + "';";
        try {
            ResultSet rs = stm.executeQuery(query);
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void loadMetaAndCreateIfNotExist(PlayerMeta meta) {
        if(playerExists(meta)) {
            String sql = "SELECT * FROM JasCollectionUsers WHERE uuid='" + meta.getUuid() + "'";
            try {
                ResultSet rs = stm.executeQuery(sql);
                if(rs.next()) {
                    int idInBase = rs.getInt("user_id");
                    int coins = rs.getInt("coins");
                    meta.setIdInBase(idInBase);
                    meta.setCoins(coins);
                    loadUnlockedItems(meta);
                    loadLockedItems(meta);
                }
                if(JasCollection.DEBUG) {
                    System.out.println("Zaladowanie Gracza: " + meta.getName());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            insertPlayerData(meta);
        }
    }
    /*
        *loadUnlockedItems()
        *@player = PlayerMeta
        Ladowanie znalezionych itemow
     */

    public static void loadUnlockedItems(PlayerMeta player) {

        String query = "SELECT * FROM JasCollectionUnlocks INNER JOIN JasCollectionUsers ON JasCollectionUnlocks.user_id = JasCollectionUsers.user_id WHERE JasCollectionUsers.username = \"" + player.getName() + "\" ";
        try {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()) {
                int itemId = rs.getInt("unlock_id");
                CollectionItem item = CollectionStorage.getItemByID(itemId);
                item.setUnlocked(true);
                player.getAllItems().add(item);
                if(JasCollection.DEBUG) {
                    System.out.println("Unlock: " + item.getId() + " dla " + player.getName());
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeCoins(PlayerMeta meta, int coins) {
        String sql = "UPDATE `JasCollectionUsers` SET `coins` = coins -" + coins + " WHERE `user_id` = '" + meta.getIdInBase() + "';";
        try {
            stm.executeUpdate(sql);
            if(JasCollection.DEBUG) {
                System.out.println("Usunięto " + coins + " monet graczoi " + meta.getName() + " przez klase ");
            }
            meta.setCoins(meta.getCoins()-coins);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setCoins(PlayerMeta meta, int coinsToSet) {
        String sql = "UPDATE `JasCollectionUsers` SET `coins` = " + coinsToSet + " WHERE `user_id` = '" + meta.getIdInBase() + "';";
        try {
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCoins(PlayerMeta meta, int coinsToAdd) {
        String sql = "UPDATE `JasCollectionUsers` SET `coins` = coins +" + coinsToAdd + " WHERE `user_id` = '" + meta.getIdInBase() + "';";
        try {
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unlockItem(int id, PlayerMeta meta) {
        if(alreadyUnlocked(id, meta.getIdInBase())) {
            if(JasCollection.DEBUG) {
                System.out.println("User: " + meta.getName() + " already unclodked: " + id + " Abortring unclock!");
            }

            return;
        }
        String sql = "INSERT INTO `JasCollectionUnlocks` VALUES ('" + id + "', '" + meta.getIdInBase() + "')";
        try {
            stm.executeUpdate(sql);
            if(JasCollection.DEBUG) {
                System.out.println("Unlock complete for user " + meta.getName() + " and unlock_id:" + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    public static boolean containsItemWithID(List<CollectionItem> items, int id) {
        return items.stream().filter(object -> object.getId() == id).findFirst().isPresent();
    }

    public static void loadLockedItems(PlayerMeta jPlayer) {
        List<CollectionItem> result = CollectionStorage.getCollectionItems().values().stream().filter(foundItem -> CollectionStorage.getCollectionItems().values().contains(foundItem)).collect(Collectors.toList());

        for(CollectionItem item : result) {
            if(!containsItemWithID(jPlayer.getAllItems(), item.getId())) {
                item.setUnlocked(false);
                jPlayer.getAllItems().add(item);
                if(JasCollection.DEBUG) {
                    System.out.println("Lock: " + item.getId() + " dla Gracza " + jPlayer.getName());
                }
            }
        }
    }


    /*
        SPRAWDZANIE CZY ZNALAZL ITEM
     */


    public static boolean alreadyUnlocked(int idToCheck, int userID) {
        String query = "SELECT * FROM JasCollectionUnlocks WHERE user_id=\"" + userID + "\"";
        try (ResultSet rs = stm.executeQuery(query)) {
            while(rs.next()) {
                if(idToCheck == rs.getInt("unlock_id")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
