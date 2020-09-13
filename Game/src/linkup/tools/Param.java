package linkup.tools;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 * ����������
 *
 */
public class Param {
	//-------��������--------
	public static int chessNum = 20;

	//-------�������̵�����������(��Сͼ���)--------
	public static int rows = 8;
	public static int cols = 10;
	
	//-------�������ӵĳ��Ϳ�-----------
	public static int chessWidth = 48;
	public static int chessHeight = 48;
	
	//-------�������̵��߽�ľ���-----------
	public static int marginWidth = 110 - chessWidth;
	public static int marginHeight = 108 - chessHeight;
	
	//-------���ñ���ͼƬ--------------
	public static Image imageBackground1 = new ImageIcon("background/1.jpg").getImage();
	public static Image imageBackground2 = new ImageIcon("background/2.jpg").getImage();
	public static Image imageBackground3 = new ImageIcon("background/3.jpg").getImage();
	public static Image imageBackground4 = new ImageIcon("background/4.jpg").getImage();
	public static Image imageBackground5 = new ImageIcon("background/5.jpg").getImage();
	public static Image imageBackground6 = new ImageIcon("background/6.jpg").getImage();
	public static Image imageBackground7 = new ImageIcon("background/7.jpg").getImage();
	public static Image imageBackground8 = new ImageIcon("background/8.jpg").getImage();
	public static Image imageBackground = imageBackground1;
	
	//-------�������Ͻ�ͼ��------------
	public static Image leftPicture = new ImageIcon("pictures/01.jpg").getImage();
	
	//-------���������ϵ�Сͼ��---------
	public static Image[] chessImage = new Image[chessNum];
	static{
		for(int i = 0; i < chessNum; i++){
			chessImage[i] = new ImageIcon("image/cute_36/" + (i + 1) + ".png").getImage();
		}
	}
	
	//��Ϸʱ��  ��
	public static int timeCount = 100;
	//ϴ�ƴ���(Ĭ��)
	public static int refreshCountConstant = 3;
	//��ʾ����(Ĭ��)
	public static int remarkCountConstant = 3;
	//��ǰ��Ϸ�ɼ�
	public static int score = 0;
	//��ǰ��Ϸ��״̬:0û��ʼ;1������;2��ͣ;3��Ϸ����
	public static int gameStatus = 0;
}
