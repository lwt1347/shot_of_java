package weapon;

import java.awt.Point;


//�⺻ �Ѿ��� �θ� Ŭ����
public class Weapon {
	
	//�Ѿ� ��ǥ
	protected Point bullet_Point;
	//�Ѿ� �ӵ�
	protected int bullet_Speed;
	//�Ѿ��� �Ŀ�
	protected int bullet_Power; 
	
	//�Ѿ��� ���ư� ����  true:�� false:��   
	protected boolean bullet_Side_LEFT_RIGHT;
	
	//�Ѿ� �浹 ������ ���� �ϱ� ����
	private boolean bullet_Remove_Bollean; 
	
	//�Ѿ��� �β�
	protected int weapon_Width;
	protected int weapon_Height;
	
	
	
	
	
	//������ �ʱ�ȭ, �Ѿ� ��ǥ �ʱ�ȭ	
	Weapon(Point character_Point, boolean bullet_Side_LEFT_RIGHT) {
		//�Ѿ� �� ���� ���ݸ� ���� �޴� ĳ������ x ���ݷ� ����
		//�Ѿ� �� ���� ���ݸ� ���� �޴� ĳ������ y ���ݷ� ����
		bullet_Point = new Point(character_Point.x, character_Point.y);
		
		
		
		
		//�Ѿ��� �⺻ �ӵ� 10���� ����
		bullet_Speed = 10;
		
		//�Ѿ��� �⺻ �Ŀ�
		bullet_Power = 1;
		
		this.bullet_Side_LEFT_RIGHT = bullet_Side_LEFT_RIGHT;
		
		bullet_Remove_Bollean = false;
		
	}
	//�Ѿ� �β� ��������
	public int get_Weapon_Width(){
		return weapon_Width;
	}
	public int get_Weapon_Height(){
		return weapon_Height;
	}
	
	
	//����� �Ѿ� 
	public void set_Remove_Bullet_Choice(){
		bullet_Remove_Bollean = true;
	}
	//����� �Ѿ� 
	public boolean get_Remove_Bullet_Choice(){
		return bullet_Remove_Bollean;
	}
	
	//�Ѿ��� ��ǥ�� ����
	public Point getPoint(){
		return bullet_Point;
	}
	
	//�Ѿ��� �¿��� ������ ����
	public boolean get_Bullet_Side_LEFT_RIGHT(){
		return bullet_Side_LEFT_RIGHT;
	}
	
	public int get_Bullet_Power(){
		return bullet_Power;
	}
	
	
	
	
}



