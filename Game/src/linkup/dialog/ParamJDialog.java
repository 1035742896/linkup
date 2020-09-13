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
 * �Ի������ã�������ǩ�ı�������ã�
 * @author lenovo
 *
 */
public class ParamJDialog extends JDialog{

	//�����ı�����Ϸʱ�����ã�ˢ�´������ã���ʾʱ������
	private JTextField textFieldTime = new JTextField(Param.timeCount + "");
	private JTextField textFieldRefleshCount = new JTextField(
			Param.refreshCountConstant + "");
	private JTextField textFieldReMarkCount = new JTextField(
			Param.remarkCountConstant + "");
	
	//���尴ť
	private JButton buttonSure = new JButton("ȷ��");
	private JButton buttonCancel = new JButton("ȡ��");
	
	mainFrame frame;
	public ParamJDialog(mainFrame frame){
		super(frame);
		this.frame = frame;
		init();
		
		//���ô���
		setTitle("��Ϸ��������");
		this.setSize(new Dimension(300, 250));
		this.setLocationRelativeTo(frame);//�ڴ�������

		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setModal(true);
		this.setVisible(true);
	}
	private void init(){
		this.setLayout(null);//���ÿղ���
		//���ñ�ǩ
		JLabel labelTime = new JLabel("��Ϸʱ��:");
		JLabel labelReflesh = new JLabel("ˢ�´���:");
		JLabel labelRemark = new JLabel("��ʾ����:");
		JLabel labelTimeMessage = new JLabel("��(����)");
		JLabel labelRefleshMessage = new JLabel("*����");
		JLabel labelRemarkMessage = new JLabel("*����");
		
		//����ǩ�������----��ɫ
		labelTimeMessage.setForeground(Color.red);
		labelRefleshMessage.setForeground(Color.red);
		labelRemarkMessage.setForeground(Color.red);
		
		//λ�ô�С���趨
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
		
		//���ı��򡢱�ǩ��ӵ��Ի�����
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
	 * ����ť��Ӽ���
	 * @param args
	 */
	private void initEvent() {
		buttonSure.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ���ı����л��ʱ��
				String timeText = textFieldTime.getText();
				int time = 0;
				try {
					time = Integer.parseInt(timeText);
					if (!(10 <= time && time <= 600)) {
						JOptionPane.showMessageDialog(frame,"ʱ�䷶Χ���Ϸ�,��Χ[10~600]!");
						return;
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "ʱ�����Ϊ����,��Χ[10~600]!");
					return;
				}

				//���ı����л��ϴ�ƴ���
				String refreshText = textFieldRefleshCount.getText();
				int refreshCount = 0;
				try {
					refreshCount = Integer.parseInt(refreshText);
					if (!(3 <= refreshCount && refreshCount <= 20)) {
						JOptionPane.showMessageDialog(frame,
								"ϴ�ƴ�����Χ���Ϸ�,��Χ[3~20]!");
						return;
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "ϴ�ƴ�������Ϊ����,��Χ[3~20]!");
					return;
				}

				// ���ı����л����ʾ����
				String reMarkText = textFieldReMarkCount.getText();
				int reMarkCount = 0;
				try {
					reMarkCount = Integer.parseInt(reMarkText);// 8984398
					if (!(3 <= reMarkCount && reMarkCount <= 20)) {
						JOptionPane.showMessageDialog(frame,
								"��ʾ������Χ���Ϸ�,��Χ[3~20]!");
						return;
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(frame, "��ʾ��������Ϊ����,��Χ[3~20]!");
					return;
				}
				// ���ٶԻ���
				dispose();
				// �Ϸ�
				Param.timeCount = time;
				Param.refreshCountConstant = refreshCount;
				Param.remarkCountConstant = reMarkCount;
				frame.restartGame();
			}
		});

		// ���ٶԻ���
		buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();//���ٶԻ���
			}
		});

	}

}
