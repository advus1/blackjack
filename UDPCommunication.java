import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCommunication {

    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    public UDPCommunication(String ipAddress, int port) {
        try {
            this.address = InetAddress.getByName(ipAddress);
            this.port = port;
            this.socket = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        try {
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            return new String(packet.getData(), 0, packet.getLength());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        socket.close();
    }
}
