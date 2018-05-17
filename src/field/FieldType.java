package field;

public enum FieldType {
<<<<<<< HEAD
	STEIN,
	WASSER,
	GRASS,
	HOHESGRASS,
	UEBERGANG,
	TIEFERSAND,
	SAND,
	CACTUS_BOTTOM,
	CACTUS_TOP,
	MTLB_GRASS,
	MTLB_SAND,
	MTLF_GRASS,
	MTLF_SAND,
	MTRB_GRASS,
	MTRB_SAND,
	MTRF_GRASS,
	MTRF_SAND,
	MTLB_WATER,
	MTLF_WATER,
	MTRB_WATER,
	MTRF_WATER,
	MTILB_WATER,
	MTILF_WATER,
	MTIRB_WATER,
	MTIRF_WATER,
	BAUM_TOP,
	BAUM_BOTTOM,
	MTR_SAND,
	MTL_SAND,
	MTB_SAND,
	MTF_SAND;
=======
	STEIN(true),
	WASSER(true),
	GRASS(false),
	HOHESGRASS(false),
	UEBERGANG(false),
	TIEFERSAND(false),
	SAND(false),
	CACTUS_BOTTOM(true),
	CACTUS_TOP(true),
	MTLB_GRASS(false),
	MTLB_SAND(false),
	MTLF_GRASS(false),
	MTLF_SAND(false),
	MTRB_GRASS(false),
	MTRB_SAND(false),
	MTRF_GRASS(false),
	MTRF_SAND(false),
	MTLB_WATER(true),
	MTLF_WATER(true),
	MTRB_WATER(true),
	MTRF_WATER(true);
	
	final private boolean blocked;
	
	public boolean isBlocked() {
		return blocked;
	}
	
	private FieldType(boolean blocked) {
		this.blocked = blocked;
	}
	
	
>>>>>>> eae0be5c315c28424600bc3bd386003e2ae7c5a0
}
