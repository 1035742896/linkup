package linkup.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import linkup.mainFrame;
import linkup.tools.Param;

/**
 * 对话框设置（包含标签文本框的设置）
 * @author lenovo
 *
 */
public class ParamJDialog extends JDialog{

	//定义文本框：游戏时间设置；刷新次数设置；提示时间设置
	private JTextField textFieldTime = new JTextField(Param.timeCount + "");
	private JTextField textFieldRefleshCount = new JTextField(
			Param.refreshCountConstant + "");
	private JTextField textFieldReMarkCount = new JTextField(
			Param.remarkCountConstant + "");
	
	//定义按钮
	private JButton buttonSure = new JButton("确定");
	private JButton buttonCancel = new JButton("取消");
	
	mainFrame frame;
	public ParamJDialog(mainFrame frame){
		super(frame);
		this.frame = frame;
		init();
		
		//设置窗口
		setTitle("游戏参数设置");
		this.setSize(new Dimension(300, 250));
		this.setLocationRelativeTo(frame);//在窗体中央

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setModal(true);
		this.setVisible(true);
	}
	private void init(){
		this.setLayout(null);//设置空布局
		//设置标签
		JLabel labelTime = new JLabel("游戏时间:");
		JLabel labelReflesh = new JLabel("刷新次数:");
		JLabel labelRemark = new JLabel("提示次数:");
		JLabel labelTimeMessage = new JLabel("秒(整数)");
		JLabel labelRefleshMessage = new JLabel("*整数");
		JLabel labelRemarkMessage = new JLabel("*整数");
		
		//给标签添加属性----颜色
		labelTimeMessage.setForeground(Color.red);
		labelRefleshMessage.setForeground(Color.red);
		labelRemarkMessage.setForeground(Color.red);
		
		//位置大小的设定
		labelTime.setBounds(60, 20, 60, 25);
		textFieldTime.setBounds(121, 20, 60, 25);
		labelTimeMessage.setBounds(182, 20, 60, 25);

		labelReflesh.setBounds(60, 60, 60, 25);
		textFieldRefleshCount.setBounds(121, 60, 60, 25);
		labelRefleshMessage.setBounds(182, 60, 60, 25);

		labelRemark.setBounds(60, 100, 60, 25);
		textFieldReMarkCount.setBounds(121, 100, 60, 25);
		labelRemarkMessage.setBounds(182, 100, 60, 25);

		buttonSure.setBounds(60, 140, 60, 25);
		buttonCancel.setBounds(150, 140, 60, 25);
		
		//将文本框、标签添加到对话框中
		this.add(labelTime);
		this.add(textFieldTime);
		this.add(labelTimeMessage);

		this.add(labelReflesh);
		this.add(textFieldRefleshCount);
		this.add(labelRefleshMessage);

		this.add(labelRemark);
		this.add(textFieldReMarkCount);
		this.add(labelRemarkMessage);

		this.add(buttonSure);
		this.add(buttonCancel);
		initEvent();
	}
	
	/**
	 * 给按钮添加监听
	 * @param args
	 */
	private void initEvent() {
		buttonSure.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 从文本框中获得时间
				String timeText = textFieldTime.getText();
				int time = 0;
				try {
					time = Integer.parseInt(timeText);
					if (!(10 <= time && time <= 600)) {
						JOptionPane.showMessageDialog(frame,"时间范围不合法,范围[10~600]!");
						return;
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "时间必须为数字,范围[10~600]!");
					return;
				}

				//从文本框中获得洗牌次数
				String refreshText = textFieldRefleshCount.getText();
				int refreshCount = 0;
				try {
					refreshCount = Integer.parseInt(refreshText);
					if (!(3 <= refreshCount && refreshCount <= 20)) {
						JOptionPane.showMessageDialog(frame,
								"洗牌次数范围不合法,范围[3~20]!");
						return;
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "洗牌次数必须为数字,范围[3~20]!");
					return;
				}

				// 从文本框中获得提示次数
				String reMarkText = textFieldReMarkCount.getText();
				int reMarkCount = 0;
				try {
					reMarkCount = Integer.parseInt(reMarkText);// 8984398
					if (!(3 <= reMarkCount && reMarkCount <= 20)) {
						JOptionPane.showMessageDialog(frame,
								"提示次数范围不合法,范围[3~20]!");
						return;
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "提示次数必须为数字,范围[3~20]!");
					return;
				}
				// 销毁对话框
				dispose();
				// 合法
				Param.timeCount = time;
				Param.refreshCountConstant = refreshCount;
				Param.remarkCountConstant = reMarkCount;
				frame.restartGame();
			}
		});

		// 销毁对话框
		buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();//销毁对话框
			}
		});

	}

}
