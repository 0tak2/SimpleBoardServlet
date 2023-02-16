package common.vo;

public class BasicSelectVO {

	private int numOfRows;
	private int start;
	private int end;
	
	public BasicSelectVO() {
	}

	public BasicSelectVO(int numOfRows, int start, int end) {
		super();
		this.numOfRows = numOfRows;
		this.start = start;
		this.end = end;
	}

	public BasicSelectVO(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	

	
	
}
