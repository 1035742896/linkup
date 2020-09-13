package linkup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import linkup.dialog.ParamJDialog;
import linkup.tools.Param;

/**
 * �˵�����
 * @author lenovo
 *
 */
public class LinkupMenuBar extends JMenuBar{
	//���ã���Ϸ���������ñ������������ӣ��鿴�ɼ����˳���Ϸ
	//��������Ϸ������Ϸ��Ȩ
	
	JMenuItem menuItemParam = new JMenuItem("��Ϸ����");
	JMenuItem menuItemBackground = new JMenu("��Ϸ����");
	JMenuItem menuItemChessIcon = new JMenu("��������");
	JMenuItem menuItemScore = new JMenuItem("�鿴�ɼ�");
	JMenuItem menuItemExit = new JMenuItem("�˳���Ϸ");
	JMenuItem menuItemRule = new JMenuItem("��Ϸ����");
	JMenuItem menuItemAbout = new JMenuItem("��Ϸ��Ȩ");
	
	mainFrame frame;
	public LinkupMenuBar(mainFrame frame){
		this.frame = frame;
		this.init();
		
	}
	private void init(){
		JMenu menuSet = new JMenu("����");
		
		//��ӵ������еĲ˵���
		menuSet.add(menuItemParam);
		menuSet.add(menuItemBackground);
		menuSet.add(menuItemChessIcon);
		menuSet.add(menuItemScore);
		menuSet.add(menuItemExit);
		
		
		//��ӱ���ͼƬ�����˵�
		String[] arr = new String[]{"����1","����2","����3","����4","����5","����6","����7","����8"};
		ButtonGroup buttonGroup =  new ButtonGroup();//���ⰴť
		for(int i = 0; i < arr.length; i++){
			final JCheckBoxMenuItem jCheckBoxMenuItem =  new JCheckBoxMenuItem(arr[i]);//�����˵�
			menuItemBackground.add(jCheckBoxMenuItem);
			buttonGroup.add(jCheckBoxMenuItem);
			//����
			jCheckBoxMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					String text = jCheckBoxMenuItem.getText();
					if(text.equals("����1")){
						Param.imageBackground = Param.imageBackground1;
					}else if(text.equals("����2")){
						Param.imageBackground = Param.imageBackground2;
					}else if(text.equals("����3")){
						Param.imageBackground = Param.imageBackground3;
					}else if(text.equals("����4")){
						Param.imageBackground = Param.imageBackground4;
					}else if(text.equals("����5")){
						Param.imageBackground = Param.imageBackground5;
					}else if(text.equals("����6")){
						Param.imageBackground = Param.imageBackground6;
					}else if(text.equals("����7")){
						Param.imageBackground = Param.imageBackground7;
					}else if(text.equals("����8")){
						Param.imageBackground = Param.imageBackground8;
					}
					frame.repaint();
				}
			});
		}
		
		JMenu menuHelp = new JMenu("����");
		menuHelp.add(menuItemRule);
		menuHelp.add(menuItemAbout);
		this.add(menuSet);
	    this.add(menuHelp);	
	  		
		// �˳��˵�������
		menuItemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		menuItemScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		// ��Ϸ����
		menuItemParam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//�����Ի���
				new ParamJDialog(frame);
			}
		});
		// ��Ϸ����
		menuItemRule.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		// ��Ϸ��Ȩ
		menuItemAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}
}
