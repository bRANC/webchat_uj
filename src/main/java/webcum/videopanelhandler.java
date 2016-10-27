/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcum;

import com.github.sarxos.webcam.WebcamResolution;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import webcum.agent.StreamClientAgent;
import webcum.agent.ui.VideoPanel;
import webcum.handler.StreamFrameListener;

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
    
    VideoPanel get_vp() {
        return videopanel;
    }
    
    public void stop() {
        clientAgent.stop();
        videopanel.updateImage(null);
    }
    
    void connect(String ip, int port) {
        clientAgent.connect(new InetSocketAddress(ip, port));
    }
    
    class StreamFrameListenerIMPL implements StreamFrameListener {
        
        private volatile long count = 0;
        
        @Override
        public void onFrameReceived(BufferedImage image) {
            //logger.info("frame received :{}",count++);
            videopanel.updateImage(image);
        }
        
    }
}
