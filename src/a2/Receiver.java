package a2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Receiver {
	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame("Erman how do you spell reciveeer");
		f.setSize(700, 400);
		f.setLocation(500, 10);
		f.setLocation(100, 10);
		GridLayout layout = new GridLayout(0,2);
		f.setLayout(layout);
		f.getContentPane().setBackground(Color.LIGHT_GRAY);
		
		
		
		
		final JLabel ip = new JLabel("IP Address of Reciver:");
		ip.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel portACK = new JLabel("Port # for ACKs:");
		portACK.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel portDATA = new JLabel("Port # for data:");
		portDATA.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel fileName = new JLabel("File name to send data to:");
		fileName.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel inOrder = new JLabel("Number of recieved in order packets:");
		inOrder.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel numRecv = new JLabel("0  ");
		numRecv.setHorizontalAlignment(SwingConstants.RIGHT);
		final JLabel mode = new JLabel("Transfer type: reliable");
		mode.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel conn = new JLabel("Connection status: not connected");
		conn.setHorizontalAlignment(SwingConstants.LEFT);
		
		final JButton modeButton = new JButton("Reliable/Unreliable");
		modeButton.setBackground(Color.GREEN);
		final JButton connect = new JButton("Connect/Disconnect");
		connect.setHorizontalAlignment(SwingConstants.CENTER);
		connect.setBackground(Color.YELLOW);
		
		final TextField ipField = new TextField();
		final TextField portACKField = new TextField();
		final TextField portDATAField = new TextField();
		final TextField fileField = new TextField();
		
		
		f.add(ip);
		f.add(ipField);
		f.add(portACK);
		f.add(portACKField);
		f.add(portDATA);
		f.add(portDATAField);
		f.add(fileName);
		f.add(fileField);
		f.add(inOrder);
		f.add(numRecv);
		f.add(mode);
		f.add(modeButton);
		f.add(conn);
		f.add(connect);
		
		f.setVisible(true);
	}
	
}

//vape