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
	public Stage() {
	
	block_Array = new ArrayList<Block>();
	temp_Block_Left_Top_Point = new Point(0, 0); //�ʱ�ȭ
	//temp_Block = new Block(temp_Block_Left_Top_Point, 0, 0); //��� ����� ���� ���

	
	}
	
	//1��������
	public void map_Stage(int stage_Num){
		
		//�������� ����
				switch(stage_Num){
					//1�������� �϶�
				    case 1:
				    	stage_Num_1();
				    	
				    	
				    	
					break;
					
					
				}
		
	}

	public void stage_Num_1(){
		
				temp_Block_Left_Top_Point.x = 0;
				temp_Block_Left_Top_Point.y = 670;
				width = 700;
				height = 30;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� �ٴ� ����
		
				
				temp_Block_Left_Top_Point.x = 300;
				temp_Block_Left_Top_Point.y = 450;
				width = 300;
				height = 35;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//������ �߰� �κ� �� �ٴ�
				
				
				
				temp_Block_Left_Top_Point.x = 150;
				temp_Block_Left_Top_Point.y = 250;
				width = 270;
				height = 40;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//��� �� �κ� ��¦ �� �ٴ�
				
				
				
				//1���������� ù ��° ���
				temp_Block_Left_Top_Point.x = 100;
				temp_Block_Left_Top_Point.y = 500;
				width = 100;
				height = 50;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				
				
				
				
				temp_Block_Left_Top_Point.x = 500;
				temp_Block_Left_Top_Point.y = 300;
				width = 70;
				height = 30;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//������ ��� ���� ���
				
				
				
				
				temp_Block_Left_Top_Point.x = 450;
				temp_Block_Left_Top_Point.y = 600;
				width = 200;
				height = 30;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				
				
				temp_Block_Left_Top_Point.x = 50;
				temp_Block_Left_Top_Point.y = 450;
				width = 30;
				height = 30;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� �߰� �Ʒ��� ���� �ٴ�
				
				
				temp_Block_Left_Top_Point.x = 100;
				temp_Block_Left_Top_Point.y = 350;
				width = 30;
				height = 20;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� �߰� �Ʒ� ���� ���� �ٴ�
				
				
				
				
				
				
				
				
				temp_Block_Left_Top_Point.x = 650;
				temp_Block_Left_Top_Point.y = 550;
				width = 30;
				height = 40;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� ������ �Ʒ� ���� �簢�� ����
				
				
				temp_Block_Left_Top_Point.x = 600;
				temp_Block_Left_Top_Point.y = 200;
				width = 100;
				height = 40;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� ������ ���� �簢��
				
				
				
				
				
	}
	
	//������ ����� ��ȯ�ؼ� ���� �׸���.
	public ArrayList<Block> get_Block(){
		return block_Array;
	}
	
	

}
