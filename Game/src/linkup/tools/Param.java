package linkup.tools;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 * 建立参数类
 *
 */
public class Param {
	//-------棋子种数--------
	public static int chessNum = 20;

	//-------设置棋盘的行数和列数(有小图标的)--------
	public static int rows = 8;
	public static int cols = 10;
	
	//-------设置棋子的长和宽-----------
	public static int chessWidth = 48;
	public static int chessHeight = 48;
	
	//-------设置棋盘到边界的距离-----------
	public static int marginWidth = 110 - chessWidth;
	public static int marginHeight = 108 - chessHeight;
	
	//-------设置背景图片--------------
	public static Image imageBackground1 = new ImageIcon("background/1.jpg").getImage();
	public static Image imageBackground2 = new ImageIcon("background/2.jpg").getImage();
	public static Image imageBackground3 = new ImageIcon("background/3.jpg").getImage();
	public static Image imageBackground4 = new ImageIcon("background/4.jpg").getImage();
	public static Image imageBackground5 = new ImageIcon("background/5.jpg").getImage();
	public static Image imageBackground6 = new ImageIcon("background/6.jpg").getImage();
	public static Image imageBackground7 = new ImageIcon("background/7.jpg").getImage();
	public static Image imageBackground8 = new ImageIcon("background/8.jpg").getImage();
	public static Image imageBackground = imageBackground1;
	
	//-------设置左上角图标------------
	public static Image leftPicture = new ImageIcon("pictures/01.jpg").getImage();
	
	//-------设置棋盘上的小图标---------
	public static Image[] chessImage = new Image[chessNum];
	static{
		for(int i = 0; i < chessNum; i++){
			chessImage[i] = new ImageIcon("image/cute_36/" + (i + 1) + ".png").getImage();
		}
	}
	
	//游戏时间  秒
	public static int timeCount = 100;
	//洗牌次数(默认)
	public static int refreshCountConstant = 3;
	//提示次数(默认)
	public static int remarkCountConstant = 3;
	//当前游戏成绩
	public static int score = 0;
	//当前游戏的状态:0没开始;1进行中;2暂停;3游戏结束
	public static int gameStatus = 0;
}
