package com.tsh.algorithm.leetcode.rotateImage;

public class ArrayLine {

	private Integer start;//读取开始索引
	private Integer end;//读取结束索引
	private Integer position;//1右,2下,3左,4上
	private ArrayLine nextLine;
	
	public ArrayLine(){
		
	}
	
	public ArrayLine( Integer start, Integer end, Integer position) {
		super();
		this.start = start;
		this.end = end;
		this.position = position;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public ArrayLine getNextLine() {
		return nextLine;
	}

	public void setNextLine(ArrayLine nextLine) {
		this.nextLine = nextLine;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
}
