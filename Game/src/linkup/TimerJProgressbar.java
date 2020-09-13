package linkup;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import linkup.tools.Param;
/**
 * �߱���ʱ�����ܵĽ�����
 * @author lenovo
 *
 */
public class TimerJProgressbar extends JProgressBar implements ActionListener{

	int count = Param.timeCount;//ʱ��
	Timer timer = new Timer(1000, this);//��1000����ִ��this����
	mainFrame Frame;
	
	public TimerJProgressbar(mainFrame Frame) {
		this.Frame = Frame;
		this.setMaximum(count);//�����������ֵ
		this.setValue(count);
		this.setString(count+"��");//��������ʵ���ַ���
		this.setStringPainted(true);//����
		this.setForeground(Color.green);//��ɫ	
	}
	
	public void actionPerformed(ActionEvent e) {
		//ÿ�μ����ִ�еķ���
		count--;
		this.setValue(count);
		this.setString(count+"��");
		//count<=0   ��Ϸ����:ֹͣ��ʱ�����������Σ���Ϸ״ֵ̬3����¼�ɼ�
		if(count <= 0){
			stop();
			Param.gameStatus = 3;
			JOptionPane.showMessageDialog(this,"��Ϸ����!");
		}
	}
	
	/**
	 * ������ʱ��
	 */
	public void start(){
		timer.start();
	}
	
	/**
	 * ֹͣ��ʱ��
	 */
	public void stop(){
		timer.stop();
	}
	
	/**
	 * ���ö�ʱ��
	 */
	public void reset(){
		timer.stop();
		count = Param.timeCount;
		this.setMaximum(count);
		this.setValue(count);
		this.setString(count+"��");
	}

}
