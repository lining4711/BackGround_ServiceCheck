package com.example.demo.notification;


import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastListener
{
	private int port;
	private String host;

	public MulticastListener(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void listen()
	{
		byte[] data = new byte[256];
		try
		{
			InetAddress ip = InetAddress.getByName(this.host);
			MulticastSocket ms = new MulticastSocket(this.port);
			
			// ���ÿͻ��˼������
			ms.joinGroup(ip);
			
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			// receive()��������������ȴ��ͻ��˷��͹�������Ϣ
			ms.receive(packet);
			
			// ������֮����Ϣ�ռ���packet��
			String message = new String(packet.getData(), 0, packet.getLength());
			
			System.out.println(message);
			ms.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void start()
	{
		int port = 10000;
		String host = "239.0.0.1";
		MulticastListener ml = new MulticastListener(host, port);
//		while (true)
//		{
			ml.listen();
//		}
	}
}