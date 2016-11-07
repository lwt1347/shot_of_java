package mapData;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
		
		String line = null;
		//�� ����Ŀ�� ���� ���� ���� �ҷ��´�.
		try {
			
			File file = new File("C:\\Users\\USER\\workspace\\Shot\\bin\\mapData\\stage_1.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			
			//while ((line = reader.readLine()) != null ) {
			line = reader.readLine();
			System.out.println(line);
			//}
			reader.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//txt �� ����� ���� �Ľ��ؼ� �ؼ��Ѵ�.
		String[] Map = line.split("#");
		String[] square_Element = null;
		for(int i=0; i<Map.length; i++){
			
			square_Element = Map[i].split("@");
			
			temp_Block_Left_Top_Point.x = Integer.parseInt(square_Element[0]);
			temp_Block_Left_Top_Point.y = Integer.parseInt(square_Element[1]);
			width = Integer.parseInt(square_Element[2]);
			height = Integer.parseInt(square_Element[3]);
			temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
			block_Array.add(temp_Block);
			
			
		}

		
		
		/*
				temp_Block_Left_Top_Point.x = 0;
				temp_Block_Left_Top_Point.y = 670;
				width = 700;
				height = 500;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� �ٴ� ����
		
				
				temp_Block_Left_Top_Point.x = 300;
				temp_Block_Left_Top_Point.y = 450;
				width = 300;
				height = 55;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//������ �߰� �κ� �� �ٴ�
				
				
				
				temp_Block_Left_Top_Point.x = 150;
				temp_Block_Left_Top_Point.y = 250;
				width = 270;
				height = 50;
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
				height = 60;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//������ ��� ���� ���
				
				
				
				
				temp_Block_Left_Top_Point.x = 450;
				temp_Block_Left_Top_Point.y = 600;
				width = 200;
				height = 55;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� ������ �Ʒ�
				
				
				temp_Block_Left_Top_Point.x = 50;
				temp_Block_Left_Top_Point.y = 450;
				width = 30;
				height = 60;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� �߰� �Ʒ��� ���� �ٴ�
				
				
				temp_Block_Left_Top_Point.x = 100;
				temp_Block_Left_Top_Point.y = 350;
				width = 30;
				height = 45;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� �߰� �Ʒ� ���� ���� �ٴ�
				
				
				
				
				
				
				
				
				temp_Block_Left_Top_Point.x = 650;
				temp_Block_Left_Top_Point.y = 550;
				width = 30;
				height = 50;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� ������ �Ʒ� ���� �簢�� ����
				
				
				temp_Block_Left_Top_Point.x = 600;
				temp_Block_Left_Top_Point.y = 200;
				width = 100;
				height = 50;
				temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
				block_Array.add(temp_Block);
				//���� ������ ���� �簢��
		*/
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
	}
	
	//������ ����� ��ȯ�ؼ� ���� �׸���.
	public ArrayList<Block> get_Block(){
		return block_Array;
	}
	
	

}
