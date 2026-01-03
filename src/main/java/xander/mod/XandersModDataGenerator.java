package xander.mod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;
import xander.mod.datagen.ModLanguageGenerator;
import xander.mod.datagen.ModLootTableGenerator;
import xander.mod.datagen.ModModelGenerator;
import xander.mod.datagen.ModRecipeGenerator;
import xander.mod.datagen.ModTagGenerator;

public class XandersModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelGenerator::new);
		pack.addProvider(ModRecipeGenerator::new);
		pack.addProvider(ModTagGenerator::new);
		pack.addProvider(ModLanguageGenerator::new);
		pack.addProvider(ModLootTableGenerator::new);
	}
}
