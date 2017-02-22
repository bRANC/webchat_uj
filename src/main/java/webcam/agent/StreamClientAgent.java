package webcam.agent;

import java.awt.Dimension;
import java.net.SocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webcam.channel.StreamClientChannelPipelineFactory;
import webcam.handler.StreamClientListener;
import webcam.handler.StreamFrameListener;

public class StreamClientAgent implements IStreamClientAgent {

    protected final static Logger logger = LoggerFactory.getLogger(StreamClientAgent.class);
    protected final ClientBootstrap clientBootstrap;
    protected final StreamClientListener streamClientListener;
    protected final StreamFrameListener streamFrameListener;
    protected final Dimension dimension;
    protected Channel clientChannel;

    public StreamClientAgent(StreamFrameListener streamFrameListener,
            Dimension dimension) {
        super();
        this.dimension = dimension;
        this.clientBootstrap = new ClientBootstrap();
        this.clientBootstrap.setFactory(new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));
        this.streamFrameListener = streamFrameListener;
        this.streamClientListener = new StreamClientListenerIMPL();
        this.clientBootstrap.setPipelineFactory(
                new StreamClientChannelPipelineFactory(
                        streamClientListener,
                        streamFrameListener,
                        dimension));
    }

    @Override
    public void connect(SocketAddress streamServerAddress) {
        logger.info("going to connect to stream server :{}", streamServerAddress);

        clientBootstrap.setOption("connectTimeoutMillis", 10000);

        logger.info("timeout :{}", clientBootstrap.getOption("connectTimeoutMillis"));
        //clientBootstrap.setOption("tcpNoDelay", true);
        //clientBootstrap.setOption("keepAlive", true);
        clientBootstrap.connect(streamServerAddress);
    }

    public void dc() {
        try {
            clientChannel.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void stop() {
        //trihard
        try {
            clientChannel.close();
        } catch (Exception e) {
        }
        try {
            clientBootstrap.releaseExternalResources();
        } catch (Exception e) {
        }
    }

    protected class StreamClientListenerIMPL implements StreamClientListener {

        public boolean isconnect = false;

        public boolean isconn() {
            return isconnect;
        }

        @Override
        public void onConnected(Channel channel) {
            //	logger.info("stream connected to server at :{}",channel);
            clientChannel = channel;
            isconnect = true;
        }

        @Override
        public void onDisconnected(Channel channel) {
            //	logger.info("stream disconnected to server at :{}",channel);
            isconnect = false;
        }

        @Override
        public void onException(Channel channel, Throwable t) {
            //	logger.debug("exception at :{},exception :{}",channel,t);
            isconnect = false;
        }

    }

}
