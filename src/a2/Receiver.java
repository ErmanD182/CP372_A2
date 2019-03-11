package a2;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
	static String UDPsize;
	static String dataStr;
	static boolean relilable = true;

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
		final JLabel mode = new JLabel("Transfer type: Reliable");
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
						// HANDSHAKING
						conn.setText("Connection status: connecting...");
						socket = new DatagramSocket(portD, ipAddress);
						received.setPort(portD);
						received.setAddress(ipAddress);
						connected = true;

						socket.receive(received);

						UDPsize = new String(received.getData(), received.getOffset(), received.getLength());

						r.setPort(portA);
						r.setAddress(ipAddress);
						r.setLength(Integer.parseInt(UDPsize));
						r.setData(bytes);
						socket.send(r);
						conn.setText("Connection status: connected");
						// GET THE FILE donkey
						boolean endOfFile = false;
						boolean zero = false;
						boolean prevSeg;
						StringBuffer buff;
						BufferedWriter out = new BufferedWriter(new FileWriter(fName));
						int numPackets = 0;
						if(relilable == true) {
							while (connected == true && endOfFile == false) {
								try {
									DatagramPacket received2 = new DatagramPacket(bytes2,
											bytes2.length - Integer.parseInt(UDPsize));
									socket.receive(received2);
									dataStr = new String(received2.getData(), received2.getOffset(), received2.getLength());
									buff = new StringBuffer(dataStr);
									if (dataStr.equals("EOF!@#$%^&*()") == true) {
										endOfFile = true;
	
									}
									if (dataStr.endsWith(" 0") == true) {
										prevSeg = zero;
										zero = true;
									} else {
										prevSeg = zero;
										zero = false;
									}
	
									r.setPort(portA);
									r.setAddress(ipAddress);
									if (prevSeg != zero) {
										if (zero == true) {
											ack = "Ack of segement number 0";
										} else {
											ack = "Ack of segement number 1";
										}
										if (endOfFile == false) {
											buff.delete(dataStr.length() - 1, dataStr.length());
											numPackets++;
											numRecv.setText(String.valueOf(numPackets));
											out.write(buff + "\n");
										}
										bytes = ack.getBytes();
										r.setData(bytes);
										socket.send(r);
									}
	
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
									System.out.println("ERROR: File not found");
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}else {
							int count = 0;
							while (connected == true && endOfFile == false) {
								try {
									DatagramPacket received2 = new DatagramPacket(bytes2,
											bytes2.length - Integer.parseInt(UDPsize));
									socket.receive(received2);
									dataStr = new String(received2.getData(), received2.getOffset(), received2.getLength());
									buff = new StringBuffer(dataStr);
									
									if(count != 10) {
										if (dataStr.equals("EOF!@#$%^&*()") == true) {
											endOfFile = true;
		
										}
										if (dataStr.endsWith(" 0") == true) {
											prevSeg = zero;
											zero = true;
										} else {
											prevSeg = zero;
											zero = false;
										}
		
										r.setPort(portA);
										r.setAddress(ipAddress);
										if (prevSeg != zero) {
											if (zero == true) {
												ack = "Ack of segement number 0";
											} else {
												ack = "Ack of segement number 1";
											}
											if (endOfFile == false) {
												buff.delete(dataStr.length() - 1, dataStr.length());
												numPackets++;
												numRecv.setText(String.valueOf(numPackets));
												count++;
												out.write(buff + "\n");
											}
											bytes = ack.getBytes();
											r.setData(bytes);
											socket.send(r);
										}
									}else {
										count = 0;
									}
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
									System.out.println("ERROR: File not found");
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
						conn.setText("Connection status: file received, disconnected.");
						connected = false;
						out.close();
						socket.close();

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		modeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(relilable == true) {
					relilable = false;
					mode.setText("Transfer type: Unreliable");
				}else {
					relilable = true;
					mode.setText("Transfer type: Reliable");
				}
				
			}
			
		});
	}

}
//vape//vape