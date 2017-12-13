package com.cs.date.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cs.inter.Monthly;
import com.cs.inter.TongJi;
import com.cs.vic.utils.TransferHandle;

/*
 * 程序界面入口，其中打卡数据，调休放假安排，结果目录均可拖拽至对应文本框，
 * 调休放假配置文件为Json格式，示例如下，
 * {
 * 	"2017-09-30":true,
 * 	"2017-10-01":false
 * }
 * 	true代表需要上班，false代表不需上班，除特殊放假调休外不需要注明，
 * 一般没有特殊情况空着就行。	
 */

public class WorkTimeTools extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField Yuan;
	private JTextField date;
	private JTextField ResultDir;

	public static void main(String[] args) {
		JFrame frame = new WorkTimeTools();
		frame.setVisible(true);
	}

	private WorkTimeTools() {
		setTitle("WorkTimeTool");
		setSize(520, 280);
		setDefaultCloseOperation(3);
		setLocation(700, 200);
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(null);

		JLabel YuanLabel = new JLabel("打卡数据文件");
		YuanLabel.setBounds(10, 20, 150, 25);
		panel.add(YuanLabel);
		this.Yuan = new JTextField(50);
		this.Yuan.setBounds(150, 20, 300, 25);
		this.Yuan.setTransferHandler(new TransferHandle(this.Yuan));
		panel.add(this.Yuan);

		JLabel dateSpecialLabel = new JLabel("调休放假时间Json");
		dateSpecialLabel.setBounds(10, 70, 150, 25);
		panel.add(dateSpecialLabel);
		this.date = new JTextField(50);
		this.date.setBounds(150, 70, 300, 25);
		this.date.setTransferHandler(new TransferHandle(this.date));
		panel.add(this.date);

		JLabel FileLabel = new JLabel("XLS Result file directory:");
		FileLabel.setBounds(10, 120, 150, 25);
		panel.add(FileLabel);
		this.ResultDir = new JTextField(50);
		this.ResultDir.setText("C:/Users/Administrator/Desktop/WorkTime");
		this.ResultDir.setTransferHandler(new TransferHandle(this.ResultDir));
		this.ResultDir.setBounds(150, 120, 300, 25);
		panel.add(this.ResultDir);

		JButton genMouthOver = new JButton("genMouthOver");
		genMouthOver.setBounds(100, 200, 100, 25);
		genMouthOver.addActionListener(new GenMouthOverAction());
		panel.add(genMouthOver);

		JButton genWorkTime = new JButton("genWorkTime");
		genWorkTime.setBounds(300, 200, 100, 25);
		genWorkTime.addActionListener(new GenWorkButtonAction());
		panel.add(genWorkTime);
	}

	class GenWorkButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent actionevent) {
			new TongJi().domain(date.getText(), Yuan.getText(), ResultDir.getText());
		}
	}

	class GenMouthOverAction implements ActionListener {
		public void actionPerformed(ActionEvent actionevent) {
			new Monthly().domain(date.getText(), Yuan.getText(), ResultDir.getText());
		}
	}

}
