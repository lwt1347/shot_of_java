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
import mapData.Block;

public class MapMakerPanel extends JPanel implements KeyListener, MouseMotionListener, MouseListener{

	//마우스 시작 지점
	Point start_Point;
	Point end_Point;
	
	
	boolean block_Make = false; //1번 블럭 생성 하기 위한 플래그
	boolean walker_Make = false; //2번 워커 생성
	
	//벽돌에 집어 넣는다.
	Block block;
	Block block_1;
	
	//워커 
	Walker walker;
	
	ArrayList<Block> array_Block;	
	ArrayList<Walker> array_Walker;
	
	
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
		
		array_Walker = new ArrayList<Walker>();
		
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
		for(int i = 0; i<array_Block.size(); i++){
			block = (Block) array_Block.get(i);
			
			g.drawRect(block.get_Left_Top_Point().x, block.get_Left_Top_Point().y, block.get_Widht() , block.get_Height() );
			
		}
		
		//어레이된 워커 그리기
		for(int i=0; i<array_Walker.size(); i++){
			walker = (Walker) array_Walker.get(i);
			
			g.drawRect(walker.get_enemy_Point().x, walker.get_enemy_Point().y, walker.get_Enemy_Width(), walker.get_Enemy_Height());
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
			str += "&";
			//블럭과 워커의 분기는 &로 표기한다.
			for(int i=0; i<array_Walker.size(); i++){
				walker = (Walker) array_Walker.get(i);
				str += walker.get_Left_Bound_Site() + "@" + walker.get_Right_Bound_Site() + "@" + walker.get_Bottom_Bound_Site() + "&";
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
			
			//1번 = 블럭 생성 2번 = 적군 워커 생성 //범위 포인터 중복시 삭제
			case KeyEvent.VK_1 : //블럭 생성
				
				block_Make = true;
				walker_Make = false;
			break;
			
			case KeyEvent.VK_2 : //블럭 생성
				
				block_Make = false;
				walker_Make = true; //워커 생성
				
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
		if(block_Make){ //블록 메이커 설정에서 사용
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
		
		
		
		
		for(int i=0; i<array_Block.size(); i++){
			block = (Block) array_Block.get(i);
			//System.out.println(block.get_Left_Top_Point().x);
			

			//발판 중복확인 중복된 것 삭제
			for(int j = 0; j<array_Block.size(); j++){
				if(i!=j){ //자기 자신과는 검사하지 않는다.
					
				block_1 = (Block) array_Block.get(j);
				
				if(block.get_Left_Top_Point().x + block.get_Widht() < block_1.get_Left_Top_Point().x ||
				 block.get_Left_Top_Point().x > block_1.get_Left_Top_Point().x + block_1.get_Widht() ||
				  block.get_Left_Top_Point().y + block.get_Height() < block_1.get_Left_Top_Point().y ||
				  block.get_Left_Top_Point().y > block_1.get_Left_Top_Point().y + block_1.get_Height()){
					
					
				}
				else {
					System.out.println("겹침");
					array_Block.remove(j);
					array_Block.remove(i);
				}
				
				}
			}
		}
	}	
		
		//워커 만들기
		if(walker_Make){		//바운딩 범위, 위치, 위치
			walker = new Walker(start_Point.x, start_Point.x, start_Point.y);  //왜 움직이지?
			//탈출시 포문도 함께 탈출
			boolean forFlag = false;
			
			
			
				//안겹쳤을때 워커의 위치를 겹칠때까지 내린다.y 축으로
				while(true){
						
						
					for(int i=0; i<array_Block.size(); i++){
					block = (Block) array_Block.get(i);
							
					
					if(block.get_Left_Top_Point().x > walker.get_enemy_Point().x + walker.get_Enemy_Width() || 
						block.get_Left_Top_Point().x + block.get_Widht() <= walker.get_enemy_Point().x ||
						block.get_Left_Top_Point().y > walker.get_enemy_Point().y + walker.get_Enemy_Height() ||
						block.get_Left_Top_Point().y + block.get_Height() <= walker.get_enemy_Point().y){
						
						//안겹쳤을때 워커의 위치를 겹칠때까지 내린다.y 축으로
						walker.set_Enemy_Point_Y();
						
						}else{
							//walker.init_Bound_Site(block.get_Left_Top_Point().x + 30, block.get_Widht(), walker.get_enemy_Point().y + 70);
							//아래코드와 같이 해야 블록위에 바로 적군이 생긴다.
							walker.init_Bound_Site(start_Point.x, start_Point.x, walker.get_enemy_Point().y + 70);
							
							array_Walker.add(walker);
							//겹칠때 탈출
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
	//**********************마우스 리스너
	
	
	
	
	

}
