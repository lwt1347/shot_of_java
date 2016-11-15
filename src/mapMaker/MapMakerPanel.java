package mapMaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JPanel;

import enemy.Walker;
import enemy.Walker_Dog;
import mapData.Block;
import mapData.Next_Page_Portal;

public class MapMakerPanel extends JPanel implements KeyListener, MouseMotionListener, MouseListener{

	//���콺 ���� ����
	Point start_Point;
	Point end_Point;
	
	
	boolean block_Make = false; //1�� �� ���� �ϱ� ���� �÷���
	boolean walker_Make = false; //2�� ��Ŀ ����
	boolean potal_Make = false; //3�� ��Ż ����
	boolean walker_Dog_Make = false; //4�� ���� ����
	
	//������ ���� �ִ´�.
	Block block;
	Block block_1;
	
	//��Ŀ 
	Walker walker;
	
	//����
	Walker_Dog walker_Dog;
	
	//��Ż
	Next_Page_Portal portal;
	
	ArrayList<Block> array_Block;	
	ArrayList<Walker> array_Walker;
	
	//���� ���
	ArrayList<Walker_Dog> array_Walker_Dog;
	
	//�簢�� swap ����
	int temp = 0;
	
	public MapMakerPanel() {
		// TODO Auto-generated constructor stub
		
		this.addKeyListener(this);			//������ ����� �ؾ� �۵���
		this.addMouseMotionListener(this);  //������ ����� �ؾ� �۵���
		this.addMouseListener(this);
		
		
		start_Point = new Point(0,0); //�ʱ� ������ �ʱ�ȭ
		end_Point = new Point(0,0);
		
		
		
		array_Block = new ArrayList<Block>();
		
		//����
		array_Walker_Dog = new ArrayList<Walker_Dog>();
		
		array_Walker = new ArrayList<Walker>();
		
		portal = new Next_Page_Portal(0, 0);
		
		setSize(1000, 1900);
		setVisible(true);			   //�������� ���� ���̰� ���	
		
		
	
		
	}
	

	
	public void paintComponent(Graphics g) {
		
		//ȭ�� ����
		g.clearRect(0, 0, 1000, 1900);
		
		setForeground(Color.black);
		
		
		//�� ��ܿ��� �� �ϴ�����
		if(start_Point.x <= end_Point.x && start_Point.y <= end_Point.y){
			g.drawRect(start_Point.x, start_Point.y, (end_Point.x - start_Point.x), (end_Point.y - start_Point.y));
		}
		
		//�� �ϴܿ��� �� �������
		else if(start_Point.x <= end_Point.x && start_Point.y > end_Point.y){
			g.drawRect(start_Point.x, end_Point.y, (end_Point.x - start_Point.x), (start_Point.y - end_Point.y));
		
		}
		
		//�� ��ܿ��� �� �ϴ�����
		else if(start_Point.x >= end_Point.x && start_Point.y <= end_Point.y){
			g.drawRect(end_Point.x, start_Point.y, (start_Point.x-end_Point.x ), (end_Point.y - start_Point.y));
		}
		
		//�� �ϴܿ��� �� ������� 
		else if(start_Point.x >= end_Point.x && start_Point.y > end_Point.y){
			g.drawRect(end_Point.x, end_Point.y, (start_Point.x-end_Point.x ), (start_Point.y - end_Point.y));
		}
		
		
		//��� �� �ڽ� �׸�
		for(int i = 0; i<array_Block.size(); i++){
			block = (Block) array_Block.get(i);
			
			g.drawRect(block.get_Left_Top_Point().x, block.get_Left_Top_Point().y, block.get_Widht() , block.get_Height() );
			
		}
		
		//��̵� ��Ŀ �׸���
		for(int i=0; i<array_Walker.size(); i++){
			walker = (Walker) array_Walker.get(i);
			
			g.drawRect(walker.get_enemy_Point().x, walker.get_enemy_Point().y, walker.get_Enemy_Width(), walker.get_Enemy_Height());
		}
		
		
		//���� �׸���
		for(int i=0; i<array_Walker_Dog.size(); i++){
			walker_Dog = (Walker_Dog)array_Walker_Dog.get(i);
			g.drawRect(walker_Dog.get_enemy_Point().x, walker_Dog.get_enemy_Point().y, walker_Dog.get_Enemy_Width(), walker_Dog.get_Enemy_Height());
		}
		
		
		//��Ż �׸���
		g.drawRect(portal.get_Left_Top_Point().x, portal.get_Left_Top_Point().y, 30, 50);
		
		
	}
	
	////////////////////////////////////Ű �̺�Ʈ
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_ENTER :
			
			//���� ��ǥ�� @�� ���̾� �����ϰ� �� �ٲ��� #���� �����Ѵ�.
			String str = "";
			for(int i=0; i<array_Block.size(); i++){
				block = (Block) array_Block.get(i);
				str+= block.get_Left_Top_Point().x + "@" + block.get_Left_Top_Point().y + "@" + 
						block.get_Widht() + "@" + block.get_Height() + "����";
				//System.out.println(block.get_Left_Top_Point().x);
			}
			str += "��Ŀ";
			//���� ��Ŀ�� �б�� &�� ǥ���Ѵ�.
			for(int i=0; i<array_Walker.size(); i++){
				walker = (Walker) array_Walker.get(i);
				str += walker.get_Left_Bound_Site() + "@" + walker.get_Right_Bound_Site() + "@" + walker.get_Bottom_Bound_Site() + "��Ŀ";
			}
			
			str += "����";
			//���� ��Ŀ�� �б�� &�� ǥ���Ѵ�.
			for(int i=0; i<array_Walker_Dog.size(); i++){
				walker_Dog = (Walker_Dog) array_Walker_Dog.get(i);
				str += walker_Dog.get_Left_Bound_Site() + "@" + walker_Dog.get_Right_Bound_Site() + "@" + walker_Dog.get_Bottom_Bound_Site() + "����";
			}
			
			
			
			//��Ż�����
			str += "��Ż" + portal.get_Left_Top_Point().x + "@" + (portal.get_Left_Top_Point().y-20);
			//4@1804@994@84����616@1602@270@50���ӿ�Ŀ721@721@1804��Ŀ730@730@1603��Ŀ��Ż50@1700
			
			//System.out.println(str);
			
			//���� �����
			try{
			String fileName = "C:\\Users\\USER\\workspace\\Shot\\bin\\mapData\\stage_5.txt"; //1���������� ����
			
			//���� ��ü����
			File file = new File(fileName);
			
			//true ������ ������ ���� ���뿡 �̾ �ۼ�
			FileWriter fw = new FileWriter(file, true);
			
			//���Ͼȿ� ���ڿ�����
			fw.write(str);
			
			//��ü �ݱ�
			fw.close();
			
			}catch (Exception e2) {
				// TODO: handle exception
			}
			
			
			System.out.println("���Ϸ� ���� �Ǿ����ϴ�.");
			break;
			
			//1�� = �� ���� 2�� = ���� ��Ŀ ���� //���� ������ �ߺ��� ����
			case KeyEvent.VK_1 : //�� ����
				
				block_Make = true;
				walker_Make = false;
				potal_Make = false;
				walker_Dog_Make = false;
			break;
			
			
			//��Ŀ
			case KeyEvent.VK_2 : //�� ����
				
				block_Make = false;
				walker_Make = true; 
				potal_Make = false;
				walker_Dog_Make = false;
			break;
			
			//��Ż ����
			case KeyEvent.VK_3 : //
				block_Make = false;
				walker_Make = false; 
				potal_Make = true;
				walker_Dog_Make = false;
				break;
				
			//����
			case KeyEvent.VK_4 :
				block_Make = false;
				walker_Make = false; 
				potal_Make = false;
				walker_Dog_Make = true;
				break;
				
				
		}
		
		
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	//********************************Ű �̺�Ʈ

	
	
	//////////////////////////////���콺 ��� ������
	@Override
	public void mouseDragged(java.awt.event.MouseEvent e) {
		//���콺 �巡��
		//�簢���� �׷����鼭 �ϼ��ȴ�.
	
		end_Point.x = e.getX();
		end_Point.y = e.getY();
		
		
		repaint();
	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent e) {
		
	}
	//******************************���콺 ��� ������

	
	
	
	///////////////////////////���콺 ������
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		//���� ���� ���� ������ �簢�� �׸��� ���� �׸���
		
		start_Point.x = e.getX();
		start_Point.y = e.getY();
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		//���콺�� ���̶� ��ġ �ٲ� �ִ��� �ٲ��� �ʴ´�.
		if(block_Make){ //��� ����Ŀ �������� ���
				//�迭�� �ֱ� ���ؼ� ��� �������־����
				block = new Block(start_Point, 0, 0);
		
				//�� ��ܿ��� �� �ϴ�����
				if(start_Point.x <= end_Point.x && start_Point.y <= end_Point.y){
					block.set_Block_Point_Width_Height(start_Point.x, start_Point.y, (end_Point.x - start_Point.x), (end_Point.y - start_Point.y));
				}
				
				//�� �ϴܿ��� �� �������
				else if(start_Point.x <= end_Point.x && start_Point.y > end_Point.y){
					block.set_Block_Point_Width_Height(start_Point.x, end_Point.y, (end_Point.x - start_Point.x), (start_Point.y - end_Point.y));
				
				}
				
				//�� ��ܿ��� �� �ϴ�����
				else if(start_Point.x >= end_Point.x && start_Point.y <= end_Point.y){
					block.set_Block_Point_Width_Height(end_Point.x, start_Point.y, (start_Point.x-end_Point.x ), (end_Point.y - start_Point.y));
				}
				
				//�� �ϴܿ��� �� ������� //if(start_Point.x >= end_Point.x && start_Point.y > end_Point.y)
				else {
					block.set_Block_Point_Width_Height(end_Point.x, end_Point.y, (start_Point.x-end_Point.x ), (start_Point.y - end_Point.y));
				}
				//�迭�� �־���
				array_Block.add(block);
		
		
		
		
		for(int i=0; i<array_Block.size(); i++){
			block = (Block) array_Block.get(i);
			//System.out.println(block.get_Left_Top_Point().x);
			

			//���� �ߺ�Ȯ�� �ߺ��� �� ����
			for(int j = 0; j<array_Block.size(); j++){
				if(i!=j){ //�ڱ� �ڽŰ��� �˻����� �ʴ´�.
					
				block_1 = (Block) array_Block.get(j);
				
				if(block.get_Left_Top_Point().x + block.get_Widht() < block_1.get_Left_Top_Point().x ||
				 block.get_Left_Top_Point().x > block_1.get_Left_Top_Point().x + block_1.get_Widht() ||
				  block.get_Left_Top_Point().y + block.get_Height() < block_1.get_Left_Top_Point().y ||
				  block.get_Left_Top_Point().y > block_1.get_Left_Top_Point().y + block_1.get_Height()){
					
					
				}
				else {
					System.out.println("��ħ");
					array_Block.remove(j);
					array_Block.remove(i);
				}
				
				}
			}
		}
	}	
		
		//��Ŀ �����
		if(walker_Make){		//�ٿ�� ����, ��ġ, ��ġ
			walker = new Walker(start_Point.x, start_Point.x, start_Point.y);  //�� ��������?
			//Ż��� ������ �Բ� Ż��
			boolean forFlag = false;
			
			
			
				//�Ȱ������� ��Ŀ�� ��ġ�� ��ĥ������ ������.y ������
				while(true){
						
						
					for(int i=0; i<array_Block.size(); i++){
					block = (Block) array_Block.get(i);
							
					
					if(block.get_Left_Top_Point().x > walker.get_enemy_Point().x + walker.get_Enemy_Width() || 
						block.get_Left_Top_Point().x + block.get_Widht() <= walker.get_enemy_Point().x ||
						block.get_Left_Top_Point().y > walker.get_enemy_Point().y + walker.get_Enemy_Height() ||
						block.get_Left_Top_Point().y + block.get_Height() <= walker.get_enemy_Point().y){
						
						//�Ȱ������� ��Ŀ�� ��ġ�� ��ĥ������ ������.y ������
						walker.set_Enemy_Point_Y();
						
						}else{
							//walker.init_Bound_Site(block.get_Left_Top_Point().x + 30, block.get_Widht(), walker.get_enemy_Point().y + 70);
							//�Ʒ��ڵ�� ���� �ؾ� ������� �ٷ� ������ �����.
							walker.init_Bound_Site(start_Point.x, start_Point.x, walker.get_enemy_Point().y + 70);
							
							array_Walker.add(walker);
							//��ĥ�� Ż��
							forFlag = true;
							break;
						}
					}
					if(forFlag){
						break;
					}
			}
			
		}
		
		
		//���� �����
				if(walker_Dog_Make){		//�ٿ�� ����, ��ġ, ��ġ
					walker_Dog = new Walker_Dog(start_Point.x, start_Point.x, start_Point.y);  //�� ��������?
					//Ż��� ������ �Բ� Ż��
					boolean forFlag = false;
					
					
					
						//�Ȱ������� ��Ŀ�� ��ġ�� ��ĥ������ ������.y ������
						while(true){
								
								
							for(int i=0; i<array_Block.size(); i++){
							block = (Block) array_Block.get(i);
									
							
							if(block.get_Left_Top_Point().x > walker_Dog.get_enemy_Point().x + walker_Dog.get_Enemy_Width() || 
								block.get_Left_Top_Point().x + block.get_Widht() <= walker_Dog.get_enemy_Point().x ||
								block.get_Left_Top_Point().y > walker_Dog.get_enemy_Point().y + walker_Dog.get_Enemy_Height() ||
								block.get_Left_Top_Point().y + block.get_Height() <= walker_Dog.get_enemy_Point().y){
								
								//�Ȱ������� ��Ŀ�� ��ġ�� ��ĥ������ ������.y ������
								walker_Dog.set_Enemy_Point_Y();
								
								}else{
									//walker.init_Bound_Site(block.get_Left_Top_Point().x + 30, block.get_Widht(), walker.get_enemy_Point().y + 70);
									//�Ʒ��ڵ�� ���� �ؾ� ������� �ٷ� ������ �����.
									walker_Dog.init_Bound_Site(start_Point.x, start_Point.x, walker_Dog.get_enemy_Point().y + 70);
									
									array_Walker_Dog.add(walker_Dog);
									//��ĥ�� Ż��
									forFlag = true;
									break;
								}
							}
							if(forFlag){
								break;
							}
					}
					
				}
		
		
		
		if(potal_Make){ //��Ż �����
			
			portal.set_portal_Point(start_Point.x, start_Point.y);
			boolean forFlag = false;
			//�Ȱ������� ��Ŀ�� ��Ż�� ��ĥ������ ������.y ������
			while(true){
				
				for(int i=0; i<array_Block.size(); i++){
					block = (Block) array_Block.get(i);
							
					
					if(block.get_Left_Top_Point().x > portal.get_Left_Top_Point().x + 30 || 
						block.get_Left_Top_Point().x + block.get_Widht() <= portal.get_Left_Top_Point().x ||
						block.get_Left_Top_Point().y > portal.get_Left_Top_Point().y + 50 ||
						block.get_Left_Top_Point().y + block.get_Height() <= portal.get_Left_Top_Point().y){
						
						//�Ȱ������� ��Ŀ�� ��ġ�� ��ĥ������ ������.y ������
						portal.set_Portal_Point_Y();
						
						}else{
							//walker.init_Bound_Site(block.get_Left_Top_Point().x + 30, block.get_Widht(), walker.get_enemy_Point().y + 70);
							//�Ʒ��ڵ�� ���� �ؾ� ������� �ٷ� ������ �����.
							
							//��ĥ�� Ż��
							forFlag = true;
							break;
						}
					}
				if(forFlag){
					break;
				}
			
			}
			
		}
		
		
		
		
		
		
		
		
		repaint();
		
	}
	//**********************���콺 ������
	
	
	
	
	

}
