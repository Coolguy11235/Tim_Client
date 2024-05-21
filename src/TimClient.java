import java.net.*;
import java.io.*;

public class TimClient {

    public static void main(String[] args)
            throws Exception {

// Default port number we are going to use
        int portnumber = 8080;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }

// Create a MulticastSocket
        MulticastSocket chatMulticastSocket = new MulticastSocket();

// Determine the IP address of a host, given the host name
        InetAddress group =
                InetAddress.getByName("225.4.5.6");

        // Joins a multicast group
        chatMulticastSocket.joinGroup(group);

        // Type in mathematical expression
        System.out.println("Type a mathematical expression: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg = br.readLine();

        // Send the message to Multicast address
        DatagramPacket data = new DatagramPacket(msg.getBytes(),
                msg.length(), group, portnumber);
        chatMulticastSocket.send(data);

        // Receive the response from the server
        byte[] buf = new byte[1024];
        DatagramPacket response = new DatagramPacket(buf, buf.length);
        chatMulticastSocket.receive(response);
        String result = new String(response.getData(), 0, response.getLength());

        // Show the result
        System.out.println("Response: " + result);

        // Close the socket
        chatMulticastSocket.leaveGroup(group);
        chatMulticastSocket.close();
    }
}