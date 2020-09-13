package linkup;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import linkup.tools.Param;
/**
 * 具备计时器功能的进度条
 * @author lenovo
 *
 */
public class TimerJProgressbar extends JProgressBar implements ActionListener{

	int count = Param.timeCount;//时间
	Timer timer = new Timer(1000, this);//隔1000毫秒执行this操作
	mainFrame Frame;
	
	public TimerJProgressbar(mainFrame Frame) {
		this.Frame = Frame;
		this.setMaximum(count);//进度条的最大值
		this.setValue(count);
		this.setString(count+"秒");//进度条现实的字符串
		this.setStringPainted(true);//可视
		this.setForeground(Color.green);//颜色	
	}
	
	public void actionPerformed(ActionEvent e) {
		//每次间隔会执行的方法
		count--;
		this.setValue(count);
		this.setString(count+"秒");
		//count<=0   游戏结束:停止计时器，界面屏蔽；游戏状态值3；记录成绩
		if(count <= 0){
			stop();
			Param.gameStatus = 3;
			JOptionPane.showMessageDialog(this,"游戏结束!");
		}
	}
	
	/**
	 * 启动定时器
	 */
	public void start(){
		timer.start();
	}
	
	/**
	 * 停止定时器
	 */
	public void stop(){
		timer.stop();
	}
	
	/**
	 * 重置定时器
	 */
	public void reset(){
		timer.stop();
		count = Param.timeCount;
		this.setMaximum(count);
		this.setValue(count);
		this.setString(count+"秒");
	}

}
