package ru.teamscore.shipments;

import java.util.Date;

/**
 * Абстрактный документ отгрузки со склада.
 */
public abstract class ShipmentDocument {
    protected final String documentId;
    protected final Date documentDate;
    protected final String storage;
    protected final String storageOwner;
    protected final ShipmentItemsCollection itemsCollection;

    protected ShipmentDocument(String documentId, Date documentDate,
                               String storage, String storageOwner,
                               ShipmentItemsCollection itemsCollection) {
        this.documentId = documentId;
        this.documentDate = documentDate;
        this.storage = storage;
        this.storageOwner = storageOwner;
        this.itemsCollection = itemsCollection;
    }

    /**
     * Суммарная стоимость товаров в документе.
     */
    public double totalAmount() {
        return itemsCollection.totalAmount();
    }

    /**
     * Стоимость товара с переданным id.
     */
    double itemAmount(String id) {
        return itemsCollection.itemAmount(id);
    }

    /**
     * Суммарная стоимость товаров, попадающих в список промо-акции.
     */
    double promoSum(String[] promoArticles) {
        return itemsCollection.promoSum(promoArticles);
    }
}