package linkup;

import java.awt.Cursor;

import javax.swing.JLabel;

/**
 * �����Լ�����ʽЧ��
 * @author lenovo
 *
 */
public class LinkupJLabel extends JLabel{
	mainFrame Frame;
	
	public LinkupJLabel(String text){
		super(text);
		//�����ʽ:����
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}


}
