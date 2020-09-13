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
 * 菜单设置
 * @author lenovo
 *
 */
public class LinkupMenuBar extends JMenuBar{
	//设置：游戏参数，设置背景，设置棋子，查看成绩，退出游戏
	//帮助：游戏规则，游戏版权
	
	JMenuItem menuItemParam = new JMenuItem("游戏参数");
	JMenuItem menuItemBackground = new JMenu("游戏背景");
	JMenuItem menuItemChessIcon = new JMenu("设置棋子");
	JMenuItem menuItemScore = new JMenuItem("查看成绩");
	JMenuItem menuItemExit = new JMenuItem("退出游戏");
	JMenuItem menuItemRule = new JMenuItem("游戏规则");
	JMenuItem menuItemAbout = new JMenuItem("游戏版权");
	
	mainFrame frame;
	public LinkupMenuBar(mainFrame frame){
		this.frame = frame;
		this.init();
		
	}
	private void init(){
		JMenu menuSet = new JMenu("设置");
		
		//添加到设置中的菜单条
		menuSet.add(menuItemParam);
		menuSet.add(menuItemBackground);
		menuSet.add(menuItemChessIcon);
		menuSet.add(menuItemScore);
		menuSet.add(menuItemExit);
		
		
		//添加背景图片二级菜单
		String[] arr = new String[]{"背景1","背景2","背景3","背景4","背景5","背景6","背景7","背景8"};
		ButtonGroup buttonGroup =  new ButtonGroup();//互斥按钮
		for(int i = 0; i < arr.length; i++){
			final JCheckBoxMenuItem jCheckBoxMenuItem =  new JCheckBoxMenuItem(arr[i]);//二级菜单
			menuItemBackground.add(jCheckBoxMenuItem);
			buttonGroup.add(jCheckBoxMenuItem);
			//监听
			jCheckBoxMenuItem.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					String text = jCheckBoxMenuItem.getText();
					if(text.equals("背景1")){
						Param.imageBackground = Param.imageBackground1;
					}else if(text.equals("背景2")){
						Param.imageBackground = Param.imageBackground2;
					}else if(text.equals("背景3")){
						Param.imageBackground = Param.imageBackground3;
					}else if(text.equals("背景4")){
						Param.imageBackground = Param.imageBackground4;
					}else if(text.equals("背景5")){
						Param.imageBackground = Param.imageBackground5;
					}else if(text.equals("背景6")){
						Param.imageBackground = Param.imageBackground6;
					}else if(text.equals("背景7")){
						Param.imageBackground = Param.imageBackground7;
					}else if(text.equals("背景8")){
						Param.imageBackground = Param.imageBackground8;
					}
					frame.repaint();
				}
			});
		}
		
		JMenu menuHelp = new JMenu("帮助");
		menuHelp.add(menuItemRule);
		menuHelp.add(menuItemAbout);
		this.add(menuSet);
	    this.add(menuHelp);	
	  		
		// 退出菜单栏监听
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

		// 游戏参数
		menuItemParam.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//创建对话框
				new ParamJDialog(frame);
			}
		});
		// 游戏规则
		menuItemRule.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		// 游戏版权
		menuItemAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}
}
