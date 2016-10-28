package mapData;

import java.awt.Point;
import java.util.ArrayList;

public class Stage {

	//배열리스트에 블록에 대한 정보를 담는다.
	ArrayList block_Array;

	Block temp_Block; 
	
	//블록의 좌상단 포인트를 반환하기 위한 임시 변수
	Point temp_Block_Left_Top_Point;
	//블록의 넓이, 높이 임시 변수
	int width;
	int height;
	
	
	//스테이지마다 블록의 위치를 지정한다.
	public Stage(int stageNum) {
	
	block_Array = new ArrayList<Block>();
	temp_Block_Left_Top_Point = new Point(0, 0); //초기화
	//temp_Block = new Block(temp_Block_Left_Top_Point, 0, 0); //블록 어레이이 넣을 블록


	//스테이지 설정
	switch(stageNum){
		//1스테이지 일때
	    case 1:
	    	stage_Num_1();
		break;
		
		
		
	}
		
	}
	
	//1스테이지
	public void stage_Num_1(){
		
		//1스테이지의 첫 번째 블록
		temp_Block_Left_Top_Point.x = 100;
		temp_Block_Left_Top_Point.y = 500;
		width = 100;
		height = 50;
		temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
		block_Array.add(temp_Block);
		//1스테이지 의 첫 번째 블록 완성
		
	}

	
	
	
	

}
