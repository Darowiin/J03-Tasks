package ru.teamscore.shipments;

import java.util.Date;

/**
 * Документ типа "Продажа".
 */
public class SaleShipmentDocument extends ShipmentDocument {
    private final String customer;

    public SaleShipmentDocument(String documentId, Date documentDate, String storage, String storageOwner,
                                ShipmentItemsCollection itemsCollection, String customer) {
        super(documentId, documentDate, storage, storageOwner, itemsCollection);
        this.customer = customer;
    }

    /**
     * Является ли продажа оптовой для переданного минимального объема.
     */
    boolean isWholesale(double minQuantity) {
        double sumQuantity = 0;

        var items = itemsCollection.getItems();

        for (ShipmentItem item : items) {
            if (item.quantity().doubleValue() >= minQuantity) {
                return true;
            }
            sumQuantity += item.quantity().doubleValue();
        }
        return sumQuantity >= minQuantity;
    }

    /**
     * Суммарная стоимость промо-товаров со скидкой.
     * @param promoArticles Список артикулов, участвующих в промо.
     * @param discount Размер скидки в процентах (0-100).
     * @return Суммарная стоимость промо-товаров со скидкой.
     */
    public double promoSum(String[] promoArticles, double discount) {
        return itemsCollection.calculatePromoSumWithDiscount(promoArticles, discount);
    }
}