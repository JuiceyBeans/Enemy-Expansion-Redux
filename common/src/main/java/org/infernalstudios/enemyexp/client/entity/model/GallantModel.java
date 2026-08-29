package org.infernalstudios.enemyexp.client.entity.model;

import org.infernalstudios.enemyexp.EEMod;
import org.infernalstudios.enemyexp.content.entity.GallantEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class GallantModel extends DefaultedEntityGeoModel<GallantEntity> {
    public GallantModel() {
        super(EEMod.location("gallant"), true);
    }
}
