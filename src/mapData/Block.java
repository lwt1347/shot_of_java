package mapData;

import java.awt.Point;

public class Block {
	
	//박스가 놓일 좌상단 포인트
	private Point block_Point;
	
	//넓이
	private int width;
	
	//캐릭터와 접척 여부
	private boolean contact;
	
	//높이
	private int height;
	
	//좌상단 포인트와 넓이 높이 를 가져와 블록을 생성한다.
	public Block(Point left_Top_Point, int width, int height) {
		
		//생성시 입력받은 좌 상단 좌쵸를 가져온다.
		block_Point = new Point(left_Top_Point.x, left_Top_Point.y);

		//넓이와 높이를 가져온다.
		this.width = width;
		this.height = height;
		
		//접촉여부
		contact = false;
		
	}
	
	//맵 메이커에서 벽돌을 만들기 위해 필요함
	public void set_Block_Point_Width_Height(int x, int y, int width, int height){
		block_Point.x = x;
		block_Point.y = y;
		this.width = width;
		this.height = height;
	}
	
	
	//리턴으로 객체를 하지 않는 이유는 private 보안상의 이유로
	
	//좌상단 포인트와 넓이 높이를 리턴한다.
	public Point get_Left_Top_Point(){
		return block_Point;
	}
	
	//넓이를 반환한다.
	public int get_Widht(){
		return width;
	}
	
	//높이를 반환한다.
	public int get_Height(){
		return height;
	}
	
	//접촉시에 true
	public void set_Contect_T(){
		contact = true;
	}
	public void set_Contect_F(){
		contact = false;
	}
	
	public boolean get_Set_Contect(){
		return contact;
	}
	
	
}
