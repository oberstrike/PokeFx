package field;

public enum FieldType {

	STEIN(true),
	WASSER(true),
	GRASS(false),
	HOHESGRASS(false),
	UEBERGANG(false),
	TIEFERSAND(false),
	SAND(false),
	
	CACTUS_BOTTOM(true),
	CACTUS_TOP(true),
	BAUM_TOP(true),
	BAUM_BOTTOM(true),
	
	GRASS_BL(true),
	MTR_GRASS(true),
	MTL_GRASS(true),
	MTB_GRASS(true),
	MTF_GRASS(true),
	MTLB_GRASS(true),
	MTLF_GRASS(true),
	MTRB_GRASS(true),
	MTRF_GRASS(true),
	MTILB_GRASS(true),
	MTILF_GRASS(true),
	MTIRB_GRASS(true),
	MTIRF_GRASS(true),
	
	MTR_WATER(true),
	MTL_WATER(true),
	MTB_WATER(true),
	MTF_WATER(true),
	MTLB_WATER(true),
	MTLF_WATER(true),
	MTRB_WATER(true),
	MTRF_WATER(true),
	MTILB_WATER(true),
	MTILF_WATER(true),
	MTIRB_WATER(true),
	MTIRF_WATER(true),
	
	MTR_SAND(true),
	MTL_SAND(true),
	MTB_SAND(true),
	MTF_SAND(true),
	MTLB_SAND(true),
	MTLF_SAND(true),
	MTRB_SAND(true),
	MTRF_SAND(true),
	MTILB_SAND(true),
	MTILF_SAND(true),
	MTIRB_SAND(true),
	MTIRF_SAND(true);
	
	final private boolean blocked;

	public boolean isBlocked() {
		return blocked;
	}

	private FieldType(boolean blocked) {
		this.blocked = blocked;
	}

}
