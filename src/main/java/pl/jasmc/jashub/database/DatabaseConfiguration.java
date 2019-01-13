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
        stm.executeUpdate("CREATE TABLE IF NOT EXISTS `JasCollectionUnlocks` (unlock_id int, user_id int not null, PRIMARY KEY(unlock_id), FOREIGN KEY(user_id) REFERENCES JasCollectionUsers(user_id));");
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
        loadTestPresentFound()
        @p = JPlayer obiekt
        Ladowanie znalezionych prezentow
     */

    public static void loadUnlockedItems(PlayerMeta player) {


       // List<CollectionItem> collectionItemList = new ArrayList<CollectionItem>();
        //for (String name : JasCollection.getItemBase().getCfg().getConfigurationSection("").getKeys(false)) {
         //   collectionItemList.add(new CollectionItem(JasCollection.getItemBase().getCfg().getItemStack(name+ ".itemstack"), JasCollection.getItemBase().getCfg().getInt(name + ".id"), JasCollection.getItemBase().getCfg().getInt(name + ".price")));
       // }

        String query = "SELECT * FROM JasCollectionUnlocks INNER JOIN JasCollectionUsers ON JasCollectionUnlocks.user_id = JasCollectionUsers.user_id WHERE JasCollectionUsers.username = \"" + player.getName() + "\" ";
        try {
            ResultSet rs = stm.executeQuery(query);
            while(rs.next()) {
                int itemId = rs.getInt("unlock_id");
                CollectionItem item = CollectionStorage.getItemByID(itemId);
                item.setUnlocked(true);
                player.getAllItems().add(item);
               // player.getUnlockedItems().add(item);
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
            meta.setCoins(meta.getCoins()-coins);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unlockItem(int id, PlayerMeta meta) {
        String sql = "INSERT INTO `JasCollectionUnlocks` VALUES ('" + id + "', '" + meta.getIdInBase() + "')";
        try {
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }














    //GETERY





























    //////////OLD ///////////////////////

    public static void purgePresentsTables(){
        String truncate_off = "SET FOREIGN_KEY_CHECKS = 0;";
        String query1 = "TRUNCATE JasPresents";
        String query2 = "TRUNCATE JasPresentsUsers";
        String truncate_on = "SET FOREIGN_KEY_CHECKS = 1;";
        try {
            stm.executeUpdate(truncate_off);
            stm.executeUpdate(query1);
            stm.executeUpdate(query2);
            stm.executeUpdate(truncate_on);

            if(JasCollection.DEBUG) {
                System.out.print("Zresetowano prezenty.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        loadPlayer()
        @p = Gracz
        Ladowanie gracza
     */

    public static void loadPlayer(PlayerMeta playerMeta, boolean loaded)  {

    }


    public static boolean containsItemWithID(List<CollectionItem> items, int id) {
        return items.stream().filter(object -> object.getId() == id).findFirst().isPresent();
    }


         /*
        loadTestPresentToFind()
        @p = Gracz
        Ladowanie prezentow do znalezienia
     */

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





            //jPlayer.getLockedItems().add(item);
        }
    }


    /*
        SPRAWDZANIE CZY ZNALAZL PREZENT
     */


    public static boolean alreadyUnlocked(CollectionItem item, String player) {
        String query = "SELECT * FROM JasCollectionUsers WHERE username=\"" + player + "\"";
        try (ResultSet rs = stm.executeQuery(query)) {
            while(rs.next()) {
                if(item.getId() == rs.getInt("item_id")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
        ZANALEZIENIE PREZENTU
     */


    public static void foundPresent(String player, PlayerMeta present) {
      //  String query  = "INSERT INTO JasPresentsUsers (username, world, x, y, z, present_id) VALUES (\"" + player + "\",\"" + present.getLocation().getWorld().getName() + "\"," +  present.getLocation().getX() + ","  + present.getLocation().getY() + "," + present.getLocation().getZ() + "," + present.getId() + ")";
        /*ThreadPool.runTaskAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    stm.executeUpdate(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    /*
        Dodawanie prezentu do bazy
     */


    public static void addPresent(String location, String name, String type) throws SQLException  {
        String query = "INSERT INTO JasPresents (location, present_name, present_type) VALUES (\"" +  location + "\",\"" + name + "\",\"" + type + "\")";
        //if(Presents.DEBUG_MODE) {
        //    System.out.println("Query: " + query);
       // }

       // stm.executeUpdate(query);
    }

    public static void deletePresent(String name)throws SQLException {
        String query = "DELETE FROM JasPresents WHERE name=\"" + name + "\"";
        stm.executeUpdate(query);
    }

    /*

        DEBUGOWANIE


    public static void loadPresents() throws SQLException {
        ResultSet rs = stm.executeQuery("SELECT * FROM JasPresents");
        int i = 0;
        while(rs.next()) {
            Present present = new Present(rs.getString("present_name"), PresentType.valueOf(rs.getString("present_type").toUpperCase()), Utils.stringToLoc(rs.getString("location")), rs.getInt("id"));
            DataManager.addPresent(present);
            i++;
        }
        if(Presents.DEBUG_MODE) {
            System.out.println("Załadowano: " + i + " prezentow");
        }

        for(Present p : DataManager.loadedPresenents) {
            if(Presents.DEBUG_MODE) {
                System.out.println("Kolor: " + p.getType().name());
            }

        }
    }
*/

}
