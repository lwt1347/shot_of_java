package weapon;

import java.awt.Point;

//권총
public class Pistol extends Weapon{

		public Pistol(Point character_Point, boolean bullet_Side_LEFT_RIGHT) {
			
			super(character_Point, bullet_Side_LEFT_RIGHT);
			
			//피스톨의 기본 공격력
			bullet_Power = 1;
			
			//권총의 기본크기
			weapon_Width = 10;
			weapon_Height = 10;
			
		
		}
		
		//권총의 날아가는 방향을 계산 좌축 또는 우측
		public void pistol_Move(boolean direction){
			//참이면 왼쪽, 거짓이면 오른쪽 으로 이동
			
			if(direction){ //좌측
				bullet_Point.x -= 15;
			}else{// 우측
				bullet_Point.x += 15;
			}
			
		}
		
	}
