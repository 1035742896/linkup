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
	// 面板 添加到窗体中
	MapPanel mapPanel = new MapPanel(this);

	//不停递减
	int refreshCount = Param.refreshCountConstant;
	int remarkCount = Param.remarkCountConstant;

	JLabel labelStart = new LinkupJLabel("开始");
	JLabel labelRemark = new LinkupJLabel("提示("+remarkCount+")");
	JLabel labelRefresh = new LinkupJLabel("洗牌("+refreshCount+")");
	JLabel labelScore = new JLabel("当前成绩:");
	JLabel labelReStart = new LinkupJLabel("重新开始");
	
	//定义进度条
	TimerJProgressbar timerJProgressbar = new TimerJProgressbar(this);

	//定义菜单条
	LinkupMenuBar menuBar = new LinkupMenuBar(this);
	
	// ------设置主窗体--------
	public mainFrame() {
		init();
		//为窗体设置菜单条
		this.setJMenuBar(menuBar);

		this.setTitle("连连看 ^_^");// 窗口标题
		this.setSize(700, 600);// 尺寸
		this.setLocationRelativeTo(null);// 居中
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);// 关闭窗口
		this.setResizable(false);// 固定窗口大小
		this.setVisible(true);// 可视化
		this.setIconImage(Param.leftPicture);// 左上角图标
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
		
		//成绩标签
		labelScore.setForeground(Color.RED);

		// 适配器 简单实现过接口中的方法，可选择性重写方法
		// 抽象类
		labelStart.addMouseListener(new MouseAdapter() {

			//鼠标监听，鼠标进入后 把字体设置变大，离开时还原
			public void mouseEntered(MouseEvent e) {
				labelStart.setFont(new Font("宋体", Font.BOLD, 16));
			    }
			public void mouseExited(MouseEvent e) {
				labelStart.setFont(new Font("宋体", Font.BOLD, 12));
				}
			public void mousePressed(MouseEvent e) {
				
				String text = labelStart.getText();
				if ("开始".equals(text)) {
					Param.gameStatus = 1;
					labelStart.setText("暂停");
					// 启动计时器
					timerJProgressbar.start();
					repaint();
				} else if ("暂停".equals(text)) {
					Param.gameStatus = 2;
					labelStart.setText("继续");
					// 停止计时器
					timerJProgressbar.stop();
					repaint();
				} else if ("继续".equals(text)) {
					Param.gameStatus = 1;
					labelStart.setText("暂停");
					// 启动计时器
					timerJProgressbar.start();
					repaint();
				}
			}
		});

		// 洗牌
		labelRefresh.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// 游戏是否开始？
				if (Param.gameStatus != 1) {
					// 弹出一个消息框
					JOptionPane.showMessageDialog(mainFrame.this,
							"游戏还没开始，请先开始游戏!");
					return;
				}

				// 判断左键
				if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
					return;
				}

				// 按照洗牌次数 来控制洗牌
				if(refreshCount > 0){
					refreshCount--;
					labelRefresh.setText("洗牌(" + refreshCount + ")");
					Core.refreshArr(mapPanel.chess);
					// 界面重新绘制
					repaint();
				}
			}
		});

		// 提示（监听）
		labelRemark.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 游戏是否开始？
				if (Param.gameStatus != 1) {
					// 弹出一个消息框
					JOptionPane.showMessageDialog(mainFrame.this,
							"游戏还没开始，请先开始游戏!");
					return;
				}

				// 判断左键
				if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
					return;
				}

				// 找到可以连通的两个点,绘制矩形(闪烁)
				List<Point> list = Core.remarkArr(mapPanel.chess);
				if (list != null) {
					//提示次数设置
					if(remarkCount > 0){
						remarkCount--;
						labelRemark.setText("提示(" + remarkCount + ")");
						remarkDeal(list);
					}
				}

			}
		});
		
		//重新开始
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
		// 设置界面的皮肤包 当前系统风格
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO: handle exception
		}

		new mainFrame();

		// 项目任务1：
		// 实现数组中的数据 通过图形界面绘制成连连看的棋盘中。
		// 注意行与列的对应：数组下标 i,j 与 坐标x,y的对应关系 ？自己思考并总结出来

		// 项目任务2：实现鼠标点击图标 绘制选中的粗线条矩形边框

	}

	public void remarkDeal(final List<Point> list) {
		//借助线程实现   闪烁 提示
		new Thread() {

			public void run() {
				// 闪烁三次
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
	 * 设置窗体上的成绩标签
	 * @param score
	 */
	public void setScore(int score){
		labelScore.setText("当前成绩:"+score);		
	}
	
	/**
	 * 重新开始游戏方法
	 */
	public void restartGame(){
		//游戏状态值 1
		Param.gameStatus = 1;
		//进度条定时器 重置
		timerJProgressbar.reset();
		//洗牌次数，提示次数 还原
		refreshCount = Param.refreshCountConstant;
		remarkCount = Param.remarkCountConstant;
		labelRefresh.setText("洗牌("+Param.refreshCountConstant+")");
		labelRemark.setText("提示("+Param.remarkCountConstant+")");
		//游戏成绩0
		Param.score = 0;
		setScore(0);
		//棋盘数组重新 部署
		mapPanel.initChess();
		//设置 暂停
		labelStart.setText("暂停");
		// 启动计时器?
		timerJProgressbar.start();
		//界面重新绘制
		repaint();
	}
}
