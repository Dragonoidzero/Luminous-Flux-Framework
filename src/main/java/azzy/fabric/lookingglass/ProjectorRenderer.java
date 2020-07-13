package azzy.fabric.lookingglass;

import com.mojang.blaze3d.platform.GlStateManager;
import com.sun.jna.Function;
import net.fabricmc.fabric.impl.client.indigo.renderer.IndigoRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.io.IOException;

import static azzy.fabric.lookingglass.LookingGlass.FFLog;

public class ProjectorRenderer extends BlockEntityRenderer<ProjectorEntity> {

    //hoi

    public ProjectorRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public boolean rendersOutsideBoundingBox(ProjectorEntity blockEntity) {
        return true;
    }

    @Override
    public void render(ProjectorEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        matrices.push();
        matrices.translate(blockEntity.disX, blockEntity.disY, blockEntity.disZ);
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(blockEntity.rotX));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(blockEntity.rotY));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(blockEntity.rotZ));
        matrices.scale((float) blockEntity.scale, (float) blockEntity.scale, (float) blockEntity.scale);


        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getSolid());
        MatrixStack.Entry matrix = matrices.peek();

        Resource resource = null;

        Sprite sprite = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEX).apply(new Identifier("minecraft:block/obsidian"));
        try {
            resource = MinecraftClient.getInstance().getResourceManager().getResource(new Identifier("minecraft:textures/block/allium.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        java.util.function.Function<Identifier, RenderLayer> layerFactory = a -> RenderLayer.getCutoutMipped();

        SpriteIdentifier spriteIdentifier = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEX, new Identifier("minecraft:textures/block/allium.png"));
        consumer = spriteIdentifier.getVertexConsumer(vertexConsumers, layerFactory);

        if (blockEntity.displayState == 0) {
            consumer.vertex(matrix.getModel(), 0, 0, 0).color(255, 255, 255, 255).texture(0, 1).light(14680160).normal(matrix.getNormal(), 1, 1, 1).next();
            consumer.vertex(matrix.getModel(), 0, 1, 0).color(255, 255, 255, 255).texture(0, 0).light(14680160).normal(matrix.getNormal(), 1, 1, 1).next();
            consumer.vertex(matrix.getModel(), 1, 1, 0).color(255, 255, 255, 255).texture(1, 0).light(14680160).normal(matrix.getNormal(), 1, 1, 1).next();
            consumer.vertex(matrix.getModel(), 1, 0, 0).color(255, 255, 255, 255).texture(1, 1).light(14680160).normal(matrix.getNormal(), 1, 1, 1).next();

            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
            matrices.translate(-1, 0, 0);

            consumer.vertex(matrix.getModel(), 0, 0, (float) (0.01 / blockEntity.scale)).color(255, 255, 255, 255).texture(sprite.getMinU(), sprite.getMinV()).light(14680160).normal(matrix.getNormal(), 1, 1, 1).next();
            consumer.vertex(matrix.getModel(), 0, 1, (float) (0.01 / blockEntity.scale)).color(255, 255, 255, 255).texture(sprite.getMinU(), sprite.getMaxV()).light(14680160).normal(matrix.getNormal(), 1, 1, 1).next();
            consumer.vertex(matrix.getModel(), 1, 1, (float) (0.01 / blockEntity.scale)).color(255, 255, 255, 255).texture(sprite.getMaxU(), sprite.getMaxV()).light(14680160).normal(matrix.getNormal(), 1, 1, 1).next();
            consumer.vertex(matrix.getModel(), 1, 0, (float) (0.01 / blockEntity.scale)).color(255, 255, 255, 255).texture(sprite.getMaxU(), sprite.getMinV()).light(14680160).normal(matrix.getNormal(), 1, 1, 1).next();
        }
        else if(blockEntity.displayState == 1){

        }
        else if(blockEntity.displayState == 2){

        }
        else if(blockEntity.displayState == 3){

        }
        else if(blockEntity.displayState == 4){

        }
        matrices.pop();

    }
}
