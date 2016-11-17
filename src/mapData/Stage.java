package mapData;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import enemy.Enemy;
import enemy.Walker;
import enemy.Walker_Dog;

public class Stage {

	//�迭����Ʈ�� ��Ͽ� ���� ������ ��´�.
	ArrayList block_Array;

	//���� �迭
	ArrayList enemy_Array;
	
	//��Ϲ��� �迭
	ArrayList Trab_Saw_Tooth_Array;
	
	Block temp_Block; 
	
	//��Ŀ ����
	Walker temp_Walker;
	
	//��Ŀ�� ����
	Walker_Dog temp_Walker_Dog;
	
	//��Ż ����
	Next_Page_Portal portal;
	
	//��Ϲ��� ����
	Trab_Saw_Tooth trab_Saw_Tooth;
	
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
	
	enemy_Array = new ArrayList<Enemy>(); //��Ŀ �ʱ�ȭ
	
	//��Ż �ʱ�ȭ
	portal = new Next_Page_Portal(0, 0);
	
	//��Ϲ��� �ʱ�ȭ
	trab_Saw_Tooth = new Trab_Saw_Tooth(0, 0);
	Trab_Saw_Tooth_Array = new ArrayList<Trab_Saw_Tooth>();
	}
	
	
	//�޸� ���� ����
	public void reset_Memory(){
		block_Array.clear();
		enemy_Array.clear();
		Trab_Saw_Tooth_Array.clear();
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
		String[] Map = line.split("����");
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
		
		Map = line.split("��Ŀ");
		String[] walker_Element = null;
		for(int i=1; i<Map.length-1; i++){
		//for(int i=1; i<3; i++){	
			walker_Element = Map[i].split("@");
			//System.out.println(walker_Element.length);
			temp_Left_Site = Integer.parseInt(walker_Element[0]);
			temp_Right_Site = Integer.parseInt(walker_Element[1]);
			temp_Bottom_Site = Integer.parseInt(walker_Element[2]);
			temp_Walker = new Walker(temp_Left_Site - 35, temp_Right_Site, temp_Bottom_Site - 70);
			enemy_Array.add(temp_Walker);
		}
		//System.out.println(Map.length);
		
		//��Ŀ��
		Map = line.split("����");
		String[] walker_Dog_Element = null;
		for(int i=1; i<Map.length-1; i++){
		//for(int i=1; i<3; i++){	
			walker_Dog_Element = Map[i].split("@");
			//System.out.println(walker_Element.length);
			temp_Left_Site = Integer.parseInt(walker_Dog_Element[0]);
			temp_Right_Site = Integer.parseInt(walker_Dog_Element[1]);
			temp_Bottom_Site = Integer.parseInt(walker_Dog_Element[2]);
			temp_Walker_Dog = new Walker_Dog(temp_Left_Site - 35, temp_Right_Site, temp_Bottom_Site - 70);
			enemy_Array.add(temp_Walker_Dog);
		}
		
		//��� ����
		Map = line.split("���");
		String[] trab_Saw_Tooth_Element = null;
		for(int i=1; i<Map.length-1; i++){
		//for(int i=1; i<3; i++){	
			trab_Saw_Tooth_Element = Map[i].split("@");
			//System.out.println(walker_Element.length);
			int x = Integer.parseInt(trab_Saw_Tooth_Element[0]);
			int y = Integer.parseInt(trab_Saw_Tooth_Element[1]);
			trab_Saw_Tooth = new Trab_Saw_Tooth(x,y);
			Trab_Saw_Tooth_Array.add(trab_Saw_Tooth);
		}
		
		
		
		
		
		//��Ż ������ *�� ����� ���� �������� ����.
		Map = line.split("��Ż");
		    	String[] portal_Point = null;
		portal_Point = Map[1].split("@");
				//�����������Ľ��ؼ� ��Ż�� ��ġ�� �˷��ش�
		portal.set_portal_Point(Integer.parseInt(portal_Point[0]), Integer.parseInt(portal_Point[1]));
				//50@1700
				
		
				
	}
	
	//������ ����� ��ȯ�ؼ� ���� �׸���.
	public ArrayList<Block> get_Block(){
		return block_Array;
	}
	
	//������ ��Ŀ�� ��ȯ�Ѵ�.
	public ArrayList<Enemy> get_Enemy(){
		return enemy_Array;
	}
	
	//������ ��Ż �� ��ȯ�Ѵ�.
	public Next_Page_Portal get_Next_Page_Portal(){
		return portal;
	}
	
	//������ ��Ϲ����� ��ȯ�Ѵ�.
	public ArrayList<Trab_Saw_Tooth> get_Trab_Saw_Tooth(){
		return Trab_Saw_Tooth_Array;
	}
	
	
	
}
