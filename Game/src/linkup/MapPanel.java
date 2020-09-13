package linkup;
/**
 * 面板，先把组件加到面板，再加到窗体中
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import linkup.tools.Core;
import linkup.tools.Param;

public class MapPanel extends JPanel implements MouseListener{

	//定义两个点 存放数组下标
	Point firstPoint;
	Point secondPoint;
	
	mainFrame Frame;
	
	Chess[][] chess = null;
	//---构造函数中初始化数组并注册监听器，把窗体作为参数传递到面板中----
	public MapPanel(mainFrame Frame){
		this.Frame = Frame;
		initChess();//初始化棋盘
		this.addMouseListener(this);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	//---------棋盘状态函数(状态值1-20)---------
	public void initChess(){
		//一定要重新创建数组
		chess =  new Chess[Param.rows + 2][Param.cols + 2];
		Random random = new Random();
		for(int i = 1; i <= Param.chessNum; i++){
			int count = 0;
			while(count < 4){
				//随机选取一个没有设置过状态的位置，设置为当前的状态值
				int x = random.nextInt(Param.rows) + 1;//横坐标0-7 --> 1-8[9](0和9设置状态为0)
				int y = random.nextInt(Param.cols) + 1;//纵坐标0-9 --> 1-10[11](0和11设置状态为0)
				if(chess[x][y] == null){
					chess[x][y] = new Chess(i);
					count++;
				}
			}
		}
		//外围设置为0
		for(int i = 0; i < chess.length; i++){
			chess[i][0] = new Chess(0);
			chess[i][chess[0].length - 1] = new Chess(0);
		}
		for(int i = 0; i < chess[0].length; i++){
			
			chess[0][i] = new Chess(0);
			chess[chess.length - 1][i] = new Chess(0);
		}
	}
	
	//--------设置线条粗细---Graphics2D 工具类配合使用----------
	Stroke stroke = new BasicStroke(3.0f);
	
	
	//------重写paint方法-------
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(Param.imageBackground,0,0,this);//背景
		g.setColor(Color.green);//颜色
		Font font = new Font("楷书",Font.BOLD,28);//字体
		g.setFont(font);//得到所设置的字体
		//g.drawString("连连看小游戏", 250, 70);//绘制字符串(绿色，font设置的字体)
		//判断游戏的状态值 绘制不同信息
		
		if (Param.gameStatus == 0) {
			g.drawString("游戏还没有开始...", 200, 200);

		} else if (Param.gameStatus == 2) {
			g.drawString("游戏暂停中...", 200, 200);

		} else if (Param.gameStatus == 3) {
			g.drawString("当前游戏结束...", 200, 200);

		} else {
			//----将图片随机放入棋盘中-----
			for(int i = 1; i <= Param.rows; i++){
				for(int j = 1; j <= Param.cols; j++){
					if(chess[i][j].getStatus() != 0){
						g.drawImage(Param.chessImage[chess[i][j].getStatus() - 1],
							Param.marginWidth + Param.chessWidth*j,
							Param.marginHeight + Param.chessHeight*i,this);
						g.drawRect(Param.marginWidth + Param.chessWidth*j,
							Param.marginHeight + Param.chessHeight*i,
							Param.chessWidth,Param.chessHeight);
					}
				}
			}
		}
	
		//判断第一个点是否为空
		if(firstPoint != null){
			drawMyRect(firstPoint,Color.blue);
		}
	}
	
	//重写鼠标点击方法，实现鼠标点击出现小方框
		public void mousePressed(MouseEvent e) {
			//游戏状态值  1进行中
			if(Param.gameStatus!=1){
				return;
			}
			
			//不是左键的判断
			if(e.getModifiers() != InputEvent.BUTTON1_MASK){
				return;
			}
			
			//----是左键----获取点击处的坐标--------
			int x = e.getX();
			int y = e.getY();
			
			//-----将坐标化为数组下标---->再化为左顶点处的坐标---->画出矩形
			//判断当前坐标是否在棋盘内
			if(x < Param.marginWidth + Param.chessWidth || x >= Param.marginWidth + Param.chessWidth * (Param.cols + 1)
				|| y < Param.marginHeight + Param.chessHeight || y >= Param.marginHeight + Param.chessHeight*(Param.rows + 1)){
				return;
			}
				int X = (y - Param.marginHeight)/Param.chessHeight;
				int Y = (x - Param.marginWidth)/Param.chessWidth;
				
				// 判断当前的位置是否已经消掉 图标状态值为0
				if (chess[X][Y].getStatus() == 0) {
					return;
				}
				int rowx = X*Param.chessWidth + Param.marginWidth;
				int rowy = Y*Param.chessHeight + Param.marginHeight;
	
				//第一次点击firstPoint||重复点击firstPoint
				if(firstPoint == null || (firstPoint.x == X && firstPoint.y == Y)){
					firstPoint = new Point(X,Y);
					drawMyRect(firstPoint,Color.blue);
					return;
					}
				
				//第二点
				secondPoint = new Point(X,Y);
				drawMyRect(secondPoint,Color.red);
				
				//状态相同：连通算法
				List<Point> list = Core.checkLinked(chess,firstPoint,secondPoint);
	
				if(list == null){
					firstPoint = secondPoint;
					Frame.repaint();
					return;
				}
				
				//可以连通：状态值设为0；将两点放空，绘制连接线，重画界面
				chess[firstPoint.x][firstPoint.y].setStatus(0);
				chess[secondPoint.x][secondPoint.y].setStatus(0);
				firstPoint = null;
				secondPoint = null;
				drawLinkedLine(list);
				Frame.repaint();
				Param.score += 10;
				Frame.setScore(Param.score);
		}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	//------绘制连通线--------------------------
	private void drawLinkedLine(List<Point> list) {
			Graphics2D G = (Graphics2D)this.getGraphics();
			G.setColor(Color.blue);
			G.setStroke(stroke);
			if(list.size() == 2){
				Point a = list.get(0);
				Point b = list.get(1);
				int ax = Param.marginWidth + a.y*Param.chessWidth + Param.chessWidth/2;
				int ay = a.x*Param.chessHeight+Param.marginHeight+Param.chessHeight/2;
				int bx = b.y*Param.chessWidth+Param.marginWidth+Param.chessWidth/2;
				int by = b.x*Param.chessHeight+Param.marginHeight+Param.chessHeight/2;
				G.drawLine(ax,ay,bx,by);
			}
			
			if(list.size() == 3){
				Point a = list.get(0);
				Point c = list.get(1);
				Point b = list.get(2);
				int ax = a.y*Param.chessWidth+Param.marginWidth+Param.chessWidth/2;
				int ay = a.x*Param.chessHeight+Param.marginHeight+Param.chessHeight/2;
				int cx = c.y*Param.chessWidth+Param.marginWidth+Param.chessWidth/2;
				int cy = c.x*Param.chessHeight+Param.marginHeight+Param.chessHeight/2;
				int bx = b.y*Param.chessWidth+Param.marginWidth+Param.chessWidth/2;
				int by = b.x*Param.chessHeight+Param.marginHeight+Param.chessHeight/2;
				G.drawLine(ax, ay, cx, cy);
				G.drawLine(cx, cy, bx, by);
			}
			
			if(list.size() == 4){
				Point a = list.get(0);
				Point c = list.get(1);
				Point d = list.get(2);
				Point b = list.get(3);
				int ax = a.y*Param.chessWidth+Param.marginWidth+Param.chessWidth/2;
				int ay = a.x*Param.chessHeight+Param.marginHeight+Param.chessHeight/2;
				int cx = c.y*Param.chessWidth+Param.marginWidth+Param.chessWidth/2;
				int cy = c.x*Param.chessHeight+Param.marginHeight+Param.chessHeight/2;
				int dx = d.y*Param.chessWidth+Param.marginWidth+Param.chessWidth/2;
				int dy = d.x*Param.chessHeight+Param.marginHeight+Param.chessHeight/2;		
				int bx = b.y*Param.chessWidth+Param.marginWidth+Param.chessWidth/2;
				int by = b.x*Param.chessHeight+Param.marginHeight+Param.chessHeight/2;
				G.drawLine(ax, ay, cx, cy);
				G.drawLine(cx, cy, dx, dy);
				G.drawLine(dx, dy, bx, by);
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void drawMyRect(Point p,Color c){
		Graphics g = getGraphics();
		Graphics2D G = (Graphics2D) g;
		G.setStroke(stroke);
		G.setColor(c);
		int rowx = p.y*Param.chessWidth + Param.marginWidth;
		int rowy = p.x*Param.chessHeight + Param.marginHeight;
		G.drawRect(rowx + 2, rowy + 2, Param.chessWidth - 4, Param.chessHeight - 4);
	}

}

