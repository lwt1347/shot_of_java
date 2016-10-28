package mapData;

import java.awt.Point;

public class Block {
	
	//박스가 놓일 좌상단 포인트
	private Point block_Point;
	
	//넓이
	private int width;
	
	//높이
	private int height;
	
	//좌상단 포인트와 넓이 높이 를 가져와 블록을 생성한다.
	public Block(Point left_Top_Point, int width, int height) {
		
		//생성시 입력받은 좌 상단 좌쵸를 가져온다.
		block_Point = new Point(left_Top_Point.x, left_Top_Point.y);

		//넓이와 높이를 가져온다.
		this.width = width;
		this.height = height;
		
	}
	
	//리턴으로 객체를 하지 않는 이유는 private 보안상의 이유로
	
	//좌상단 포인트와 넓이 높이를 리턴한다.
	public Point get_Left_Top_Point(){
		return block_Point;
	}
	
	//넓이를 반환한다.
	public int widht(){
		return width;
	}
	
	//높이를 반환한다.
	public int height(){
		return height;
	}
	
	
	
	
}
