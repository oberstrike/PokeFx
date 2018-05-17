package field;

public enum FieldType {
	STEIN(true), WASSER(true), GRASS(false), HOHESGRASS(false), UEBERGANG(false), TIEFERSAND(false), SAND(
			false), CACTUS_BOTTOM(true), CACTUS_TOP(true), MTLB_GRASS(false), MTLB_SAND(false), MTLF_GRASS(
					false), MTLF_SAND(false), MTRB_GRASS(false), MTRB_SAND(false), MTRF_GRASS(false), MTRF_SAND(
							false), MTLB_WATER(true), MTLF_WATER(true), MTRB_WATER(true), MTRF_WATER(
									true), MTL_SAND(true), MTF_SAND(true), MTB_SAND(true), MTR_SAND(true);

	final private boolean blocked;

	public boolean isBlocked() {
		return blocked;
	}

	private FieldType(boolean blocked) {
		this.blocked = blocked;
	}

}
