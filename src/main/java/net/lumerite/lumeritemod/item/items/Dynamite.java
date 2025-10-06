package net.lumerite.lumeritemod.item.items;

import net.lumerite.lumeritemod.item.base.BaseDynamiteItem;
import net.lumerite.lumeritemod.item.entity.DynamiteEntity;

public class Dynamite extends BaseDynamiteItem<DynamiteEntity> {

    public Dynamite(Properties properties) {
        super(properties, DynamiteEntity::new);
    }
}

