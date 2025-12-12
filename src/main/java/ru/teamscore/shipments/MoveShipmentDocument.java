package ru.teamscore.shipments;

import java.util.Date;

/**
 * Документ типа "Перемещение".
 */
public class MoveShipmentDocument extends ShipmentDocument {
    private final String movingStorage; // Название склада получения
    private final String movingStorageOwner; // Владелец склада получения

    /**
     * Конструктор документа перемещения.
     */
    public MoveShipmentDocument(String documentId, Date documentDate, String storage, String storageOwner,
                                ShipmentItemsCollection itemsCollection,
                                String movingStorage, String movingStorageOwner) {
        super(documentId, documentDate, storage, storageOwner, itemsCollection);
        this.movingStorage = movingStorage;
        this.movingStorageOwner = movingStorageOwner;
    }

    /**
     * Является ли перемещение внутренним (между складами одного владельца).
     */
    boolean isInternalMovement() {
        return storageOwner.equals(movingStorageOwner);
    }
}