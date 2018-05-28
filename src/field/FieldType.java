package field;

import application.Main;
import javafx.scene.image.Image;

public enum FieldType {
	STEIN(true, Main.stein),
	WASSER(true, Main.wasser),
	GRASS(false, Main.grass),
	HOHESGRASS(false, Main.hohesgrass),
	UEBERGANG(false, Main.uebergang),
	TIEFERSAND(false, Main.tiefersand),
	SAND(false, Main.sand),
	CACTUS_BOTTOM(true, Main.cactus_bottom),
	CACTUS_TOP(true, Main.cactus_top),
	BAUM_TOP(true, Main.baum_top),
	BAUM_BOTTOM(true, Main.baum_bottom),
	HAUS_1a(true, Main.haus_1a),
	HAUS_1b(true, Main.haus_1b),
	HAUS_1c(true, Main.haus_1c),
	HAUS_1d(true, Main.haus_1d),
	HAUS_2a(true, Main.haus_2a),
	HAUS_2b(true, Main.haus_2b),
	HAUS_2c(true, Main.haus_2c),
	HAUS_2d(true, Main.haus_2d),
	HAUS_2e(true, Main.haus_2e),
	HAUS_2f(true, Main.haus_2f),
	HAUS_3a(true, Main.haus_3a),
	HAUS_3b(true, Main.haus_3b),
	HAUS_3c(true, Main.haus_3c),
	HAUS_3d(true, Main.haus_3d),
	HAUS_3e(true, Main.haus_3e),
	HAUS_3f(true, Main.haus_3f),
	HAUS_3g(true, Main.haus_3g),
	HAUS_3h(true, Main.haus_3h),
	HAUS_3i(true, Main.haus_3i),
	HAUS_4a(true, Main.haus_4a),
	HAUS_4b(true, Main.haus_4b),
	HAUS_4c(true, Main.haus_4c),
	HAUS_4d(true, Main.haus_4d),
	HAUS_4e(true, Main.haus_4e),
	HAUS_4f(true, Main.haus_4f),
	FELS_WATER(true, Main.fels_water),
	FELS_GRASS(true, Main.fels_grass),
	FELS_SAND(true, Main.fels_sand),
	GRASS_BL(true, Main.grass_bl),
	MTR_GRASS(true, Main.mtr_grass),
	MTL_GRASS(true, Main.mtl_grass ),
	MTB_GRASS(true, Main.mtb_grass),
	MTF_GRASS(true, Main.mtf_grass),
	MTLB_GRASS(true, Main.mtlb_grass),
	MTLF_GRASS(true, Main.mtlf_grass),
	MTRB_GRASS(true, Main.mtrb_grass),
	MTRF_GRASS(true, Main.mtrf_grass),
	MTILB_GRASS(true, Main.mtilb_grass),
	MTILF_GRASS(true, Main.mtilf_grass),
	MTIRB_GRASS(true, Main.mtirb_grass),
	MTIRF_GRASS(true, Main.mtirf_grass),
	MTR_WATER(true, Main.mtr_water),
	MTL_WATER(true, Main.mtl_water),
	MTB_WATER(true, Main.mtb_water),
	MTF_WATER(true, Main.mtf_water),
	MTLB_WATER(true, Main.mtlb_water),
	MTLF_WATER(true, Main.mtlf_water),
	MTRB_WATER(true, Main.mtrb_water),
	MTRF_WATER(true, Main.mtrf_water),
	MTILB_WATER(true, Main.mtilb_water),
	MTILF_WATER(true, Main.mtilf_water),
	MTIRB_WATER(true, Main.mtirb_water),
	MTIRF_WATER(true, Main.mtirf_water),
	MTR_SAND(true, Main.mtr_sand),
	MTL_SAND(true, Main.mtl_sand),
	MTB_SAND(true, Main.mtb_sand),
	MTF_SAND(true, Main.mtf_sand),
	MTLB_SAND(true, Main.mtlb_sand),
	MTLF_SAND(true, Main.mtlf_sand),
	MTRB_SAND(true, Main.mtrb_sand),
	MTRF_SAND(true, Main.mtrf_sand),
	MTILB_SAND(true, Main.mtilb_sand),
	MTILF_SAND(true, Main.mtilf_sand),
	MTIRB_SAND(true, Main.mtirb_sand),
	MTIRF_SAND(true, Main.mtirf_sand);

	final private boolean blocked;
	final private Image image;
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public Image getImage() {
		return image;
	}

	private FieldType(boolean blocked, Image image) {
		this.blocked = blocked;
		this.image = image;
	}
}
