package mapData;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import enemy.Walker;

public class Stage {

	//�迭����Ʈ�� ��Ͽ� ���� ������ ��´�.
	ArrayList block_Array;

	//��Ŀ �迭
	ArrayList walker_Array;
	
	Block temp_Block; 
	
	Walker temp_Walker;
	
	//����� �»�� ����Ʈ�� ��ȯ�ϱ� ���� �ӽ� ����
	Point temp_Block_Left_Top_Point;
	//����� ����, ���� �ӽ� ����
	int width;
	int height;
	
	//��Ŀ �� ���� �ӽ� ����
	int temp_Left_Site;
	int temp_Right_Site;
	int temp_Bottom_Site;
	
	
	//������������ ����� ��ġ�� �����Ѵ�.
	public Stage() {
	
	block_Array = new ArrayList<Block>();
	temp_Block_Left_Top_Point = new Point(0, 0); //�ʱ�ȭ
	//temp_Block = new Block(temp_Block_Left_Top_Point, 0, 0); //��� ����� ���� ���
	
	walker_Array = new ArrayList<Walker>(); //��Ŀ �ʱ�ȭ
	
	
	
	
	}
	
	//1��������
	public void map_Stage(int stage_Num){
		
		//�������� ����
				
		stage_Num(stage_Num);
				    	
				    	
				
		
	}

	public void stage_Num(int stage_Num){
		
		String line = null;
		//�� ����Ŀ�� ���� ���� ���� �ҷ��´�.
		try {
			
			File file = new File("C:\\Users\\USER\\workspace\\Shot\\bin\\mapData\\stage_" + stage_Num +".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			
			
			//while ((line = reader.readLine()) != null ) {
			line = reader.readLine();
			//System.out.println(line);
			//}
			reader.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//txt �� ����� ���� �Ľ��ؼ� �ؼ��Ѵ�.
		String[] Map = line.split("#");
		String[] square_Element = null;
		for(int i=0; i<Map.length-1; i++){ //���� ����
			
			square_Element = Map[i].split("@");
			
			temp_Block_Left_Top_Point.x = Integer.parseInt(square_Element[0]);
			temp_Block_Left_Top_Point.y = Integer.parseInt(square_Element[1]);
			width = Integer.parseInt(square_Element[2]);
			height = Integer.parseInt(square_Element[3]);
			temp_Block = new Block(temp_Block_Left_Top_Point, width, height);
			block_Array.add(temp_Block);
			
		}	
		
		
		
		//��Ŀ ����
		
		Map = line.split("&");
		String[] walker_Element = null;
		for(int i=1; i<Map.length; i++){
		//for(int i=1; i<3; i++){	
			walker_Element = Map[i].split("@");
			//System.out.println(walker_Element.length);
			temp_Left_Site = Integer.parseInt(walker_Element[0]);
			temp_Right_Site = Integer.parseInt(walker_Element[1]);
			temp_Bottom_Site = Integer.parseInt(walker_Element[2]);
			temp_Walker = new Walker(temp_Left_Site - 35, temp_Right_Site, temp_Bottom_Site - 70);
			walker_Array.add(temp_Walker);
		}
		//System.out.println(Map.length);
		//^359^359^443&724^724^644&222^222^829&793^793^323&826^826^108&
				
	}
	
	//������ ����� ��ȯ�ؼ� ���� �׸���.
	public ArrayList<Block> get_Block(){
		return block_Array;
	}
	
	//������ ��Ŀ�� ��ȯ�Ѵ�.
	public ArrayList<Walker> get_Walker(){
		return walker_Array;
	}
	

}
