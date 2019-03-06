package a2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Sender {
	static int portNum;
	static int portNum2;
	static int UDPsize;
	static int transmissionTime;
	static int timeoutNumber;
	static InetAddress ipAddress;
	static String fileName = "";
	static boolean connected = false;
	static DatagramSocket socket;
	static BufferedReader in;
	static PrintWriter out;
	static DatagramPacket send;
	static DatagramPacket receive;
	static String str = "That'll do donkey, that'll do";
	static byte[] handShake = str.getBytes();

	class Helper extends TimerTask {
		public static int i = 0;

		@Override
		public void run() {
			System.out.println("Timer ran " + ++i);
		}
	}

	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame("Sender");
		f.setSize(700, 400);
		f.setLocation(400, 10);
		GridLayout layout = new GridLayout(0, 2);
		f.setLayout(layout);
		f.getContentPane().setBackground(Color.LIGHT_GRAY);

		final JLabel ip = new JLabel("IP Address of Reciever: ");
		ip.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel port = new JLabel("Port of Receiver:");
		port.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel port2 = new JLabel("Port of Sender: ");
		port2.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel file_name = new JLabel("Name of file to be transferred: ");
		file_name.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel UDPdatagram = new JLabel("Maximum size of UDP datagram: ");
		UDPdatagram.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel transmission = new JLabel("Total transmission time: ");
		transmission.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel timeout = new JLabel("Timeout: ");
		timeout.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel connectionStatus = new JLabel("Connection status: not connected ");
		connectionStatus.setHorizontalAlignment(SwingConstants.LEFT);
		final JLabel space = new JLabel(" ");
		space.setHorizontalAlignment(SwingConstants.RIGHT);
		final TextField ipField = new TextField();
		final TextField portField = new TextField();
		final TextField portField2 = new TextField();
		final TextField fileField = new TextField();
		final TextField datagramField = new TextField();
		final TextField transmissionField = new TextField();
		final TextField timeoutNumField = new TextField();
		final JButton transferButton = new JButton("TRANSFER");
		transferButton.setBackground(Color.YELLOW);
		final JButton connectButton = new JButton("CONNECT/DISCONNECT");
		connectButton.setBackground(Color.GREEN);

		f.add(ip);
		f.add(ipField);
		f.add(port);
		f.add(portField);
		f.add(port2);
		f.add(portField2);
		f.add(file_name);
		f.add(fileField);
		f.add(UDPdatagram);
		f.add(datagramField);
		f.add(transmission);
		f.add(transmissionField);
		f.add(timeout);
		f.add(timeoutNumField);
		f.add(connectionStatus);
		f.add(space);
		f.add(connectButton);
		f.add(transferButton);

		f.setVisible(true);

		connectButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (connected == false) {
					connectButton.setBackground(Color.RED);
					if (ipField.getText() != "") {
						try {
							ipAddress = InetAddress.getByName(ipField.getText());
						} catch (UnknownHostException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if (portField.getText().isEmpty() == false) {
						portNum = Integer.parseInt(portField.getText());
					}
					if (portField2.getText().isEmpty() == false) {
						portNum2 = Integer.parseInt(portField2.getText());
					}
					if (fileField.getText().isEmpty() == false) {
						fileName = fileField.getText();
					}
					if (datagramField.getText().isEmpty() == false) {
						UDPsize = Integer.parseInt(datagramField.getText());
					}
					if (transmissionField.getText().isEmpty() == false) {
						transmissionTime = Integer.parseInt(transmissionField.getText());
					}
					if (timeoutNumField.getText().isEmpty() == false) {
						timeoutNumber = Integer.parseInt(timeoutNumField.getText());
					}

					try {
						socket = new DatagramSocket(portNum2, ipAddress);
						send.setPort(portNum);
						send.setAddress(ipAddress);
						send.setData(handShake);
						socket.send(send);
						boolean receivedACK = false;
						int time = 0;
						while (receivedACK = false && time < timeoutNumber) {
							socket.receive(receive);
							if (receive.getData().toString().isEmpty() == true) {
								Thread.sleep(timeoutNumber / 10);
								time = time + timeoutNumber / 10;
							} else {
								receivedACK = true;
							}
						}
						connectionStatus.setText("Connection status: successfully connected");
						connected = true;

					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

				} else {
					connectionStatus.setText("Connection status: not connected");
					connectButton.setBackground(Color.GREEN);
					socket.close();
					connected = false;
				}
			}
		});

	}
}
