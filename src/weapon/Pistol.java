package weapon;

import java.awt.Point;

//����
public class Pistol extends Weapon{

		public Pistol(Point character_Point, boolean bullet_Side_LEFT_RIGHT) {
			
			super(character_Point, bullet_Side_LEFT_RIGHT);
			
			//�ǽ����� �⺻ ���ݷ�
			bullet_Power = 1;
			
		}
		
		//������ ���ư��� ������ ��� ���� �Ǵ� ����
		public void pistol_Move(boolean direction){
			//���̸� ����, �����̸� ������ ���� �̵�
			
			if(direction){ //����
				bullet_Point.x -= 15;
			}else{// ����
				bullet_Point.x += 15;
			}
			
		}
		
	}
