package mapData;

	import java.awt.Point;


public class Next_Page_Portal {

		//�ڽ��� ���� �»�� ����Ʈ
			private Point portal_Pount;
			
			//����
			private int width = 30;
			
			//ĳ���Ϳ� ��ô ����
			private boolean contact;
			
			//����
			private int height = 50;
			
			//�»�� ����Ʈ�� ���� ���� �� ������ ����� �����Ѵ�.
			public Next_Page_Portal(int x, int y) {
				
				//������ �Է¹��� �� ��� ���ݸ� �����´�.
				portal_Pount = new Point(x, y);

			
				
				//���˿���
				contact = false;
				
			}
			
			//�� ����Ŀ���� ������ ����� ���� �ʿ���
			public void set_portal_Point(int x, int y){
				portal_Pount.x = x;
				portal_Pount.y = y;
			}
			
			
			//��Ż �̹��� ȸ��
			private int portal_Img_Cut=0;
			private int Portal_Delay = 35;
			//���������� ����
			public int set_portal_Img_Cut(){
				portal_Img_Cut+=3;
				
				if(portal_Img_Cut > Portal_Delay){ //�Ͼ� �������� �ɾ������� ������ ������ �ٸ���.
					portal_Img_Cut = 1;
				}
				
				return portal_Img_Cut;
			}
			
		
			
			
			//�� ����Ŀ���� ��Ż ������
			public void set_Portal_Point_Y(){
				portal_Pount.y ++;
			}
			
			
			//�������� ��ü�� ���� �ʴ� ������ private ���Ȼ��� ������
			
			//�»�� ����Ʈ�� ���� ���̸� �����Ѵ�.
			public Point get_Left_Top_Point(){
				return portal_Pount;
			}
		
			//���˽ÿ� true
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
