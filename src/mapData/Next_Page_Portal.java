package mapData;

	import java.awt.Point;


public class Next_Page_Portal {

		//박스가 놓일 좌상단 포인트
			private Point portal_Pount;
			
			//넓이
			private int width = 30;
			
			//캐릭터와 접척 여부
			private boolean contact;
			
			//높이
			private int height = 50;
			
			//좌상단 포인트와 넓이 높이 를 가져와 블록을 생성한다.
			public Next_Page_Portal(int x, int y) {
				
				//생성시 입력받은 좌 상단 좌쵸를 가져온다.
				portal_Pount = new Point(x, y);

			
				
				//접촉여부
				contact = false;
				
			}
			
			//맵 메이커에서 벽돌을 만들기 위해 필요함
			public void set_portal_Point(int x, int y){
				portal_Pount.x = x;
				portal_Pount.y = y;
			}
			
			
			//포탈 이미지 회전
			private int portal_Img_Cut=0;
			private int Portal_Delay = 9;
			//오른쪽으로 증가
			public int set_portal_Img_Cut(){
				portal_Img_Cut+=1;
				
				if(portal_Img_Cut > Portal_Delay){ //일어 나있을때 앉아있을때 사진의 개수가 다르다.
					portal_Img_Cut = 1;
				}
				
				return portal_Img_Cut;
			}
			
		
			
			
			//맵 메이커에서 포탈 내리기
			public void set_Portal_Point_Y(){
				portal_Pount.y ++;
			}
			
			
			//리턴으로 객체를 하지 않는 이유는 private 보안상의 이유로
			
			//좌상단 포인트와 넓이 높이를 리턴한다.
			public Point get_Left_Top_Point(){
				return portal_Pount;
			}
		
			//접촉시에 true
			public void set_Contect_T(){
				contact = true;
			}
			public void set_Contect_F(){
				contact = false;
			}
			
			public boolean get_Set_Contect(){
				return contact;
			}
	

}
