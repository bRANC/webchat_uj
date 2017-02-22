/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package felulet.webcam.own;

import com.github.sarxos.webcam.WebcamResolution;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import org.jboss.netty.channel.Channel;
import webcam.agent.StreamClientAgent;
import webcam.agent.ui.VideoPanel;
import webcam.handler.StreamFrameListener;

/**
 *
 * @author branc
 */
public class videopanelhandler {

    VideoPanel videopanel;
    StreamClientAgent clientAgent;

    public videopanelhandler() {
        videopanel = new VideoPanel();
        clientAgent = new StreamClientAgent(new StreamFrameListenerIMPL(), WebcamResolution.VGA.getSize());
    }

    public VideoPanel get_vp() {
        return videopanel;
    }

    public void stop() {
        clientAgent.stop();
        videopanel.updateImage(null);
    }

    public void clear() {
        videopanel.stop();
        //videopanel.updateImage(null);
    }

    public void megy() {

    }

    String ip = "";
    int port = 0;

    public void connect(String ip, int port) {
        this.ip = ip;
        this.port = port;
        videopanel.megy();
        clientAgent.connect(new InetSocketAddress(ip, port));
    }

    public void connect() {
        if (!ip.isEmpty()) {
            videopanel.megy();
            clientAgent.connect(new InetSocketAddress(ip, port));
        }
    }

    public void dc() {
        clientAgent.dc();
    }

    class StreamFrameListenerIMPL implements StreamFrameListener {

        private volatile long count = 0;

        @Override
        public void onFrameReceived(BufferedImage image) {
            //logger.info("frame received :{}",count++);
            videopanel.updateImage(image);
        }
        public boolean isconnect = false;

        public void onDisconnected(Channel channel) {
            //	logger.info("stream disconnected to server at :{}",channel);
            isconnect = false;
        }

        public void onException(Channel channel, Throwable t) {
            //	logger.debug("exception at :{},exception :{}",channel,t);
            isconnect = false;
        }

    }
}
