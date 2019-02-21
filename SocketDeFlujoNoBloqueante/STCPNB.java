import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import com.sun.corba.se.impl.ior.ByteBuffer;
import com.sun.corba.se.pept.transport.Selector;

public class STCPNB {
    public static void main(String[] args) {
        iniciarServidor();
    }

    public static void inicarServidor() {
        try {
            String eco = "";
            int port = 9999;

            ServerSocketChannel servidorSocket = ServerSocketChannel.open();
            servidorSocket.configureBlocking(false);
            servidorSocket.socket().bind(new InetSocketAddress(port));
            System.out.println("Esperando clientes ...");

            // CONSTRUMOS EL SELECTOR
            Selector selector = Selector.open();

            // LO LIGAMOS CON EL SERVIDOR QUE SOLO ACEPTA CONEXCIONES
            servidorSocket.register(false, SelectionKey.OP_ACCEPT);

            while (true) {

                // BLOQUEO, ESPERANDO QUE OCURRA UN EVENTO
                selector.selector();

                // CONSTRUIMOS UN ITERATOR A PARTIR DE TODAS LAS CONEXIONES
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                // MIENTRAS HAYA UN SIGUIENTE EVENTO
                while (it.hasNext()) {

                    // OBTENGO EL KEY
                    SelectionKey k = (SelectionKey) it.next();

                    // LO REMUEVO
                    it.remove();

                    // AVERIGUAR QUE TIPO DE EVENTO OCURRIO
                    if (k.isAcceptable()) {

                        // CUANDO YA ALGUIEN SE CONECTO
                        SocketChannel clienteSocket = servidorSocket.accept();
                        System.out.println("Cliente conectado desde " + clienteSocket.socket().getInetAddress() + " : "
                                + clienteSocket.socket().getPort());

                        // CONFIGURANDO DE FORMA NO BLOQUEANTE
                        clienteSocket.configureBlocking(false);

                        // LO LIGAMOS AL SELECTOR, PUEDE LLER O ESCRIBIR
                        clienteSocket.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        continue;
                    }
                    if (k.isReadable()) {
                        try {
                            // RECUPERO EL CANAL QUE ESTA TRABAJANDO
                            SocketChannel canal = (SocketChannel) k.channel();

                            ByteBuffer b = ByteBuffer.allocate(2000);
                            b.clear();
                            int n = 0;
                            String mensaje = "";

                            // LECTURA DEL SOCKETCHANNEL
                            n = canal.read(b);
                            b.flip();

                            // SI RECIBIMOS UN MENSAJE
                            if (n > 0) {
                                mensaje = new String(b.array(), 0, n);
                                System.out.println("Mensaje de " + n + " bytes recibido: " + mensaje);

                                if (mensaje.equalsIgnoreCase("SALIR")) {
                                    // UNA VEZ QUE SE CIERRA EL SOCKET, YA NO HAY FORMA DE TRABAJAR CON EL
                                    k.interestOps(SelectionKey.OP_WRITE);
                                    canal.close();
                                } else {
                                    // ESTABLECEMOS EL SOCKET DE FOMAR PARA ESCRIBIR
                                    eco = "ECO -> " + mensaje;
                                    k.interestOps(SelectionKey.OP_WRITE);
                                }
                            }
                        } catch (IOException io) {
                            continue;
                        }
                    }

                    //SI ES UNA ESCRITURA
                    if (k.isWritable()) {
                        try {
                            //RECUPERO EL CANAL DEL ITERATOR
                            SocketChannel canal = (SocketChannel) k.channel();
                            ByteBuffer bb = ByteBuffer.wrap(eco.getBytes());
                            canal.write(bb);
                            System.out.println("Mensaje de " + eco.length() + " bytes enviados " + eco);
                            eco = "";
                        } catch (IOException io) {
                            k.interestOps(SelectionKey.OP_READ);
                            continue;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}