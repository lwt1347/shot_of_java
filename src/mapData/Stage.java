package mapData;

import java.awt.Point;
import java.util.ArrayList;

public class Stage {

	//�迭����Ʈ�� ��Ͽ� ���� ������ ��´�.
	ArrayList block_Array;

	Block temp_Block; 
	
	//����� �»�� ����Ʈ�� ��ȯ�ϱ� ���� �ӽ� ����
	Point temp_Block_Left_Top_Point;
	//����� ����, ���� �ӽ� ����
	int width;
	int height;
	
	
	//������������ ����� ��ġ�� �����Ѵ�.
	public Stage(int stageNum) {
	
	block_Array = new ArrayList<Block>();
	temp_Block_Left_Top_Point = new Point(0, 0); //�ʱ�ȭ
	//temp_Block = new Block(temp_Block_Left_Top_Point, 0, 0); //��� ����� ���� ���


	//�������� ����
	switch(stageNum){
		//1�������� �϶�
	    case 1:
	    	stage_Num_1();
		break;
		
		
		
	}
		
	}
	
	//1��������
	public void stage_Num_1(){
		
		//1���������� ù ��° ���
		temp_Block_Left_Top_Point.x = 100;
		temp_Block_Left_Top_Point.y = 500;
		width = 100;
		height = 50;
		temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
		block_Array.add(temp_Block);
		//1�������� �� ù ��° ��� �ϼ�
		
	}

	
	
	
	

}
