package a2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Sender {
	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame("Sender");
		f.setSize(700, 700);
		f.setLocation(400, 10);
		GridLayout layout = new GridLayout(0, 2);
		f.setLayout(layout);

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
		final JLabel timeout = new JLabel("Timeout ");
		timeout.setHorizontalAlignment(SwingConstants.LEFT);
		final TextField ipField = new TextField();
		final TextField portField = new TextField();
		final TextField portField2 = new TextField();
		final TextField fileField = new TextField();
		final TextField datagramField = new TextField();
		final TextField transmissionField = new TextField();
		final JButton transferButton = new JButton("TRANSFER");
		transferButton.setBackground(Color.GREEN);

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
		f.add(transferButton);

		f.setVisible(true);
	}
}
