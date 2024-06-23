package Chatting_app;

import java.util.*;
import java.text.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener {
	JTextField text;
	static JPanel a1;
	static Box vertical = Box.createVerticalBox();

	static DataOutputStream dout;
	static JFrame f = new JFrame();

	Client() {

		f.setLayout(null);

		// top panel

		JPanel p1 = new JPanel();
		p1.setBackground(new Color(7, 94, 84));
		p1.setBounds(0, 0, 400, 60);
		p1.setLayout(null);
		f.add(p1);

		// back
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel back = new JLabel(i3);
		back.setBounds(10, 20, 25, 25);
		p1.add(back);

		// action on clicking back button
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				f.setVisible(false);// or System.exit(0);
				// System.exit(0);
			}
		});

		// DP
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/DP.png"));
		Image i8 = i7.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel dp = new JLabel(i9);
		dp.setBounds(45, 12, 40, 40);
		p1.add(dp);

		// video call

		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image i5 = i4.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel video = new JLabel(i6);
		video.setBounds(270, 17, 25, 25);
		p1.add(video);

		// audio call
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		JLabel phone = new JLabel(i12);
		phone.setBounds(320, 15, 30, 30);
		p1.add(phone);

		// 3 dot
		ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
		Image i14 = i13.getImage().getScaledInstance(17, 17, Image.SCALE_DEFAULT);
		ImageIcon i15 = new ImageIcon(i14);
		JLabel dot3 = new JLabel(i15);
		dot3.setBounds(360, 21, 17, 17);
		p1.add(dot3);

		// name
		JLabel name = new JLabel("Gaurav");
		name.setBounds(95, 12, 100, 18);
		name.setForeground(Color.white);
		name.setFont(new Font("SAN_SERIF", Font.BOLD, 17));
		p1.add(name);

		// last seen
		JLabel ls = new JLabel("Online");
		ls.setBounds(95, 33, 100, 18);
		ls.setForeground(Color.white);
		ls.setFont(new Font("SAN_SERIF", Font.BOLD, 12));
		p1.add(ls);

		// message area panel
		a1 = new JPanel();
		a1.setBounds(0, 60, 384, 462);
		f.add(a1);

		// text area
		text = new JTextField();
		text.setBounds(0, 522, 320, 40);
		text.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
		f.add(text);

		// send button
		JButton send = new JButton("Send");
		send.setBounds(320, 522, 65, 40);
		send.setBackground(new Color(7, 94, 84));
		send.setForeground(Color.white);
		send.addActionListener(this);
		send.setFont(new Font("SAN_SERIF", Font.PLAIN, 13));
		f.add(send);

		// frame
		f.setSize(400, 600);
		f.setLocation(900, 50);
		f.getContentPane().setBackground(Color.black);
		// setUndecorated(true); //remove the top bar
		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		// text in the message box is stored in the out
		try {
			String out = text.getText();
			// .add function doesn't take string as input or value. so
			// JLabel output= new JLabel(out);
			JPanel p2 = formatLabel(out);
			// p2.add(output);

			a1.setLayout(new BorderLayout());

			// new panel in the right side of a1
			JPanel right = new JPanel(new BorderLayout());
			right.add(p2, BorderLayout.LINE_END);

			vertical.add(right);
			vertical.add(Box.createVerticalStrut(20));
			a1.add(vertical, BorderLayout.PAGE_START);// page start is used for y axis i.e. 0

			// to reload the site after clicking the send button
			dout.writeUTF(out);
			text.setText("");
			f.repaint();
			f.invalidate();
			f.validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JPanel formatLabel(String out) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");
		output.setFont(new Font("Tahoma", Font.PLAIN, 16));
		output.setBackground(new Color(37, 211, 102));
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15, 10, 15, 30));
		panel.add(output);

		// time
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);

		// clear the text box after clicking send button

		return panel;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Client();

		try {
			Socket s = new Socket("127.0.0.1", 6001);
			DataInputStream din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());

			while (true) {

			//	a1.setLayout(new BorderLayout());

				String msg = din.readUTF();

				JPanel panel = formatLabel(msg);
				JPanel left = new JPanel(new BorderLayout());
				left.add(panel, BorderLayout.LINE_START);
			//	vertical.add(Box.createVerticalStrut(20));
			//	a1.add(vertical, BorderLayout.PAGE_START);
				vertical.add(left);

				f.validate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
