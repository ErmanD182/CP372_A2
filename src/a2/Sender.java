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
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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
	static String str = "That'll do donkey, that'll do";
	static byte[] handShake = str.getBytes();
	static byte[] handShake2 = new byte[1024];
	static DatagramPacket send = new DatagramPacket(handShake, UDPsize);

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
		final JLabel timeout = new JLabel("Timeout (milliseconds): ");
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
			@Override
			public void actionPerformed(ActionEvent e) {
				if (connected == false) {
					connectButton.setBackground(Color.RED);
					if (ipField.getText().isEmpty() == false) {
						try {
							ipAddress = InetAddress.getByName(ipField.getText());
						} catch (UnknownHostException e1) {
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
						// HANDSHAKING
						connectionStatus.setText("Connection status: connecting...");
						socket = new DatagramSocket(portNum2, ipAddress);
						send.setPort(portNum);
						send.setAddress(ipAddress);
						send.setLength(UDPsize);
						send.setData(handShake);
						socket.send(send);
						DatagramPacket receive = new DatagramPacket(handShake2, handShake2.length, ipAddress, portNum);
						socket.setSoTimeout(timeoutNumber);

						while (true) {
							try {
								socket.receive(receive);
								String rcvd = "rcvd from " + receive.getAddress() + ", " + receive.getPort() + ": "
										+ new String(receive.getData(), 0, receive.getLength());
								System.out.println(rcvd);
								connectionStatus.setText("Connection status: successfully connected");
								connected = true;
								break;
							} catch (SocketTimeoutException e1) {
								System.out.println("Timeout reached! Resending packet...");
								socket.send(send);
							}

						}
					} catch (IOException e1) {
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
