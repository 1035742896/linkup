package linkup;
/**
 * ��壬�Ȱ�����ӵ���壬�ټӵ�������
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

	//���������� ��������±�
	Point firstPoint;
	Point secondPoint;
	
	mainFrame Frame;
	
	Chess[][] chess = null;
	//---���캯���г�ʼ�����鲢ע����������Ѵ�����Ϊ�������ݵ������----
	public MapPanel(mainFrame Frame){
		this.Frame = Frame;
		initChess();//��ʼ������
		this.addMouseListener(this);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	//---------����״̬����(״ֵ̬1-20)---------
	public void initChess(){
		//һ��Ҫ���´�������
		chess =  new Chess[Param.rows + 2][Param.cols + 2];
		Random random = new Random();
		for(int i = 1; i <= Param.chessNum; i++){
			int count = 0;
			while(count < 4){
				//���ѡȡһ��û�����ù�״̬��λ�ã�����Ϊ��ǰ��״ֵ̬
				int x = random.nextInt(Param.rows) + 1;//������0-7 --> 1-8[9](0��9����״̬Ϊ0)
				int y = random.nextInt(Param.cols) + 1;//������0-9 --> 1-10[11](0��11����״̬Ϊ0)
				if(chess[x][y] == null){
					chess[x][y] = new Chess(i);
					count++;
				}
			}
		}
		//��Χ����Ϊ0
		for(int i = 0; i < chess.length; i++){
			chess[i][0] = new Chess(0);
			chess[i][chess[0].length - 1] = new Chess(0);
		}
		for(int i = 0; i < chess[0].length; i++){
			
			chess[0][i] = new Chess(0);
			chess[chess.length - 1][i] = new Chess(0);
		}
	}
	
	//--------����������ϸ---Graphics2D ���������ʹ��----------
	Stroke stroke = new BasicStroke(3.0f);
	
	
	//------��дpaint����-------
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(Param.imageBackground,0,0,this);//����
		g.setColor(Color.green);//��ɫ
		Font font = new Font("����",Font.BOLD,28);//����
		g.setFont(font);//�õ������õ�����
		//g.drawString("������С��Ϸ", 250, 70);//�����ַ���(��ɫ��font���õ�����)
		//�ж���Ϸ��״ֵ̬ ���Ʋ�ͬ��Ϣ
		
		if (Param.gameStatus == 0) {
			g.drawString("��Ϸ��û�п�ʼ...", 200, 200);

		} else if (Param.gameStatus == 2) {
			g.drawString("��Ϸ��ͣ��...", 200, 200);

		} else if (Param.gameStatus == 3) {
			g.drawString("��ǰ��Ϸ����...", 200, 200);

		} else {
			//----��ͼƬ�������������-----
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
	
		//�жϵ�һ�����Ƿ�Ϊ��
		if(firstPoint != null){
			drawMyRect(firstPoint,Color.blue);
		}
	}
	
	//��д�����������ʵ�����������С����
		public void mousePressed(MouseEvent e) {
			//��Ϸ״ֵ̬  1������
			if(Param.gameStatus!=1){
				return;
			}
			
			//����������ж�
			if(e.getModifiers() != InputEvent.BUTTON1_MASK){
				return;
			}
			
			//----�����----��ȡ�����������--------
			int x = e.getX();
			int y = e.getY();
			
			//-----�����껯Ϊ�����±�---->�ٻ�Ϊ�󶥵㴦������---->��������
			//�жϵ�ǰ�����Ƿ���������
			if(x < Param.marginWidth + Param.chessWidth || x >= Param.marginWidth + Param.chessWidth * (Param.cols + 1)
				|| y < Param.marginHeight + Param.chessHeight || y >= Param.marginHeight + Param.chessHeight*(Param.rows + 1)){
				return;
			}
				int X = (y - Param.marginHeight)/Param.chessHeight;
				int Y = (x - Param.marginWidth)/Param.chessWidth;
				
				// �жϵ�ǰ��λ���Ƿ��Ѿ����� ͼ��״ֵ̬Ϊ0
				if (chess[X][Y].getStatus() == 0) {
					return;
				}
				int rowx = X*Param.chessWidth + Param.marginWidth;
				int rowy = Y*Param.chessHeight + Param.marginHeight;
	
				//��һ�ε��firstPoint||�ظ����firstPoint
				if(firstPoint == null || (firstPoint.x == X && firstPoint.y == Y)){
					firstPoint = new Point(X,Y);
					drawMyRect(firstPoint,Color.blue);
					return;
					}
				
				//�ڶ���
				secondPoint = new Point(X,Y);
				drawMyRect(secondPoint,Color.red);
				
				//״̬��ͬ����ͨ�㷨
				List<Point> list = Core.checkLinked(chess,firstPoint,secondPoint);
	
				if(list == null){
					firstPoint = secondPoint;
					Frame.repaint();
					return;
				}
				
				//������ͨ��״ֵ̬��Ϊ0��������ſգ����������ߣ��ػ�����
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
	//------������ͨ��--------------------------
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

