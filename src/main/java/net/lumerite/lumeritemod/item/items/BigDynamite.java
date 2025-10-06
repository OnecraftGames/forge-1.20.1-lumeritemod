package net.lumerite.lumeritemod.item.items;

import net.lumerite.lumeritemod.item.base.BaseDynamiteItem;
import net.lumerite.lumeritemod.item.entity.BigDynamiteEntity;

public class BigDynamite extends BaseDynamiteItem<BigDynamiteEntity> {

    public BigDynamite(Properties properties) {
        super(properties, BigDynamiteEntity::new);
    }

}
