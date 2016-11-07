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

import mapData.Block;

public class MapMakerPanel extends JPanel implements KeyListener, MouseMotionListener, MouseListener{

	//���콺 ���� ����
	Point start_Point;
	Point end_Point;
	
	
	
	//������ ���� �ִ´�.
	Block block;
	ArrayList<Block> array_Block;	
	
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
		for(int i=0; i<array_Block.size(); i++){
			block = (Block) array_Block.get(i);
			
			g.drawRect(block.get_Left_Top_Point().x, block.get_Left_Top_Point().y, block.get_Widht() , block.get_Height() );
			
			
		}
		
		
		
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
						block.get_Widht() + "@" + block.get_Height() + "#";
				//System.out.println(block.get_Left_Top_Point().x);
			}
			
			//System.out.println(str);
			
			//���� �����
			try{
			String fileName = "C:\\Users\\USER\\workspace\\Shot\\bin\\mapData\\stage_1.txt"; //1���������� ����
			
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
		
		
		
		repaint();
		
		
		for(int i=0; i<array_Block.size(); i++){
			block = (Block) array_Block.get(i);
			//System.out.println(block.get_Left_Top_Point().x);
		}

		
	}
	//**********************���콺 ������
	
	
	
	
	

}
