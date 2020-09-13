package linkup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import linkup.tools.Core;
import linkup.tools.Param;

public class mainFrame extends JFrame {
	// ��� ��ӵ�������
	MapPanel mapPanel = new MapPanel(this);

	//��ͣ�ݼ�
	int refreshCount = Param.refreshCountConstant;
	int remarkCount = Param.remarkCountConstant;

	JLabel labelStart = new LinkupJLabel("��ʼ");
	JLabel labelRemark = new LinkupJLabel("��ʾ("+remarkCount+")");
	JLabel labelRefresh = new LinkupJLabel("ϴ��("+refreshCount+")");
	JLabel labelScore = new JLabel("��ǰ�ɼ�:");
	JLabel labelReStart = new LinkupJLabel("���¿�ʼ");
	
	//���������
	TimerJProgressbar timerJProgressbar = new TimerJProgressbar(this);

	//����˵���
	LinkupMenuBar menuBar = new LinkupMenuBar(this);
	
	// ------����������--------
	public mainFrame() {
		init();
		//Ϊ�������ò˵���
		this.setJMenuBar(menuBar);

		this.setTitle("������ ^_^");// ���ڱ���
		this.setSize(700, 600);// �ߴ�
		this.setLocationRelativeTo(null);// ����
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);// �رմ���
		this.setResizable(false);// �̶����ڴ�С
		this.setVisible(true);// ���ӻ�
		this.setIconImage(Param.leftPicture);// ���Ͻ�ͼ��
	}
	private void init() {
		this.setLayout(null);
		
		labelStart.setBounds(20, 20, 80, 25);
		labelReStart.setBounds(20, 55, 80, 25);
		labelRemark.setBounds(110, 20, 80, 25);
		labelRefresh.setBounds(200, 20, 80, 25);
		labelScore.setBounds(300, 20, 150, 25);
		timerJProgressbar.setBounds(460, 20, 200, 23);
		
		this.add(labelStart);
		this.add(labelReStart);
		this.add(labelRemark);
		this.add(labelRefresh);
		this.add(labelScore);
		this.add(timerJProgressbar);

		mapPanel.setBounds(0, 0, 700, 550);
		this.add(mapPanel);
		
		//�ɼ���ǩ
		labelScore.setForeground(Color.RED);

		// ������ ��ʵ�ֹ��ӿ��еķ�������ѡ������д����
		// ������
		labelStart.addMouseListener(new MouseAdapter() {

			//��������������� ���������ñ���뿪ʱ��ԭ
			public void mouseEntered(MouseEvent e) {
				labelStart.setFont(new Font("����", Font.BOLD, 16));
			    }
			public void mouseExited(MouseEvent e) {
				labelStart.setFont(new Font("����", Font.BOLD, 12));
				}
			public void mousePressed(MouseEvent e) {
				
				String text = labelStart.getText();
				if ("��ʼ".equals(text)) {
					Param.gameStatus = 1;
					labelStart.setText("��ͣ");
					// ������ʱ��
					timerJProgressbar.start();
					repaint();
				} else if ("��ͣ".equals(text)) {
					Param.gameStatus = 2;
					labelStart.setText("����");
					// ֹͣ��ʱ��
					timerJProgressbar.stop();
					repaint();
				} else if ("����".equals(text)) {
					Param.gameStatus = 1;
					labelStart.setText("��ͣ");
					// ������ʱ��
					timerJProgressbar.start();
					repaint();
				}
			}
		});

		// ϴ��
		labelRefresh.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// ��Ϸ�Ƿ�ʼ��
				if (Param.gameStatus != 1) {
					// ����һ����Ϣ��
					JOptionPane.showMessageDialog(mainFrame.this,
							"��Ϸ��û��ʼ�����ȿ�ʼ��Ϸ!");
					return;
				}

				// �ж����
				if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
					return;
				}

				// ����ϴ�ƴ��� ������ϴ��
				if(refreshCount > 0){
					refreshCount--;
					labelRefresh.setText("ϴ��(" + refreshCount + ")");
					Core.refreshArr(mapPanel.chess);
					// �������»���
					repaint();
				}
			}
		});

		// ��ʾ��������
		labelRemark.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// ��Ϸ�Ƿ�ʼ��
				if (Param.gameStatus != 1) {
					// ����һ����Ϣ��
					JOptionPane.showMessageDialog(mainFrame.this,
							"��Ϸ��û��ʼ�����ȿ�ʼ��Ϸ!");
					return;
				}

				// �ж����
				if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
					return;
				}

				// �ҵ�������ͨ��������,���ƾ���(��˸)
				List<Point> list = Core.remarkArr(mapPanel.chess);
				if (list != null) {
					//��ʾ��������
					if(remarkCount > 0){
						remarkCount--;
						labelRemark.setText("��ʾ(" + remarkCount + ")");
						remarkDeal(list);
					}
				}

			}
		});
		
		//���¿�ʼ
		labelReStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
					return;
				}
				restartGame();		
			}
		});

	}

	public static void main(String[] args) {
		// ���ý����Ƥ���� ��ǰϵͳ���
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO: handle exception
		}

		new mainFrame();

		// ��Ŀ����1��
		// ʵ�������е����� ͨ��ͼ�ν�����Ƴ��������������С�
		// ע�������еĶ�Ӧ�������±� i,j �� ����x,y�Ķ�Ӧ��ϵ ���Լ�˼�����ܽ����

		// ��Ŀ����2��ʵ�������ͼ�� ����ѡ�еĴ��������α߿�

	}

	public void remarkDeal(final List<Point> list) {
		//�����߳�ʵ��   ��˸ ��ʾ
		new Thread() {

			public void run() {
				// ��˸����
				int count = 0;
				while (count < 3) {
					mapPanel.drawMyRect(list.get(0), Color.GREEN);
					mapPanel.drawMyRect(list.get(1), Color.GREEN);
					count++;
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					repaint();
					try {
						Thread.sleep(300);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		}.start();

	}
	
	/**
	 * ���ô����ϵĳɼ���ǩ
	 * @param score
	 */
	public void setScore(int score){
		labelScore.setText("��ǰ�ɼ�:"+score);		
	}
	
	/**
	 * ���¿�ʼ��Ϸ����
	 */
	public void restartGame(){
		//��Ϸ״ֵ̬ 1
		Param.gameStatus = 1;
		//��������ʱ�� ����
		timerJProgressbar.reset();
		//ϴ�ƴ�������ʾ���� ��ԭ
		refreshCount = Param.refreshCountConstant;
		remarkCount = Param.remarkCountConstant;
		labelRefresh.setText("ϴ��("+Param.refreshCountConstant+")");
		labelRemark.setText("��ʾ("+Param.remarkCountConstant+")");
		//��Ϸ�ɼ�0
		Param.score = 0;
		setScore(0);
		//������������ ����
		mapPanel.initChess();
		//���� ��ͣ
		labelStart.setText("��ͣ");
		// ������ʱ��?
		timerJProgressbar.start();
		//�������»���
		repaint();
	}
}
