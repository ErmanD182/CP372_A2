package a2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Receiver {
	static boolean connected = false;
	static InetAddress ipAddress;
	static int portA;
	static int portD;
	static String fName;
	static String ack = "Ack of segement number 0";
	static byte[] bytes = ack.getBytes();
	static byte[] bytes2 = new byte[1024];
	static DatagramSocket socket;
	static DatagramPacket r = new DatagramPacket(bytes, bytes.length);
	static DatagramPacket received = new DatagramPacket(bytes2, bytes2.length);

	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame("Receiver");
		f.setSize(700, 400);
		f.setLocation(500, 10);
		f.setLocation(100, 10);
		GridLayout layout = new GridLayout(0, 2);
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
		final JButton connectButton = new JButton("Connect/Disconnect");
		connectButton.setHorizontalAlignment(SwingConstants.CENTER);
		connectButton.setBackground(Color.YELLOW);

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
		f.add(connectButton);

		f.setVisible(true);

		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (connected == false) {
					if (ipField.getText().isEmpty() == false) {
						try {
							ipAddress = InetAddress.getByName(ipField.getText());
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if (portACKField.getText().isEmpty() == false) {
						portA = Integer.parseInt(portACKField.getText());
					}
					if (portDATAField.getText().isEmpty() == false) {
						portD = Integer.parseInt(portDATAField.getText());
					}
					if (fileField.getText().isEmpty() == false) {
						fName = fileField.getText();
					}

					try {
						socket = new DatagramSocket(portD, ipAddress);
						received.setPort(portD);
						received.setAddress(ipAddress);
						connected = true;

						socket.receive(received);
						System.out.println(new String(received.getData()));

						r.setPort(portA);
						r.setAddress(ipAddress);
						r.setData(bytes);
						socket.send(r);

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

	}

}
//vape