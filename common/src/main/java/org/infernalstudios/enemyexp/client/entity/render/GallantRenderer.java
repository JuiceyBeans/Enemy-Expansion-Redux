package org.infernalstudios.enemyexp.client.entity.render;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.infernalstudios.enemyexp.client.entity.model.GallantModel;
import org.infernalstudios.enemyexp.content.entity.GallantEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GallantRenderer extends GeoEntityRenderer<GallantEntity> {
    public GallantRenderer(EntityRendererProvider.Context context) {
        super(context, new GallantModel());
        this.shadowRadius = 0.6F;
    }

    @Override
    public RenderType getRenderType(GallantEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
