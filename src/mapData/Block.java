package mapData;

import java.awt.Point;

public class Block {
	
	//�ڽ��� ���� �»�� ����Ʈ
	private Point block_Point;
	
	//����
	private int width;
	
	//����
	private int height;
	
	//�»�� ����Ʈ�� ���� ���� �� ������ ����� �����Ѵ�.
	public Block(Point left_Top_Point, int width, int height) {
		
		//������ �Է¹��� �� ��� ���ݸ� �����´�.
		block_Point = new Point(left_Top_Point.x, left_Top_Point.y);

		//���̿� ���̸� �����´�.
		this.width = width;
		this.height = height;
		
	}
	
	//�������� ��ü�� ���� �ʴ� ������ private ���Ȼ��� ������
	
	//�»�� ����Ʈ�� ���� ���̸� �����Ѵ�.
	public Point get_Left_Top_Point(){
		return block_Point;
	}
	
	//���̸� ��ȯ�Ѵ�.
	public int widht(){
		return width;
	}
	
	//���̸� ��ȯ�Ѵ�.
	public int height(){
		return height;
	}
	
	
	
	
}
