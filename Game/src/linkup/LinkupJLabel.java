package linkup;

import java.awt.Cursor;

import javax.swing.JLabel;

/**
 * 定制自己的样式效果
 * @author lenovo
 *
 */
public class LinkupJLabel extends JLabel{
	mainFrame Frame;
	
	public LinkupJLabel(String text){
		super(text);
		//光标样式:手型
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}


}
