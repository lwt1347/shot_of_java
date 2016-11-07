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

	//마우스 시작 지점
	Point start_Point;
	Point end_Point;
	
	
	
	//벽돌에 집어 넣는다.
	Block block;
	ArrayList<Block> array_Block;	
	
	//사각형 swap 변수
	int temp = 0;
	
	public MapMakerPanel() {
		// TODO Auto-generated constructor stub
		
		this.addKeyListener(this);			//리스너 등록을 해야 작동됨
		this.addMouseMotionListener(this);  //리스너 등록을 해야 작동됨
		this.addMouseListener(this);
		
		
		start_Point = new Point(0,0); //초기 포인터 초기화
		end_Point = new Point(0,0);
		
		
		
		array_Block = new ArrayList<Block>();
		
		setSize(1000, 1900);
		setVisible(true);			   //프레임을 눈에 보이게 띄움	
		
	}
	

	
	public void paintComponent(Graphics g) {
		
		//화면 지움
		g.clearRect(0, 0, 1000, 1900);
		
		setForeground(Color.black);
		
		
		//좌 상단에서 우 하단으로
		if(start_Point.x <= end_Point.x && start_Point.y <= end_Point.y){
			g.drawRect(start_Point.x, start_Point.y, (end_Point.x - start_Point.x), (end_Point.y - start_Point.y));
		}
		
		//좌 하단에서 우 상단으로
		else if(start_Point.x <= end_Point.x && start_Point.y > end_Point.y){
			g.drawRect(start_Point.x, end_Point.y, (end_Point.x - start_Point.x), (start_Point.y - end_Point.y));
		
		}
		
		//우 상단에서 좌 하단으로
		else if(start_Point.x >= end_Point.x && start_Point.y <= end_Point.y){
			g.drawRect(end_Point.x, start_Point.y, (start_Point.x-end_Point.x ), (end_Point.y - start_Point.y));
		}
		
		//우 하단에서 좌 상단으로 
		else if(start_Point.x >= end_Point.x && start_Point.y > end_Point.y){
			g.drawRect(end_Point.x, end_Point.y, (start_Point.x-end_Point.x ), (start_Point.y - end_Point.y));
		}
		
		
		//어레이 된 박스 그림
		for(int i=0; i<array_Block.size(); i++){
			block = (Block) array_Block.get(i);
			
			g.drawRect(block.get_Left_Top_Point().x, block.get_Left_Top_Point().y, block.get_Widht() , block.get_Height() );
			
			
		}
		
		
		
	}
	
	////////////////////////////////////키 이벤트
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_ENTER :
			
			//블럭의 좌표를 @로 나늬어 구분하고 줄 바꿈을 #으로 구분한다.
			
			
			String str = "";
			for(int i=0; i<array_Block.size(); i++){
				block = (Block) array_Block.get(i);
				str+= block.get_Left_Top_Point().x + "@" + block.get_Left_Top_Point().y + "@" + 
						block.get_Widht() + "@" + block.get_Height() + "#";
				//System.out.println(block.get_Left_Top_Point().x);
			}
			
			//System.out.println(str);
			
			//파일 만들기
			try{
			String fileName = "C:\\Users\\USER\\workspace\\Shot\\bin\\mapData\\stage_1.txt"; //1스테이지로 만듬
			
			//파일 객체생성
			File file = new File(fileName);
			
			//true 지정시 파일의 기존 내용에 이어서 작성
			FileWriter fw = new FileWriter(file, true);
			
			//파일안에 문자열쓰기
			fw.write(str);
			
			//객체 닫기
			fw.close();
			
			}catch (Exception e2) {
				// TODO: handle exception
			}
			
			
			System.out.println("파일로 저장 되었습니다.");
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
	//********************************키 이벤트

	
	
	//////////////////////////////마우스 모션 리스너
	@Override
	public void mouseDragged(java.awt.event.MouseEvent e) {
		//마우스 드래그
		//사각형이 그려지면서 완성된다.
		end_Point.x = e.getX();
		end_Point.y = e.getY();
		
		
		repaint();
	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent e) {
		
	}
	//******************************마우스 모션 리스너

	
	
	
	///////////////////////////마우스 리스너
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
		//시작 지점 좌쵸 가져옴 사각형 그릴때 발판 그릴때
		
		start_Point.x = e.getX();
		start_Point.y = e.getY();
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		//마우스가 놓이땔 위치 바꿔 주던지 바꾸지 않는다.
		
				//배열에 넣기 위해서 계속 생성해주어야함
				block = new Block(start_Point, 0, 0);
		
				//좌 상단에서 우 하단으로
				if(start_Point.x <= end_Point.x && start_Point.y <= end_Point.y){
					block.set_Block_Point_Width_Height(start_Point.x, start_Point.y, (end_Point.x - start_Point.x), (end_Point.y - start_Point.y));
				}
				
				//좌 하단에서 우 상단으로
				else if(start_Point.x <= end_Point.x && start_Point.y > end_Point.y){
					block.set_Block_Point_Width_Height(start_Point.x, end_Point.y, (end_Point.x - start_Point.x), (start_Point.y - end_Point.y));
				
				}
				
				//우 상단에서 좌 하단으로
				else if(start_Point.x >= end_Point.x && start_Point.y <= end_Point.y){
					block.set_Block_Point_Width_Height(end_Point.x, start_Point.y, (start_Point.x-end_Point.x ), (end_Point.y - start_Point.y));
				}
				
				//우 하단에서 좌 상단으로 //if(start_Point.x >= end_Point.x && start_Point.y > end_Point.y)
				else {
					block.set_Block_Point_Width_Height(end_Point.x, end_Point.y, (start_Point.x-end_Point.x ), (start_Point.y - end_Point.y));
				}
				//배열에 넣어줌
				array_Block.add(block);
		
		
		
		repaint();
		
		
		for(int i=0; i<array_Block.size(); i++){
			block = (Block) array_Block.get(i);
			//System.out.println(block.get_Left_Top_Point().x);
		}

		
	}
	//**********************마우스 리스너
	
	
	
	
	

}
