package pl.jasmc.jashub;

import pl.jasmc.jashub.database.DatabaseConfiguration;
import pl.jasmc.jashub.objects.CollectionItem;
import pl.jasmc.jashub.objects.CollectionStorage;
import pl.jasmc.jashub.objects.MetaStorage;
import pl.jasmc.jashub.objects.PlayerMeta;

import java.util.Map;
import java.util.Random;

public class CollectionAPI {

    /**
    *Pobieranie obiektu Gracza PlayerMeta
    *
    @param - String to nazwa Gracza
    @return - Obiekt PlayerMeta z dostępen do wszystkich metod
     *
    */

    public static PlayerMeta getPlayerMeta(String name) {
        return MetaStorage.getPlayerMeta(name);
    }

    /**
     *
     * Losowanie itemu z kolekcji, jeżeli znalazł już wylosowany item to dostaje połowe waluty
     *
     * @param /PlayerMeta Obiekt Gracza
     *
     */
    public static void getRandomCollectionItem(PlayerMeta meta) {
        Random random = new Random();

        CollectionItem[] entries = (CollectionItem[]) CollectionStorage.getCollectionItems().values().toArray();
        CollectionItem randomItem = entries[random.nextInt(entries.length)];

        if(meta.isUnlocked(randomItem.getId())) {
            meta.addCoins(randomItem.getPrice()/2);
        } else {
            DatabaseConfiguration.unlockItem(randomItem.getId(), meta);
            meta.getItemByID(randomItem.getId()).setUnlocked(true);
        }
    }

}
