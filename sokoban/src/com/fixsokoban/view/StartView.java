package com.fixsokoban.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.fixsokoban.config.Config;

/**
 * 开始界面
 * 
 * @author Shinelon
 *
 */
public class StartView extends JFrame implements ActionListener {

	private JLabel startBackgroundLabel = new JLabel();
	private JButton enterGameButton = new JButton();
	private ImageIcon backgroundImg = null;
	private ImageIcon enterGameButtonImg = null;

	public StartView() {

		// 加载图片
		backgroundImg = new ImageIcon("res/startBackground.jpg");
		enterGameButtonImg = new ImageIcon("res/enterGameButton.jpg");

		// 给标签设置图片
		startBackgroundLabel.setIcon(backgroundImg);

		// 设置label的大小
		startBackgroundLabel.setBounds(0, 0, backgroundImg.getIconWidth(),
				backgroundImg.getIconHeight());

		// 获取窗口的第二层，将label放入
		this.getLayeredPane().add(startBackgroundLabel,
				new Integer(Integer.MIN_VALUE));

		// 获取frame的顶层容器,并设置为透明
		JPanel j = (JPanel) this.getContentPane();
		j.setOpaque(false);

		JPanel panel = new JPanel();
		enterGameButton.setIcon(enterGameButtonImg);
		enterGameButton.setBounds(412, 248, 125, 38);

		panel.setLayout(null);
		panel.add(enterGameButton);

		// 必须设置为透明的。否则看不到图片
		panel.setOpaque(false);

		// 给进入游戏的按钮添加监听器
		enterGameButton.addActionListener(this);

		this.add(panel);
		this.setVisible(true);
		this.setTitle("推箱子游戏");

		// 设置窗口出现的位置
		this.setLocation(Config.START_WINDOW_X, Config.START_WINDOW_Y);

		// 设置窗口的大小
		this.setSize(Config.START_WINDOW_W, Config.START_WINDOW_H);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new StartView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// 判断进入游戏的按钮是否被按下
		if (e.getSource() == enterGameButton) {

			String input;

			do {
				input = JOptionPane.showInputDialog(this, "(姓名长度限制在6以内!)",
						"请输入玩家姓名", JOptionPane.QUESTION_MESSAGE);

				// 点击取消,默认退出游戏
				if (input == null)
					System.exit(EXIT_ON_CLOSE);

			} while (input.length() == 0 || input.length() > 6);

			// 获取玩家姓名
			PlayView.palyerName = new String(input);

			// 开启游戏界面
			new PlayView();

			// 隐藏当前窗口
			this.dispose();
		}
	}
}
