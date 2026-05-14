package com.example.lendahand;

import java.util.UUID;

public class RVLevelItem {
    public UUID parentId;

    public RVLevelItem(UUID parentId){
        this.parentId = parentId;
    }

    public UUID getParentId() { return parentId; }

}
