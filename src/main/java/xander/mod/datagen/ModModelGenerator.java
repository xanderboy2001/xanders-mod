package xander.mod.datagen;

import java.util.Optional;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import xander.mod.ModBlocks;
import xander.mod.ModLayerBlock;

public class ModModelGenerator extends FabricModelProvider {

  public ModModelGenerator(FabricPackOutput _output_) {
    super(_output_);
  }

  @Override
  public void generateBlockStateModels(BlockModelGenerators _generator_) {
    ModBlocks.BLOCK_TO_WALL.forEach((_base_, _wall_) -> generateWallModels(_generator_, _base_, _wall_));
    ModBlocks.BLOCK_TO_STAIRS.forEach((_base_, _stairs_) -> generateStairsModels(_generator_, _base_, _stairs_));
    ModBlocks.BLOCK_TO_SLAB.forEach((_base_, _slab_) -> generateSlabModels(_generator_, _base_, _slab_));
    ModBlocks.BLOCK_TO_LAYER.forEach((_base_, _layer_) -> generateLayerModels(_generator_, _base_, _layer_));
  }

  public static final ModelTemplate LAYER_TEMPLATE = new ModelTemplate(
      Optional.of(Identifier.fromNamespaceAndPath("xander", "block/layer_base")),
      Optional.empty(),
      TextureSlot.LAYER0);

  public void generateLayerModels(BlockModelGenerators _generator_, Block _base_, Block _layer_) {
    TextureMapping textures = new TextureMapping().put(TextureSlot.LAYER0, TextureMapping.getBlockTexture(_base_));
    Identifier[] layerModels = new Identifier[7];

    for (int i = 1; i <= 7; i++) {
      int height = i * 2;

      ModelTemplate template = new ModelTemplate(
          Optional.of(Identifier.fromNamespaceAndPath("xander", "block/layer" + height)),
          Optional.empty(),
          TextureSlot.LAYER0);

      layerModels[i - 1] = template.createWithSuffix(_layer_, "_height" + height, textures, _generator_.modelOutput);
    }

    Identifier baseKey = BuiltInRegistries.BLOCK.getKey(_base_);
    Identifier fullBlockModelId = Identifier.fromNamespaceAndPath(baseKey.getNamespace(), "block/" + baseKey.getPath());
    _generator_.blockStateOutput.accept(MultiVariantGenerator.dispatch(_layer_)
        .with(PropertyDispatch.initial(ModLayerBlock.LAYERS)
            .generate(layers -> {
              if (layers == 8)
                return BlockModelGenerators.plainVariant(fullBlockModelId);
              return BlockModelGenerators.plainVariant(layerModels[layers - 1]);
            })));
    _generator_.registerSimpleItemModel(_layer_, layerModels[0]);
  }

  private void generateSlabModels(BlockModelGenerators _generator_, Block _base_, Block _slab_) {
    TextureMapping textures = new TextureMapping()
        .put(TextureSlot.BOTTOM,
            TextureMapping.getBlockTexture(_base_))
        .put(TextureSlot.TOP,
            TextureMapping.getBlockTexture(
                _base_))
        .put(TextureSlot.SIDE,
            TextureMapping.getBlockTexture(
                _base_))
        .put(TextureSlot.ALL, TextureMapping.getBlockTexture(_base_));

    Identifier bottomModel = ModelTemplates.SLAB_BOTTOM.create(_slab_, textures, _generator_.modelOutput);
    Identifier topModel = ModelTemplates.SLAB_TOP.create(_slab_, textures, _generator_.modelOutput);
    Identifier doubleModel = ModelTemplates.CUBE_ALL.createWithSuffix(
        _slab_, "_double", textures, _generator_.modelOutput);

    _generator_.blockStateOutput.accept(
        BlockModelGenerators.createSlab(
            _slab_,
            BlockModelGenerators.plainVariant(bottomModel),
            BlockModelGenerators.plainVariant(topModel),
            BlockModelGenerators.plainVariant(doubleModel)));

    _generator_.registerSimpleItemModel(_slab_, bottomModel);
  }

  private void generateStairsModels(BlockModelGenerators _generator_, Block _base_, Block _stairs_) {
    TextureMapping textures = new TextureMapping()
        .put(TextureSlot.BOTTOM,
            TextureMapping.getBlockTexture(
                _base_))
        .put(TextureSlot.TOP,
            TextureMapping.getBlockTexture(
                _base_))
        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(_base_));

    Identifier innerModel = ModelTemplates.STAIRS_INNER.create(_stairs_, textures, _generator_.modelOutput);
    Identifier straightModel = ModelTemplates.STAIRS_STRAIGHT.create(_stairs_, textures, _generator_.modelOutput);
    Identifier outerModel = ModelTemplates.STAIRS_OUTER.create(_stairs_, textures, _generator_.modelOutput);

    _generator_.blockStateOutput.accept(BlockModelGenerators.createStairs(
        _stairs_,
        BlockModelGenerators.plainVariant(
            innerModel),
        BlockModelGenerators.plainVariant(
            straightModel),
        BlockModelGenerators.plainVariant(
            outerModel)));

    _generator_.registerSimpleItemModel(_stairs_, straightModel);
  }

  private void generateWallModels(BlockModelGenerators _generator_, Block _base_, Block _wall_) {
    TextureMapping textureMap = new TextureMapping().put(TextureSlot.WALL, TextureMapping.getBlockTexture(_base_));

    Identifier postModelId = ModelTemplates.WALL_POST.create(_wall_, textureMap, _generator_.modelOutput);
    Identifier sideModelId = ModelTemplates.WALL_LOW_SIDE.create(_wall_, textureMap, _generator_.modelOutput);
    Identifier tallModelId = ModelTemplates.WALL_TALL_SIDE.create(_wall_, textureMap, _generator_.modelOutput);

    _generator_.blockStateOutput.accept(BlockModelGenerators.createWall(
        _wall_,
        BlockModelGenerators.plainVariant(postModelId),
        BlockModelGenerators.plainVariant(sideModelId),
        BlockModelGenerators.plainVariant(tallModelId)));

    _generator_.registerSimpleItemModel(_wall_,
        ModelTemplates.WALL_INVENTORY.create(_wall_, textureMap, _generator_.modelOutput));
  }

  @Override
  public void generateItemModels(ItemModelGenerators _generator_) {
  }
}
