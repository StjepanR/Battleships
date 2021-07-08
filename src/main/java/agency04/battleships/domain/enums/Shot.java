package agency04.battleships.domain.enums;

public enum Shot {
	HIT,
	KILL,
	MISS;
	
	private String shot;

	public String getShot() {
		return shot;
	}

	public void setShot(String shot) {
		this.shot = shot;
	}
	
}
